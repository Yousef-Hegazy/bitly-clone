package com.yousef.bitlyClone.controllers;

import com.yousef.bitlyClone.dtos.MappingResponse;
import com.yousef.bitlyClone.dtos.ShortenUrlRequest;
import com.yousef.bitlyClone.services.urls.UrlMappingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/urls")
@RequiredArgsConstructor
public class UrlMappingController {
    private final UrlMappingService urlMappingService;

    @PostMapping("/shorten")
    public ResponseEntity<MappingResponse> createShortUrl(@Valid @RequestBody ShortenUrlRequest request, Authentication auth) {
        return ResponseEntity.ok(urlMappingService.shortenUrl(request.originalUrl(), auth));
    }

    @GetMapping("/my-urls")
    public ResponseEntity<List<MappingResponse>> getMyUrls(Authentication auth) {
        return ResponseEntity.ok(urlMappingService.getMyUrls(auth));
    }
}
