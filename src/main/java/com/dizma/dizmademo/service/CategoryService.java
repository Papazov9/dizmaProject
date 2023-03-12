package com.dizma.dizmademo.service;

import com.dizma.dizmademo.model.entity.Category;
import com.dizma.dizmademo.model.enums.CategoryEnum;

public interface CategoryService {
    void initCategories();

    Category findByName(CategoryEnum categoryName);

}
