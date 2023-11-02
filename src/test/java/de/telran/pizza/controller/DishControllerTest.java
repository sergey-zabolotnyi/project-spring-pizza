package de.telran.pizza.controller;

import de.telran.pizza.MockData;
import de.telran.pizza.config.MessageHelper;
import de.telran.pizza.domain.dto.DishDTO;
import de.telran.pizza.domain.entity.Dish;
import de.telran.pizza.service.DishService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
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
    public void testCreateDishException() {
        // Готовим Mock данные
        Dish dish = MockData.getMockedDish();

        // Определите, какое исключение может быть брошено при выполнении dishService.saveNewDish(dish)
        Mockito.when(dishService.saveNewDish(dish)).thenThrow(new RuntimeException("Текст ошибки"));

        // Вызываем тестируемый метод
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            ResponseEntity<Dish> response = dishController.create(dish);
        });

        // Проверяем ожидаемый результат
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
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
    void testGetDishByIdException() {
        // Готовим Mock данные
        int invalidDishId = -1;

        // Определите, какое исключение может быть брошено при выполнении dishService.findById(invalidDishId)
        Mockito.when(dishService.findById(invalidDishId)).thenThrow(new NoSuchElementException("Текст ошибки"));

        // Вызываем тестируемый метод
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            ResponseEntity<Dish> response = dishController.getDishById(invalidDishId);
        });

        // Проверяем ожидаемый результат
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
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
    void testUpdateDishException() {
        // Готовим Mock данные
        Dish dish = MockData.getMockedDish(); // Подготовьте подходящий Dish

        // Определите, какое исключение может быть брошено при выполнении dishService.update(dish)
        Mockito.doThrow(new RuntimeException("Текст ошибки")).when(dishService).update(dish);

        // Вызываем тестируемый метод
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            dishController.update(dish);
        });

        // Проверяем ожидаемый результат
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
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
    void testDeleteDishException() {
        // Готовим Mock данные
        int dishId = 1; // Подготовьте подходящий ID блюда

        // Определите, какое исключение может быть брошено при выполнении dishService.delete(dishId)
        Mockito.doThrow(new RuntimeException("Текст ошибки")).when(dishService).delete(dishId);

        // Проверяем, что метод не вызывает исключение
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            dishController.delete(dishId);
        });

        // Проверяем ожидаемый результат
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
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
