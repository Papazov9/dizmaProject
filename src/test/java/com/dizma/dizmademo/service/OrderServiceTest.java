package com.dizma.dizmademo.service;

import com.dizma.dizmademo.model.entity.Category;
import com.dizma.dizmademo.model.entity.Order;
import com.dizma.dizmademo.model.entity.Product;
import com.dizma.dizmademo.model.enums.CategoryEnum;
import com.dizma.dizmademo.repository.OrderRepository;
import com.dizma.dizmademo.repository.ProductRepository;
import com.dizma.dizmademo.service.impl.OrderServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private OrderRepository orderRepository;

    private OrderService orderService;
    private Product adminProduct;

    private Order adminOrder;

    @BeforeEach
    void setUp() {

        this.orderService = new OrderServiceImpl(this.productRepository, this.orderRepository);

        Category category = new Category();
        category.setId(1L);
        category.setCategory(CategoryEnum.BEDROOM);

        this.adminProduct = new Product();
        adminProduct.setDescription("desc")
                .setQuantity(10)
                .setPrice(BigDecimal.valueOf(100.10))
                .setPicture("picture")
                .setCategory(category)
                .setName("Large bedroom")
                .setCreatedOn(LocalDate.now())
                .setId(1L);

        this.adminOrder = new Order();

        this.adminOrder.setOrderName(String.format("%s-%s-%d", "Admin", this.adminProduct.getName(), 1L))
                .setQuantityBought(5)
                .setBuyer("Admin")
                .setProduct(this.adminProduct)
                .setCreatedOn(LocalDate.now())
                .setId(1L);
    }

    @Test
    void testCreateOrderSuccessfully() {
        when(this.productRepository.findById(1L))
                .thenReturn(Optional.of(this.adminProduct));

        this.orderService.createOrderByProductId(1L, 5, "Admin");
    }

}
