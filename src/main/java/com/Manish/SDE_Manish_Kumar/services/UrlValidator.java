package com.Manish.SDE_Manish_Kumar.services;

import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URL;

@Service
public class UrlValidator {


    public  boolean isValidUrl(String urlString) {
        if (urlString == null || urlString.isBlank()) {
            return false;
        }

        try {
            URL url = new URL(urlString);
            return url.getProtocol().equals("http") ||
                    url.getProtocol().equals("https");

        } catch (MalformedURLException e) {
            return false;
        }
    }
}
