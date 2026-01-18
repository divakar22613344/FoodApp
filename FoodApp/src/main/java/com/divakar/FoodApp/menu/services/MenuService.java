package com.divakar.FoodApp.menu.services;

import java.util.List;

import org.apache.coyote.BadRequestException;

import com.divakar.FoodApp.menu.dtos.MenuDTO;
import com.divakar.FoodApp.response.Response;

public interface MenuService {
    Response<MenuDTO> createMenu(MenuDTO menuDTO) throws BadRequestException;
    Response<MenuDTO> updateMenu(MenuDTO menuDTO);
    Response<MenuDTO> getMenuById(Long id);
    Response<?> deleteMenu(Long id);
    Response<List<MenuDTO>> getMenus(Long categoryId, String search);
}
