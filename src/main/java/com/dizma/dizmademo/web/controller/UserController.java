package com.dizma.dizmademo.web.controller;

import com.dizma.dizmademo.exceptions.UserNotFoundException;
import com.dizma.dizmademo.model.entity.User;
import com.dizma.dizmademo.model.user.DizmaUserDetails;
import com.dizma.dizmademo.model.viewModels.UserViewModel;
import com.dizma.dizmademo.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.Optional;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    private final ModelMapper modelMapper;

    public UserController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/profile")
    public String profileInfo(Principal principal, Model model) throws UserNotFoundException {
        String username = principal.getName();


        User user = this.userService.findByUsername(username).orElseThrow(() -> new UserNotFoundException("User with username: " + username + " not found!"));
        UserViewModel userView = this.modelMapper.map(user, UserViewModel.class);
                model.addAttribute("user", userView);

        return "user-profile";
    }

    @PostMapping("/changeUsername/{newUsername}")
    public String changeUsername(@PathVariable("newUsername") String newUsername, RedirectAttributes redirectAttributes) throws UserNotFoundException {
        Optional<User> byUsername = this.userService.findByUsername(newUsername);



        if (byUsername.isPresent()) {
            redirectAttributes.addFlashAttribute("userExists", true);
            return "redirect:/user/profile";
        }



        this.userService.changeUsername(newUsername);

        return "redirect:/user/profile";
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(UserNotFoundException.class)
    public String productNotFound() {
        return "error";
    }
}
