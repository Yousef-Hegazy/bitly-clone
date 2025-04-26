package com.yousef.bitlyClone.services.urls;

import com.yousef.bitlyClone.dtos.MappingResponse;
import com.yousef.bitlyClone.models.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
@RequiredArgsConstructor
public class UrlMappingServiceImpl implements UrlMappingService {

    @Override
    public MappingResponse shortenUrl(String originalUrl, Authentication auth) {
        var user = (User) auth.getPrincipal();



        return null;
    }
}
