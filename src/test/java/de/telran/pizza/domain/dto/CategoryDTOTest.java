package de.telran.pizza.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CategoryDTOTest {

    @Test
    void categoryDTO_CreatingCategoryObject_ExpectCorrectData() {
        // Подготовка
        int expectedId = 2;
        String expectedCategory = "Pizzas";

        // Выполнение
        CategoryDTO categoryDTO = CategoryDTO.builder()
                .id(expectedId)
                .category(expectedCategory)
                .build();

        // Проверка
        assertNotNull(categoryDTO);
        assertEquals(expectedId, categoryDTO.getId());
        assertEquals(expectedCategory, categoryDTO.getCategory());
    }

    @Test
    void categoryDTO_AnnotationsDescription_ExpectMatch() {
        // Подготовка
        Class<CategoryDTO> clazz = CategoryDTO.class;

        // Проверка аннотации @Schema
        Schema schemaAnnotation = clazz.getAnnotation(Schema.class);
        assertNotNull(schemaAnnotation);
        assertEquals("ДТО объект категории Пиццерии", schemaAnnotation.description());

        // Проверка поля id
        try {
            java.lang.reflect.Field idField = clazz.getDeclaredField("id");
            Schema idFieldAnnotation = idField.getAnnotation(Schema.class);
            assertNotNull(idFieldAnnotation);
            assertEquals(Schema.AccessMode.READ_ONLY, idFieldAnnotation.accessMode());
            assertEquals("Идентификатор категории", idFieldAnnotation.description());
            assertEquals("2", idFieldAnnotation.example());
        } catch (NoSuchFieldException e) {
            fail("Field 'id' not found");
        }

        // Проверка поля category
        try {
            java.lang.reflect.Field categoryField = clazz.getDeclaredField("category");
            Schema categoryFieldAnnotation = categoryField.getAnnotation(Schema.class);
            assertNotNull(categoryFieldAnnotation);
            assertEquals("Название категории", categoryFieldAnnotation.description());
            assertEquals("Пиццы", categoryFieldAnnotation.example());
        } catch (NoSuchFieldException e) {
            fail("Field 'category' not found");
        }
    }

    @Test
    void getId() {
        int id = 1;
        CategoryDTO categoryDTO = CategoryDTO.builder().id(id).build();
        int result = categoryDTO.getId();
        assertEquals(id, result);
    }

    @Test
    void getCategory() {
        String category = "Pizza";
        CategoryDTO categoryDTO = CategoryDTO.builder().category(category).build();
        String result = categoryDTO.getCategory();
        assertEquals(category, result);
    }

    @Test
    void setId() {
        int id = 1;
        CategoryDTO categoryDTO = CategoryDTO.builder().id(id).build();
        categoryDTO.setId(2);
        assertEquals(2, categoryDTO.getId());
    }

    @Test
    void setCategory() {
        String category = "Pizza";
        CategoryDTO categoryDTO = CategoryDTO.builder().category(category).build();
        categoryDTO.setCategory("Salad");
        assertEquals("Salad", categoryDTO.getCategory());
    }

    @Test
    void testEquals() {
        CategoryDTO categoryDTO1 = CategoryDTO.builder().id(1).category("Pizza").build();
        CategoryDTO categoryDTO2 = CategoryDTO.builder().id(1).category("Pizza").build();

        assertEquals(categoryDTO1, categoryDTO2);
    }

    @Test
    void canEqual() {
        CategoryDTO categoryDTO1 = CategoryDTO.builder().id(1).category("Pizza").build();
        CategoryDTO categoryDTO2 = CategoryDTO.builder().id(1).category("Pizza").build();

        assertTrue(categoryDTO1.canEqual(categoryDTO2));
    }

    @Test
    void testHashCode() {
        CategoryDTO categoryDTO1 = CategoryDTO.builder().id(1).category("Pizza").build();
        CategoryDTO categoryDTO2 = CategoryDTO.builder().id(1).category("Pizza").build();

        assertEquals(categoryDTO1.hashCode(), categoryDTO2.hashCode());
    }

    @Test
    void testToString() {
        CategoryDTO categoryDTO = CategoryDTO.builder().id(1).category("Pizza").build();
        assertNotNull(categoryDTO.toString());
    }

    @Test
    void builder() {
        CategoryDTO categoryDTO = CategoryDTO.builder().build();

        assertNotNull(categoryDTO);
    }
}