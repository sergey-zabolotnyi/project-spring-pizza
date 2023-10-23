package de.telran.pizza.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class DishDTOTest {

    @Test
    void dishDTO_CreatingDishObject_ExpectCorrectData() {
        // Подготовка
        Long expectedId = 10L;
        String expectedName = "Margherita";
        BigDecimal expectedPrice = new BigDecimal("10.50");
        CategoryDTO expectedCategory = CategoryDTO.builder()
                .id(2L)
                .category("Pizzas")
                .build();

        // Выполнение
        DishDTO dishDTO = DishDTO.builder()
                .id(expectedId)
                .name(expectedName)
                .price(expectedPrice)
                .category(expectedCategory)
                .build();

        // Проверка
        assertNotNull(dishDTO);
        assertEquals(expectedId, dishDTO.getId());
        assertEquals(expectedName, dishDTO.getName());
        assertEquals(expectedPrice, dishDTO.getPrice());
        assertEquals(expectedCategory, dishDTO.getCategory());
    }

    @Test
    void dishDTO_AnnotationsDescription_ExpectMatch() {
        // Подготовка
        Class<DishDTO> clazz = DishDTO.class;

        // Проверка аннотации @Schema
        Schema schemaAnnotation = clazz.getAnnotation(Schema.class);
        assertNotNull(schemaAnnotation);
        assertEquals("ДТО объект блюд в Пиццерии", schemaAnnotation.description());

        // Check the 'id' field
        try {
            java.lang.reflect.Field idField = clazz.getDeclaredField("id");
            Schema idFieldAnnotation = idField.getAnnotation(Schema.class);
            assertNotNull(idFieldAnnotation);
            assertEquals(Schema.AccessMode.READ_ONLY, idFieldAnnotation.accessMode());
            assertEquals("Идентификатор категории", idFieldAnnotation.description());
            assertEquals("10", idFieldAnnotation.example());
        } catch (NoSuchFieldException e) {
            fail("Field 'id' not found");
        }

        // Проверка поля id
        try {
            java.lang.reflect.Field nameField = clazz.getDeclaredField("name");
            Schema nameFieldAnnotation = nameField.getAnnotation(Schema.class);
            assertNotNull(nameFieldAnnotation);
            assertEquals("Название блюда", nameFieldAnnotation.description());
            assertEquals("Маргарита", nameFieldAnnotation.example());
        } catch (NoSuchFieldException e) {
            fail("Field 'name' not found");
        }

        // Проверка поля price
        try {
            java.lang.reflect.Field priceField = clazz.getDeclaredField("price");
            Schema priceFieldAnnotation = priceField.getAnnotation(Schema.class);
            assertNotNull(priceFieldAnnotation);
            assertEquals("Стоимость блюда", priceFieldAnnotation.description());
            assertEquals("10.50", priceFieldAnnotation.example());
        } catch (NoSuchFieldException e) {
            fail("Field 'price' not found");
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