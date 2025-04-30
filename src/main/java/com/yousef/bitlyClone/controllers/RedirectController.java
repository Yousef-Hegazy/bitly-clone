package com.yousef.bitlyClone.controllers;

import com.yousef.bitlyClone.models.UrlMapping;
import com.yousef.bitlyClone.services.urls.UrlMappingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RedirectController {

    private final UrlMappingService urlMappingService;

    @GetMapping
    public ResponseEntity<Void> redirect(@PathVariable String shortUrl) {
        UrlMapping urlMapping = urlMappingService.findByShortUrl(shortUrl);

        if (urlMapping == null) {
            return ResponseEntity.notFound().build();
        }

        HttpHeaders headers = new HttpHeaders();

        headers.add("Location", urlMapping.getOriginalUrl());

        return ResponseEntity.status(HttpStatus.FOUND).headers(headers).build();
    }
}
