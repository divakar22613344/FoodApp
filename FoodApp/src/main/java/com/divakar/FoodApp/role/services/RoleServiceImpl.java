package com.divakar.FoodApp.role.services;

import java.util.List;

import javax.ws.rs.NotFoundException;

import org.apache.coyote.BadRequestException;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.divakar.FoodApp.response.Response;
import com.divakar.FoodApp.role.dtos.RoleDTO;
import com.divakar.FoodApp.role.entity.Role;
import com.divakar.FoodApp.role.repository.RoleRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;
    
    @Override
    public Response<RoleDTO> createRole(RoleDTO roleDTO) {

        Role role = modelMapper.map(roleDTO,Role.class);

        Role savedRole = roleRepository.save(role);

        return Response.<RoleDTO>builder().statusCode(HttpStatus.OK.value()).message("Role Created Successfully").data(modelMapper.map(savedRole,RoleDTO.class)).build();
       
    }

    @Override
    public Response<RoleDTO> updateRole(RoleDTO roleDTO) throws BadRequestException {

        Role existingRole = roleRepository.findById(roleDTO.getId()).orElseThrow(() -> new NotFoundException("Role not found"));
        if(roleRepository.findByName(roleDTO.getName()).isPresent()){
            throw new BadRequestException("Role with name already exists");
        }

        existingRole.setName(roleDTO.getName());
        Role updatedRole = roleRepository.save(existingRole);

        return Response.<RoleDTO>builder().statusCode(HttpStatus.OK.value()).message("Role Updated Successfully").data(modelMapper.map(updatedRole,RoleDTO.class)).build();


        
    }

    @Override
    public Response<List<RoleDTO>> getAllRoles() {
        List<Role> roles = roleRepository.findAll();

        List<RoleDTO> roleDTOS = roles.stream()
                                .map(role->modelMapper.map(role,RoleDTO.class))
                                .toList();
        

        return Response.<List<RoleDTO>>builder().statusCode(HttpStatus.OK.value()).message("Role Retrieved Successfully").data(roleDTOS).build();                      

    }

    @Override
    public Response<?> deleteRole(Long id) throws BadRequestException {
        if(!roleRepository.existsById(id)){
            throw new NotFoundException("Rolde Does Not Exists");
        }

        roleRepository.deleteById(id);

        return Response.builder().statusCode(HttpStatus.OK.value()).message("Role Deleted Successfully").build(); 
    }

}
