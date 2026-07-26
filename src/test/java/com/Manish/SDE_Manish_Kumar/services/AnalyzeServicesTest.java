package com.Manish.SDE_Manish_Kumar.services;

import com.Manish.SDE_Manish_Kumar.dto.FetchResult;
import com.Manish.SDE_Manish_Kumar.dto.UrlResponse;
import com.Manish.SDE_Manish_Kumar.exception.FetchFailedException;
import com.Manish.SDE_Manish_Kumar.exception.InvalidUrlException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AnalyzeServicesTest {

    @Mock
    private UrlValidator urlValidator;

    @Mock
    private WebPageFetcher webPageFetcher;

    @Mock
    private MetaDataExtrator metaDataExtrator;

    private AnalyzeServices analyzeServices;

    @BeforeEach
    void setUp() {
        analyzeServices = new AnalyzeServices(urlValidator, webPageFetcher, metaDataExtrator);
    }

    // --- Happy path ---

    @Test
    void validUrl_returnsCompleteReport() {
        String url = "https://example.com";
        Document doc = Jsoup.parse("<html><head><title>Example</title></head><body></body></html>");
        FetchResult fetchResult = new FetchResult(doc, 200, 150L);

        when(urlValidator.isValidUrl(url)).thenReturn(true);
        when(webPageFetcher.fetch(url)).thenReturn(fetchResult);
        when(metaDataExtrator.getTitle(doc)).thenReturn("Example");
        when(metaDataExtrator.getMetaDescription(doc)).thenReturn("An example page");
        when(metaDataExtrator.getH1Count(doc)).thenReturn(1);
        when(metaDataExtrator.getImagesWithoutAlt(doc)).thenReturn(0);
        when(metaDataExtrator.getWordCount(doc)).thenReturn(42);

        UrlResponse response = analyzeServices.analyze(url);

        assertEquals(200, response.getStatusCode());
        assertEquals(150L, response.getResponseTime());
        assertEquals("Example", response.getTitle());
        assertEquals("An example page", response.getMetaDescription());
        assertEquals(1, response.getH1count());
        assertEquals(0, response.getImagesWithoutAlt());
        assertEquals(42, response.getWordCount());
    }

    // --- Failure case 1: invalid URL, should short-circuit before fetching ---

    @Test
    void invalidUrl_throwsInvalidUrlExceptionAndNeverFetches() {
        String url = "not-a-url";
        when(urlValidator.isValidUrl(url)).thenReturn(false);

        assertThrows(InvalidUrlException.class, () -> analyzeServices.analyze(url));

        verifyNoInteractions(webPageFetcher);
        verifyNoInteractions(metaDataExtrator);
    }

    // --- Failure case 2: URL passes validation but fetch fails (timeout, DNS, etc.) ---

    @Test
    void validUrlButFetchFails_throwsFetchFailedException() {
        String url = "https://unreachable-site.example";
        when(urlValidator.isValidUrl(url)).thenReturn(true);
        when(webPageFetcher.fetch(url)).thenThrow(new FetchFailedException("Unable to fetch Web Page"));

        assertThrows(FetchFailedException.class, () -> analyzeServices.analyze(url));

        verifyNoInteractions(metaDataExtrator);
    }
}