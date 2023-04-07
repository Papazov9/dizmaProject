package com.dizma.dizmademo.web.controller;

import com.dizma.dizmademo.exceptions.ProductNotFoundException;
import com.dizma.dizmademo.model.binding.ProductBindingModel;
import com.dizma.dizmademo.model.viewModels.ProductViewModel;
import com.dizma.dizmademo.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.IOException;

@Controller
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;
    private final ModelMapper modelMapper;

    public ProductController(ProductService productService, ModelMapper modelMapper) {
        this.productService = productService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/all")
    public String allProducts(Model model,
                              @PageableDefault(
                                      sort = "createdOn",
                                      direction = Sort.Direction.DESC,
                                      page = 0,
                                      size = 6
                              )Pageable pageable) {
        Page<ProductViewModel> allProducts = this.productService.findAll(pageable);
        model.addAttribute("furniture", allProducts);

        return "all-offers";
    }

    @GetMapping("/{id}")
    public String detailsProduct(Model model, @PathVariable("id") Long id) {
            ProductViewModel productDetails = this.productService.findById(id);
            model.addAttribute("productDetails", productDetails);

        return "product-details";
    }

    @GetMapping("/edit/{id}")
    public String editProduct(Model model, @PathVariable Long id) {
        try {
            ProductBindingModel productEdit = this.modelMapper.map(this.productService.findById(id), ProductBindingModel.class);
            model.addAttribute("productEdit", productEdit);
        } catch (ProductNotFoundException e) {
            model.addAttribute("message", e.getMessage());
            return "error";
        }

        return "edit";
    }

//    @GetMapping("/add-cart/{id]")
//    public String addToCart(@PathVariable("id") Long id)

    @PostMapping("/edit/{id}")
    public String editProductConfirm(@Valid ProductBindingModel productEdit,
                                     BindingResult bindingResult, RedirectAttributes redirectAttributes,
                                     @RequestParam("file")MultipartFile file, @PathVariable Long id) throws IOException {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("productEdit", productEdit);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.productEdit", bindingResult);

            return "redirect:/products/edit/{id}";
        }

        this.productService.editProduct(id, productEdit, file);

        return "redirect:/products/{id}";
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ProductNotFoundException.class)
    public String productNotFound(ProductNotFoundException exception, Model model) {
        model.addAttribute("message", exception.getMessage());

        return "error";
    }
}
