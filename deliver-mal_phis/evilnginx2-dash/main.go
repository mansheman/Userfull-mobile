package main

import (
	"flag"
	"fmt"
	_log "log"
	"os"
	"os/signal"
	"os/user"
	"path/filepath"
	"regexp"
	"syscall"

	"github.com/caddyserver/certmagic"
	"github.com/kgretzky/evilginx2/core"
	"github.com/kgretzky/evilginx2/dashboard"
	"github.com/kgretzky/evilginx2/database"
	"github.com/kgretzky/evilginx2/log"
	"go.uber.org/zap"

	"github.com/fatih/color"
)

var phishlets_dir = flag.String("p", "", "Phishlets directory path")
var redirectors_dir = flag.String("t", "", "HTML redirector pages directory path")
var debug_log = flag.Bool("debug", false, "Enable debug output")
var developer_mode = flag.Bool("developer", false, "Enable developer mode (generates self-signed certificates for all hostnames)")
var cfg_dir = flag.String("c", "", "Configuration directory path")
var version_flag = flag.Bool("v", false, "Show version")
var dashboard_enabled = flag.Bool("dashboard", false, "Enable the web reporting dashboard")
var dashboard_addr = flag.String("dashboard-addr", "127.0.0.1:8080", "Listen address for the web dashboard (default loopback only)")
var agent_enabled = flag.Bool("agent", false, "Enable the engine agent API (token-auth control plane for a fleet dashboard)")
var agent_addr = flag.String("agent-addr", "127.0.0.1:8081", "Listen address for the agent API (bind to a WireGuard/private interface)")
var agent_token = flag.String("agent-token", "", "Static token for the agent API (else uses EVILGINX_AGENT_TOKEN, or is generated and printed)")
var headless = flag.Bool("headless", false, "Run without the interactive terminal (for systemd/headless servers); keeps the engine + dashboard running until SIGINT/SIGTERM")
var geoip_db = flag.String("geoip-db", "", "Path to a MaxMind GeoLite2-City/Country .mmdb for session IP geolocation (default: <config-dir>/GeoLite2-City.mmdb if present)")
var geoip_asn_db = flag.String("geoip-asn-db", "", "Path to a MaxMind GeoLite2-ASN .mmdb for session IP ASN lookup (default: <config-dir>/GeoLite2-ASN.mmdb if present)")

func joinPath(base_path string, rel_path string) string {
	var ret string
	if filepath.IsAbs(rel_path) {
		ret = rel_path
	} else {
		ret = filepath.Join(base_path, rel_path)
	}
	return ret
}

// resolveGeoIP returns the explicit flag value if set; otherwise it returns the
// first of the given default filenames that exists in cfg_dir, or "" if none.
func resolveGeoIP(flagVal, cfg_dir string, defaults ...string) string {
	if flagVal != "" {
		return flagVal
	}
	for _, name := range defaults {
		p := filepath.Join(cfg_dir, name)
		if _, err := os.Stat(p); err == nil {
			return p
		}
	}
	return ""
}

func showEvilginxProAd() {
	lred := color.New(color.FgHiRed)
	lyellow := color.New(color.FgHiYellow)
	white := color.New(color.FgHiWhite)
	message := fmt.Sprintf("%s %s: %s %s", lred.Sprint("Evilginx Pro"), white.Sprint("is finally out"), lyellow.Sprint("https://evilginx.com"), white.Sprint("(advanced phishing framework for red teams)"))
	log.Info("%s", message)
}

func showEvilginxMasteryAd() {
	lyellow := color.New(color.FgHiYellow)
	white := color.New(color.FgHiWhite)
	hcyan := color.New(color.FgHiCyan)
	message := fmt.Sprintf("%s: %s %s", hcyan.Sprint("Evilginx Mastery Course"), lyellow.Sprint("https://academy.breakdev.org/evilginx-mastery"), white.Sprint("(learn how to create phishlets)"))
	log.Info("%s", message)
}

func main() {
	flag.Parse()

	if *version_flag == true {
		log.Info("version: %s", core.VERSION)
		return
	}

	exe_path, _ := os.Executable()
	exe_dir := filepath.Dir(exe_path)

	core.Banner()
	showEvilginxProAd()
	showEvilginxMasteryAd()

	_log.SetOutput(log.NullLogger().Writer())
	certmagic.Default.Logger = zap.NewNop()
	certmagic.DefaultACME.Logger = zap.NewNop()

	if *phishlets_dir == "" {
		*phishlets_dir = joinPath(exe_dir, "./phishlets")
		if _, err := os.Stat(*phishlets_dir); os.IsNotExist(err) {
			*phishlets_dir = "/usr/share/evilginx/phishlets/"
			if _, err := os.Stat(*phishlets_dir); os.IsNotExist(err) {
				log.Fatal("you need to provide the path to directory where your phishlets are stored: ./evilginx -p <phishlets_path>")
				return
			}
		}
	}
	if *redirectors_dir == "" {
		*redirectors_dir = joinPath(exe_dir, "./redirectors")
		if _, err := os.Stat(*redirectors_dir); os.IsNotExist(err) {
			*redirectors_dir = "/usr/share/evilginx/redirectors/"
			if _, err := os.Stat(*redirectors_dir); os.IsNotExist(err) {
				*redirectors_dir = joinPath(exe_dir, "./redirectors")
			}
		}
	}
	if _, err := os.Stat(*phishlets_dir); os.IsNotExist(err) {
		log.Fatal("provided phishlets directory path does not exist: %s", *phishlets_dir)
		return
	}
	if _, err := os.Stat(*redirectors_dir); os.IsNotExist(err) {
		os.MkdirAll(*redirectors_dir, os.FileMode(0700))
	}

	log.DebugEnable(*debug_log)
	if *debug_log {
		log.Info("debug output enabled")
	}

	phishlets_path := *phishlets_dir
	log.Info("loading phishlets from: %s", phishlets_path)

	if *cfg_dir == "" {
		usr, err := user.Current()
		if err != nil {
			log.Fatal("%v", err)
			return
		}
		*cfg_dir = filepath.Join(usr.HomeDir, ".evilginx")
	}

	config_path := *cfg_dir
	log.Info("loading configuration from: %s", config_path)

	err := os.MkdirAll(*cfg_dir, os.FileMode(0700))
	if err != nil {
		log.Fatal("%v", err)
		return
	}

	crt_path := joinPath(*cfg_dir, "./crt")

	cfg, err := core.NewConfig(*cfg_dir, "")
	if err != nil {
		log.Fatal("config: %v", err)
		return
	}
	cfg.SetRedirectorsDir(*redirectors_dir)

	db, err := database.NewDatabase(filepath.Join(*cfg_dir, "data.db"))
	if err != nil {
		log.Fatal("database: %v", err)
		return
	}

	bl, err := core.NewBlacklist(filepath.Join(*cfg_dir, "blacklist.txt"))
	if err != nil {
		log.Error("blacklist: %s", err)
		return
	}

	files, err := os.ReadDir(phishlets_path)
	if err != nil {
		log.Fatal("failed to list phishlets directory '%s': %v", phishlets_path, err)
		return
	}
	for _, f := range files {
		if !f.IsDir() {
			pr := regexp.MustCompile(`([a-zA-Z0-9\-\.]*)\.yaml`)
			rpname := pr.FindStringSubmatch(f.Name())
			if rpname == nil || len(rpname) < 2 {
				continue
			}
			pname := rpname[1]
			if pname != "" {
				pl, err := core.NewPhishlet(pname, filepath.Join(phishlets_path, f.Name()), nil, cfg)
				if err != nil {
					log.Error("failed to load phishlet '%s': %v", f.Name(), err)
					continue
				}
				cfg.AddPhishlet(pname, pl)
			}
		}
	}
	cfg.LoadSubPhishlets()
	cfg.CleanUp()

	ns, _ := core.NewNameserver(cfg)
	ns.Start()

	crt_db, err := core.NewCertDb(crt_path, cfg, ns)
	if err != nil {
		log.Fatal("certdb: %v", err)
		return
	}

	hp, _ := core.NewHttpProxy(cfg.GetServerBindIP(), cfg.GetHttpsPort(), cfg, crt_db, db, bl, *developer_mode)
	hp.Start()

	// Resolve optional GeoIP databases: explicit flag, else auto-detect the
	// standard filenames in the config dir.
	geoCityPath := resolveGeoIP(*geoip_db, *cfg_dir, "GeoLite2-City.mmdb", "GeoLite2-Country.mmdb")
	geoASNPath := resolveGeoIP(*geoip_asn_db, *cfg_dir, "GeoLite2-ASN.mmdb")

	if *dashboard_enabled {
		dash, err := dashboard.New(dashboard.Config{
			Addr:          *dashboard_addr,
			StorePath:     filepath.Join(*cfg_dir, "dashboard.db"),
			PhishletsDir:  phishlets_path,
			Version:       core.VERSION,
			GeoIPCityPath: geoCityPath,
			GeoIPASNPath:  geoASNPath,
		}, cfg, db, hp, bl)
		if err != nil {
			log.Error("dashboard: %v", err)
		} else {
			go dash.Start()
		}
	}

	if *agent_enabled {
		token := *agent_token
		if token == "" {
			token = os.Getenv("EVILGINX_AGENT_TOKEN")
		}
		generated := false
		if token == "" {
			token = core.GenRandomToken()
			generated = true
		}
		agent, err := dashboard.New(dashboard.Config{
			Addr:          *agent_addr,
			StorePath:     filepath.Join(*cfg_dir, "agent.db"),
			PhishletsDir:  phishlets_path,
			Version:       core.VERSION,
			AgentToken:    token,
			GeoIPCityPath: geoCityPath,
			GeoIPASNPath:  geoASNPath,
		}, cfg, db, hp, bl)
		if err != nil {
			log.Error("agent: %v", err)
		} else {
			if generated {
				log.Important("agent: generated API token (set EVILGINX_AGENT_TOKEN to control it):")
				log.Important("agent token -> %s", token)
			}
			log.Important("agent: control API enabled on %s", *agent_addr)
			go agent.Start()
		}
	}

	if *headless {
		log.Important("running headless: interactive terminal disabled; engine + dashboard active")
		log.Important("manage the engine via the dashboard; stop with SIGINT/SIGTERM (systemctl stop)")
		sig := make(chan os.Signal, 1)
		signal.Notify(sig, syscall.SIGINT, syscall.SIGTERM)
		<-sig
		log.Important("shutting down")
		return
	}

	t, err := core.NewTerminal(hp, cfg, crt_db, db, *developer_mode)
	if err != nil {
		log.Fatal("%v", err)
		return
	}

	t.DoWork()
}
