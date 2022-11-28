package com.example.userservice.user.controller;

import com.example.userservice.user.dto.LoginDto;
import com.example.userservice.user.dto.Oauth2AttributeDto;
import com.example.userservice.user.dto.UserDto;
import com.example.userservice.user.entity.User;
import com.example.userservice.user.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.HashMap;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserServiceImpl userService;
    private final ModelMapper modelMapper;

    @GetMapping("/hello")
    public String index() {
        log.info("test");
        return "hello-auth";
    }

    @PostMapping("/refresh")
    public ResponseEntity<Object> newRefreshToken(HttpServletRequest request) {

        String expiredAccessToken = request.getHeader("token");
        String refreshToken = request.getHeader("refresh");

        Object newToken = userService.refreshService(expiredAccessToken, refreshToken);

        return ResponseEntity.ok(modelMapper.map(newToken, Object.class));

    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody LoginDto loginDto) throws Exception {
        UserDto info = userService.oauth2Authorization(loginDto.getCode(), loginDto.getState(), loginDto.getSocial());
        return ResponseEntity.ok(modelMapper.map(info, Object.class));
    }

    @GetMapping("/logout")
    public void logout(HttpServletRequest request, @RequestBody String social) {
        String token = request.getHeader("token");
        String refreshToken = request.getHeader("refresh");
        userService.logout(token, refreshToken, social);
    }
    @PostMapping("/save-update")
    public ResponseEntity<UserDto> saveOrUpdate(@RequestBody Oauth2AttributeDto oauth2AttributeDto) throws ParseException {
        User info = userService.userSaveOrUpdate(oauth2AttributeDto);
        UserDto userInfo = new UserDto().toDto(info);
        return ResponseEntity.ok(userInfo);
    }

    @GetMapping("/team/{userid}")
    public ResponseEntity<HashMap<String,String>> findByaddress(@PathVariable Long userid){
        return ResponseEntity.ok(userService.findById(userid));
    }

    @GetMapping("team/email/{useremail}/")
    public ResponseEntity<HashMap<String,String>> getaddressByUserEmail(@PathVariable String useremail){
        return ResponseEntity.ok(userService.findaddressbyemail(useremail));
    }
}
