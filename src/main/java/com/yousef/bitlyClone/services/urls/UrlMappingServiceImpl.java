package com.yousef.bitlyClone.services.urls;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
import com.yousef.bitlyClone.dtos.ClickEventResponse;
import com.yousef.bitlyClone.dtos.MappingResponse;
import com.yousef.bitlyClone.mappers.ClickEventMapper;
import com.yousef.bitlyClone.mappers.UrlMappingMapper;
import com.yousef.bitlyClone.models.UrlMapping;
import com.yousef.bitlyClone.models.User;
import com.yousef.bitlyClone.repositories.ClickEventRepository;
import com.yousef.bitlyClone.repositories.UrlMappingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UrlMappingServiceImpl implements UrlMappingService {

    private final UrlMappingRepository urlMappingRepository;
    private final ClickEventRepository clickEventRepository;
    private final ClickEventMapper clickEventMapper;
    private final char[] charsForRandomGenerator = NanoIdUtils.DEFAULT_ALPHABET.clone().toString().replace("_-", "").toCharArray();

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

        return UrlMappingMapper.toMappingResponse(savedMapping);
    }

    @Override
    public List<MappingResponse> getMyUrls(Authentication auth) {
        var user = (User) auth.getPrincipal();

        var urlMappings = urlMappingRepository.findAllByUser(user);

        if (urlMappings == null || urlMappings.isEmpty()) return List.of();

        return UrlMappingMapper.toMappingResponseList(urlMappings);
    }

    @Override
    public List<ClickEventResponse> getUrlAnalytics(String shortUrl, String startDate, String endDate, Authentication auth) {

        if (startDate == null || endDate == null) return List.of();

        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;

        LocalDateTime start = LocalDateTime.parse(startDate, formatter);
        LocalDateTime end = LocalDateTime.parse(endDate, formatter);
        var urlMapping = urlMappingRepository.findByShortUrl(shortUrl);

        var urlClicks = clickEventRepository.findByUrlMappingAndClickedAtBetween(urlMapping, start, end);
        if (urlClicks == null || urlClicks.isEmpty()) return List.of();

        return clickEventMapper.toClickEventResponse(urlClicks);
    }

    @Override
    public Map<LocalDate, Long> getTotalClicksForDate(String startDate, String endDate, Authentication auth) {
        if (startDate == null || endDate == null) return Map.of();

        var user = (User) auth.getPrincipal();

        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;

        LocalDate start = LocalDate.parse(startDate, formatter);

        LocalDate end = LocalDate.parse(endDate, formatter);

        var urlMappings = urlMappingRepository.findAllByUser(user);

        if (urlMappings == null || urlMappings.isEmpty()) return Map.of();

        var clickEvents = clickEventRepository.findByUrlMappingInAndClickedAtBetween(urlMappings, start, end);

        return clickEvents.stream().collect(Collectors.groupingBy(ce -> ce.getClickedAt().toLocalDate(), Collectors.counting()));
    }

    @Override
    public UrlMapping findByShortUrl(String shortUrl) {
        return urlMappingRepository.findByShortUrl(shortUrl)
                .orElseThrow(() -> new IllegalStateException("Mapping not found for this url"));
    }

    private String generateShortUtl(String originalUrl) {
        Random random = new Random();

        return NanoIdUtils.randomNanoId(random, charsForRandomGenerator, 8);
    }
}
