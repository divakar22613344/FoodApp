package com.divakar.FoodApp.menu.controller;

import java.util.List;

import org.apache.coyote.BadRequestException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.divakar.FoodApp.menu.dtos.MenuDTO;
import com.divakar.FoodApp.menu.services.MenuService;
import com.divakar.FoodApp.response.Response;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/menu")
@RequiredArgsConstructor
public class MenuController {

    private final MenuService menuService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response<MenuDTO>> createMenu(@ModelAttribute @Valid MenuDTO menuDTO, @RequestPart(value="imageFile", required = true)MultipartFile imagFile) throws BadRequestException{
        menuDTO.setImageFile(imagFile);
        return ResponseEntity.ok(menuService.createMenu(menuDTO));
    }


    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response<MenuDTO>> updateMenu(@ModelAttribute @Valid MenuDTO menuDTO,
            @RequestPart(value = "imageFile", required = false) MultipartFile imagFile) throws BadRequestException {
        menuDTO.setImageFile(imagFile);
        return ResponseEntity.ok(menuService.updateMenu(menuDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<MenuDTO>> getMenuById(@PathVariable Long id){
        return ResponseEntity.ok(menuService.getMenuById(id));
    }


    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response<?>> deleteMenu(@PathVariable Long id) {
        return ResponseEntity.ok(menuService.deleteMenu(id));
    }


    @GetMapping
    public ResponseEntity<Response<List<MenuDTO>>> getMenus(@RequestParam(required=false) Long categoryId, @RequestParam(required=false) String search) {
        return ResponseEntity.ok(menuService.getMenus(categoryId,search));
    }
}
