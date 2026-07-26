package com.Manish.SDE_Manish_Kumar.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UrlResponse {
   private int statusCode;
   private long responseTime;
   private String title;
   private String metaDescription;
   private int h1count;
   private int imagesWithoutAlt;
   private int wordCount;

}
