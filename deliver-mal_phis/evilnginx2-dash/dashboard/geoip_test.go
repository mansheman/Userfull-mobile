package dashboard

import (
	"encoding/binary"
	"math"
	"testing"
)

// These exercise the MMDB data-section decoder with hand-crafted bytes built
// per the MaxMind DB spec (https://maxmind.github.io/MaxMind-DB/), since we
// can't ship a real .mmdb in the repo.

func TestDecodeString(t *testing.T) {
	// type 2 (string), size 2, "EN"
	data := []byte{0x42, 'E', 'N'}
	v, off := decodeValue(data, 0, 0)
	if s, _ := v.(string); s != "EN" {
		t.Fatalf("string: got %v", v)
	}
	if off != 3 {
		t.Fatalf("string off: got %d", off)
	}
}

func TestDecodeUint(t *testing.T) {
	// type 6 (uint32), size 1, value 42
	v, off := decodeValue([]byte{0xC1, 0x2A}, 0, 0)
	if n, _ := v.(uint64); n != 42 {
		t.Fatalf("uint: got %v", v)
	}
	if off != 2 {
		t.Fatalf("uint off: got %d", off)
	}
}

func TestDecodeBool(t *testing.T) {
	// extended type 14 (bool), size field carries the value (1 = true)
	v, _ := decodeValue([]byte{0x01, 0x07}, 0, 0)
	if b, _ := v.(bool); !b {
		t.Fatalf("bool: got %v", v)
	}
}

func TestDecodeDouble(t *testing.T) {
	// type 3 (double), size 8
	b := make([]byte, 9)
	b[0] = 0x68
	binary.BigEndian.PutUint64(b[1:], math.Float64bits(1.5))
	v, off := decodeValue(b, 0, 0)
	if f, _ := v.(float64); f != 1.5 {
		t.Fatalf("double: got %v", v)
	}
	if off != 9 {
		t.Fatalf("double off: got %d", off)
	}
}

func TestDecodeMap(t *testing.T) {
	// map{ "x": uint16(5) }
	data := []byte{0xE1, 0x41, 'x', 0xA1, 0x05}
	v, off := decodeValue(data, 0, 0)
	m, ok := v.(map[string]interface{})
	if !ok {
		t.Fatalf("map: got %T", v)
	}
	if n, _ := m["x"].(uint64); n != 5 {
		t.Fatalf("map[x]: got %v", m["x"])
	}
	if off != 5 {
		t.Fatalf("map off: got %d", off)
	}
}

func TestDecodePointer(t *testing.T) {
	// pointer (type 1, ps=0) -> base+4, where a string "HI" lives
	data := []byte{0x20, 0x04, 0x00, 0x00, 0x42, 'H', 'I'}
	v, off := decodeValue(data, 0, 0)
	if s, _ := v.(string); s != "HI" {
		t.Fatalf("pointer: got %v", v)
	}
	if off != 2 { // offset advances past the pointer itself, not the target
		t.Fatalf("pointer off: got %d", off)
	}
}

func TestNilGeoIPLookup(t *testing.T) {
	var g *GeoIP
	if g.Lookup("8.8.8.8") != nil {
		t.Fatal("nil GeoIP should return nil")
	}
}
