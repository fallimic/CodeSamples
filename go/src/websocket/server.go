package main

import (
	"code.google.com/p/go.net/websocket"
	"flag"
	"fmt"
	"log"
	"net/http"
	"text/template"
)

type connection struct {
	// The websocket connection.
	ws *websocket.Conn

	// Buffered channel of outbound messages.
	send chan message
	user string
}

type message struct {
	msg  string
	user string
}

func (c *connection) reader() {
	for {
		var msg message
		err := websocket.Message.Receive(c.ws, &msg.msg)
		if err != nil {
			break
		}
		msg.user = c.user
		h.broadcast <- msg
	}
	c.ws.Close()
}

func (c *connection) writer() {
	for message := range c.send {
		if message.user != "" {
			err := websocket.Message.Send(c.ws, fmt.Sprintf("From: %s", message.user))
			if err != nil {
				break
			}
		}
		err := websocket.Message.Send(c.ws, message.msg)
		if err != nil {
			break
		}
	}
	c.ws.Close()
}

func wsHandler(ws *websocket.Conn) {
	c := &connection{user: "None", send: make(chan message, 256), ws: ws}
	getUser(c)
	h.register <- c
	h.broadcast <- message{msg: fmt.Sprintf("%s joined the chat.", c.user), user: ""}
	defer func() { h.unregister <- c }()
	go c.writer()
	c.reader()
}

func handshake(con *websocket.Config, req *http.Request) error {
	return nil
}

func getUser(c *connection) {
	var user string
	err := websocket.Message.Receive(c.ws, &user)
	if err != nil {
		log.Fatal(err)
	}
	c.user = user
}

type hub struct {
	// Registered connections.
	connections map[*connection]bool

	// Inbound messages from the connections.
	broadcast chan message

	// Register requests from the connections.
	register chan *connection

	// Unregister requests from connections.
	unregister chan *connection
}

var h = hub{
	broadcast:   make(chan message),
	register:    make(chan *connection),
	unregister:  make(chan *connection),
	connections: make(map[*connection]bool),
}

func (h *hub) run() {
	for {
		select {
		case c := <-h.register:
			h.connections[c] = true
		case c := <-h.unregister:
			delete(h.connections, c)
			close(c.send)
		case m := <-h.broadcast:
			for c := range h.connections {
				select {
				case c.send <- m:
				default:
					delete(h.connections, c)
					close(c.send)
					go c.ws.Close()
				}
			}
		}
	}
}

var addr = flag.String("addr", ":8080", "http service address")
var homeTempl = template.Must(template.ParseFiles("home.html"))

func homeHandler(c http.ResponseWriter, req *http.Request) {
	homeTempl.Execute(c, req.Host)
}

func main() {
	flag.Parse()
	go h.run()
	http.HandleFunc("/", homeHandler)
	http.Handle("/ws", websocket.Server{Handler: wsHandler, Handshake: handshake})
	if err := http.ListenAndServe(*addr, nil); err != nil {
		log.Fatal("ListenAndServe:", err)
	}
}
