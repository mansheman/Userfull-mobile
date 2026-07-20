package dashboard

import (
	"context"
	"crypto/hmac"
	"crypto/sha256"
	"crypto/subtle"
	"encoding/base64"
	"encoding/json"
	"fmt"
	"net/http"
	"strings"
	"time"
)

// tokenTTL is how long an issued session token stays valid.
const tokenTTL = 12 * time.Hour

type ctxKey int

const userCtxKey ctxKey = 0

// claims is the JWT payload (HS256). Kept minimal and self-contained so no
// external JWT library is needed.
type claims struct {
	Sub  string `json:"sub"`  // username
	Role string `json:"role"` // role at issue time
	Iat  int64  `json:"iat"`  // issued at (unix)
	Exp  int64  `json:"exp"`  // expiry (unix)
}

func b64url(b []byte) string {
	return base64.RawURLEncoding.EncodeToString(b)
}

func b64urlDecode(s string) ([]byte, error) {
	return base64.RawURLEncoding.DecodeString(s)
}

// signToken produces a compact HS256 JWT for the given user.
func signToken(secret []byte, username, role string) (string, error) {
	now := time.Now().UTC()
	header := map[string]string{"alg": "HS256", "typ": "JWT"}
	hb, _ := json.Marshal(header)
	cl := claims{
		Sub:  username,
		Role: role,
		Iat:  now.Unix(),
		Exp:  now.Add(tokenTTL).Unix(),
	}
	cb, _ := json.Marshal(cl)
	signingInput := b64url(hb) + "." + b64url(cb)
	mac := hmac.New(sha256.New, secret)
	mac.Write([]byte(signingInput))
	sig := mac.Sum(nil)
	return signingInput + "." + b64url(sig), nil
}

// parseToken verifies the signature and expiry and returns the claims.
func parseToken(secret []byte, token string) (*claims, error) {
	parts := strings.Split(token, ".")
	if len(parts) != 3 {
		return nil, fmt.Errorf("malformed token")
	}
	signingInput := parts[0] + "." + parts[1]
	mac := hmac.New(sha256.New, secret)
	mac.Write([]byte(signingInput))
	expected := mac.Sum(nil)
	got, err := b64urlDecode(parts[2])
	if err != nil {
		return nil, fmt.Errorf("malformed signature")
	}
	if !hmac.Equal(expected, got) {
		return nil, fmt.Errorf("invalid signature")
	}
	cb, err := b64urlDecode(parts[1])
	if err != nil {
		return nil, fmt.Errorf("malformed claims")
	}
	var cl claims
	if err := json.Unmarshal(cb, &cl); err != nil {
		return nil, fmt.Errorf("malformed claims")
	}
	if time.Now().UTC().Unix() >= cl.Exp {
		return nil, fmt.Errorf("token expired")
	}
	return &cl, nil
}

// userFromContext returns the authenticated user attached by requireAuth.
func userFromContext(ctx context.Context) *User {
	u, _ := ctx.Value(userCtxKey).(*User)
	return u
}

// bearerToken extracts the token from the Authorization header.
func bearerToken(r *http.Request) string {
	h := r.Header.Get("Authorization")
	if strings.HasPrefix(strings.ToLower(h), "bearer ") {
		return strings.TrimSpace(h[7:])
	}
	return ""
}

// requireAuth wraps a handler, rejecting requests without a valid token and
// re-loading the user from the store on every request (so disabled accounts and
// role changes take effect immediately).
func (s *Server) requireAuth(next http.HandlerFunc) http.HandlerFunc {
	return func(w http.ResponseWriter, r *http.Request) {
		tok := bearerToken(r)
		if tok == "" {
			writeError(w, http.StatusUnauthorized, "missing authorization")
			return
		}
		// agent mode: a request bearing the engine's static agent token is
		// authenticated as a machine admin (used by the fleet dashboard).
		if s.agentToken != "" && subtle.ConstantTimeCompare([]byte(tok), []byte(s.agentToken)) == 1 {
			ctx := context.WithValue(r.Context(), userCtxKey, &User{Username: "agent", Role: RoleAdmin})
			next(w, r.WithContext(ctx))
			return
		}
		cl, err := parseToken(s.secret, tok)
		if err != nil {
			writeError(w, http.StatusUnauthorized, "invalid token")
			return
		}
		u, err := s.store.GetUser(cl.Sub)
		if err != nil || u.Disabled {
			writeError(w, http.StatusUnauthorized, "account unavailable")
			return
		}
		ctx := context.WithValue(r.Context(), userCtxKey, u)
		next(w, r.WithContext(ctx))
	}
}

// requireRole wraps a handler, requiring at least the given role. Must be nested
// inside requireAuth.
func (s *Server) requireRole(minRole string, next http.HandlerFunc) http.HandlerFunc {
	return s.requireAuth(func(w http.ResponseWriter, r *http.Request) {
		u := userFromContext(r.Context())
		if u == nil || roleRank(u.Role) < roleRank(minRole) {
			writeError(w, http.StatusForbidden, "insufficient privileges")
			return
		}
		next(w, r)
	})
}
