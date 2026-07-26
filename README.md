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

### 1. Followed the Single Responsibility Principle
The application is split into dedicated services for URL validation, webpage fetching, and metadata extraction. Each component has one clear responsibility, making the code easier to maintain, test, and extend without affecting the rest of the application.

### 2. Used Jsoup for both HTTP requests and HTML parsing
Instead of combining multiple libraries, Jsoup was used to fetch webpages and parse HTML. This kept the implementation lightweight, reduced dependencies, and simplified the data extraction process.

### 3. Measured response time at the application level
Response time is recorded from the moment the fetch request is initiated until the page is successfully retrieved. This provides a practical measure of the time experienced by the application rather than relying on server-reported timings.

### 4. Counted empty `alt` attributes as missing
Images with no `alt` attribute or a blank `alt` value are both counted as missing. This better reflects accessibility issues, since non-decorative images should contain meaningful alternative text.

### 5. Validated URLs before making network requests
Incoming URLs are validated before any HTTP request is sent. This avoids unnecessary network calls, provides faster feedback for invalid input, and keeps error handling straightforward.

---
## AI Usage

AI was used to assist with the frontend UI, improve the README, and generate test cases during testing. All backend development, API design, validation, parsing logic, architectural decisions, and project structure were designed and implemented by me.

---
## Built for Digital Heroes Training Task

Live tool built for the [Digital Heroes](https://digitalheroesco.com) SDE hiring assessment.
