package de.telran.pizza.controller;

import de.telran.pizza.MockData;
import de.telran.pizza.config.MessageHelper;
import de.telran.pizza.domain.dto.CategoryDTO;
import de.telran.pizza.service.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CategoryControllerTest {
    @Mock
    private CategoryService categoryService;
    @Mock
    private MessageHelper messageHelper;
    @InjectMocks
    private CategoryController categoryController;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetAllCategories() {
        // Создаем фиктивные данные для возвращения из сервиса
        List<CategoryDTO> mockCategories = MockData.getMockedListOfCategoriesDTO();

        // Устанавливаем поведение сервиса при вызове метода
        when(categoryService.findAllCategory()).thenReturn(mockCategories);

        // Вызываем метод контроллера
        ResponseEntity<List<CategoryDTO>> response = categoryController.getAllCategories();

        // Проверяем, что ответ не равен null
        assertNotNull(response);

        // Проверяем, что статус ответа - 200 (OK)
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // Проверяем, что список категорий в ответе соответствует ожидаемому
        List<CategoryDTO> categories = response.getBody();
        assertNotNull(categories);
        assertEquals(mockCategories, categories);
    }

    @Test
    void testGetCategoriesCount() {
        // Создаем фиктивные данные для возвращения из сервиса
        List<CategoryDTO> mockCategories = MockData.getMockedListOfCategoriesDTO();

        // Устанавливаем поведение сервиса при вызове метода
        when(categoryService.findAllCategory()).thenReturn(mockCategories);

        // Вызываем метод контроллера
        ResponseEntity<Integer> response = categoryController.getCategoriesCount();

        // Проверяем, что ответ не равен null
        assertNotNull(response);

        // Проверяем, что статус ответа - 200 (OK)
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // Проверяем, что количество категорий в ответе соответствует ожидаемому
        Integer count = response.getBody();
        assertNotNull(count);
        assertEquals(mockCategories.size(), count);
    }
}
