package de.telran.pizza.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import org.junit.jupiter.api.Test;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import static org.junit.jupiter.api.Assertions.*;

public class ItemDTOTest {

    private Validator validator;

    public ItemDTOTest() {
        // Создаем фабрику валидаторов
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        // Получаем валидатор из фабрики
        validator = factory.getValidator();
    }

    @Test
    void itemDTO_ValidationSuccess() {
        // Подготовка
        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setItemId(20L);

        // Валидация
        var violations = validator.validate(itemDTO);

        // Проверка
        assertTrue(violations.isEmpty());
    }

    @Test
    void itemDTO_ValidationFailure_ItemIdLessThanOne() {
        // Подготовка
        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setItemId(0L);

        // Валидация
        var violations = validator.validate(itemDTO);

        // Проверка
        assertEquals(1, violations.size());
        assertEquals("error.itemDTO", violations.iterator().next().getMessage());
    }

    @Test
    void itemDTO_ValidationFailure_ItemIdNull() {
        // Подготовка
        ItemDTO itemDTO = new ItemDTO();

        // Валидация
        var violations = validator.validate(itemDTO);

        // Проверка
        assertEquals(0, violations.size());
    }

    @Test
    void itemDTO_SchemaDescriptionMatches() {
        // Подготовка
        Class<ItemDTO> clazz = ItemDTO.class;

        // Проверка аннотации @Schema
        Schema schemaAnnotation = clazz.getAnnotation(Schema.class);
        assertNotNull(schemaAnnotation);
        assertEquals("ДТО объект блюд в Корзине", schemaAnnotation.description());

        // Проверка поля 'itemId'
        try {
            java.lang.reflect.Field itemIdField = clazz.getDeclaredField("itemId");
            Schema itemIdFieldAnnotation = itemIdField.getAnnotation(Schema.class);
            assertNotNull(itemIdFieldAnnotation);
            assertEquals(Schema.AccessMode.READ_ONLY, itemIdFieldAnnotation.accessMode());
            assertEquals("Идентификатор категории", itemIdFieldAnnotation.description());
            assertEquals("20", itemIdFieldAnnotation.example());
        } catch (NoSuchFieldException e) {
            fail("Поле 'itemId' не найдено");
        }
    }
}