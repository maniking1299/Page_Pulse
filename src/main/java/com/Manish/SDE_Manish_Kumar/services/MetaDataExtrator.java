package com.Manish.SDE_Manish_Kumar.services;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

@Service
public class MetaDataExtrator {
    public String getTitle(Document document) {
        return document.title();
    }

    public String getMetaDescription(Document document) {
        Element meta = document.selectFirst("meta[name=description]");

        if (meta != null) {
            return meta.attr("content");
        }

        return "";

    }

    public int getH1Count(Document document) {
        Elements h1Tags = document.select("h1");

        return h1Tags.size();
    }

    public int getImagesWithoutAlt(Document document) {
        Elements images = document.select("img");
        int count = 0;
        for (Element img : images) {
            if (!img.hasAttr("alt") || img.attr("alt").trim().isEmpty()) {
                count++;
            }
        }

        return count;
    }

    public int getWordCount(Document document) {
        String text = document.body().text();

        String[] words = text.split("\\s+");

        return words.length;
    }
}
