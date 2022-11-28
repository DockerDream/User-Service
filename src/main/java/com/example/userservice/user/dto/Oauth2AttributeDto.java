package com.example.userservice.user.dto;

import com.example.userservice.user.entity.Role;
import com.example.userservice.user.entity.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ToString
@Builder(access = AccessLevel.PRIVATE)
@Getter
public class Oauth2AttributeDto {
    private String attributeKey;
    private String email;
    private String name;
    private String picture;


    public User toEntity() {
        return User
                .builder()
                .social(attributeKey)
                .name(name)
                .email(email)
                .picture(picture)
                .role(Role.USER)
                .build();
    }
}