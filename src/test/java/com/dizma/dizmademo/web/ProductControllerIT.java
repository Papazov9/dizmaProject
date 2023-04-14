package com.dizma.dizmademo.web;

import com.dizma.dizmademo.model.binding.ProductBindingModel;
import com.dizma.dizmademo.model.entity.Category;
import com.dizma.dizmademo.model.entity.Product;
import com.dizma.dizmademo.model.entity.User;
import com.dizma.dizmademo.model.enums.CategoryEnum;
import com.dizma.dizmademo.model.viewModels.ProductViewModel;
import com.dizma.dizmademo.repository.ProductRepository;
import com.dizma.dizmademo.service.OrderService;
import com.dizma.dizmademo.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @MockBean
    private ModelMapper modelMapper;
    @MockBean
    private OrderService orderService;

    @MockBean
    private ProductRepository productRepository;


    private Product adminProduct;

    private ProductViewModel productViewModel;

    private ProductBindingModel productBindingModel;

    @BeforeEach
    void setUp() {
        Category category = new Category();
        category.setCategory(CategoryEnum.BEDROOM);
        this.adminProduct = new Product();
        this.adminProduct.setQuantity(10)
                .setPrice(BigDecimal.valueOf(100))
                .setName("bedroom")
                .setPicture("picture")
                .setCategory(category)
                .setDescription("desc")
                .setCreatedOn(LocalDate.now())
                .setId(1L);

        this.productViewModel =
                new ProductViewModel(1L,
                        "bedroom",
                        "desc",
                        BigDecimal.valueOf(100),
                        "picture",
                        LocalDate.now(),
                        10,
                        category);

        this.productBindingModel = new ProductBindingModel();
        this.productBindingModel.setId(1L)
                .setQuantity(10)
                .setName("bedroom")
                .setPrice(BigDecimal.valueOf(100))
                .setDescription("desc")
                .setPicture("picture")
                .setCategory(category.getCategory().name());
    }

    @Test
    @WithMockUser(username = "Member1", roles = {"MEMBER"})
    void testGetProductById() throws Exception{
        when(this.productService.findById(1L))
                .thenReturn(this.productViewModel);

        mockMvc.perform(get("/products/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(view().name("product-details"));
    }

    @Test
    @WithMockUser(username = "Admin1", roles = {"ADMIN"})
    void testEditProductById() throws Exception {
        when(this.productService.findById(1L))
                .thenReturn(this.productViewModel);

        when(this.modelMapper.map(this.productService.findById(1L), ProductBindingModel.class))
                .thenReturn(this.productBindingModel);

        mockMvc.perform(get("/products/edit/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(view().name("edit"));
    }
}
