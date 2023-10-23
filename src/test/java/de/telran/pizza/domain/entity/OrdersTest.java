package de.telran.pizza.domain.entity;

import static org.junit.jupiter.api.Assertions.*;

import com.fasterxml.jackson.annotation.JsonFormat;
import de.telran.pizza.domain.entity.enums.Status;
import io.swagger.v3.oas.annotations.media.Schema;
import org.junit.jupiter.api.Test;

import javax.persistence.*;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Set;

public class OrdersTest {

    @Test
    void testOrdersValidation() {
        Orders orders = Orders.builder()
                .login(Login.builder().id(7L).build())
                .totalPrice(new BigDecimal("45.99"))
                .status(Status.NEW)
                .build();

        Set<ConstraintViolation<Orders>> violations = Validation.buildDefaultValidatorFactory().getValidator().validate(orders);
        assertTrue(violations.isEmpty());
    }

    @Test
    void testOrdersValidationInvalidTotalPrice() {
        Orders orders = Orders.builder()
                .login(Login.builder().id(7L).build())
                .totalPrice(new BigDecimal("-10.00"))
                .status(Status.NEW)
                .build();

        Set<ConstraintViolation<Orders>> violations = Validation.buildDefaultValidatorFactory().getValidator().validate(orders);
        assertEquals(1, violations.size());
        assertEquals("error.orders.totalPrice", violations.iterator().next().getMessage());
    }

    @Test
    void testOrdersAnnotations() throws NoSuchFieldException {
        Field idField = Orders.class.getDeclaredField("id");
        assertNotNull(idField.getAnnotation(Id.class));
        assertNotNull(idField.getAnnotation(GeneratedValue.class));
        assertNotNull(idField.getAnnotation(Column.class));
        assertNotNull(idField.getAnnotation(Schema.class));

        Field loginField = Orders.class.getDeclaredField("login");
        assertNotNull(loginField.getAnnotation(ManyToOne.class));
        assertNotNull(loginField.getAnnotation(JoinColumn.class));
        assertNotNull(loginField.getAnnotation(Schema.class));

        Field totalPriceField = Orders.class.getDeclaredField("totalPrice");
        assertNotNull(totalPriceField.getAnnotation(Schema.class));

        Field statusField = Orders.class.getDeclaredField("status");
        assertNotNull(statusField.getAnnotation(Enumerated.class));
        assertNotNull(statusField.getAnnotation(Schema.class));

        Field timeField = Orders.class.getDeclaredField("time");
        assertNotNull(timeField.getAnnotation(Schema.class));
        assertNotNull(timeField.getAnnotation(JsonFormat.class));
    }
}