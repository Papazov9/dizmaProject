package com.dizma.dizmademo.service.impl;

import com.dizma.dizmademo.exceptions.ProductNotFoundException;
import com.dizma.dizmademo.model.entity.Order;
import com.dizma.dizmademo.model.entity.Product;
import com.dizma.dizmademo.repository.OrderRepository;
import com.dizma.dizmademo.repository.ProductRepository;
import com.dizma.dizmademo.service.OrderService;
import com.dizma.dizmademo.service.UserService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    private final ProductRepository productRepository;

    private final OrderRepository orderRepository;


    public OrderServiceImpl(ProductRepository productRepository, OrderRepository orderRepository) {
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
    }

    @Override
    public void createOrderByProductId(Long id, int chosenQuantity, String username) {
        Optional<Product> product = this.productRepository.findById(id);

        if (product.isEmpty()) {
            throw new ProductNotFoundException(String.format("Product with id: %d was not found!", id), id);
        }

        product.get().setQuantity(product.get().getQuantity() - chosenQuantity);
        this.productRepository.saveAndFlush(product.get());

        Order order = new Order();

        order.setProduct(product.get())
                .setBuyer(username)
                .setCreatedOn(LocalDate.of(2020, 12, 10))
                .setQuantityBought(chosenQuantity)
                .setOrderName(String.format("%s-%s-%d",username, product.get().getName(), id));
        this.orderRepository.saveAndFlush(order);
    }

    @Override
    public void deleteOldOrders() {
        this.orderRepository
                .deleteAll(this.orderRepository
                .findAllByCreatedOnBefore(LocalDate.now().minusDays(30)));
    }

    @Override
    public long getOrdersCount() {
        return this.orderRepository.count();
    }
}
