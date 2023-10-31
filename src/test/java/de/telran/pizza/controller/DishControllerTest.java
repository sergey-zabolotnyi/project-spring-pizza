package de.telran.pizza.controller;

import de.telran.pizza.MockData;
import de.telran.pizza.config.MessageHelper;
import de.telran.pizza.domain.dto.DishDTO;
import de.telran.pizza.domain.entity.Dish;
import de.telran.pizza.service.DishService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class DishControllerTest {
    private DishService dishService;
    private MessageHelper messageHelper;
    private DishController dishController;

    @BeforeEach
    void setUp() {
        dishService = mock(DishService.class);
        messageHelper = mock(MessageHelper.class);
        dishController = new DishController(dishService, messageHelper);
    }

    @Test
    void getAllDishes_shouldReturnListOfDishDTO() {
        // Готовим Mock данные
        List<DishDTO> dishList = MockData.getMockedListOfDishesDTO();

        // Устанавливаем поведение mock-объекта
        when(dishService.findAllDishes()).thenReturn(dishList);

        // Вызываем тестируемый метод
        ResponseEntity<List<DishDTO>> response = dishController.getAllDishes();

        // Проверяем ожидаемый результат
        assertEquals(dishList, response.getBody());
    }

    @Test
    void create_shouldReturnCreatedDish() {
        // Готовим Mock данные
        Dish dish = MockData.getMockedDish();

        // Устанавливаем поведение mock-объекта
        when(dishService.saveNewDish(any())).thenReturn(dish);

        // Вызываем тестируемый метод
        ResponseEntity<Dish> response = dishController.create(dish);

        // Проверяем ожидаемый результат
        assertEquals(dish, response.getBody());
    }

    @Test
    void getDishById_shouldReturnDish() {
        // Готовим Mock данные
        Dish dish = MockData.getMockedDish();

        // Устанавливаем поведение mock-объекта
        when(dishService.findById(dish.getId())).thenReturn(dish);

        // Вызываем тестируемый метод
        ResponseEntity<Dish> response = dishController.getDishById(dish.getId());

        // Проверяем ожидаемый результат
        assertEquals(dish, response.getBody());
    }

    @Test
    void update_shouldNotThrowException() {
        // Готовим Mock данные
        Dish dish = MockData.getMockedDish();

        // Устанавливаем поведение mock-объекта
        assertDoesNotThrow(() -> dishController.update(dish));

        // Проверяем, что метод dishService.update(dish) вызывается ровно один раз
        verify(dishService, times(1)).update(dish);
    }

    @Test
    void delete_shouldNotThrowException() {
        // Готовим Mock данные
        int id = 1;

        // Проверяем, что метод не вызывает исключение
        assertDoesNotThrow(() -> dishController.delete(id));

        // Проверяем, что метод dishService.delete(id) вызывается ровно один раз с этим ID
        verify(dishService, times(1)).delete(id);
    }

    @Test
    void getAllDishesCount_shouldReturnCount() {
        // Готовим Mock данные
        List<DishDTO> dishList = MockData.getMockedListOfDishesDTO();

        // Устанавливаем поведение mock-объекта
        when(dishService.findAllDishes()).thenReturn(dishList);

        // Шаг 3: Вызываем тестируемый метод
        ResponseEntity<Integer> response = dishController.getAllDishesCount();

        // Проверяем ожидаемый результат
        assertEquals(dishList.size(), response.getBody());
    }
}
