package com.dizma.dizmademo.web.controller;

import com.dizma.dizmademo.model.viewModels.ProductViewModel;
import com.dizma.dizmademo.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/all")
    public String allProducts(Model model) {
        List<ProductViewModel> allProducts = this.productService.findAll();
        model.addAttribute("furniture", allProducts);

        return "all-offers";
    }
}
