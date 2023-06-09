package com.dizma.dizmademo.repository;

import com.dizma.dizmademo.model.entity.Category;
import com.dizma.dizmademo.model.enums.CategoryEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findByCategory(CategoryEnum categoryName);
}
