package com.dizma.dizmademo.model.entity;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "orders")
public class Order extends BaseEntity{

    @Column(nullable = false, name = "order_name")
    private String orderName;
    @Column(nullable = false)
    private String buyer;

    @Column(nullable = false)
    private Integer quantityBought;

    @Column(nullable = false, name = "created_on")
    private LocalDate createdOn;
    @ManyToOne(fetch = FetchType.EAGER)
    private Product product;

    public Order() {

    }

    public Order setOrderName(String orderName) {
        this.orderName = orderName;
        return this;
    }


    public Order setBuyer(String buyer) {
        this.buyer = buyer;
        return this;
    }

    public Integer getQuantityBought() {
        return quantityBought;
    }

    public Order setQuantityBought(Integer quantityBought) {
        this.quantityBought = quantityBought;
        return this;
    }

    public Product getProduct() {
        return product;
    }

    public Order setProduct(Product product) {
        this.product = product;
        return this;
    }

    public LocalDate getCreatedOn() {
        return createdOn;
    }

    public Order setCreatedOn(LocalDate createdOn) {
        this.createdOn = createdOn;
        return this;
    }
}
