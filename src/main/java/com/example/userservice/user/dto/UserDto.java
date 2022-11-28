package com.example.userservice.user.dto;

import com.example.userservice.user.entity.Role;
import com.example.userservice.user.entity.User;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class UserDto {
    private Long id;
    private String social;
    private String name;
    private String email;
    private String picture;
    private Role role;
    private String token;
    private String refreshToken;
    private String profile;
    private String field;
    private int year;

    public UserDto toDto(User user) {
        return UserDto.builder().id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .picture(user.getPicture())
                .role(user.getRole())
                .social(user.getSocial())
                .profile(user.getProfile())
                .field(user.getField())
                .year(user.getYear())
                .build();
    }
}
