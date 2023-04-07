package com.dizma.dizmademo.service.impl;

import com.dizma.dizmademo.model.entity.Category;
import com.dizma.dizmademo.model.entity.Role;
import com.dizma.dizmademo.model.entity.User;
import com.dizma.dizmademo.model.enums.CategoryEnum;
import com.dizma.dizmademo.repository.CategoryRepository;
import com.dizma.dizmademo.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void initCategories() {
        if (this.categoryRepository.count() == 0) {
            Arrays.stream(CategoryEnum.values()).forEach(c -> {
                Category currentCategory = new Category();
                currentCategory.setCategory(c);

                this.categoryRepository.saveAndFlush(currentCategory);
            });
        }
    }

    @Override
    public List<Category> getAllCategories() {
        return this.categoryRepository.findAll();
    }

    @Override
    public Category findByName(CategoryEnum categoryName) {

        return this.categoryRepository.findByCategory(categoryName);
    }


}
