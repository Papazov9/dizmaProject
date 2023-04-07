package com.dizma.dizmademo.web.controller;

import com.dizma.dizmademo.model.binding.UserRegistrationBinding;
import com.dizma.dizmademo.service.UserService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/user")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String register(){
        return "register";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @PostMapping("/register")
    public String registerConfirm(@Valid UserRegistrationBinding userRegistrationBinding, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()
                || !userRegistrationBinding.getPassword().equals(userRegistrationBinding.getConfirmPassword())
                || this.userService.isUserExists(userRegistrationBinding)) {
            redirectAttributes.addFlashAttribute("userRegistrationBinding", userRegistrationBinding);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userRegistrationBinding", bindingResult);

            return "redirect:register";
        }

        this.userService.registerAndLogin(userRegistrationBinding);

        return "redirect:/";
    }

    @PostMapping("/login-error")
    public String onFailedLogin(@ModelAttribute(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY) String username,
                                RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY, username);
        redirectAttributes.addFlashAttribute("badCredentials", true);

        return "redirect:/user/login";
    }

    @ModelAttribute
    private UserRegistrationBinding registrationBinding(){
        return new UserRegistrationBinding();
    }

}
