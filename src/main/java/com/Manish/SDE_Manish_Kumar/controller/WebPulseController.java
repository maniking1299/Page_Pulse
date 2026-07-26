package com.Manish.SDE_Manish_Kumar.controller;

import com.Manish.SDE_Manish_Kumar.dto.UrlResponse;
import com.Manish.SDE_Manish_Kumar.dto.UrlStore;
import com.Manish.SDE_Manish_Kumar.services.AnalyzeServices;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api")
public class WebPulseController {

    private final AnalyzeServices analyzeServices;

    public WebPulseController(AnalyzeServices analyzeServices) {
        this.analyzeServices = analyzeServices;
    }

    @PostMapping("/analyze")
    public UrlResponse urlAnalyzer(@RequestBody UrlStore request) throws IOException {
        return analyzeServices.analyze(request.getUrl());
    }
}
