package de.telran.pizza.controller;

import de.telran.pizza.config.MessageHelper;
import de.telran.pizza.domain.dto.PageDishesDTO;
import de.telran.pizza.service.DishService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MainControllerTest {
    @Mock
    private DishService dishService;
    @Mock
    private MessageHelper helper;
    @InjectMocks
    private MainController mainController;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testFindPaginated() {
        // Создаем фиктивные данные для возвращения из сервиса
        PageDishesDTO mockPageDishesDTO = new PageDishesDTO();

        // Устанавливаем поведение сервиса при вызове метода
        when(dishService.findAllDishesPage(anyInt(), anyString(), anyString(), anyInt())).thenReturn(mockPageDishesDTO);

        // Вызываем метод контроллера
        ResponseEntity<PageDishesDTO> response = mainController.findPaginated(1, "name", "asc", 1);

        // Проверяем, что ответ не равен null
        assertNotNull(response);

        // Проверяем, что статус ответа - 200 (OK)
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // Проверяем, что PageDishesDTO в ответе соответствует ожидаемому
        PageDishesDTO pageDishesDTO = response.getBody();
        assertNotNull(pageDishesDTO);
        assertEquals(mockPageDishesDTO, pageDishesDTO);
    }

    @Test
    void testFindPaginatedException() {
        // Устанавливаем поведение сервиса при возникновении исключения
        when(dishService.findAllDishesPage(anyInt(), anyString(), anyString(), anyInt())).thenThrow(new RuntimeException("Error"));

        // Вызываем метод контроллера и ожидаем исключение
        assertThrows(ResponseStatusException.class, () -> {
            mainController.findPaginated(1, "name", "asc", 1);
        });
    }
}
