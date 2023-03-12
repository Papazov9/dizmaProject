package com.dizma.dizmademo.service.impl;

import com.dizma.dizmademo.model.binding.ProductBindingModel;
import com.dizma.dizmademo.model.entity.Category;
import com.dizma.dizmademo.model.entity.Product;
import com.dizma.dizmademo.model.enums.CategoryEnum;
import com.dizma.dizmademo.model.viewModels.ProductViewModel;
import com.dizma.dizmademo.repository.ProductRepository;
import com.dizma.dizmademo.service.CategoryService;
import com.dizma.dizmademo.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    private final CategoryService categoryService;
    private final ModelMapper modelMapper;

    public ProductServiceImpl(ProductRepository productRepository, CategoryService categoryService, ModelMapper modelMapper) {
        this.productRepository = productRepository;
        this.categoryService = categoryService;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<ProductViewModel> findAll() {
        return this.productRepository
                .findAll()
                .stream()
                .map(p -> this.modelMapper.map(p, ProductViewModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Product> findByName(String name) {
        return this.productRepository.findByName(name);
    }

    @Override
    public Product saveProduct(ProductBindingModel productBindingModel, MultipartFile file) throws IOException {
        productBindingModel.setPicture(Base64.getEncoder().encodeToString(file.getBytes()));

        Product product = this.modelMapper.map(productBindingModel, Product.class);
        CategoryEnum categoryEnum = CategoryEnum.valueOf(productBindingModel.getCategory());
        Category category = this.categoryService.findByName(categoryEnum);
        product.setCategory(category);

        return this.productRepository.saveAndFlush(product);
    }
}
