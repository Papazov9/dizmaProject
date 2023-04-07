package com.dizma.dizmademo.service;

import com.dizma.dizmademo.model.entity.Category;
import com.dizma.dizmademo.model.enums.CategoryEnum;
import com.dizma.dizmademo.repository.CategoryRepository;
import com.dizma.dizmademo.service.impl.CategoryServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.when;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    private CategoryService categoryService;

    @BeforeEach
    void setUp() {
        this.categoryService = new CategoryServiceImpl(categoryRepository);
    }

    @Test
    public void testAllCategories() {
        Category categoryLivingRoom = new Category();
        categoryLivingRoom.setCategory(CategoryEnum.LIVING_ROOM);
        categoryLivingRoom.setId(2L);

        Category categoryBedroom = new Category();
        categoryBedroom.setCategory(CategoryEnum.BEDROOM);
        categoryBedroom.setId(1L);

        Category categoryBathroom = new Category();
        categoryBedroom.setCategory(CategoryEnum.BATHROOM);
        categoryBedroom.setId(3L);

        Category categoryOffice = new Category();
        categoryBedroom.setCategory(CategoryEnum.OFFICE);
        categoryBedroom.setId(4L);

        Category categoryKitchen = new Category();
        categoryBedroom.setCategory(CategoryEnum.KITCHEN);
        categoryBedroom.setId(4L);

        List<Category> entities = List.of(categoryBedroom, categoryLivingRoom, categoryBathroom, categoryOffice, categoryKitchen);

        when(categoryRepository.findAll())
                .thenReturn(entities);

        List<Category> allCategories = this.categoryService.getAllCategories();
        Assertions.assertEquals(entities.size(), allCategories.size());
        Assertions.assertTrue(allCategories.stream().anyMatch(category -> category.getId().equals(categoryLivingRoom.getId())));
    }

    @Test
    public void testGetCategoryByName() {

        Category categoryBedroom = new Category();
        categoryBedroom.setCategory(CategoryEnum.BEDROOM);
        categoryBedroom.setId(1L);

        when(categoryRepository.findByCategory(CategoryEnum.BEDROOM))
                .thenReturn(categoryBedroom);

        Category toTest = this.categoryService.findByName(categoryBedroom.getCategory());
        Assertions.assertEquals(categoryBedroom.getId(), toTest.getId());
        Assertions.assertEquals(categoryBedroom.getCategory(), toTest.getCategory());
    }
}
