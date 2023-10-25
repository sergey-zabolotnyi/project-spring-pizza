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
import java.time.LocalDateTime;
import java.util.Set;

public class OrdersTest {

    @Test
    void testOrdersValidation() {
        Orders orders = Orders.builder()
                .login(Login.builder().id(7).build())
                .totalPrice(new BigDecimal("45.99"))
                .status(Status.NEW)
                .build();

        Set<ConstraintViolation<Orders>> violations = Validation.buildDefaultValidatorFactory().getValidator().validate(orders);
        assertTrue(violations.isEmpty());
    }

    @Test
    void testOrdersValidationInvalidTotalPrice() {
        Orders orders = Orders.builder()
                .login(Login.builder().id(5).build())
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

    @Test
    void getId() {
        Orders orders = Orders.builder().id(1).build();
        assertEquals(1, orders.getId());
    }

    @Test
    void getLogin() {
        Login login = Login.builder().id(7).build();
        Orders orders = Orders.builder().login(login).build();
        assertEquals(login, orders.getLogin());
    }

    @Test
    void getTotalPrice() {
        Orders orders = Orders.builder().totalPrice(new BigDecimal("45.99")).build();
        assertEquals(new BigDecimal("45.99"), orders.getTotalPrice());
    }

    @Test
    void getStatus() {
        Orders orders = Orders.builder().status(Status.NEW).build();
        assertEquals(Status.NEW, orders.getStatus());
    }

    @Test
    void getTime() {
        LocalDateTime time = LocalDateTime.now();
        Orders orders = Orders.builder().time(time).build();
        assertEquals(time, orders.getTime());
    }

    @Test
    void setId() {
        Orders orders = Orders.builder().build();
        orders.setId(2);
        assertEquals(2, orders.getId());
    }

    @Test
    void setLogin() {
        Login login = Login.builder().id(8).build();
        Orders orders = Orders.builder().build();
        orders.setLogin(login);
        assertEquals(login, orders.getLogin());
    }

    @Test
    void setTotalPrice() {
        Orders orders = Orders.builder().build();
        orders.setTotalPrice(new BigDecimal("55.99"));
        assertEquals(new BigDecimal("55.99"), orders.getTotalPrice());
    }

    @Test
    void setStatus() {
        Orders orders = Orders.builder().build();
        orders.setStatus(Status.COOKING);
        assertEquals(Status.COOKING, orders.getStatus());
    }

    @Test
    void setTime() {
        LocalDateTime newTime = LocalDateTime.now();
        Orders orders = Orders.builder().build();
        orders.setTime(newTime);
        assertEquals(newTime, orders.getTime());
    }

    @Test
    void testEquals() {
        Orders orders1 = Orders.builder().id(1).build();
        Orders orders2 = Orders.builder().id(1).build();
        assertEquals(orders1, orders2);
    }

    @Test
    void canEqual() {
        Orders orders1 = Orders.builder().id(1).build();
        Orders orders2 = Orders.builder().id(1).build();
        assertTrue(orders1.canEqual(orders2));
    }

    @Test
    void testHashCode() {
        Orders orders1 = Orders.builder().id(1).build();
        Orders orders2 = Orders.builder().id(1).build();
        assertEquals(orders1.hashCode(), orders2.hashCode());
    }

    @Test
    void testToString() {
        Orders orders = Orders.builder().id(1).build();
        String result = orders.toString();
        assertNotNull(result);
    }

    @Test
    void builder() {
        Orders orders = Orders.builder().id(1).build();
        assertNotNull(orders);
    }
}