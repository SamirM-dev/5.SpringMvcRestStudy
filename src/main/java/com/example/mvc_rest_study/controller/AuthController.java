package com.example.mvc_rest_study.controller;

import com.example.mvc_rest_study.dto.LoginRequest;
import com.example.mvc_rest_study.dto.TokenResponse;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody LoginRequest request, HttpServletResponse response) {
        String token = UUID.randomUUID().toString();
        ResponseCookie cookie = ResponseCookie.from("access_token",token)
                .httpOnly(true).secure(true).maxAge(3600).path("/").sameSite("Strict")
                .build();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE,cookie.toString()).body(new TokenResponse(token));
    }
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletResponse response) {
        ResponseCookie cookie = ResponseCookie.from("access_token","").maxAge(0).path("/").build();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE,cookie.toString()).build();
    }
}
