package com.dizma.dizmademo.service.impl;
import com.dizma.dizmademo.exceptions.ProductNotFoundException;
import com.dizma.dizmademo.model.binding.ProductBindingModel;
import com.dizma.dizmademo.model.entity.Category;
import com.dizma.dizmademo.model.entity.Product;
import com.dizma.dizmademo.model.enums.CategoryEnum;
import com.dizma.dizmademo.model.viewModels.ProductViewModel;
import com.dizma.dizmademo.repository.ProductRepository;
import com.dizma.dizmademo.service.CategoryService;
import com.dizma.dizmademo.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.time.LocalDate;
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
    public Page<ProductViewModel> findAll(Pageable pageable) {
        return this.productRepository
                .findAll(pageable)
                .map(this::map);
    }

    private ProductViewModel map(Product product) {
        String picture = product.getPicture();
        return modelMapper.map(product, ProductViewModel.class).setPicture(picture);
    }

    @Override
    public Optional<Product> findByName(String name) {
        return this.productRepository.findByName(name);
    }

    @Override
    public Product saveProduct(ProductBindingModel productBindingModel, MultipartFile file) throws IOException {
        productBindingModel.setPicture(Base64.getEncoder().encodeToString(file.getBytes()));

        Product product = this.modelMapper.map(productBindingModel, Product.class);
        product.setCreatedOn(LocalDate.now());
        CategoryEnum categoryEnum = CategoryEnum.valueOf(productBindingModel.getCategory());
        Category category = this.categoryService.findByName(categoryEnum);
        product.setCategory(category);

        return this.productRepository.saveAndFlush(product);

    }

    @Override
    public ProductViewModel findById(Long id){
        Product product = this.productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("Product not found!", id));


        return this.modelMapper.map(product, ProductViewModel.class);

    }

    @Override
    @Transactional
    public void editProduct(Long id, ProductBindingModel productEdit, MultipartFile file) throws ProductNotFoundException, IOException {
        Product productToEdit = this.productRepository
                .findById(id)
                .orElseThrow(() ->
                        new ProductNotFoundException("Unable to find product with this id!", id));

        if (!file.isEmpty()) {
            productToEdit.setPicture(Base64.getEncoder().encodeToString(file.getBytes()));
        }
        CategoryEnum categoryEnum = CategoryEnum.valueOf(productEdit.getCategory());
        Category newCategory = this.categoryService.findByName(categoryEnum);

        productToEdit.setCategory(newCategory);
        productToEdit
                .setName(productEdit.getName())
                .setPrice(productEdit.getPrice())
                .setQuantity(productEdit.getQuantity())
                .setDescription(productEdit.getDescription());

        this.productRepository.save(productToEdit);
    }

    @Override
    @Transactional
    public void deleteOfferById(Long id) throws ProductNotFoundException {
        Product product = this.productRepository
                .findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found!", id));

        this.productRepository.delete(product);
    }

}
