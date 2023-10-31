package de.telran.pizza.domain.dto;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CartDTOTest {

    // Тест на пустую корзину
    @Test
    void testEmptyCart() {
        CartDTO cart = CartDTO.builder()
                .dishes(Arrays.asList())
                .totalPrice(BigDecimal.ZERO)
                .build();

        assertTrue(cart.getDishes().isEmpty());
        assertEquals(BigDecimal.ZERO, cart.getTotalPrice());
    }

    // Тест на корзину с блюдами
    @Test
    void testCartWithDishes() {
        DishDTO dish1 = DishDTO.builder().name("Pizza Margherita").price(BigDecimal.valueOf(10)).build();
        DishDTO dish2 = DishDTO.builder().name("Pasta Carbonara").price(BigDecimal.valueOf(8)).build();

        CartDTO cart = CartDTO.builder()
                .dishes(Arrays.asList(dish1, dish2))
                .totalPrice(BigDecimal.valueOf(18))
                .build();

        assertEquals(2, cart.getDishes().size());
        assertEquals(dish1, cart.getDishes().get(0));
        assertEquals(dish2, cart.getDishes().get(1));
        assertEquals(BigDecimal.valueOf(18), cart.getTotalPrice());
    }

    // Тест на корзину с нулевыми блюдами
    @Test
    void testNullDishes() {
        CartDTO cart = CartDTO.builder()
                .dishes(null)
                .totalPrice(BigDecimal.valueOf(5))
                .build();

        assertNull(cart.getDishes());
        assertEquals(BigDecimal.valueOf(5), cart.getTotalPrice());
    }
    @Test
    void getDishes() {
        // Подготовка данных
        List<DishDTO> dishes = new ArrayList<>();
        dishes.add(new DishDTO());
        dishes.add(new DishDTO());
        CartDTO cartDTO = CartDTO.builder()
                .dishes(dishes)
                .totalPrice(BigDecimal.ZERO)
                .build();

        // Действие (вызов метода)
        List<DishDTO> result = cartDTO.getDishes();

        // Проверка результата
        assertEquals(dishes, result);
    }

    @Test
    void getTotalPrice() {
        // Подготовка данных
        BigDecimal totalPrice = BigDecimal.valueOf(50.0);
        CartDTO cartDTO = CartDTO.builder()
                .dishes(new ArrayList<>())
                .totalPrice(totalPrice)
                .build();

        // Действие (вызов метода)
        BigDecimal result = cartDTO.getTotalPrice();

        // Проверка результата
        assertEquals(totalPrice, result);
    }

    @Test
    void setDishes() {
        // Подготовка данных
        List<DishDTO> newDishes = new ArrayList<>();
        newDishes.add(new DishDTO());
        CartDTO cartDTO = CartDTO.builder()
                .dishes(new ArrayList<>())
                .totalPrice(BigDecimal.ZERO)
                .build();

        // Действие (вызов метода)
        cartDTO.setDishes(newDishes);

        // Проверка результата
        assertEquals(newDishes, cartDTO.getDishes());
    }

    @Test
    void setTotalPrice() {
        // Подготовка данных
        BigDecimal newTotalPrice = BigDecimal.valueOf(100.0);
        CartDTO cartDTO = CartDTO.builder()
                .dishes(new ArrayList<>())
                .totalPrice(BigDecimal.ZERO)
                .build();

        // Действие (вызов метода)
        cartDTO.setTotalPrice(newTotalPrice);

        // Проверка результата
        assertEquals(newTotalPrice, cartDTO.getTotalPrice());
    }

    @Test
    void testEquals() {
        // Подготовка данных
        CartDTO cartDTO1 = CartDTO.builder()
                .dishes(new ArrayList<>())
                .totalPrice(BigDecimal.ZERO)
                .build();
        CartDTO cartDTO2 = CartDTO.builder()
                .dishes(new ArrayList<>())
                .totalPrice(BigDecimal.ZERO)
                .build();

        // Действие (вызов метода) и проверка результата
        assertEquals(cartDTO1, cartDTO2);
    }

    @Test
    void canEqual() {
        // Подготовка данных
        CartDTO cartDTO1 = CartDTO.builder()
                .dishes(new ArrayList<>())
                .totalPrice(BigDecimal.ZERO)
                .build();
        CartDTO cartDTO2 = CartDTO.builder()
                .dishes(new ArrayList<>())
                .totalPrice(BigDecimal.ZERO)
                .build();

        // Действие (вызов метода) и проверка результата
        assertTrue(cartDTO1.canEqual(cartDTO2));
    }

    @Test
    void testHashCode() {
        // Подготовка данных
        CartDTO cartDTO1 = CartDTO.builder()
                .dishes(new ArrayList<>())
                .totalPrice(BigDecimal.ZERO)
                .build();
        CartDTO cartDTO2 = CartDTO.builder()
                .dishes(new ArrayList<>())
                .totalPrice(BigDecimal.ZERO)
                .build();

        // Действие (вызов метода) и проверка результата
        assertEquals(cartDTO1.hashCode(), cartDTO2.hashCode());
    }

    @Test
    void testToString() {
        // Подготовка данных
        CartDTO cartDTO = CartDTO.builder()
                .dishes(new ArrayList<>())
                .totalPrice(BigDecimal.ZERO)
                .build();

        // Действие (вызов метода) и проверка результата
        assertNotNull(cartDTO.toString());
    }

    @Test
    void builder() {
        // Действие (вызов метода)
        CartDTO cartDTO = CartDTO.builder().build();

        // Проверка результата
        assertNotNull(cartDTO);
    }
}