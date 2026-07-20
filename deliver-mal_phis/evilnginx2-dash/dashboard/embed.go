package dashboard

import (
	"embed"
	"io"
	"io/fs"
	"net/http"
	"path"
	"strings"
)

// distFS holds the compiled frontend. The `all:` prefix includes files that
// start with '_' or '.' (Vite emits an `assets` dir; this is just future-proof).
//
//go:embed all:web/dist
var distFS embed.FS

// spaHandler serves the embedded single-page app, falling back to index.html for
// any path that doesn't map to a real asset (client-side routing).
func spaHandler() http.Handler {
	sub, err := fs.Sub(distFS, "web/dist")
	if err != nil {
		// Should never happen: embed guarantees the dir exists at build time.
		return http.HandlerFunc(func(w http.ResponseWriter, r *http.Request) {
			http.Error(w, "frontend not built", http.StatusInternalServerError)
		})
	}
	fileServer := http.FileServer(http.FS(sub))

	return http.HandlerFunc(func(w http.ResponseWriter, r *http.Request) {
		cleaned := strings.TrimPrefix(path.Clean(r.URL.Path), "/")
		if cleaned == "" {
			cleaned = "index.html"
		}
		if f, err := sub.Open(cleaned); err == nil {
			f.Close()
			fileServer.ServeHTTP(w, r)
			return
		}
		// fall back to index.html for SPA routes
		index, err := sub.Open("index.html")
		if err != nil {
			http.Error(w, "frontend not built", http.StatusInternalServerError)
			return
		}
		defer index.Close()
		w.Header().Set("Content-Type", "text/html; charset=utf-8")
		io.Copy(w, index)
	})
}
