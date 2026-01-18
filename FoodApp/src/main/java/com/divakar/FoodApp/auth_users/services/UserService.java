package com.divakar.FoodApp.auth_users.services;

import java.util.List;

import org.apache.coyote.BadRequestException;

import com.divakar.FoodApp.auth_users.dtos.UserDTO;
import com.divakar.FoodApp.auth_users.entity.User;
import com.divakar.FoodApp.response.Response;

public interface UserService {

    User getCurrentLoggedInUser();

    Response<List<UserDTO>> getAllUsers();

    Response<UserDTO> getOwnAccountDetails();

    Response<?> updateOwnAccount(UserDTO userDTO) throws BadRequestException;

    Response<?> deactivateOwnAccount();

}
