package com.yousef.bitlyClone.mappers;

import com.yousef.bitlyClone.dtos.MappingResponse;
import com.yousef.bitlyClone.models.UrlMapping;

import java.util.List;

public class UrlMappingMapper {

    public static MappingResponse toMappingResponse(UrlMapping urlMapping) {
        return MappingResponse.builder()
                .id(urlMapping.getId())
                .shortenedUrl(urlMapping.getShortUrl())
                .originalUrl(urlMapping.getOriginalUrl())
                .clickCount(urlMapping.getClickCount())
                .username(urlMapping.getUser().getActualUsername())
                .createdAt(urlMapping.getCreatedAt())
                .build();
    }

    public static List<MappingResponse> toMappingResponseList(List<UrlMapping> urlMappings) {
        return urlMappings.stream()
                .map(UrlMappingMapper::toMappingResponse)
                .toList();
    }
}
