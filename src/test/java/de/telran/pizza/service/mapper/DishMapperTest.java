package de.telran.pizza.service.mapper;

import de.telran.pizza.domain.dto.CategoryDTO;
import de.telran.pizza.domain.dto.DishDTO;
import de.telran.pizza.domain.entity.Cart;
import de.telran.pizza.domain.entity.Category;
import de.telran.pizza.domain.entity.Dish;
import de.telran.pizza.domain.entity.Login;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class DishMapperTest {
    @Test
    public void testDishListToDtoList() {
        // Создаем фиктивные данные для теста
        List<Dish> dishList = Arrays.asList(
                new Dish(1, "Dish1", "Блюдо1", new BigDecimal("11.00"), new Category(),
                        LocalDateTime.now()),
                new Dish(2, "Dish2", "Блюдо2", new BigDecimal("12.00"), new Category(),
                        LocalDateTime.now())
        );

        // Вызываем метод, который мы хотим протестировать
        List<DishDTO> result = DishMapper.dishListToDtoList(dishList);

        // Проверяем, что результат соответствует ожидаемому
        assertEquals(2, result.size());
        assertEquals("Блюдо1", result.get(0).getName());
        assertEquals("Блюдо2", result.get(1).getName());
    }

    @Test
    public void testDishToDishDto() {
        // Создаем фиктивные данные для теста
        Dish dish = new Dish(1, "Dish1", "Блюдо1", new BigDecimal("11.00"), new Category(),
                LocalDateTime.now());

        // Вызываем метод, который мы хотим протестировать
        DishDTO result = DishMapper.dishToDishDto(dish);

        // Проверяем, что результат соответствует ожидаемому
        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("Блюдо1", result.getName());
        assertEquals(new BigDecimal("11.00"), result.getPrice());
    }

    @Test
    public void testGetDishTotalPrice() {
        // Создаем фиктивные данные для теста
        List<DishDTO> dishDTOList = Arrays.asList(
                new DishDTO(1, "Dish1", new BigDecimal("10.00"), new CategoryDTO()),
                new DishDTO(2, "Dish2", new BigDecimal("20.00"), new CategoryDTO())
        );

        // Вызываем метод, который мы хотим протестировать
        BigDecimal result = DishMapper.getDishTotalPrice(dishDTOList);

        // Проверяем, что результат соответствует ожидаемому
        assertEquals(new BigDecimal("30.00"), result);
    }

    @Test
    public void testListDishesDTOtoCart() {
        // Создаем фиктивные данные для теста
        List<Cart> cartList = Arrays.asList(
                new Cart(1, new Login(), new Dish(1, "Dish1", "Блюдо1",
                        new BigDecimal("11.00"), new Category(), LocalDateTime.now())),
                new Cart(2, new Login(), new Dish(2, "Dish2", "Блюдо2",
                        new BigDecimal("12.00"), new Category(), LocalDateTime.now()))
        );

        // Вызываем метод, который мы хотим протестировать
        List<DishDTO> result = DishMapper.listDishesDTOtoCart(cartList);

        // Проверяем, что результат соответствует ожидаемому
        assertEquals(2, result.size());
        assertEquals("Блюдо1", result.get(0).getName());
        assertEquals("Блюдо2", result.get(1).getName());
    }
}