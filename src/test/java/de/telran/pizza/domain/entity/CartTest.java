package de.telran.pizza.domain.entity;

import static org.junit.jupiter.api.Assertions.*;

import io.swagger.v3.oas.annotations.media.Schema;
import org.junit.jupiter.api.Test;

public class CartTest {

    @Test
    void cartBuilder_CreateCart_ReturnsCartWithCorrectValues() {
        // Arrange
        Long expectedId = 1L;
        Long expectedLoginId = 2L;
        Long expectedDishId = 3L;

        // Act
        Cart cart = Cart.builder()
                .id(expectedId)
                .login(Login.builder().id(expectedLoginId).build())
                .dish(Dish.builder().id(expectedDishId).build())
                .build();

        // Assert
        assertNotNull(cart);
        assertEquals(expectedId, cart.getId());
        assertNotNull(cart.getLogin());
        assertEquals(expectedLoginId, cart.getLogin().getId());
        assertNotNull(cart.getDish());
        assertEquals(expectedDishId, cart.getDish().getId());
    }

    @Test
    void cartSchemaDescription_IsCorrect() {
        // Arrange
        Class<Cart> cartClass = Cart.class;

        // Act
        Schema schemaAnnotation = cartClass.getAnnotation(Schema.class);

        // Assert
        assertNotNull(schemaAnnotation);
        assertEquals("Объект корзины пользователя", schemaAnnotation.description());
    }

    @Test
    void cartId_IsGenerated() {
        // Arrange
        Cart cart = new Cart();

        // Assert
        assertNull(cart.getId());

        // Act
        cart.setId(1L);

        // Assert
        assertEquals(1L, cart.getId());
    }
}