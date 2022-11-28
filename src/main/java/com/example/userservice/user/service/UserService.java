package com.example.userservice.user.service;

import com.example.userservice.user.dto.Oauth2AttributeDto;
import com.example.userservice.user.dto.UserDto;
import com.example.userservice.user.entity.User;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Optional;

@Component
public interface UserService {
    User userSaveOrUpdate(Oauth2AttributeDto oauth2AttributeDto);
    Optional<User> findByEmailAndSocial(String email, String social);
    HashMap<String,String> findById(Long id);
    HashMap<String, String> findaddressbyemail(String email);

    Object refreshService(String token, String refreshToken);

    UserDto oauth2Authorization(String code, String state, String type) throws Exception;

    void logout(String token, String refreshToken, String type);
}
