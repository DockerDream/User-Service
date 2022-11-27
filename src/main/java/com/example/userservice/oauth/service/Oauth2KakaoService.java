package com.example.userservice.oauth.service;

import com.example.userservice.oauth.domain.Authorization;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public interface Oauth2KakaoService {
    Authorization getAccessToken(String code);

    Map<String, Object> getKakaoUserInfo(String accessToken) throws Exception;
}