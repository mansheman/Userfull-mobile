package dashboard

import (
	"net/http"
)

func (s *Server) handleHealth(w http.ResponseWriter, r *http.Request) {
	writeJSON(w, http.StatusOK, map[string]string{"status": "ok", "version": s.version})
}

type loginRequest struct {
	Username string `json:"username"`
	Password string `json:"password"`
}

type loginResponse struct {
	Token string   `json:"token"`
	User  userView `json:"user"`
}

type userView struct {
	Username   string `json:"username"`
	Role       string `json:"role"`
	Disabled   bool   `json:"disabled"`
	CreateTime int64  `json:"create_time"`
	LastLogin  int64  `json:"last_login"`
}

func toUserView(u *User) userView {
	return userView{
		Username:   u.Username,
		Role:       u.Role,
		Disabled:   u.Disabled,
		CreateTime: u.CreateTime,
		LastLogin:  u.LastLogin,
	}
}

func (s *Server) handleLogin(w http.ResponseWriter, r *http.Request) {
	var req loginRequest
	if err := decodeJSON(r, &req); err != nil {
		writeError(w, http.StatusBadRequest, "invalid request body")
		return
	}
	u, err := s.store.GetUser(req.Username)
	if err != nil || u.Disabled || !verifyPassword(req.Password, u.PassHash, u.Salt) {
		// uniform error to avoid user enumeration
		s.store.Audit(req.Username, "login_failed", "", "", clientIP(r))
		writeError(w, http.StatusUnauthorized, "invalid credentials")
		return
	}
	token, err := signToken(s.secret, u.Username, u.Role)
	if err != nil {
		writeError(w, http.StatusInternalServerError, "could not issue token")
		return
	}
	s.store.TouchLogin(u.Username)
	s.store.Audit(u.Username, "login", "", "", clientIP(r))
	writeJSON(w, http.StatusOK, loginResponse{Token: token, User: toUserView(u)})
}

func (s *Server) handleMe(w http.ResponseWriter, r *http.Request) {
	u := userFromContext(r.Context())
	writeJSON(w, http.StatusOK, toUserView(u))
}

type changePasswordRequest struct {
	OldPassword string `json:"old_password"`
	NewPassword string `json:"new_password"`
}

func (s *Server) handleChangeOwnPassword(w http.ResponseWriter, r *http.Request) {
	u := userFromContext(r.Context())
	var req changePasswordRequest
	if err := decodeJSON(r, &req); err != nil {
		writeError(w, http.StatusBadRequest, "invalid request body")
		return
	}
	if !verifyPassword(req.OldPassword, u.PassHash, u.Salt) {
		writeError(w, http.StatusForbidden, "current password is incorrect")
		return
	}
	if err := s.store.UpdatePassword(u.Username, req.NewPassword); err != nil {
		writeError(w, http.StatusBadRequest, err.Error())
		return
	}
	s.store.Audit(u.Username, "change_password", u.Username, "", clientIP(r))
	writeJSON(w, http.StatusOK, map[string]string{"status": "ok"})
}
