package com.dizma.dizmademo.model.entity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "orders")
public class Order extends BaseEntity{

    @ManyToOne
    private Product product;

    @ManyToOne
    private User client;

    private Integer quantityBought;

}
