package com.Manish.SDE_Manish_Kumar.services;

import com.Manish.SDE_Manish_Kumar.dto.FetchResult;
import com.Manish.SDE_Manish_Kumar.exception.FetchFailedException;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class WebPageFetcher {

        public FetchResult fetch(String url)  {
          try{
              long startTime = System.currentTimeMillis();

              Connection.Response response = Jsoup.connect(url).execute();
              int statusCode = response.statusCode();

              long endTime = System.currentTimeMillis();

              Document document = response.parse();

              long responseTime = endTime - startTime;

              return new FetchResult(document, statusCode, responseTime);
          }catch (IOException e){
              throw new FetchFailedException("Unable to fetch Web Page");
          }
        }
}
