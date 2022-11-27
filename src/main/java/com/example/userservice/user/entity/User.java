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
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String social;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column
    private String picture;

    @Enumerated
    @Column(nullable = false)
    private Role role;

    @Column(nullable = false)
    private String profile;

    private String field;

    private int year;

    @Builder
    public User(String name, String social, String email, String picture, Role role){
        this.name = name;
        this.social = social;
        this.email = email;
        this.picture = picture;
        this.role = role;
    }

    public void updateProfile(String profile, String field, int year){
        this.profile = profile;
        this.field = field;
        this.year = year;
    }

    public User update(String name, String picture) {
        this.name = name;
        this.picture = picture;

        return this;
    }

    public String getRoleKey() {
        return this.role.getKey();
    }
}
