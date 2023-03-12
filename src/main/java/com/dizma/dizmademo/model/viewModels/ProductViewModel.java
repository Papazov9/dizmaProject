package com.dizma.dizmademo.model.viewModels;

import com.dizma.dizmademo.model.entity.Category;

import java.math.BigDecimal;

public class ProductViewModel {

    private Long id;

    private String name;

    private BigDecimal price;

    private String picture;

    private Integer quantity;

    private Category category;

    public ProductViewModel(){

    }

    public Long getId() {
        return id;
    }

    public ProductViewModel setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public ProductViewModel setName(String name) {
        this.name = name;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public ProductViewModel setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public String getPicture() {
        return picture;
    }

    public ProductViewModel setPicture(String picture) {
        this.picture = picture;
        return this;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public ProductViewModel setQuantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }

    public Category getCategory() {
        return category;
    }

    public ProductViewModel setCategory(Category category) {
        this.category = category;
        return this;
    }
}
