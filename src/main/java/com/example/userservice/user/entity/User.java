package com.example.userservice.user.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 30, unique = true)
    private String username; // 아이디

    @Column(nullable = false, length = 100)
    private String password;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false, length = 50)
    private String email;

    @Column(nullable = false)
    private String profile;

    private String field;

    private int year;

    public User(String username, String password, String nickname, String email){
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.email = email;
        this.profile = "비전공자";
    }

    public User(String username, String password, String nickname, String email, int year){
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.email = email;
        this.profile = "대학생";
        this.year = year;
    }

    public User(String username, String password, String nickname, String email, String profile, String field ,int year){
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.email = email;
        this.profile = profile;
        this.field = field;
        this.year = year;
    }
}
