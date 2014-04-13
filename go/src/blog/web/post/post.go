package post

import ("time"
	"appengine/datastore"
	"appengine/user"
	"appengine"
	"net/http"
	"github.com/gorilla/mux"
	"text/template"
)
type Post struct{
	Title string
	Key string
	Body []byte
	Author user.User
	Date time.Time
	Id int
	Bstring string
}
var templates map[string]*template.Template

func init(){
	templates = parseTemplates("createpost.html", "viewpost.html")
}

func parseTemplates(filenames ...string) map[string]*template.Template {
	temps := make(map[string]*template.Template)
	for _, name := range filenames {
		temps[name] = template.Must(
			template.ParseFiles("templates/base.html", "web/post/templates/" + name))
	}
	return temps
}
func NewPost(title, key, body string, user user.User) *Post{
	return &Post {
		Title:title,
		Key: key,
		Body: []byte(body),
		Date: time.Now().Local(),
		Author: user,
	}
}

func createPost(w http.ResponseWriter, r *http.Request){
	c := appengine.NewContext(r)
	data := make(map[string]interface{})
	data["user"] = user.Current(c)
	var err error
	data["login_url"], err = user.LoginURL(c, r.URL.String())
	if err != nil {
		http.Error(w, err.Error(), http.StatusInternalServerError)
		return
	}

	template := templates["createpost.html"]
	if err := template.ExecuteTemplate(w, "base", data); err != nil {
		http.Error(w, err.Error(), http.StatusInternalServerError)
		return
	}
}

func addPost(w http.ResponseWriter, r *http.Request){
	c := appengine.NewContext(r)
	post := NewPost(r.FormValue("title"), r.FormValue("key"), 
		r.FormValue("body"), *user.Current(c))
	_, err := datastore.Put(c, datastore.NewIncompleteKey(c, "Post", nil), post)
	if err != nil {
		http.Error(w, err.Error(), http.StatusInternalServerError)
		return
	}
	time.Sleep(2 * time.Second)
	http.Redirect(w, r, "/post/" + r.FormValue("key") + "/", http.StatusFound)
}

func viewPost(w http.ResponseWriter, r *http.Request){
	c := appengine.NewContext(r)
	vars := mux.Vars(r)
	key := vars["key"]
	data := make(map[string]interface{})
	var err error
	data["user"] = user.Current(c)
	data["login_url"], err = user.LoginURL(c, r.URL.String())
	if err != nil {
		http.Error(w, err.Error(), http.StatusInternalServerError)
		return
	}

	q := datastore.NewQuery("Post").Filter("Key =", key).Limit(1)
	posts := make([]Post, 0,1)
	_, err = q.GetAll(c, &posts)
	if err != nil {
		http.Error(w, err.Error(), http.StatusInternalServerError)
		return
	}
	if len(posts) != 1 {
		http.Error(w, "Post not found", http.StatusInternalServerError)
		return
	}
	post := posts[0]
	post.Bstring = string(post.Body)
	data["post"] = post
	template := templates["viewpost.html"]
	if err := template.ExecuteTemplate(w, "base", data); err != nil {
		http.Error(w, err.Error(), http.StatusInternalServerError)
		return
	}
	
}
