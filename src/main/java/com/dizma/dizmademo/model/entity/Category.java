package com.dizma.dizmademo.model.entity;

import com.dizma.dizmademo.model.enums.CategoryEnum;

import javax.persistence.*;

@Entity
@Table(name = "categories")
public class Category extends BaseEntity{

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CategoryEnum category;

    public Category() {

    }

    public CategoryEnum getCategory() {
        return category;
    }

    public void setCategory(CategoryEnum category) {
        this.category = category;
    }
}
