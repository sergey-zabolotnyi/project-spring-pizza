package de.telran.pizza.service;

import de.telran.pizza.domain.dto.DishDTO;
import de.telran.pizza.domain.entity.Category;
import de.telran.pizza.domain.entity.Dish;
import de.telran.pizza.repository.DishRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Sort;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

class DishServiceTest {
    @Mock
    private DishRepository dishRepository;
    @InjectMocks
    private DishService dishService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void update() {
        Dish dish = dishRepository.findByNameEn("test").orElse(null);
        if (dish == null) return;

        dishService.update(Dish.builder()
                .id(dish.getId())
                .nameEn("testNew")
                .nameRu("тест123")
                .price(new BigDecimal(15))
                .category(Category.builder().id(1L).build())
                .build());
    }

    @Test
    public void delete() {
        Dish dish = dishRepository.findByNameEn("testNew").orElse(null);
        Dish dish1 = dishRepository.findByNameEn("test").orElse(null);
        if (dish == null || dish1 == null) return;

        dishService.delete(dish.getId());
        dishService.delete(dish1.getId());
    }

    @Test
    void testFindAllDishes() {
        // Создаем тестовые данные
        List<Dish> dishes = Arrays.asList(
                new Dish(1L,"Pizza", "Пицца", new BigDecimal("12.30"), new Category(), LocalDateTime.now()),
                new Dish(2L,"Margarita", "Маргарита", new BigDecimal("10.50"), new Category(), LocalDateTime.now())
        );

        // Настроим mock-репозиторий, чтобы возвращать тестовые данные при вызове findAll
        when(dishRepository.findAll(any(Sort.class))).thenReturn(dishes);

        // Вызываем метод, который мы хотим протестировать
        List<DishDTO> result = dishService.findAllDishes();

        // Проверяем, что результат соответствует ожиданиям
        assertEquals(dishes.size(), result.size());
        // Можно добавить другие проверки, в зависимости от ожидаемого результата
    }

    @Test
    void testFindById() {
        // Создаем тестовые данные
        Long dishId = 1L;
        Dish dish = new Dish(1L,"Pizza", "Пицца", new BigDecimal("12.30"), new Category(), LocalDateTime.now());

        // Настроим mock-репозиторий, чтобы возвращать тестовый dish при вызове findById
        when(dishRepository.findById(dishId)).thenReturn(Optional.of(dish));

        // Вызываем метод, который мы хотим протестировать
        Dish result = dishService.findById(dishId);

        // Проверяем, что результат соответствует ожиданиям
        assertEquals(dish, result);
    }

}
