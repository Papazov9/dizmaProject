package com.dizma.dizmademo.service;

public interface OrderService {
    void createOrderByProductId(Long id, int chosenQuantity, String username);

    void deleteOldOrders();

    long getOrdersCount();
}
