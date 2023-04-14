package com.dizma.dizmademo.web.controller;

import com.dizma.dizmademo.exceptions.ProductNotFoundException;
import com.dizma.dizmademo.model.binding.ProductBindingModel;
import com.dizma.dizmademo.model.viewModels.ProductViewModel;
import com.dizma.dizmademo.service.OrderService;
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
import java.security.Principal;

@Controller
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;
    private final ModelMapper modelMapper;
    private final OrderService orderService;

    public ProductController(ProductService productService, ModelMapper modelMapper, OrderService orderService) {
        this.productService = productService;
        this.modelMapper = modelMapper;
        this.orderService = orderService;
    }

    @GetMapping("/all")
    public String allProducts(Model model,
                              @PageableDefault(
                                      sort = "createdOn",
                                      direction = Sort.Direction.DESC,
                                      page = 0,
                                      size = 8
                              )Pageable pageable) {
        Page<ProductViewModel> allProducts = this.productService.findAllWithQuantityMoreThanZero(pageable);
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
            ProductBindingModel productEdit = this.modelMapper.map(this.productService.findById(id), ProductBindingModel.class);
            model.addAttribute("productEdit", productEdit);


        return "edit";
    }

    @GetMapping("/buyIt/{id}/{chosenQuantity}")
    public String buyNow(@PathVariable("id") Long id,@PathVariable int chosenQuantity, Principal principal) {
        this.orderService.createOrderByProductId(id,chosenQuantity, principal.getName());

        return "successful-page";
    }

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
    public String productNotFound() {
        return "error";
    }
}
