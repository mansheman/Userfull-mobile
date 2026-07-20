package dashboard

// Minimal, dependency-free reader for the MaxMind DB (.mmdb) binary format, used
// to enrich captured-session IPs with geolocation for the dashboard. It supports
// the GeoLite2-City / GeoLite2-Country and GeoLite2-ASN databases.
//
// We deliberately hand-roll the format (per the project's zero-new-Go-deps rule)
// rather than pull in oschwald/maxminddb-golang. The reader is intentionally
// read-only and defensive: any malformed input degrades to "no result" instead
// of panicking (see GeoIP.Lookup), so a bad DB file can never take down the
// dashboard. Format reference: https://maxmind.github.io/MaxMind-DB/

import (
	"encoding/binary"
	"fmt"
	"math"
	"net"
	"os"
	"sync"

	"github.com/kgretzky/evilginx2/log"
)

// GeoInfo is the geolocation attached to a session IP (all fields optional).
type GeoInfo struct {
	CountryCode string  `json:"country_code,omitempty"`
	Country     string  `json:"country,omitempty"`
	City        string  `json:"city,omitempty"`
	Lat         float64 `json:"lat,omitempty"`
	Lon         float64 `json:"lon,omitempty"`
	ASN         uint32  `json:"asn,omitempty"`
	ASOrg       string  `json:"as_org,omitempty"`
}

func (g *GeoInfo) empty() bool {
	return g.CountryCode == "" && g.Country == "" && g.City == "" &&
		g.Lat == 0 && g.Lon == 0 && g.ASN == 0 && g.ASOrg == ""
}

// GeoIP wraps an optional City/Country DB and an optional ASN DB, with a small
// per-IP result cache. A nil *GeoIP is valid and returns no data.
type GeoIP struct {
	city *mmdb
	asn  *mmdb

	mu    sync.RWMutex
	cache map[string]*GeoInfo
}

// OpenGeoIP opens the given .mmdb files (either path may be empty). It returns
// nil — the feature simply stays off — if neither DB can be opened.
func OpenGeoIP(cityPath, asnPath string) *GeoIP {
	g := &GeoIP{cache: make(map[string]*GeoInfo)}
	if cityPath != "" {
		if m, err := openMMDB(cityPath); err == nil {
			g.city = m
			log.Info("geoip: loaded city/country database: %s (%s)", cityPath, m.dbType)
		} else {
			log.Warning("geoip: could not load %s: %v", cityPath, err)
		}
	}
	if asnPath != "" {
		if m, err := openMMDB(asnPath); err == nil {
			g.asn = m
			log.Info("geoip: loaded ASN database: %s (%s)", asnPath, m.dbType)
		} else {
			log.Warning("geoip: could not load %s: %v", asnPath, err)
		}
	}
	if g.city == nil && g.asn == nil {
		return nil
	}
	return g
}

// Lookup resolves an IP (with or without a :port / [v6]:port suffix) to GeoInfo,
// or nil if there's no data or the address is private/loopback. It never panics:
// a malformed DB recovers to nil.
func (g *GeoIP) Lookup(addr string) (gi *GeoInfo) {
	if g == nil {
		return nil
	}
	defer func() {
		if recover() != nil {
			gi = nil
		}
	}()

	host := addr
	if h, _, err := net.SplitHostPort(addr); err == nil {
		host = h
	}
	ip := net.ParseIP(host)
	if ip == nil || ip.IsLoopback() || ip.IsPrivate() || ip.IsUnspecified() {
		return nil
	}

	g.mu.RLock()
	if c, ok := g.cache[host]; ok {
		g.mu.RUnlock()
		return c
	}
	g.mu.RUnlock()

	info := &GeoInfo{}
	if g.city != nil {
		if rec := g.city.lookup(ip); rec != nil {
			if c, ok := rec["country"].(map[string]interface{}); ok {
				if iso, ok := c["iso_code"].(string); ok {
					info.CountryCode = iso
				}
				info.Country = nameEN(c["names"])
			}
			if c, ok := rec["city"].(map[string]interface{}); ok {
				info.City = nameEN(c["names"])
			}
			if loc, ok := rec["location"].(map[string]interface{}); ok {
				if lat, ok := loc["latitude"].(float64); ok {
					info.Lat = lat
				}
				if lon, ok := loc["longitude"].(float64); ok {
					info.Lon = lon
				}
			}
		}
	}
	if g.asn != nil {
		if rec := g.asn.lookup(ip); rec != nil {
			if n, ok := rec["autonomous_system_number"].(uint64); ok {
				info.ASN = uint32(n)
			}
			if o, ok := rec["autonomous_system_organization"].(string); ok {
				info.ASOrg = o
			}
		}
	}

	if info.empty() {
		info = nil
	}
	g.mu.Lock()
	g.cache[host] = info
	g.mu.Unlock()
	return info
}

func nameEN(v interface{}) string {
	if names, ok := v.(map[string]interface{}); ok {
		if en, ok := names["en"].(string); ok {
			return en
		}
	}
	return ""
}

// ---- mmdb file ------------------------------------------------------------

type mmdb struct {
	data      []byte
	dbType    string
	nodeCount uint32
	recSize   uint32 // 24, 28, or 32
	nodeBytes uint32 // recSize / 4
	ipVersion uint32
	dataStart uint32 // nodeCount*nodeBytes + 16 (16-byte separator)
	ipv4Start uint32
}

var mmdbMarker = []byte("\xab\xcd\xefMaxMind.com")

func openMMDB(path string) (*mmdb, error) {
	data, err := os.ReadFile(path)
	if err != nil {
		return nil, err
	}
	// metadata lives after the last occurrence of the marker
	idx := lastIndex(data, mmdbMarker)
	if idx < 0 {
		return nil, fmt.Errorf("not a MaxMind DB: marker not found")
	}
	metaStart := uint32(idx + len(mmdbMarker))
	meta, _ := decodeValue(data, metaStart, metaStart)
	mm, ok := meta.(map[string]interface{})
	if !ok {
		return nil, fmt.Errorf("invalid metadata")
	}
	m := &mmdb{data: data}
	m.nodeCount = toU32(mm["node_count"])
	m.recSize = toU32(mm["record_size"])
	m.ipVersion = toU32(mm["ip_version"])
	if s, ok := mm["database_type"].(string); ok {
		m.dbType = s
	}
	if m.recSize != 24 && m.recSize != 28 && m.recSize != 32 {
		return nil, fmt.Errorf("unsupported record size %d", m.recSize)
	}
	m.nodeBytes = m.recSize / 4
	m.dataStart = m.nodeCount*m.nodeBytes + 16
	if m.dataStart > uint32(len(data)) {
		return nil, fmt.Errorf("corrupt search tree")
	}
	m.computeIPv4Start()
	return m, nil
}

// computeIPv4Start finds the tree node reached after 96 zero bits, where IPv4
// addresses live inside an IPv6 database.
func (m *mmdb) computeIPv4Start() {
	if m.ipVersion == 4 {
		m.ipv4Start = 0
		return
	}
	node := uint32(0)
	for i := 0; i < 96 && node < m.nodeCount; i++ {
		node = m.readRecord(node, false)
	}
	m.ipv4Start = node
}

func (m *mmdb) readRecord(node uint32, right bool) uint32 {
	b := m.data
	base := node * m.nodeBytes
	switch m.recSize {
	case 24:
		if !right {
			return uint32(b[base])<<16 | uint32(b[base+1])<<8 | uint32(b[base+2])
		}
		return uint32(b[base+3])<<16 | uint32(b[base+4])<<8 | uint32(b[base+5])
	case 28:
		if !right {
			return uint32(b[base+3]&0xf0)<<20 | uint32(b[base])<<16 | uint32(b[base+1])<<8 | uint32(b[base+2])
		}
		return uint32(b[base+3]&0x0f)<<24 | uint32(b[base+4])<<16 | uint32(b[base+5])<<8 | uint32(b[base+6])
	default: // 32
		if !right {
			return binary.BigEndian.Uint32(b[base : base+4])
		}
		return binary.BigEndian.Uint32(b[base+4 : base+8])
	}
}

func (m *mmdb) lookup(ip net.IP) map[string]interface{} {
	var ipb []byte
	node := uint32(0)
	if v4 := ip.To4(); v4 != nil {
		ipb = v4
		node = m.ipv4Start
	} else {
		ipb = ip.To16()
	}
	bits := len(ipb) * 8
	for i := 0; i < bits; i++ {
		if node >= m.nodeCount {
			break
		}
		bit := (ipb[i>>3] >> (7 - uint(i&7))) & 1
		node = m.readRecord(node, bit == 1)
	}
	if node <= m.nodeCount {
		return nil // == nodeCount: no data; < nodeCount: ran out of bits
	}
	off := node - m.nodeCount - 16 + m.dataStart
	v, _ := decodeValue(m.data, off, m.dataStart)
	if rec, ok := v.(map[string]interface{}); ok {
		return rec
	}
	return nil
}

// ---- data-section decoder -------------------------------------------------

// decodeValue decodes one value at data[off]. base is the start of the section
// that pointers are relative to (data section start, or metadata start while
// parsing metadata). Returns the value and the offset just past it.
func decodeValue(data []byte, off, base uint32) (interface{}, uint32) {
	ctrl := data[off]
	off++
	typ := ctrl >> 5
	if typ == 0 { // extended type: real type is in the next byte
		typ = data[off] + 7
		off++
	}
	size := uint32(ctrl & 0x1f)

	if typ == 1 { // pointer
		ps := (size >> 3) & 0x3
		var ptr uint32
		switch ps {
		case 0:
			ptr = (size&0x7)<<8 | uint32(data[off])
			off++
		case 1:
			ptr = (size&0x7)<<16 | uint32(data[off])<<8 | uint32(data[off+1])
			ptr += 2048
			off += 2
		case 2:
			ptr = (size&0x7)<<24 | uint32(data[off])<<16 | uint32(data[off+1])<<8 | uint32(data[off+2])
			ptr += 526336
			off += 3
		default: // 3
			ptr = uint32(data[off])<<24 | uint32(data[off+1])<<16 | uint32(data[off+2])<<8 | uint32(data[off+3])
			off += 4
		}
		v, _ := decodeValue(data, base+ptr, base)
		return v, off
	}

	// resolve the payload size for non-pointer types
	switch size {
	case 29:
		size = 29 + uint32(data[off])
		off++
	case 30:
		size = 285 + (uint32(data[off])<<8 | uint32(data[off+1]))
		off += 2
	case 31:
		size = 65821 + (uint32(data[off])<<16 | uint32(data[off+1])<<8 | uint32(data[off+2]))
		off += 3
	}

	switch typ {
	case 2: // utf-8 string
		return string(data[off : off+size]), off + size
	case 5, 6, 9, 10: // uint16 / uint32 / uint64 / uint128
		var n uint64
		for i := uint32(0); i < size && i < 8; i++ {
			n = n<<8 | uint64(data[off+i])
		}
		return n, off + size
	case 7: // map
		out := make(map[string]interface{}, size)
		o := off
		for i := uint32(0); i < size; i++ {
			k, no := decodeValue(data, o, base)
			v, nv := decodeValue(data, no, base)
			if ks, ok := k.(string); ok {
				out[ks] = v
			}
			o = nv
		}
		return out, o
	case 8: // int32 (two's complement, minimal bytes)
		var n int64
		for i := uint32(0); i < size; i++ {
			n = n<<8 | int64(data[off+i])
		}
		if size > 0 && size < 8 && data[off]&0x80 != 0 {
			n -= 1 << (8 * size) // sign-extend
		}
		return n, off + size
	case 3: // double
		return math.Float64frombits(binary.BigEndian.Uint64(data[off : off+8])), off + 8
	case 15: // float
		return float64(math.Float32frombits(binary.BigEndian.Uint32(data[off : off+4]))), off + 4
	case 11: // array
		arr := make([]interface{}, 0, size)
		o := off
		for i := uint32(0); i < size; i++ {
			v, nv := decodeValue(data, o, base)
			arr = append(arr, v)
			o = nv
		}
		return arr, o
	case 14: // boolean — value encoded in the size field, no payload
		return size != 0, off
	case 4: // bytes
		return data[off : off+size], off + size
	default: // 12 (data cache container) / unknowns — skip payload
		return nil, off + size
	}
}

// ---- small helpers --------------------------------------------------------

func toU32(v interface{}) uint32 {
	if n, ok := v.(uint64); ok {
		return uint32(n)
	}
	return 0
}

func lastIndex(haystack, needle []byte) int {
	for i := len(haystack) - len(needle); i >= 0; i-- {
		if bytesEqual(haystack[i:i+len(needle)], needle) {
			return i
		}
	}
	return -1
}

func bytesEqual(a, b []byte) bool {
	if len(a) != len(b) {
		return false
	}
	for i := range a {
		if a[i] != b[i] {
			return false
		}
	}
	return true
}
