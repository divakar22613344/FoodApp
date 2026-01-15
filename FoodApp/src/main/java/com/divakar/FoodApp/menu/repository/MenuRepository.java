package com.divakar.FoodApp.menu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.divakar.FoodApp.menu.entity.Menu;

public interface MenuRepository extends JpaRepository<Menu,Long>, JpaSpecificationExecutor<Menu> {

}
