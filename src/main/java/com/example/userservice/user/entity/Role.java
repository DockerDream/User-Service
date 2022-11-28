package com.example.userservice.user.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum Role {

    ADMIN("ROLE_ADMIN", "관리자권한"),
    USER("ROLE_USER", "사용자권한"),
    UNKNOWN_USER("ROLE_UNKNOWN_USER", "알수없는 사용자");
    //생성자해서 위에 이넘벨류에 들어간다.
    private final String key;
    private final String description;

    public static Role of(String key) {
        return Arrays.stream(Role.values())
                .filter(i -> i.getKey().equals(key))
                .findAny()
                .orElse(UNKNOWN_USER);
    }
}
