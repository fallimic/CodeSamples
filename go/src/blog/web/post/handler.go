package post

import (
	"net/http"
	"github.com/gorilla/mux"
)

func Handler() http.Handler {
	s := mux.NewRouter().PathPrefix("/post/").Subrouter()
	s.HandleFunc("/", addPost).Methods("POST")
	s.HandleFunc("/create/", createPost).Methods("GET")
	s.HandleFunc("/{key}/", viewPost).Methods("GET")
	return s
}
