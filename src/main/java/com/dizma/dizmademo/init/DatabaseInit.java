package com.dizma.dizmademo.init;

import com.dizma.dizmademo.service.CategoryService;
import com.dizma.dizmademo.service.RoleService;
import com.dizma.dizmademo.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DatabaseInit implements CommandLineRunner {

    private final RoleService roleService;
    private final CategoryService categoryService;

    private final UserService userService;

    public DatabaseInit(RoleService roleService, CategoryService categoryService, UserService userService) {
        this.roleService = roleService;
        this.categoryService = categoryService;
        this.userService = userService;
    }


    @Override
    public void run(String... args) {
        this.roleService.initRoles();
        this.categoryService.initCategories();
        this.userService.initFirstUser();
    }
}
