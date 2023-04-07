package com.dizma.dizmademo.service;

import com.dizma.dizmademo.model.entity.Category;
import com.dizma.dizmademo.model.entity.Role;
import com.dizma.dizmademo.model.entity.User;
import com.dizma.dizmademo.model.enums.CategoryEnum;

import java.util.List;

public interface CategoryService {
    void initCategories();

    Category findByName(CategoryEnum categoryName);

    List<Category> getAllCategories();
}
