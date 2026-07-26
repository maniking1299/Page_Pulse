package com.Manish.SDE_Manish_Kumar.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UrlValidatorTest {

    private final UrlValidator urlValidator = new UrlValidator();

    @Test
    void validHttpsUrl_returnsTrue() {
        assertTrue(urlValidator.isValidUrl("https://example.com"));
    }

    @Test
    void validHttpUrl_returnsTrue() {
        assertTrue(urlValidator.isValidUrl("http://example.com"));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "not-a-url",
            "ftp://example.com",
            "htp://typo.com",
            "example.com"
    })
    void malformedOrUnsupportedProtocol_returnsFalse(String badUrl) {
        assertFalse(urlValidator.isValidUrl(badUrl));
    }

    @Test
    void nullUrl_returnsFalse() {
        assertFalse(urlValidator.isValidUrl(null));
    }

    @Test
    void blankUrl_returnsFalse() {
        assertFalse(urlValidator.isValidUrl("   "));
    }
}