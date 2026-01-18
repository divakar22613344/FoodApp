package com.divakar.FoodApp.auth_users.controller;

import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.divakar.FoodApp.auth_users.dtos.LoginRequest;
import com.divakar.FoodApp.auth_users.dtos.LoginResponse;
import com.divakar.FoodApp.auth_users.dtos.RegistrationRequest;
import com.divakar.FoodApp.auth_users.services.AuthService;
import com.divakar.FoodApp.response.Response;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<Response<?>> register(@RequestBody @Valid RegistrationRequest registrationRequest)
            throws BadRequestException {
        return ResponseEntity.ok(authService.register(registrationRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<Response<LoginResponse>> register(@RequestBody @Valid LoginRequest loginRequest)
            throws BadRequestException {
        return ResponseEntity.ok(authService.login(loginRequest));
    }

}
