package core

import (
	"bufio"
	"fmt"
	"net"
	"os"
	"sort"
	"strings"
	"sync"

	"github.com/kgretzky/evilginx2/log"
)

type BlockIP struct {
	ipv4 net.IP
	mask *net.IPNet
}

type Blacklist struct {
	ips        map[string]*BlockIP
	masks      []*BlockIP
	configPath string
	verbose    bool
	mtx        sync.RWMutex
}

func NewBlacklist(path string) (*Blacklist, error) {
	f, err := os.OpenFile(path, os.O_CREATE|os.O_RDONLY, 0644)
	if err != nil {
		return nil, err
	}
	defer f.Close()

	bl := &Blacklist{
		ips:        make(map[string]*BlockIP),
		configPath: path,
		verbose:    true,
	}

	fs := bufio.NewScanner(f)
	fs.Split(bufio.ScanLines)

	for fs.Scan() {
		l := fs.Text()
		// remove comments
		if n := strings.Index(l, ";"); n > -1 {
			l = l[:n]
		}
		l = strings.Trim(l, " ")

		if len(l) > 0 {
			if strings.Contains(l, "/") {
				ipv4, mask, err := net.ParseCIDR(l)
				if err == nil {
					bl.masks = append(bl.masks, &BlockIP{ipv4: ipv4, mask: mask})
				} else {
					log.Error("blacklist: invalid ip/mask address: %s", l)
				}
			} else {
				ipv4 := net.ParseIP(l)
				if ipv4 != nil {
					bl.ips[ipv4.String()] = &BlockIP{ipv4: ipv4, mask: nil}
				} else {
					log.Error("blacklist: invalid ip address: %s", l)
				}
			}
		}
	}

	log.Info("blacklist: loaded %d ip addresses and %d ip masks", len(bl.ips), len(bl.masks))
	return bl, nil
}

func (bl *Blacklist) GetStats() (int, int) {
	bl.mtx.RLock()
	defer bl.mtx.RUnlock()
	return len(bl.ips), len(bl.masks)
}

func (bl *Blacklist) AddIP(ip string) error {
	bl.mtx.Lock()
	defer bl.mtx.Unlock()

	if bl.isBlacklistedLocked(ip) {
		return nil
	}

	ipv4 := net.ParseIP(ip)
	if ipv4 != nil {
		bl.ips[ipv4.String()] = &BlockIP{ipv4: ipv4, mask: nil}
	} else {
		return fmt.Errorf("invalid ip address: %s", ip)
	}

	// write to file
	f, err := os.OpenFile(bl.configPath, os.O_APPEND|os.O_WRONLY, 0644)
	if err != nil {
		return err
	}
	defer f.Close()

	_, err = f.WriteString(ipv4.String() + "\n")
	if err != nil {
		return err
	}

	return nil
}

func (bl *Blacklist) IsBlacklisted(ip string) bool {
	bl.mtx.RLock()
	defer bl.mtx.RUnlock()
	return bl.isBlacklistedLocked(ip)
}

// isBlacklistedLocked is the lock-free core of IsBlacklisted; callers must hold
// at least a read lock.
func (bl *Blacklist) isBlacklistedLocked(ip string) bool {
	ipv4 := net.ParseIP(ip)
	if ipv4 == nil {
		return false
	}

	if _, ok := bl.ips[ipv4.String()]; ok {
		return true
	}
	for _, m := range bl.masks {
		if m.mask != nil && m.mask.Contains(ipv4) {
			return true
		}
	}
	return false
}

func (bl *Blacklist) SetVerbose(verbose bool) {
	bl.verbose = verbose
}

func (bl *Blacklist) IsVerbose() bool {
	return bl.verbose
}

func (bl *Blacklist) IsWhitelisted(ip string) bool {
	if ip == "127.0.0.1" {
		return true
	}
	return false
}

// ---- management (used by the dashboard) -----------------------------------

// BlacklistEntry is a JSON-friendly view of a single blacklist entry.
type BlacklistEntry struct {
	Value  string `json:"value"`
	IsMask bool   `json:"is_mask"`
}

// Entries returns all blacklist entries (single IPs and CIDR masks), sorted.
func (bl *Blacklist) Entries() []BlacklistEntry {
	bl.mtx.RLock()
	defer bl.mtx.RUnlock()

	out := make([]BlacklistEntry, 0, len(bl.ips)+len(bl.masks))
	for ip := range bl.ips {
		out = append(out, BlacklistEntry{Value: ip, IsMask: false})
	}
	for _, m := range bl.masks {
		if m.mask != nil {
			out = append(out, BlacklistEntry{Value: m.mask.String(), IsMask: true})
		}
	}
	sort.Slice(out, func(i, j int) bool { return out[i].Value < out[j].Value })
	return out
}

// addEntryLocked adds a single IP or CIDR to the in-memory set without saving.
// Returns false (no error) if the entry already exists.
func (bl *Blacklist) addEntryLocked(entry string) (added bool, err error) {
	entry = strings.TrimSpace(entry)
	if entry == "" {
		return false, fmt.Errorf("empty entry")
	}
	if strings.Contains(entry, "/") {
		ipv4, mask, err := net.ParseCIDR(entry)
		if err != nil {
			return false, fmt.Errorf("invalid CIDR: %s", entry)
		}
		for _, m := range bl.masks {
			if m.mask != nil && m.mask.String() == mask.String() {
				return false, nil
			}
		}
		bl.masks = append(bl.masks, &BlockIP{ipv4: ipv4, mask: mask})
		return true, nil
	}
	ipv4 := net.ParseIP(entry)
	if ipv4 == nil {
		return false, fmt.Errorf("invalid IP: %s", entry)
	}
	if _, ok := bl.ips[ipv4.String()]; ok {
		return false, nil
	}
	bl.ips[ipv4.String()] = &BlockIP{ipv4: ipv4, mask: nil}
	return true, nil
}

// AddEntry adds a single IP or CIDR and persists the blacklist.
func (bl *Blacklist) AddEntry(entry string) error {
	bl.mtx.Lock()
	defer bl.mtx.Unlock()
	if _, err := bl.addEntryLocked(entry); err != nil {
		return err
	}
	return bl.saveLocked()
}

// RemoveEntry removes a single IP or CIDR and persists the blacklist.
func (bl *Blacklist) RemoveEntry(entry string) error {
	bl.mtx.Lock()
	defer bl.mtx.Unlock()

	entry = strings.TrimSpace(entry)
	removed := false
	if strings.Contains(entry, "/") {
		_, mask, err := net.ParseCIDR(entry)
		if err != nil {
			return fmt.Errorf("invalid CIDR: %s", entry)
		}
		kept := bl.masks[:0]
		for _, m := range bl.masks {
			if m.mask != nil && m.mask.String() == mask.String() {
				removed = true
				continue
			}
			kept = append(kept, m)
		}
		bl.masks = kept
	} else {
		ipv4 := net.ParseIP(entry)
		if ipv4 == nil {
			return fmt.Errorf("invalid IP: %s", entry)
		}
		if _, ok := bl.ips[ipv4.String()]; ok {
			delete(bl.ips, ipv4.String())
			removed = true
		}
	}
	if !removed {
		return fmt.Errorf("entry not found: %s", entry)
	}
	return bl.saveLocked()
}

// ImportEntries bulk-adds entries from text (one IP/CIDR per line; ';' comments
// allowed). Returns the count added and any per-line errors.
func (bl *Blacklist) ImportEntries(text string) (added int, errors []string) {
	bl.mtx.Lock()
	defer bl.mtx.Unlock()

	for _, line := range strings.Split(text, "\n") {
		if n := strings.Index(line, ";"); n > -1 {
			line = line[:n]
		}
		line = strings.TrimSpace(line)
		if line == "" {
			continue
		}
		ok, err := bl.addEntryLocked(line)
		if err != nil {
			errors = append(errors, line+": "+err.Error())
		} else if ok {
			added++
		}
	}
	if added > 0 {
		bl.saveLocked()
	}
	return added, errors
}

// Clear removes all entries and persists the empty blacklist.
func (bl *Blacklist) Clear() error {
	bl.mtx.Lock()
	defer bl.mtx.Unlock()
	bl.ips = make(map[string]*BlockIP)
	bl.masks = nil
	return bl.saveLocked()
}

// saveLocked rewrites the blacklist file from the in-memory set. Callers must
// hold the write lock. Note: inline comments in the file are not preserved.
func (bl *Blacklist) saveLocked() error {
	var b strings.Builder
	ips := make([]string, 0, len(bl.ips))
	for ip := range bl.ips {
		ips = append(ips, ip)
	}
	sort.Strings(ips)
	for _, ip := range ips {
		b.WriteString(ip + "\n")
	}
	masks := make([]string, 0, len(bl.masks))
	for _, m := range bl.masks {
		if m.mask != nil {
			masks = append(masks, m.mask.String())
		}
	}
	sort.Strings(masks)
	for _, m := range masks {
		b.WriteString(m + "\n")
	}
	return os.WriteFile(bl.configPath, []byte(b.String()), 0644)
}
