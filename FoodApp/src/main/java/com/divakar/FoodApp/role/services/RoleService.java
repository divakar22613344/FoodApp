package com.divakar.FoodApp.role.services;

import java.util.List;

import org.apache.coyote.BadRequestException;

import com.divakar.FoodApp.response.Response;
import com.divakar.FoodApp.role.dtos.RoleDTO;

public interface RoleService {
    Response<RoleDTO> createRole(RoleDTO roleDTO);
    Response<RoleDTO> updateRole(RoleDTO roleDTO) throws BadRequestException;
    Response<List<RoleDTO>> getAllRoles();
    Response<?> deleteRole(Long id) throws BadRequestException;
}
