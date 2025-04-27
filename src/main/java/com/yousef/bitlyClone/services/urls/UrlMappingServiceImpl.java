package com.yousef.bitlyClone.services.urls;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
import com.yousef.bitlyClone.dtos.MappingResponse;
import com.yousef.bitlyClone.models.UrlMapping;
import com.yousef.bitlyClone.models.User;
import com.yousef.bitlyClone.repositories.UrlMappingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class UrlMappingServiceImpl implements UrlMappingService {

    private final UrlMappingRepository urlMappingRepository;

    @Override
    public MappingResponse shortenUrl(String originalUrl, Authentication auth) {
        var user = (User) auth.getPrincipal();

        String shortUrl = generateShortUtl(originalUrl);

        var urlMapping = UrlMapping.builder()
                .originalUrl(originalUrl)
                .shortUrl(shortUrl)
                .user(user)
                .build();

        var savedMapping = urlMappingRepository.save(urlMapping);

        return MappingResponse.builder()
                .id(savedMapping.getId())
                .shortenedUrl(savedMapping.getShortUrl())
                .originalUrl(savedMapping.getOriginalUrl())
                .clickCount(savedMapping.getClickCount())
                .username(savedMapping.getUser().getActualUsername())
                .createdAt(savedMapping.getCreatedAt())
                .build();
    }

    private String generateShortUtl(String originalUrl) {
        Random random = new Random();
        String chars = NanoIdUtils.DEFAULT_ALPHABET.clone().toString();
        return NanoIdUtils.randomNanoId(random, chars.replace("_-", "").toCharArray(), 8);
    }
}
