package com.dizma.dizmademo.web.controller;

import com.dizma.dizmademo.exceptions.UserNotFoundException;
import com.dizma.dizmademo.model.binding.AddRoleBindingModel;
import com.dizma.dizmademo.model.binding.ProductBindingModel;
import com.dizma.dizmademo.model.entity.Product;
import com.dizma.dizmademo.model.entity.Role;
import com.dizma.dizmademo.model.entity.User;
import com.dizma.dizmademo.model.viewModels.UserViewModel;
import com.dizma.dizmademo.service.CategoryService;
import com.dizma.dizmademo.service.ProductService;
import com.dizma.dizmademo.service.RoleService;
import com.dizma.dizmademo.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final ProductService productService;
    private final UserService userService;

    private final RoleService roleService;

    private final ModelMapper modelMapper;

    public AdminController(ProductService productService, UserService userService, RoleService roleService, ModelMapper modelMapper) {
        this.productService = productService;
        this.userService = userService;
        this.roleService = roleService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/add-product")
    public String addProduct() {
        return "add-product";
    }

    @PostMapping("/add-product")
    public String addProduct(@Valid ProductBindingModel productBindingModel,
                             BindingResult bindingResult, RedirectAttributes redirectAttributes,
                             @RequestParam("file") MultipartFile file) throws IOException {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("productBindingModel", productBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.productBindingModel", bindingResult);
            return "redirect:add-product";
        }

        Optional<Product> product = this.productService.findByName(productBindingModel.getName());

        if (product.isPresent()) {
            redirectAttributes.addFlashAttribute("productExists", true);
            redirectAttributes.addFlashAttribute("productBindingModel", productBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.productBindingModel", bindingResult);
            return "redirect:add-product";
        }

        this.productService.saveProduct(productBindingModel, file);

        return "redirect:/products/all";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = {"/delete/{id}"}, method = { RequestMethod.GET, RequestMethod.DELETE})
    public String deleteOffer(@PathVariable Long id){
        this.productService.deleteOfferById(id);

        return "redirect:/products/all";
    }

    @GetMapping("/users")
    public String adminUsers() {
        return "admin-users";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{username}/delete")
    public String deleteUser(@PathVariable String username, Principal principal, HttpServletRequest request) throws  ServletException {
        UserViewModel userViewModel = userService.deleteByUsername(username);

        if (userViewModel.getUsername().equals(principal.getName())) {
            request.logout();
            return "index";
        }

        return "redirect:/admin/users";
    }

    @GetMapping("/{username}/addRole")
    public String addRole(@PathVariable String username, Model model) {
        User user = this.userService
                .findByUsername(username)
                .orElseThrow(() ->
                        new UserNotFoundException(String.format("User with username: %s was not found!", username)));
        AddRoleBindingModel userModel = this.modelMapper.map(user, AddRoleBindingModel.class);
        model.addAttribute("possibleRolesToAdd", this.roleService.getPossibleRolesToAdd(user));

        model.addAttribute("userModel", userModel);

        return "add-role";
    }

    @GetMapping("/{username}/removeRole")
    public String removeRole(@PathVariable String username, Model model) {
        User user = this.userService
                .findByUsername(username)
                .orElseThrow(() ->
                        new UserNotFoundException(String.format("User with username: %s was not found!", username)));
        AddRoleBindingModel userModel = this.modelMapper.map(user, AddRoleBindingModel.class);

        List<Role> rolesToRemove = this.roleService.getRolesToRemove(user);
        model.addAttribute("rolesToRemove", rolesToRemove);

        model.addAttribute("userModel", userModel);

        return "remove-role";
    }

    @PostMapping("/{username}/addRole")
    public String updateRole(@Valid AddRoleBindingModel userModel,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes,
                             @PathVariable String username) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("userModel", userModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userModel", bindingResult);
            redirectAttributes.addFlashAttribute("isRoleNull", true);
            return "redirect:/admin/{username}/addRole";
        }
        this.userService.addRole(username, userModel);

        return "redirect:/admin/users";
    }

    @PostMapping("/{username}/removeRole")
    public String removeRole(@Valid AddRoleBindingModel userModel,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes,
                             @PathVariable String username) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("userModel", userModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userModel", bindingResult);
            return "redirect:/admin/{username}/removeRole";
        }
        if (!this.userService.removeRole(username, userModel)){
            redirectAttributes.addFlashAttribute("userModel", userModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userModel", bindingResult);
            redirectAttributes.addFlashAttribute("isLastRole", true);
            return "redirect:/admin/{username}/removeRole";
        }

        return "redirect:/admin/users";
    }

    @ModelAttribute
    public ProductBindingModel productBindingModel() {
        return new ProductBindingModel();
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(UserNotFoundException.class)
    public String productNotFound() {
        return "error";
    }
}
