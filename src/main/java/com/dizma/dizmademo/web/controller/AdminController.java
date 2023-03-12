package com.dizma.dizmademo.web.controller;

import com.dizma.dizmademo.model.binding.ProductBindingModel;
import com.dizma.dizmademo.model.entity.Product;
import com.dizma.dizmademo.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Base64;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final ProductService productService;

    public AdminController(ProductService productService) {
        this.productService = productService;
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
            return "redirect:add-product";
        }

        this.productService.saveProduct(productBindingModel, file);

        return "redirect:/products/all";
    }

    @ModelAttribute
    public ProductBindingModel productBindingModel() {
        return new ProductBindingModel();
    }
}
