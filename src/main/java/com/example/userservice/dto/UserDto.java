package com.example.userservice.dto;

import lombok.Data;

@Data
public class UserDto {

    private String username;
    private String password;
    private String nickname;
    private String email;
    private String userstatus;
    private String field;
    private int year;
}
