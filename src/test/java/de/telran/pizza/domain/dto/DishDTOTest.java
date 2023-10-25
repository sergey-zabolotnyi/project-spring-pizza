package de.telran.pizza.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class DishDTOTest {

    @Test
    void dishDTO_CreatingDishObject_ExpectCorrectData() {
        // Подготовка
        int expectedId = 10;
        String expectedName = "Margherita";
        BigDecimal expectedPrice = new BigDecimal("10.50");
        CategoryDTO expectedCategory = CategoryDTO.builder()
                .id(2)
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
    @Test
    void getId() {
        int id = 1;
        DishDTO dishDTO = DishDTO.builder().id(id).build();
        int result = dishDTO.getId();
        assertEquals(id, result);
    }

    @Test
    void getName() {
        String name = "Pepperoni";
        DishDTO dishDTO = DishDTO.builder().name(name).build();
        String result = dishDTO.getName();
        assertEquals(name, result);
    }

    @Test
    void getPrice() {
        BigDecimal price = BigDecimal.valueOf(10.50);
        DishDTO dishDTO = DishDTO.builder().price(price).build();
        BigDecimal result = dishDTO.getPrice();
        assertEquals(price, result);
    }

    @Test
    void getCategory() {
        CategoryDTO categoryDTO = CategoryDTO.builder().id(1).category("Pizza").build();
        DishDTO dishDTO = DishDTO.builder().category(categoryDTO).build();
        CategoryDTO result = dishDTO.getCategory();
        assertEquals(categoryDTO, result);
    }

    @Test
    void setId() {
        int id = 1;
        DishDTO dishDTO = DishDTO.builder().id(id).build();
        dishDTO.setId(2);
        assertEquals(2, dishDTO.getId());
    }

    @Test
    void setName() {
        String name = "Pepperoni";
        DishDTO dishDTO = DishDTO.builder().name(name).build();
        dishDTO.setName("Margherita");
        assertEquals("Margherita", dishDTO.getName());
    }

    @Test
    void setPrice() {
        BigDecimal price = BigDecimal.valueOf(10.50);
        DishDTO dishDTO = DishDTO.builder().price(price).build();
        dishDTO.setPrice(BigDecimal.valueOf(11.0));
        assertEquals(BigDecimal.valueOf(11.0), dishDTO.getPrice());
    }

    @Test
    void setCategory() {
        CategoryDTO categoryDTO1 = CategoryDTO.builder().id(1).category("Pizza").build();
        CategoryDTO categoryDTO2 = CategoryDTO.builder().id(2).category("Salad").build();
        DishDTO dishDTO = DishDTO.builder().category(categoryDTO1).build();
        dishDTO.setCategory(categoryDTO2);
        assertEquals(categoryDTO2, dishDTO.getCategory());
    }

    @Test
    void testEquals() {
        DishDTO dishDTO1 = DishDTO.builder().id(1).name("Margherita").price(BigDecimal.valueOf(10.50)).build();
        DishDTO dishDTO2 = DishDTO.builder().id(1).name("Margherita").price(BigDecimal.valueOf(10.50)).build();
        assertEquals(dishDTO1, dishDTO2);
    }

    @Test
    void canEqual() {
        DishDTO dishDTO1 = DishDTO.builder().id(1).name("Margherita").price(BigDecimal.valueOf(10.50)).build();
        DishDTO dishDTO2 = DishDTO.builder().id(1).name("Margherita").price(BigDecimal.valueOf(10.50)).build();
        assertTrue(dishDTO1.canEqual(dishDTO2));
    }

    @Test
    void testHashCode() {
        DishDTO dishDTO1 = DishDTO.builder().id(1).name("Margherita").price(BigDecimal.valueOf(10.50)).build();
        DishDTO dishDTO2 = DishDTO.builder().id(1).name("Margherita").price(BigDecimal.valueOf(10.50)).build();
        assertEquals(dishDTO1.hashCode(), dishDTO2.hashCode());
    }

    @Test
    void testToString() {
        DishDTO dishDTO = DishDTO.builder().id(1).name("Маргарита").price(BigDecimal.valueOf(10.50)).build();
        assertNotNull(dishDTO.toString());
    }

    @Test
    void builder() {
        DishDTO dishDTO = DishDTO.builder().build();
        assertNotNull(dishDTO);
    }
}