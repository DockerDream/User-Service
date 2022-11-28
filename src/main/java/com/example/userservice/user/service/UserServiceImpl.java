package com.example.userservice.user.service;

import com.example.userservice.config.GWErrorResponse;
import com.example.userservice.oauth.domain.Authorization;
import com.example.userservice.oauth.domain.OAuth2Attribute;
import com.example.userservice.oauth.domain.SecurityToken;
import com.example.userservice.oauth.service.Oauth2KakaoServiceImpl;
import com.example.userservice.oauth.service.SecurityTokenService;
import com.example.userservice.user.dto.Oauth2AttributeDto;
import com.example.userservice.user.dto.UserDto;
import com.example.userservice.user.entity.Role;
import com.example.userservice.user.entity.User;
import com.example.userservice.user.repository.UserRepository;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final SecurityTokenService securityTokenService;
    private final UserRepository userRepository;
    private final Oauth2KakaoServiceImpl oauth2KakaoService;
    private final RedisServiceImpl redisServiceImpl;

    @Override
    public Object refreshService(String expiredAccessToken, String refreshToken) {
        String email = null;

        try {
            email = securityTokenService.getEmail(expiredAccessToken);
        } catch (ExpiredJwtException e) {
            email = e.getClaims().getSubject();
            log.info("email from expired access token: " + email);
        }

        if (email == null) throw new IllegalArgumentException();
        String refreshTokenFromRedis = redisServiceImpl.findByEmail(email);
        if (!refreshToken.equals(refreshTokenFromRedis)) {
            log.info("\"refresh is not equal\"");
            return GWErrorResponse.defaultBuild("refresh is not equal", 58);
        }
        if (securityTokenService.checkExpiredRefreshToken(refreshToken)) {
            log.info("refresh token is expried go to loginpage");

            return GWErrorResponse.defaultBuild("refresh token is expried go to loginpage", 58);
        }

        SecurityToken newToken = securityTokenService.generateToken(email, Role.USER.name());
        return newToken;
    }

    @Override
    public UserDto oauth2Authorization(String code, String state, String type) throws Exception {
        Map<String, Object> userInfo = null;
        if (type.equals("kakao")) {
            Authorization authorization = oauth2KakaoService.getAccessToken(code);
            userInfo = oauth2KakaoService.getKakaoUserInfo(authorization.getAccess_token());
        }
        OAuth2Attribute oAuth2Attribute = OAuth2Attribute.of(type, userInfo);
        Oauth2AttributeDto oauth2AttributeDto = new Oauth2AttributeDto(
                oAuth2Attribute.getAttributeKey(),
                oAuth2Attribute.getEmail(),
                oAuth2Attribute.getName(),
                oAuth2Attribute.getPicture()
        );
        User user = userSaveOrUpdate(oauth2AttributeDto);
        log.info("save user info");

        SecurityToken securityToken = securityTokenService.generateToken(user.getEmail(), Role.USER.name());

        log.info("save our jwt token");
        redisServiceImpl.saveToken(user.getEmail() + "_" + type, securityToken.getRefreshToken());
        UserDto userDto = UserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .picture(user.getPicture())
                .token(securityToken.getToken())
                .refreshToken(securityToken.getRefreshToken())
                .build();
        return userDto;
    }

    @Override
    public void logout(String token, String refreshToken, String type) {
        log.info("delete refresh token");
        String email = securityTokenService.getEmail(token);
        redisServiceImpl.deleteToken(email + "_" + type);
    }

    @Override
    public User userSaveOrUpdate(Oauth2AttributeDto oauth2AttributeDto) {
        User user = saveOrUpdate(oauth2AttributeDto);
        return user;
    }

    @Override
    public Optional<User> findByEmailAndSocial(String email, String social) {
        Optional<User> userOptional = userRepository.findByEmailAndSocial(email, social);
        return userOptional;
    }

    @Override
    public HashMap<String, String> findaddressbyemail(String email) {
        User user = userRepository.findByemail(email).orElse(null);
        HashMap<String, String> result = new HashMap<>();
        result.put("id",user.getId().toString());
        return result;
    }

    @Override
    public HashMap<String,String> findById(Long id) {
        User user =  userRepository.findById(id).orElse(User.builder().name("null").build());
        HashMap<String,String> result = new HashMap<>();
        if(user.getName().equals("null"))
        {
            result.put("address","");
        }
        return result;
    }

    private User saveOrUpdate(Oauth2AttributeDto attribute) {
        User user = userRepository.findByEmailAndSocial(attribute.getEmail(), attribute.getAttributeKey())
                .map(entity -> entity.update(attribute.getName(), attribute.getPicture(), attribute.getAttributeKey()))
                .orElse(attribute.toEntity());
        return userRepository.save(user);
    }


}