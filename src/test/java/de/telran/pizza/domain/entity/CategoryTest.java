package de.telran.pizza.domain.entity;

import static org.junit.jupiter.api.Assertions.*;

import io.swagger.v3.oas.annotations.media.Schema;
import org.junit.jupiter.api.Test;

public class CategoryTest {

    @Test
    void categoryBuilder_CreateCategory_ReturnsCategoryWithCorrectValues() {
        // Arrange
        Long expectedId = 4L;
        String expectedCategoryEn = "Pizza";
        String expectedCategoryRu = "Пицца";

        // Act
        Category category = Category.builder()
                .id(expectedId)
                .categoryEn(expectedCategoryEn)
                .categoryRu(expectedCategoryRu)
                .build();

        // Assert
        assertNotNull(category);
        assertEquals(expectedId, category.getId());
        assertEquals(expectedCategoryEn, category.getCategoryEn());
        assertEquals(expectedCategoryRu, category.getCategoryRu());
    }

    @Test
    void categorySchemaDescription_IsCorrect() {
        // Arrange
        Class<Category> categoryClass = Category.class;

        // Act
        Schema schemaAnnotation = categoryClass.getAnnotation(Schema.class);

        // Assert
        assertNotNull(schemaAnnotation);
        assertEquals("Объект категории пиццерии", schemaAnnotation.description());
    }

    @Test
    void categoryId_IsGenerated() {
        // Arrange
        Category category = new Category();

        // Assert
        assertNull(category.getId());

        // Act
        category.setId(1L);

        // Assert
        assertEquals(1L, category.getId());
    }
}