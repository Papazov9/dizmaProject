package com.dizma.dizmademo.model.entity;


import javax.persistence.*;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "products")
public class Product extends BaseEntity{
    @Column(nullable = false, unique = true)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    @Positive
    private BigDecimal price;

    @Column(columnDefinition = "LONGTEXT")
    private String picture;

    @Column(nullable = false)
    private LocalDate createdOn;

    @Column(nullable = false)
    @PositiveOrZero
    private Integer quantity;

    @ManyToOne
    private Category category;

    public Product() {

    }

    public String getName() {
        return name;
    }

    public Product setName(String name) {
        this.name = name;
        return this;
    }

    public Product setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public Product setQuantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }

    public Product setCategory(Category category) {
        this.category = category;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Category getCategory() {
        return category;
    }


    public String getPicture() {
        return picture;
    }

    public Product setPicture(String picture) {
        this.picture = picture;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Product setDescription(String description) {
        this.description = description;
        return this;
    }

    public LocalDate getCreatedOn() {
        return createdOn;
    }

    public Product setCreatedOn(LocalDate createdOn) {
        this.createdOn = createdOn;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(name, product.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
