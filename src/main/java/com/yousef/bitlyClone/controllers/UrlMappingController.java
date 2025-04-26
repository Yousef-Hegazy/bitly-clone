package com.yousef.bitlyClone.controllers;

import com.yousef.bitlyClone.dtos.MappingResponse;
import com.yousef.bitlyClone.services.urls.UrlMappingService;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api/urls")
@RequiredArgsConstructor
public class UrlMappingController {
    private final UrlMappingService urlMappingService;

    @PostMapping("/shorten")
    public ResponseEntity<MappingResponse> createShortUrl(@RequestBody @NotEmpty
                                                          @NotBlank
                                                          String originalUrl, Authentication auth) {
        return ResponseEntity.ok(urlMappingService.shortenUrl(originalUrl, auth));
    }
}
