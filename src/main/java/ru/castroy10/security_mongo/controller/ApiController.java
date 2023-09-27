package ru.castroy10.security_mongo.controller;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.castroy10.security_mongo.dto.AppUserDto;
import ru.castroy10.security_mongo.jwt.JwtUtil;

import java.util.Map;

@RestController
public class ApiController {

    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    public ApiController(JwtUtil jwtUtil, AuthenticationManager authenticationManager) {
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
    }


    @PostMapping("/api/gettoken")
    public Map<String, String> login(@RequestBody AppUserDto appUserDto) {
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(appUserDto.getUsername(), appUserDto.getPassword());
        try {
            authenticationManager.authenticate(auth);
        } catch (AuthenticationException e) {
            return Map.of("auth error", e.toString());
        }
        return Map.of("Вот ваш токен", jwtUtil.generateToken(appUserDto.getUsername()));
    }
}
