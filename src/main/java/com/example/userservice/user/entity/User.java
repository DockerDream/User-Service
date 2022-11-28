package com.example.userservice.user.entity;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;

@Data
@Entity
@Getter
@NoArgsConstructor
@DynamicInsert
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "social")
    private String social;

    @Column(nullable = true)
    private String name;

    @Column(name = "email", nullable = false)
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

    public User update(String name, String picture, String social) {
        this.name = name;
        this.picture = picture;
        this.social = social;

        return this;
    }

    public String getRoleKey() {
        return this.role.getKey();
    }
}
