package com.dizma.dizmademo.web.controller.rest;

import com.dizma.dizmademo.model.viewModels.ProductViewModel;
import com.dizma.dizmademo.model.viewModels.UserViewModel;
import com.dizma.dizmademo.service.ProductService;
import com.dizma.dizmademo.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class RestApiController {

    private final UserService userService;

    public RestApiController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserViewModel>> getAllUsers() {
        List<UserViewModel> allUsers = this.userService.findAllUsers();

        if (allUsers.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(allUsers, HttpStatus.OK);
    }
 }
