package com.example.userservice.user.dto;

import lombok.Data;

@Data
public class UserDto {

    private String username;
    private String password;
    private String nickname;
    private String email;
    private String profile;
    private String field;
    private int year;
}
