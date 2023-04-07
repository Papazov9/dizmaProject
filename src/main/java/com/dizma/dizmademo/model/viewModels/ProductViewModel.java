package com.dizma.dizmademo.model.viewModels;

import com.dizma.dizmademo.model.entity.Category;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class ProductViewModel {

    private Long id;

    private String name;

    private String description;

    private BigDecimal price;

    private String picture;

    private LocalDate createdOn;

    private Integer quantity;

    private Category category;

    public ProductViewModel(){

    }

    public ProductViewModel(Long id, String name, String description, BigDecimal price, String picture, LocalDate createdOn, Integer quantity, Category category) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.picture = picture;
        this.createdOn = createdOn;
        this.quantity = quantity;
        this.category = category;
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

    public String getDescription() {
        return description;
    }

    public ProductViewModel setDescription(String description) {
        this.description = description;
        return this;
    }

    public LocalDate getCreatedOn() {
        return createdOn;
    }

    public ProductViewModel setCreatedOn(LocalDate createdOn) {
        this.createdOn = createdOn;
        return this;
    }
}
