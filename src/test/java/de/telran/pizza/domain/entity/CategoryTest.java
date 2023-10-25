package de.telran.pizza.domain.entity;

import static org.junit.jupiter.api.Assertions.*;

import io.swagger.v3.oas.annotations.media.Schema;
import org.junit.jupiter.api.Test;

public class CategoryTest {

    @Test
    void categoryBuilder_CreateCategory_ReturnsCategoryWithCorrectValues() {
        // Arrange
        int expectedId = 4;
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
        Class<Category> categoryClass = Category.class;
        Schema schemaAnnotation = categoryClass.getAnnotation(Schema.class);
        assertNotNull(schemaAnnotation);
        assertEquals("Объект категории пиццерии", schemaAnnotation.description());
    }

    @Test
    void categoryId_IsGenerated() {
        Category category = new Category();
        category.setId(1);
        assertEquals(1, category.getId());
    }

    @Test
    void getId() {
        int id = 1;
        Category category = Category.builder().id(id).build();
        int result = category.getId();
        assertEquals(id, result);
    }

    @Test
    void getCategoryEn() {
        String categoryEn = "Pizza";
        Category category = Category.builder().categoryEn(categoryEn).build();
        String result = category.getCategoryEn();
        assertEquals(categoryEn, result);
    }

    @Test
    void getCategoryRu() {
        String categoryRu = "Пицца";
        Category category = Category.builder().categoryRu(categoryRu).build();
        String result = category.getCategoryRu();
        assertEquals(categoryRu, result);
    }

    @Test
    void setId() {
        int id = 1;
        Category category = Category.builder().build();
        category.setId(id);
        assertEquals(id, category.getId());
    }

    @Test
    void setCategoryEn() {
        String categoryEn = "Pizza";
        Category category = Category.builder().build();
        category.setCategoryEn(categoryEn);
        assertEquals(categoryEn, category.getCategoryEn());
    }

    @Test
    void setCategoryRu() {
        String categoryRu = "Пицца";
        Category category = Category.builder().build();
        category.setCategoryRu(categoryRu);
        assertEquals(categoryRu, category.getCategoryRu());
    }

    @Test
    void testEquals() {
        Category category1 = Category.builder().build();
        Category category2 = Category.builder().build();
        assertEquals(category1, category2);
    }

    @Test
    void canEqual() {
        Category category1 = Category.builder().build();
        Category category2 = Category.builder().build();
        assertTrue(category1.canEqual(category2));
    }

    @Test
    void testHashCode() {
        Category category1 = Category.builder().build();
        Category category2 = Category.builder().build();
        assertEquals(category1.hashCode(), category2.hashCode());
    }

    @Test
    void testToString() {
        Category category = Category.builder().build();
        String result = category.toString();
        assertNotNull(result);
    }

    @Test
    void builder() {
        Category category = Category.builder().build();
        assertNotNull(category);
    }
}