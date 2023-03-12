package com.dizma.dizmademo.model.binding;

import com.dizma.dizmademo.model.enums.CategoryEnum;

import javax.validation.constraints.*;
import java.math.BigDecimal;

public class ProductBindingModel {

    @NotBlank(message = "The name is missing!")
    @Size(min = 3, message = "The name should be at least 3 characters long.")
    private String name;

    @NotBlank(message = "The price is missing!")
    @Positive(message = "The price should be positive!")
    private BigDecimal price;

    @NotNull(message = "The category is missing!")
    private String category;

    private String picture;

    @Min(value = 0, message = "The quantity should be 0 or higher!")
    private Integer quantity;

    public ProductBindingModel() {

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
}
