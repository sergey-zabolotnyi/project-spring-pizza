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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
                .nameEn("P^izza%$#")
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

    @Test
    void getId() {
        int id = 1;
        Dish dish = Dish.builder().id(id).build();
        int result = dish.getId();
        assertEquals(id, result);
    }

    @Test
    void getNameEn() {
        String nameEn = "Margherita";
        Dish dish = Dish.builder().nameEn(nameEn).build();
        String result = dish.getNameEn();
        assertEquals(nameEn, result);
    }

    @Test
    void getNameRu() {
        String nameRu = "Маргарита";
        Dish dish = Dish.builder().nameRu(nameRu).build();
        String result = dish.getNameRu();
        assertEquals(nameRu, result);
    }

    @Test
    void getPrice() {
        BigDecimal price = BigDecimal.valueOf(10.50);
        Dish dish = Dish.builder().price(price).build();
        BigDecimal result = dish.getPrice();
        assertEquals(price, result);
    }

    @Test
    void getCategory() {
        Category category = Category.builder().id(1).build();
        Dish dish = Dish.builder().category(category).build();
        Category result = dish.getCategory();
        assertEquals(category, result);
    }

    @Test
    void getTime() {
        LocalTime time = LocalTime.of(12, 30);
        LocalDateTime dateTime = LocalDateTime.of(LocalDate.now(), time);
        Dish dish = Dish.builder().time(dateTime).build();
        LocalDateTime result = dish.getTime();
        assertEquals(dateTime, result);
    }

    @Test
    void setId() {
        int id = 1;
        Dish dish = Dish.builder().build();
        dish.setId(id);
        assertEquals(id, dish.getId());
    }

    @Test
    void setNameEn() {
        String nameEn = "Margherita";
        Dish dish = Dish.builder().build();
        dish.setNameEn(nameEn);
        assertEquals(nameEn, dish.getNameEn());
    }

    @Test
    void setNameRu() {
        String nameRu = "Маргарита";
        Dish dish = Dish.builder().build();
        dish.setNameRu(nameRu);
        assertEquals(nameRu, dish.getNameRu());
    }

    @Test
    void setPrice() {
        BigDecimal price = BigDecimal.valueOf(10.50);
        Dish dish = Dish.builder().build();
        dish.setPrice(price);
        assertEquals(price, dish.getPrice());
    }

    @Test
    void setCategory() {
        Category category = Category.builder().id(1).build();
        Dish dish = Dish.builder().build();
        dish.setCategory(category);
        assertEquals(category, dish.getCategory());
    }

    @Test
    void setTime() {
        LocalDateTime dateTime = LocalDateTime.now();
        Dish dish = Dish.builder().build();
        dish.setTime(dateTime);
        assertEquals(dateTime, dish.getTime());
    }

    @Test
    void testEquals() {
        Dish dish1 = Dish.builder().build();
        Dish dish2 = Dish.builder().build();
        assertEquals(dish1, dish2);
    }

    @Test
    void canEqual() {
        Dish dish1 = Dish.builder().build();
        Dish dish2 = Dish.builder().build();
        assertTrue(dish1.canEqual(dish2));
    }

    @Test
    void testHashCode() {
        Dish dish1 = Dish.builder().build();
        Dish dish2 = Dish.builder().build();
        assertEquals(dish1.hashCode(), dish2.hashCode());
    }

    @Test
    void testToString() {
        Dish dish = Dish.builder().build();
        String result = dish.toString();
        assertNotNull(result);
    }

    @Test
    void builder() {
        Dish dish = Dish.builder().build();
        assertNotNull(dish);
    }
}