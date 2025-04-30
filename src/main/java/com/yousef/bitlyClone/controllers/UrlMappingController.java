package com.yousef.bitlyClone.controllers;

import com.yousef.bitlyClone.dtos.ClickEventResponse;
import com.yousef.bitlyClone.dtos.MappingResponse;
import com.yousef.bitlyClone.dtos.ShortenUrlRequest;
import com.yousef.bitlyClone.services.urls.UrlMappingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

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

    @GetMapping("/analytics/{shortUrl}")
    public ResponseEntity<List<ClickEventResponse>> getUrlAnalytics(
            @PathVariable String shortUrl,
            @RequestParam(name = "startDate", required = false) String startDate,
            @RequestParam(name = "endDate", required = false) String endDate,
            Authentication auth
    ) {
        return ResponseEntity.ok(urlMappingService.getUrlAnalytics(shortUrl, startDate, endDate, auth));
    }

    @GetMapping("/totalClicks")
    public ResponseEntity<Map<LocalDate, Long>> getTotalClicksByDate(
            @RequestParam(name = "date") String startDate,
            @RequestParam(name = "date") String endDate,
            Authentication auth
    ) {
        return ResponseEntity.ok(urlMappingService.getTotalClicksForDate(startDate, endDate, auth));
    }
}
