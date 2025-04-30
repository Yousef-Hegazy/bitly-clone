package com.yousef.bitlyClone.services.urls;

import com.yousef.bitlyClone.dtos.ClickEventResponse;
import com.yousef.bitlyClone.dtos.MappingResponse;
import com.yousef.bitlyClone.models.UrlMapping;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.security.core.Authentication;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface UrlMappingService {
    MappingResponse shortenUrl(@NotEmpty @NotBlank String originalUrl, Authentication auth);

    List<MappingResponse> getMyUrls(Authentication auth);

    List<ClickEventResponse> getUrlAnalytics(String shortUrl, String startDate, String endDate, Authentication auth);

    Map<LocalDate, Long> getTotalClicksForDate(String startDate, String endDate, Authentication auth);

    UrlMapping findByShortUrl(String shortUrl);
}
