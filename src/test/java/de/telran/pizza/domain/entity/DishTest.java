package de.telran.pizza.domain.entity;

import static org.junit.jupiter.api.Assertions.*;

import io.swagger.v3.oas.annotations.media.Schema;
import org.junit.jupiter.api.Test;

import javax.persistence.*;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Set;

public class DishTest {

    @Test
    void testDishValidation() {
        Dish dish = Dish.builder()
                .nameEn("Pizza Quattro Formaggi")
                .nameRu("Пицца 4 сыра")
                .price(new BigDecimal("10.50"))
                .build();

        Set<ConstraintViolation<Dish>> violations = Validation.buildDefaultValidatorFactory().getValidator().validate(dish);
        assertTrue(violations.isEmpty());
    }

    @Test
    void testDishValidationInvalidNameEn() {
        Dish dish = Dish.builder()
                .nameEn("Pizza#123")
                .nameRu("Пицца 4 сыра")
                .price(new BigDecimal("10.50"))
                .build();

        Set<ConstraintViolation<Dish>> violations = Validation.buildDefaultValidatorFactory().getValidator().validate(dish);
        assertEquals(1, violations.size());
        assertEquals("error.dish.name_en", violations.iterator().next().getMessage());
    }

    @Test
    void testDishValidationInvalidPrice() {
        Dish dish = Dish.builder()
                .nameEn("Pizza Quattro Formaggi")
                .nameRu("Пицца 4 сыра")
                .price(new BigDecimal("-5.00"))
                .build();

        Set<ConstraintViolation<Dish>> violations = Validation.buildDefaultValidatorFactory().getValidator().validate(dish);
        assertEquals(1, violations.size());
        assertEquals("error.dish.price", violations.iterator().next().getMessage());
    }

    @Test
    void testDishAnnotations() throws NoSuchFieldException {
        Field idField = Dish.class.getDeclaredField("id");
        assertNotNull(idField.getAnnotation(Id.class));
        assertNotNull(idField.getAnnotation(GeneratedValue.class));
        assertNotNull(idField.getAnnotation(Column.class));
        assertNotNull(idField.getAnnotation(Schema.class));

        Field nameEnField = Dish.class.getDeclaredField("nameEn");
        assertNotNull(nameEnField.getAnnotation(Pattern.class));
        assertNotNull(nameEnField.getAnnotation(Column.class));
        assertNotNull(nameEnField.getAnnotation(Schema.class));

        Field nameRuField = Dish.class.getDeclaredField("nameRu");
        assertNotNull(nameRuField.getAnnotation(Pattern.class));
        assertNotNull(nameRuField.getAnnotation(Column.class));
        assertNotNull(nameRuField.getAnnotation(Schema.class));

        Field priceField = Dish.class.getDeclaredField("price");
        assertNotNull(priceField.getAnnotation(Min.class));
        assertNotNull(priceField.getAnnotation(Column.class));
        assertNotNull(priceField.getAnnotation(Schema.class));

        Field categoryField = Dish.class.getDeclaredField("category");
        assertNotNull(categoryField.getAnnotation(ManyToOne.class));
        assertNotNull(categoryField.getAnnotation(JoinColumn.class));
        assertNotNull(categoryField.getAnnotation(Schema.class));

        Field timeField = Dish.class.getDeclaredField("time");
        assertNotNull(timeField.getAnnotation(Schema.class));
    }
}