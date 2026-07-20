package dashboard

import (
	"crypto/rand"
	"crypto/subtle"
	"encoding/base64"
	"encoding/json"
	"fmt"
	"strconv"
	"strings"
	"time"

	"github.com/tidwall/buntdb"
	"golang.org/x/crypto/pbkdf2"

	"crypto/sha256"
)

// Roles, in ascending order of privilege.
const (
	RoleViewer   = "viewer"   // read-only: dashboard, sessions, lures, phishlets
	RoleOperator = "operator" // viewer + mutate lures/phishlets, delete sessions
	RoleAdmin    = "admin"    // operator + user management
)

func ValidRole(r string) bool {
	switch r {
	case RoleViewer, RoleOperator, RoleAdmin:
		return true
	}
	return false
}

// roleRank is used for "at least this role" checks.
func roleRank(r string) int {
	switch r {
	case RoleAdmin:
		return 3
	case RoleOperator:
		return 2
	case RoleViewer:
		return 1
	}
	return 0
}

const (
	pbkdfIters  = 120000
	pbkdfKeyLen = 32
	saltLen     = 16
)

// User is a dashboard account. PassHash/Salt are base64-encoded.
type User struct {
	Username   string `json:"username"`
	PassHash   string `json:"-"`
	Salt       string `json:"-"`
	Role       string `json:"role"`
	Disabled   bool   `json:"disabled"`
	CreateTime int64  `json:"create_time"`
	LastLogin  int64  `json:"last_login"`
}

// AuditEntry records an action taken through the dashboard.
type AuditEntry struct {
	Id     int64  `json:"id"`
	Time   int64  `json:"time"`
	User   string `json:"user"`
	Action string `json:"action"`
	Target string `json:"target"`
	Detail string `json:"detail"`
	IP     string `json:"ip"`
}

// Store is the dashboard's own persistence (separate from evilginx's data.db).
// It holds accounts, the audit log and small settings such as the JWT secret.
// Backed by BuntDB to avoid adding new dependencies; kept behind this type so a
// different backend (e.g. SQLite) can be swapped in without touching callers.
type Store struct {
	db *buntdb.DB
}

func NewStore(path string) (*Store, error) {
	db, err := buntdb.Open(path)
	if err != nil {
		return nil, err
	}
	s := &Store{db: db}
	return s, nil
}

func (s *Store) Close() error {
	return s.db.Close()
}

// ---- settings -------------------------------------------------------------

func (s *Store) getSetting(key string) (string, error) {
	var val string
	err := s.db.View(func(tx *buntdb.Tx) error {
		v, err := tx.Get("setting:" + key)
		if err != nil {
			return err
		}
		val = v
		return nil
	})
	return val, err
}

func (s *Store) setSetting(key, val string) error {
	return s.db.Update(func(tx *buntdb.Tx) error {
		_, _, err := tx.Set("setting:"+key, val, nil)
		return err
	})
}

// JWTSecret returns the persisted signing secret, generating one on first use.
func (s *Store) JWTSecret() ([]byte, error) {
	v, err := s.getSetting("jwt_secret")
	if err == nil && v != "" {
		return base64.StdEncoding.DecodeString(v)
	}
	secret := make([]byte, 32)
	if _, err := rand.Read(secret); err != nil {
		return nil, err
	}
	if err := s.setSetting("jwt_secret", base64.StdEncoding.EncodeToString(secret)); err != nil {
		return nil, err
	}
	return secret, nil
}

// ---- password hashing -----------------------------------------------------

func hashPassword(password string) (hash, salt string, err error) {
	saltB := make([]byte, saltLen)
	if _, err = rand.Read(saltB); err != nil {
		return "", "", err
	}
	dk := pbkdf2.Key([]byte(password), saltB, pbkdfIters, pbkdfKeyLen, sha256.New)
	return base64.StdEncoding.EncodeToString(dk), base64.StdEncoding.EncodeToString(saltB), nil
}

func verifyPassword(password, hash, salt string) bool {
	saltB, err := base64.StdEncoding.DecodeString(salt)
	if err != nil {
		return false
	}
	want, err := base64.StdEncoding.DecodeString(hash)
	if err != nil {
		return false
	}
	dk := pbkdf2.Key([]byte(password), saltB, pbkdfIters, pbkdfKeyLen, sha256.New)
	return subtle.ConstantTimeCompare(dk, want) == 1
}

// ---- users ----------------------------------------------------------------

func userKey(username string) string {
	return "user:" + strings.ToLower(username)
}

func (s *Store) CreateUser(username, password, role string) (*User, error) {
	username = strings.TrimSpace(username)
	if username == "" {
		return nil, fmt.Errorf("username cannot be empty")
	}
	if !ValidRole(role) {
		return nil, fmt.Errorf("invalid role: %s", role)
	}
	if len(password) < 8 {
		return nil, fmt.Errorf("password must be at least 8 characters")
	}
	if _, err := s.GetUser(username); err == nil {
		return nil, fmt.Errorf("user '%s' already exists", username)
	}
	hash, salt, err := hashPassword(password)
	if err != nil {
		return nil, err
	}
	u := &User{
		Username:   username,
		PassHash:   hash,
		Salt:       salt,
		Role:       role,
		CreateTime: time.Now().UTC().Unix(),
	}
	if err := s.putUser(u); err != nil {
		return nil, err
	}
	return u, nil
}

func (s *Store) putUser(u *User) error {
	// persist with sensitive fields included
	type stored struct {
		User
		PassHash string `json:"pass_hash"`
		Salt     string `json:"salt"`
	}
	st := stored{User: *u, PassHash: u.PassHash, Salt: u.Salt}
	b, err := json.Marshal(st)
	if err != nil {
		return err
	}
	return s.db.Update(func(tx *buntdb.Tx) error {
		_, _, err := tx.Set(userKey(u.Username), string(b), nil)
		return err
	})
}

func (s *Store) GetUser(username string) (*User, error) {
	var raw string
	err := s.db.View(func(tx *buntdb.Tx) error {
		v, err := tx.Get(userKey(username))
		if err != nil {
			return err
		}
		raw = v
		return nil
	})
	if err != nil {
		return nil, fmt.Errorf("user not found")
	}
	var st struct {
		User
		PassHash string `json:"pass_hash"`
		Salt     string `json:"salt"`
	}
	if err := json.Unmarshal([]byte(raw), &st); err != nil {
		return nil, err
	}
	u := st.User
	u.PassHash = st.PassHash
	u.Salt = st.Salt
	return &u, nil
}

func (s *Store) ListUsers() ([]*User, error) {
	users := []*User{}
	err := s.db.View(func(tx *buntdb.Tx) error {
		return tx.AscendKeys("user:*", func(key, val string) bool {
			var st struct {
				User
				PassHash string `json:"pass_hash"`
				Salt     string `json:"salt"`
			}
			if err := json.Unmarshal([]byte(val), &st); err == nil {
				u := st.User
				users = append(users, &u)
			}
			return true
		})
	})
	return users, err
}

func (s *Store) UpdatePassword(username, password string) error {
	u, err := s.GetUser(username)
	if err != nil {
		return err
	}
	if len(password) < 8 {
		return fmt.Errorf("password must be at least 8 characters")
	}
	hash, salt, err := hashPassword(password)
	if err != nil {
		return err
	}
	u.PassHash = hash
	u.Salt = salt
	return s.putUser(u)
}

func (s *Store) UpdateRole(username, role string) error {
	if !ValidRole(role) {
		return fmt.Errorf("invalid role: %s", role)
	}
	u, err := s.GetUser(username)
	if err != nil {
		return err
	}
	u.Role = role
	return s.putUser(u)
}

func (s *Store) SetDisabled(username string, disabled bool) error {
	u, err := s.GetUser(username)
	if err != nil {
		return err
	}
	u.Disabled = disabled
	return s.putUser(u)
}

func (s *Store) TouchLogin(username string) error {
	u, err := s.GetUser(username)
	if err != nil {
		return err
	}
	u.LastLogin = time.Now().UTC().Unix()
	return s.putUser(u)
}

func (s *Store) DeleteUser(username string) error {
	return s.db.Update(func(tx *buntdb.Tx) error {
		_, err := tx.Delete(userKey(username))
		return err
	})
}

func (s *Store) CountUsers() (int, error) {
	n := 0
	err := s.db.View(func(tx *buntdb.Tx) error {
		return tx.AscendKeys("user:*", func(key, val string) bool {
			n++
			return true
		})
	})
	return n, err
}

// ---- audit log ------------------------------------------------------------

func (s *Store) nextAuditId() (int64, error) {
	var id int64 = 1
	err := s.db.Update(func(tx *buntdb.Tx) error {
		v, err := tx.Get("audit:seq")
		if err == nil {
			if n, perr := strconv.ParseInt(v, 10, 64); perr == nil {
				id = n + 1
			}
		}
		_, _, err = tx.Set("audit:seq", strconv.FormatInt(id, 10), nil)
		return err
	})
	return id, err
}

// Audit appends an entry to the audit log. Errors are intentionally swallowed by
// callers (auditing must never break a request), so it returns nothing.
func (s *Store) Audit(user, action, target, detail, ip string) {
	id, err := s.nextAuditId()
	if err != nil {
		return
	}
	e := AuditEntry{
		Id:     id,
		Time:   time.Now().UTC().Unix(),
		User:   user,
		Action: action,
		Target: target,
		Detail: detail,
		IP:     ip,
	}
	b, err := json.Marshal(e)
	if err != nil {
		return
	}
	// zero-pad the id so lexical key order matches numeric order
	key := fmt.Sprintf("auditlog:%012d", id)
	s.db.Update(func(tx *buntdb.Tx) error {
		_, _, err := tx.Set(key, string(b), nil)
		return err
	})
}

// ListAudit returns up to `limit` most recent audit entries (newest first).
func (s *Store) ListAudit(limit int) ([]*AuditEntry, error) {
	if limit <= 0 || limit > 1000 {
		limit = 200
	}
	entries := []*AuditEntry{}
	err := s.db.View(func(tx *buntdb.Tx) error {
		return tx.DescendKeys("auditlog:*", func(key, val string) bool {
			if len(entries) >= limit {
				return false
			}
			var e AuditEntry
			if err := json.Unmarshal([]byte(val), &e); err == nil {
				entries = append(entries, &e)
			}
			return true
		})
	})
	return entries, err
}
