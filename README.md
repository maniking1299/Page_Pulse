# Page Pulse

A backend tool that audits any URL and returns a structured report covering HTTP status, response time, page metadata, and content signals.

**Live demo:** https://webpage-pulse.onrender.com/

> Note: this is deployed on Render's free tier, which spins down after ~15 minutes of inactivity. The first request after idle time may take 30-50 seconds to respond while the instance wakes up.

---

## What it does

Given a URL, Page Pulse fetches the page and returns:
- HTTP status code
- Response time (ms)
- Page title
- Meta description
- H1 tag count
- Count of images missing `alt` attributes
- Approximate word count

---
## Working

<img width="1917" height="963" alt="Screenshot 2026-07-26 184646" src="https://github.com/user-attachments/assets/37f555e6-f2b4-404a-ac18-3a465ad4439b" />
<img width="1246" height="832" alt="Screenshot 2026-07-26 184917" src="https://github.com/user-attachments/assets/4d61e8dd-5816-48bd-90d1-aa0ae1a2e27b" />



---
## API Contract

### `POST /api/analyze`

**Request body:**
```json
{
  "url": "https://example.com"
}
```

**Response:**
```json
{
  "statusCode": 200,
  "responseTime": 342,
  "title": "Example Domain",
  "metaDescription": "This domain is for use in illustrative examples.",
  "h1count": 1,
  "imagesWithoutAlt": 0,
  "wordCount": 28
}
```

## Setup (running locally)

**Prerequisites:** Java 21, Maven (or use the included wrapper)

```bash
git clone https://github.com/<your-username>/Page_Pulse.git
cd Page_Pulse
./mvnw clean package
java -jar target/*.jar
```

The app starts on `http://localhost:8080` by default. Send requests to `POST /api/analyze` as shown above.

---

## Design Decisions

**1. Separated fetching, validation, and parsing into distinct services**
Rather than one monolithic method, `UrlValidator`, `WebPageFetcher`, and `MetaDataExtrator` each own one responsibility. This made the parsing logic — the part most likely to have edge cases — independently unit-testable without needing a live network call or a mocked HTTP layer for every test.

**2. Used Jsoup's built-in `Connection.execute()` for fetching instead of raw `HttpClient`**
Jsoup handles both the HTTP fetch and HTML parsing in one dependency, and returns a `Document` directly — avoiding a second parsing step and keeping `WebPageFetcher` simple.

**3. Treated an empty or whitespace-only `alt` attribute the same as a missing one**
`getImagesWithoutAlt()` checks both `!img.hasAttr("alt")` and an empty/blank value — an `alt=""` on a non-decorative image is still an accessibility failure, so counting it separately would have understated the real problem the task is testing for.

---

## Built for Digital Heroes Training Task

Live tool built for the [Digital Heroes](https://digitalheroesco.com) SDE hiring assessment.
