package web

import (
	"github.com/gorilla/mux"
	"net/http"
	"appengine"
	"appengine/user"
	"appengine/datastore"
	"text/template"
	"web/post"
)

func init(){
	r := mux.NewRouter()
	r.HandleFunc("/", Index)
	r.HandleFunc("/{path:.*}", notFound)
	http.Handle("/", r)
	http.Handle("/post/", post.Handler())
	templates = parseTemplates("index.html")
}
var templates map[string]*template.Template

func notFound(w http.ResponseWriter, r *http.Request) {
	w.WriteHeader(http.StatusNotFound)
	w.Write([]byte("Page not here, yo!"))
}
func parseTemplates(filenames ...string) map[string]*template.Template{
	templates := make(map[string]*template.Template)
	for _, name := range filenames {
		templates[name] = template.Must(template.ParseFiles("templates/base.html",
			"templates/" + name))
	}
	return templates
}

func Index(w http.ResponseWriter, r *http.Request){
	c := appengine.NewContext(r)
	q := datastore.NewQuery("Post").Order("-Date").Limit(5)
	postsQ := make([]post.Post, 0, 5)
	if _, err := q.GetAll(c, &postsQ); err != nil {
		http.Error(w, err.Error(), http.StatusInternalServerError)
		return
	}
	data := make(map[string]interface{})

	posts := make([]interface{}, len(postsQ), 5)
	for i, post := range postsQ {
		if len(post.Body) > 2000 {
			post.Bstring = string(post.Body[0:2000]) + "...<a href='/post/" + post.Key + "/'> See more</a>"
		} else {
			post.Bstring = string(post.Body)
		}
		postMap := make(map[string]interface{})
		postMap["post"] = post
		postMap["date"] = post.Date.Format("Jan 2, 2006 at 3:02pm")
		posts[i] = postMap
	}
	data["posts"] = posts
	data["user"] = user.Current(c)
	
	var err error
	data["login_url"], err = user.LoginURL(c, r.URL.String())
	if err != nil {
		http.Error(w, err.Error(), http.StatusInternalServerError)
		return
	}
	
	template := templates["index.html"]
	if err := template.ExecuteTemplate(w,"base", data); err != nil {
		http.Error(w, err.Error(), http.StatusInternalServerError)
		return
	}
}
