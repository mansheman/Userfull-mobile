package dashboard

import (
	"encoding/json"
	"fmt"
	"net/url"
	"strings"
	"time"

	"github.com/tidwall/buntdb"

	"github.com/kgretzky/evilginx2/core"
)

// Engine is a registered evilginx engine the fleet dashboard manages over its
// agent API. Token is the per-engine agent token (secret — never serialized to
// API responses).
type Engine struct {
	Id         string `json:"id"`
	Name       string `json:"name"`
	URL        string `json:"url"` // base, e.g. http://10.66.0.2:8081
	Token      string `json:"-"`
	CreateTime int64  `json:"create_time"`
}

func engineKey(id string) string { return "engine:" + id }

// normalizeEngineURL validates and trims a trailing slash from the base URL.
func normalizeEngineURL(raw string) (string, error) {
	raw = strings.TrimSpace(raw)
	if raw == "" {
		return "", fmt.Errorf("engine url is required")
	}
	u, err := url.ParseRequestURI(raw)
	if err != nil || (u.Scheme != "http" && u.Scheme != "https") || u.Host == "" {
		return "", fmt.Errorf("invalid engine url (expected http(s)://host:port)")
	}
	return strings.TrimRight(raw, "/"), nil
}

func (s *Store) CreateEngine(name, rawURL, token string) (*Engine, error) {
	name = strings.TrimSpace(name)
	if name == "" {
		return nil, fmt.Errorf("engine name is required")
	}
	base, err := normalizeEngineURL(rawURL)
	if err != nil {
		return nil, err
	}
	if strings.TrimSpace(token) == "" {
		return nil, fmt.Errorf("engine token is required")
	}
	e := &Engine{
		Id:         core.GenRandomToken()[:12],
		Name:       name,
		URL:        base,
		Token:      strings.TrimSpace(token),
		CreateTime: time.Now().UTC().Unix(),
	}
	if err := s.putEngine(e); err != nil {
		return nil, err
	}
	return e, nil
}

func (s *Store) putEngine(e *Engine) error {
	type stored struct {
		Engine
		Token string `json:"token"`
	}
	b, err := json.Marshal(stored{Engine: *e, Token: e.Token})
	if err != nil {
		return err
	}
	return s.db.Update(func(tx *buntdb.Tx) error {
		_, _, err := tx.Set(engineKey(e.Id), string(b), nil)
		return err
	})
}

func decodeEngine(raw string) (*Engine, error) {
	var st struct {
		Engine
		Token string `json:"token"`
	}
	if err := json.Unmarshal([]byte(raw), &st); err != nil {
		return nil, err
	}
	e := st.Engine
	e.Token = st.Token
	return &e, nil
}

func (s *Store) GetEngine(id string) (*Engine, error) {
	var raw string
	err := s.db.View(func(tx *buntdb.Tx) error {
		v, err := tx.Get(engineKey(id))
		if err != nil {
			return err
		}
		raw = v
		return nil
	})
	if err != nil {
		return nil, fmt.Errorf("engine not found")
	}
	return decodeEngine(raw)
}

func (s *Store) ListEngines() ([]*Engine, error) {
	engines := []*Engine{}
	err := s.db.View(func(tx *buntdb.Tx) error {
		return tx.AscendKeys("engine:*", func(key, val string) bool {
			if e, err := decodeEngine(val); err == nil {
				engines = append(engines, e)
			}
			return true
		})
	})
	return engines, err
}

func (s *Store) UpdateEngine(id, name, rawURL, token string) (*Engine, error) {
	e, err := s.GetEngine(id)
	if err != nil {
		return nil, err
	}
	if strings.TrimSpace(name) != "" {
		e.Name = strings.TrimSpace(name)
	}
	if strings.TrimSpace(rawURL) != "" {
		base, err := normalizeEngineURL(rawURL)
		if err != nil {
			return nil, err
		}
		e.URL = base
	}
	if strings.TrimSpace(token) != "" {
		e.Token = strings.TrimSpace(token)
	}
	if err := s.putEngine(e); err != nil {
		return nil, err
	}
	return e, nil
}

func (s *Store) DeleteEngine(id string) error {
	return s.db.Update(func(tx *buntdb.Tx) error {
		_, err := tx.Delete(engineKey(id))
		return err
	})
}
