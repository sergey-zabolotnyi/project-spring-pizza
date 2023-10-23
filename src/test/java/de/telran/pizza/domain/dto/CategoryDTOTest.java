package de.telran.pizza.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CategoryDTOTest {

    @Test
    void categoryDTO_CreatingCategoryObject_ExpectCorrectData() {
        // Подготовка
        Long expectedId = 2L;
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
}