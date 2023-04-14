package com.dizma.dizmademo.service;

import com.dizma.dizmademo.exceptions.ProductNotFoundException;
import com.dizma.dizmademo.model.binding.ProductBindingModel;
import com.dizma.dizmademo.model.entity.Category;
import com.dizma.dizmademo.model.entity.Product;
import com.dizma.dizmademo.model.enums.CategoryEnum;
import com.dizma.dizmademo.model.viewModels.ProductViewModel;
import com.dizma.dizmademo.repository.ProductRepository;
import com.dizma.dizmademo.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private  ProductRepository productRepository;

    @Mock
    private  CategoryService categoryService;

    @Mock
    private  ModelMapper modelMapper;

    private ProductService productService;

    private MultipartFile multipartFile = new MockMultipartFile("file", "file", "image/jpg", "file".getBytes());
    private Product adminProduct;

    private ProductViewModel productViewModel;

    private ProductBindingModel productBindingModel;

    @BeforeEach
    void setUp() {
        Category category = new Category();
        category.setId(1L);
        category.setCategory(CategoryEnum.BEDROOM);
        this.productService = new ProductServiceImpl(productRepository, categoryService, modelMapper);
        this.adminProduct = new Product();
        adminProduct.setDescription("desc")
                .setQuantity(10)
                .setPrice(BigDecimal.valueOf(100.10))
                .setPicture("picture")
                .setCategory(category)
                .setName("Large bedroom")
                .setCreatedOn(LocalDate.now())
                .setId(1L);

        this.productViewModel = new ProductViewModel();
        this.productViewModel.setPicture(adminProduct.getPicture())
                .setDescription(adminProduct.getDescription())
                .setName(adminProduct.getName())
                .setCategory(adminProduct.getCategory())
                .setId(adminProduct.getId())
                .setCreatedOn(adminProduct.getCreatedOn())
                .setQuantity(adminProduct.getQuantity())
                .setPrice(adminProduct.getPrice());

        this.productBindingModel = new ProductBindingModel();
        this.productBindingModel.setPicture(adminProduct.getPicture())
                .setCategory(adminProduct.getCategory().getCategory().name())
                .setDescription(adminProduct.getDescription())
                .setName(adminProduct.getName())
                .setPrice(adminProduct.getPrice())
                .setQuantity(adminProduct.getQuantity())
                .setId(adminProduct.getId());

    }

    @Test
    void findProductByName() {
        when(productRepository.findByName(adminProduct.getName()))
                .thenReturn(Optional.of(adminProduct));

        Optional<Product> byName = this.productService.findByName(adminProduct.getName());

        Assertions.assertTrue(byName.isPresent());
        Assertions.assertEquals(byName.get().getName(), adminProduct.getName());
    }

    @Test
    void findProductById() {
        when(productRepository.findById(adminProduct.getId()))
                .thenReturn(Optional.of(adminProduct));
        Optional<Product> optionalProduct = this.productRepository.findById(adminProduct.getId());

        Assertions.assertTrue(optionalProduct.isPresent());
        when(modelMapper.map(optionalProduct.get(), ProductViewModel.class))
                .thenReturn(productViewModel);

        ProductViewModel byId = this.productService.findById(adminProduct.getId());
        Assertions.assertEquals(productViewModel.getId(), byId.getId());
    }

    @Test
    void deleteProductSuccess() {
        when(productRepository.findById(adminProduct.getId()))
                .thenReturn(Optional.of(adminProduct));
        this.productService.deleteOfferById(adminProduct.getId());
        verify(productRepository).delete(adminProduct);
    }

    @Test
    void deleteProductFailedWrongId() {
        long newID = 3L;
        Assertions.assertThrows(ProductNotFoundException.class, () -> this.productService.deleteOfferById(newID));
    }
    @Test
    void  saveProductSuccess() throws IOException {
        when(modelMapper.map(productBindingModel, Product.class))
                .thenReturn(adminProduct);
        when(categoryService.findByName(CategoryEnum.valueOf(adminProduct.getCategory().getCategory().name())))
                .thenReturn(adminProduct.getCategory());

        this.productService.saveProduct(productBindingModel, multipartFile);

        verify(this.productRepository).saveAndFlush(adminProduct);
    }

    @Test
    void editProduct() throws IOException {

        when(this.productRepository.findById(1L))
                .thenReturn(Optional.of(this.adminProduct));

        when(this.categoryService.findByName(this.adminProduct.getCategory().getCategory()))
                .thenReturn(new Category());

        this.productService.editProduct(1L, productBindingModel, multipartFile);

        verify(this.productRepository).save(this.adminProduct);
    }
}
