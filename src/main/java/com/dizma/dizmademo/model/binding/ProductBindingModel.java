package com.dizma.dizmademo.model.binding;

import com.dizma.dizmademo.model.enums.CategoryEnum;

import javax.validation.constraints.*;
import java.math.BigDecimal;

public class ProductBindingModel {

    private Long id;
    @NotBlank(message = "The name is missing!")
    @Size(min = 3, message = "The name should be at least 3 characters long.")
    private String name;

    @Positive(message = "The price should be positive!")
    @NotNull(message = "The price is missing!")
    private BigDecimal price;

    @Min(value = 0, message = "The quantity should be 0 or higher!")
    @NotNull(message = "The quantity is missing!")
    private Integer quantity;

    private String description;

    private String picture;

    @NotBlank(message = "No category is selected!")
    private String category;


    public ProductBindingModel() {

    }

    public Long getId() {
        return id;
    }

    public ProductBindingModel setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public ProductBindingModel setName(String name) {
        this.name = name;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public ProductBindingModel setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public String getCategory() {
        return category;
    }

    public ProductBindingModel setCategory(String category) {
        this.category = category;
        return this;
    }

    public String getPicture() {
        return picture;
    }

    public ProductBindingModel setPicture(String picture) {
        this.picture = picture;
        return this;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public ProductBindingModel setQuantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public ProductBindingModel setDescription(String description) {
        this.description = description;
        return this;
    }
}
