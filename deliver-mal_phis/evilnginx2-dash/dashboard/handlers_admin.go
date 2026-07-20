package dashboard

import (
	"net/http"
	"strconv"

	"github.com/gorilla/mux"
)

// ---- users ----------------------------------------------------------------

func (s *Server) handleListUsers(w http.ResponseWriter, r *http.Request) {
	users, err := s.store.ListUsers()
	if err != nil {
		writeError(w, http.StatusInternalServerError, "failed to list users")
		return
	}
	views := make([]userView, 0, len(users))
	for _, u := range users {
		views = append(views, toUserView(u))
	}
	writeJSON(w, http.StatusOK, map[string]interface{}{"users": views})
}

type createUserRequest struct {
	Username string `json:"username"`
	Password string `json:"password"`
	Role     string `json:"role"`
}

func (s *Server) handleCreateUser(w http.ResponseWriter, r *http.Request) {
	actor := userFromContext(r.Context())
	var req createUserRequest
	if err := decodeJSON(r, &req); err != nil {
		writeError(w, http.StatusBadRequest, "invalid request body")
		return
	}
	u, err := s.store.CreateUser(req.Username, req.Password, req.Role)
	if err != nil {
		writeError(w, http.StatusBadRequest, err.Error())
		return
	}
	s.store.Audit(actor.Username, "create_user", u.Username, u.Role, clientIP(r))
	writeJSON(w, http.StatusCreated, toUserView(u))
}

type updateUserRequest struct {
	Role     *string `json:"role"`
	Password *string `json:"password"`
	Disabled *bool   `json:"disabled"`
}

func (s *Server) handleUpdateUser(w http.ResponseWriter, r *http.Request) {
	actor := userFromContext(r.Context())
	username := mux.Vars(r)["username"]
	target, err := s.store.GetUser(username)
	if err != nil {
		writeError(w, http.StatusNotFound, "user not found")
		return
	}
	var req updateUserRequest
	if err := decodeJSON(r, &req); err != nil {
		writeError(w, http.StatusBadRequest, "invalid request body")
		return
	}

	// guard rails: don't let an admin lock themselves out
	if actor.Username == target.Username {
		if req.Disabled != nil && *req.Disabled {
			writeError(w, http.StatusBadRequest, "you cannot disable your own account")
			return
		}
		if req.Role != nil && *req.Role != RoleAdmin {
			writeError(w, http.StatusBadRequest, "you cannot demote your own account")
			return
		}
	}

	if req.Role != nil {
		if err := s.store.UpdateRole(username, *req.Role); err != nil {
			writeError(w, http.StatusBadRequest, err.Error())
			return
		}
		s.store.Audit(actor.Username, "update_user_role", username, *req.Role, clientIP(r))
	}
	if req.Password != nil {
		if err := s.store.UpdatePassword(username, *req.Password); err != nil {
			writeError(w, http.StatusBadRequest, err.Error())
			return
		}
		s.store.Audit(actor.Username, "reset_user_password", username, "", clientIP(r))
	}
	if req.Disabled != nil {
		if err := s.store.SetDisabled(username, *req.Disabled); err != nil {
			writeError(w, http.StatusBadRequest, err.Error())
			return
		}
		s.store.Audit(actor.Username, "set_user_disabled", username, strconv.FormatBool(*req.Disabled), clientIP(r))
	}

	updated, _ := s.store.GetUser(username)
	writeJSON(w, http.StatusOK, toUserView(updated))
}

func (s *Server) handleDeleteUser(w http.ResponseWriter, r *http.Request) {
	actor := userFromContext(r.Context())
	username := mux.Vars(r)["username"]
	if actor.Username == username {
		writeError(w, http.StatusBadRequest, "you cannot delete your own account")
		return
	}
	if _, err := s.store.GetUser(username); err != nil {
		writeError(w, http.StatusNotFound, "user not found")
		return
	}
	// never allow deleting the last admin
	users, _ := s.store.ListUsers()
	admins := 0
	for _, u := range users {
		if u.Role == RoleAdmin && !u.Disabled {
			admins++
		}
	}
	target, _ := s.store.GetUser(username)
	if target.Role == RoleAdmin && admins <= 1 {
		writeError(w, http.StatusBadRequest, "cannot delete the last admin account")
		return
	}
	if err := s.store.DeleteUser(username); err != nil {
		writeError(w, http.StatusInternalServerError, "failed to delete user")
		return
	}
	s.store.Audit(actor.Username, "delete_user", username, "", clientIP(r))
	writeJSON(w, http.StatusOK, map[string]string{"status": "ok"})
}

// ---- audit ----------------------------------------------------------------

func (s *Server) handleListAudit(w http.ResponseWriter, r *http.Request) {
	limit := 200
	if l := r.URL.Query().Get("limit"); l != "" {
		if n, err := strconv.Atoi(l); err == nil {
			limit = n
		}
	}
	entries, err := s.store.ListAudit(limit)
	if err != nil {
		writeError(w, http.StatusInternalServerError, "failed to load audit log")
		return
	}
	writeJSON(w, http.StatusOK, map[string]interface{}{"entries": entries})
}
