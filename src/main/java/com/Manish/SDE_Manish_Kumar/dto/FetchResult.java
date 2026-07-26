package com.Manish.SDE_Manish_Kumar.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.jsoup.nodes.Document;


@Data
@AllArgsConstructor
public class FetchResult {

    private Document document;
    private int statusCode;
    private long responseTime;
}
