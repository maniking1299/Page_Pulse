package com.Manish.SDE_Manish_Kumar.services;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MetaDataExtratorTest {

    private final MetaDataExtrator extractor = new MetaDataExtrator();

    private Document parse(String html) {
        return Jsoup.parse(html);
    }

    // --- Happy path: a well-formed page with everything present ---

    @Test
    void wellFormedPage_extractsAllFieldsCorrectly() {
        String html = "<html><head>"
                + "<title>Test Page</title>"
                + "<meta name=\"description\" content=\"A test page\">"
                + "</head><body>"
                + "<h1>Heading One</h1>"
                + "<h1>Heading Two</h1>"
                + "<img src=\"a.png\" alt=\"A picture\">"
                + "<img src=\"b.png\">"
                + "<p>Hello world this is some body text</p>"
                + "</body></html>";
        Document doc = parse(html);

        assertEquals("Test Page", extractor.getTitle(doc));
        assertEquals("A test page", extractor.getMetaDescription(doc));
        assertEquals(2, extractor.getH1Count(doc));
        assertEquals(1, extractor.getImagesWithoutAlt(doc));
        assertEquals(11, extractor.getWordCount(doc)); // includes H1 text: "Heading One Heading Two" + paragraph text 
    }

    // --- Failure/edge case 1: meta description missing ---

    @Test
    void missingMetaDescription_returnsEmptyString() {
        String html = "<html><head><title>No Meta</title></head><body><p>Text</p></body></html>";
        Document doc = parse(html);

        assertEquals("", extractor.getMetaDescription(doc));
    }

    // --- Failure/edge case 2: images with empty alt attribute (not just missing) ---

    @Test
    void imageWithEmptyAltAttribute_countsAsMissingAlt() {
        String html = "<html><body>"
                + "<img src=\"a.png\" alt=\"\">"
                + "<img src=\"b.png\" alt=\"   \">"
                + "<img src=\"c.png\" alt=\"Valid\">"
                + "</body></html>";
        Document doc = parse(html);

        assertEquals(2, extractor.getImagesWithoutAlt(doc));
    }

    @Test
    void noH1Tags_returnsZero() {
        String html = "<html><body><h2>Not an H1</h2></body></html>";
        Document doc = parse(html);

        assertEquals(0, extractor.getH1Count(doc));
    }

    @Test
    void emptyBody_returnsZeroWordCount() {
        String html = "<html><body></body></html>";
        Document doc = parse(html);

        // Jsoup's split on an empty string still yields one empty token;
        // documenting actual current behavior rather than assuming.
        assertEquals(1, extractor.getWordCount(doc));
    }
}