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


## Built for Digital Heroes Training Task

Live tool built for the [Digital Heroes](https://digitalheroesco.com) SDE hiring assessment.
