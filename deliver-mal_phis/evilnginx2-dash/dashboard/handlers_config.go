package dashboard

import (
	"net/http"
	"net/url"
	"strings"

	"github.com/gorilla/mux"
)

type generalPatch struct {
	Domain       *string `json:"domain"`
	ExternalIpv4 *string `json:"external_ipv4"`
	BindIpv4     *string `json:"bind_ipv4"`
	UnauthUrl    *string `json:"unauth_url"`
	HttpsPort    *int    `json:"https_port"`
	DnsPort      *int    `json:"dns_port"`
	Autocert     *bool   `json:"autocert"`
}

type blacklistPatch struct {
	Mode *string `json:"mode"`
}

type proxyPatch struct {
	Enabled  *bool   `json:"enabled"`
	Type     *string `json:"type"`
	Address  *string `json:"address"`
	Port     *int    `json:"port"`
	Username *string `json:"username"`
	Password *string `json:"password"`
}

type gophishPatch struct {
	AdminUrl    *string `json:"admin_url"`
	ApiKey      *string `json:"api_key"`
	InsecureTLS *bool   `json:"insecure_tls"`
}

type configPatch struct {
	General   *generalPatch   `json:"general"`
	Blacklist *blacklistPatch `json:"blacklist"`
	Proxy     *proxyPatch     `json:"proxy"`
	GoPhish   *gophishPatch   `json:"gophish"`
}

func validPort(p int) bool { return p >= 1 && p <= 65535 }

// handleUpdateConfig updates the full server configuration (general, blacklist,
// upstream proxy, gophish). All fields are optional. Admin only. Note: changing
// https_port, dns_port or bind_ipv4 requires an engine restart to take effect.
func (s *Server) handleUpdateConfig(w http.ResponseWriter, r *http.Request) {
	u := userFromContext(r.Context())
	var p configPatch
	if err := decodeJSON(r, &p); err != nil {
		writeError(w, http.StatusBadRequest, "invalid request body")
		return
	}

	if g := p.General; g != nil {
		if g.Domain != nil {
			d := strings.TrimSpace(strings.ToLower(*g.Domain))
			if d != "" && !looksLikeDomain(d) {
				writeError(w, http.StatusBadRequest, "invalid domain")
				return
			}
			s.cfg.SetBaseDomain(d)
		}
		if g.ExternalIpv4 != nil {
			ip := strings.TrimSpace(*g.ExternalIpv4)
			if ip != "" && !looksLikeIPv4(ip) {
				writeError(w, http.StatusBadRequest, "invalid external IPv4")
				return
			}
			s.cfg.SetServerExternalIP(ip)
		}
		if g.BindIpv4 != nil {
			ip := strings.TrimSpace(*g.BindIpv4)
			if ip != "" && !looksLikeIPv4(ip) {
				writeError(w, http.StatusBadRequest, "invalid bind IPv4")
				return
			}
			s.cfg.SetServerBindIP(ip)
		}
		if g.UnauthUrl != nil {
			ru := strings.TrimSpace(*g.UnauthUrl)
			if ru != "" {
				if _, err := url.ParseRequestURI(ru); err != nil {
					writeError(w, http.StatusBadRequest, "invalid unauth_url")
					return
				}
			}
			s.cfg.SetUnauthUrl(ru)
		}
		if g.HttpsPort != nil {
			if !validPort(*g.HttpsPort) {
				writeError(w, http.StatusBadRequest, "invalid https_port")
				return
			}
			s.cfg.SetHttpsPort(*g.HttpsPort)
		}
		if g.DnsPort != nil {
			if !validPort(*g.DnsPort) {
				writeError(w, http.StatusBadRequest, "invalid dns_port")
				return
			}
			s.cfg.SetDnsPort(*g.DnsPort)
		}
		if g.Autocert != nil {
			s.cfg.EnableAutocert(*g.Autocert)
		}
		s.store.Audit(u.Username, "update_general", "", "", clientIP(r))
	}

	if b := p.Blacklist; b != nil && b.Mode != nil {
		mode := strings.TrimSpace(*b.Mode)
		if !stringInSlice(mode, s.cfg.ExportSettings().BlacklistModes) {
			writeError(w, http.StatusBadRequest, "invalid blacklist mode")
			return
		}
		s.cfg.SetBlacklistMode(mode)
		s.store.Audit(u.Username, "set_blacklist_mode", mode, "", clientIP(r))
	}

	if pr := p.Proxy; pr != nil {
		cur := s.cfg.ExportSettings().Proxy
		if pr.Enabled != nil {
			cur.Enabled = *pr.Enabled
		}
		if pr.Type != nil {
			cur.Type = strings.TrimSpace(*pr.Type)
		}
		if pr.Address != nil {
			cur.Address = strings.TrimSpace(*pr.Address)
		}
		if pr.Port != nil {
			if *pr.Port != 0 && !validPort(*pr.Port) {
				writeError(w, http.StatusBadRequest, "invalid proxy port")
				return
			}
			cur.Port = *pr.Port
		}
		if pr.Username != nil {
			cur.Username = *pr.Username
		}
		if pr.Password != nil {
			cur.Password = *pr.Password
		}
		if err := s.cfg.ApplyProxyConfig(cur); err != nil {
			writeError(w, http.StatusBadRequest, err.Error())
			return
		}
		s.store.Audit(u.Username, "update_proxy", cur.Type, "", clientIP(r))
	}

	if gp := p.GoPhish; gp != nil {
		if gp.AdminUrl != nil {
			au := strings.TrimSpace(*gp.AdminUrl)
			if au != "" {
				if _, err := url.ParseRequestURI(au); err != nil {
					writeError(w, http.StatusBadRequest, "invalid gophish admin_url")
					return
				}
				s.cfg.SetGoPhishAdminUrl(au)
			}
		}
		if gp.ApiKey != nil {
			s.cfg.SetGoPhishApiKey(strings.TrimSpace(*gp.ApiKey))
		}
		if gp.InsecureTLS != nil {
			s.cfg.SetGoPhishInsecureTLS(*gp.InsecureTLS)
		}
		s.store.Audit(u.Username, "update_gophish", "", "", clientIP(r))
	}

	writeJSON(w, http.StatusOK, s.cfg.ExportSettings())
}

func stringInSlice(s string, list []string) bool {
	for _, v := range list {
		if v == s {
			return true
		}
	}
	return false
}

type hostnameRequest struct {
	Hostname string `json:"hostname"`
}

// handleSetPhishletHostname sets a phishlet's phishing hostname. Mirrors the
// terminal: setting the hostname disables the phishlet and refreshes certs, so
// the operator can then enable it cleanly.
func (s *Server) handleSetPhishletHostname(w http.ResponseWriter, r *http.Request) {
	u := userFromContext(r.Context())
	name := mux.Vars(r)["name"]
	var req hostnameRequest
	if err := decodeJSON(r, &req); err != nil {
		writeError(w, http.StatusBadRequest, "invalid request body")
		return
	}
	if err := s.cfg.SetPhishletHostname(name, strings.TrimSpace(strings.ToLower(req.Hostname))); err != nil {
		writeError(w, http.StatusBadRequest, err.Error())
		return
	}
	s.cfg.SetSiteDisabled(name)
	s.manageCertificates()
	s.store.Audit(u.Username, "set_phishlet_hostname", name, req.Hostname, clientIP(r))
	writeJSON(w, http.StatusOK, map[string]interface{}{"phishlets": s.cfg.ExportPhishlets()})
}

func looksLikeDomain(d string) bool {
	if len(d) < 3 || !strings.Contains(d, ".") || strings.HasPrefix(d, ".") || strings.HasSuffix(d, ".") {
		return false
	}
	for _, c := range d {
		if !(c >= 'a' && c <= 'z' || c >= '0' && c <= '9' || c == '.' || c == '-') {
			return false
		}
	}
	return true
}

func looksLikeIPv4(ip string) bool {
	parts := strings.Split(ip, ".")
	if len(parts) != 4 {
		return false
	}
	for _, p := range parts {
		if p == "" || len(p) > 3 {
			return false
		}
		n := 0
		for _, c := range p {
			if c < '0' || c > '9' {
				return false
			}
			n = n*10 + int(c-'0')
		}
		if n > 255 {
			return false
		}
	}
	return true
}
