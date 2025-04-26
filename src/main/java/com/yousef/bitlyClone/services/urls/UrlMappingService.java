package com.yousef.bitlyClone.services.urls;

import com.yousef.bitlyClone.dtos.MappingResponse;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.security.core.Authentication;

import java.security.Principal;

public interface UrlMappingService {
    MappingResponse shortenUrl(@NotEmpty @NotBlank String originalUrl, Authentication auth);
}
