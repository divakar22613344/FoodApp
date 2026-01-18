package com.divakar.FoodApp.auth_users.services;

import org.apache.coyote.BadRequestException;

import com.divakar.FoodApp.auth_users.dtos.LoginRequest;
import com.divakar.FoodApp.auth_users.dtos.LoginResponse;
import com.divakar.FoodApp.auth_users.dtos.RegistrationRequest;
import com.divakar.FoodApp.response.Response;

public interface AuthService {
    Response<?> register(RegistrationRequest registrationRequest) throws BadRequestException;

    Response<LoginResponse> login(LoginRequest loginRequest) throws BadRequestException;
}
