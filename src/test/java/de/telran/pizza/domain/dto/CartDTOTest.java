package de.telran.pizza.domain.dto;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

public class CartDTOTest {

    // Тест на пустую корзину
    @Test
    void testEmptyCart() {
        // Arrange
        CartDTO cart = CartDTO.builder()
                .dishes(List.of())
                .totalPrice(BigDecimal.ZERO)
                .build();

        // Act & Assert
        assertTrue(cart.getDishes().isEmpty());
        assertEquals(BigDecimal.ZERO, cart.getTotalPrice());
    }

    // Тест на корзину с блюдами
    @Test
    void testCartWithDishes() {
        // Arrange
        DishDTO dish1 = DishDTO.builder().name("Pizza Margherita").price(BigDecimal.valueOf(10)).build();
        DishDTO dish2 = DishDTO.builder().name("Pasta Carbonara").price(BigDecimal.valueOf(8)).build();

        CartDTO cart = CartDTO.builder()
                .dishes(List.of(dish1, dish2))
                .totalPrice(BigDecimal.valueOf(18))
                .build();

        // Act & Assert
        assertEquals(2, cart.getDishes().size());
        assertEquals(dish1, cart.getDishes().get(0));
        assertEquals(dish2, cart.getDishes().get(1));
        assertEquals(BigDecimal.valueOf(18), cart.getTotalPrice());
    }

    // Тест на корзину с нулевыми блюдами
    @Test
    void testNullDishes() {
        // Arrange
        CartDTO cart = CartDTO.builder()
                .dishes(null)
                .totalPrice(BigDecimal.valueOf(5))
                .build();

        // Act & Assert
        assertNull(cart.getDishes());
        assertEquals(BigDecimal.valueOf(5), cart.getTotalPrice());
    }
}