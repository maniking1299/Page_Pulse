package com.Manish.SDE_Manish_Kumar.services;


import com.Manish.SDE_Manish_Kumar.dto.FetchResult;
import com.Manish.SDE_Manish_Kumar.dto.UrlResponse;
import com.Manish.SDE_Manish_Kumar.exception.InvalidUrlException;
import org.springframework.stereotype.Service;


@Service
public class AnalyzeServices {

    private final UrlValidator urlValidator;
    private final WebPageFetcher webPageFetcher;
    private final MetaDataExtrator metaDataExtrator;

    public AnalyzeServices(UrlValidator urlValidator, WebPageFetcher webPageFetcher, MetaDataExtrator metaDataExtrator) {
        this.urlValidator = urlValidator;
        this.webPageFetcher = webPageFetcher;
        this.metaDataExtrator = metaDataExtrator;
    }


    public UrlResponse analyze(String url) {

        System.out.println("Url :"+url);
        // Step 1: Validate URL
        if (!urlValidator.isValidUrl(url)) {
            throw new InvalidUrlException("Invalid URL");
        }

        // Step 2: Fetch webpage
        FetchResult fetchResult = webPageFetcher.fetch(url);


        // Step 3: Extract metadata
        String title = metaDataExtrator.getTitle(fetchResult.getDocument());
        String metaDescription = metaDataExtrator.getMetaDescription(fetchResult.getDocument());
        int h1Count = metaDataExtrator.getH1Count(fetchResult.getDocument());
        int imagesWithoutAlt = metaDataExtrator.getImagesWithoutAlt(fetchResult.getDocument());
        int wordCount = metaDataExtrator.getWordCount(fetchResult.getDocument());


        return new UrlResponse(fetchResult.getStatusCode(), fetchResult.getResponseTime(), title,metaDescription,h1Count,imagesWithoutAlt,wordCount);

    }
}
