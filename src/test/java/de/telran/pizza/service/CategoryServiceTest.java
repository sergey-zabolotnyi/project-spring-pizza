package de.telran.pizza.service;

import de.telran.pizza.MockData;
import de.telran.pizza.domain.dto.CategoryDTO;
import de.telran.pizza.domain.entity.Category;
import de.telran.pizza.repository.CategoryRepository;
import de.telran.pizza.service.mapper.Mappers;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * Тесты для класса CategoryService.
 */
@SpringBootTest
class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private Mappers mappers;

    @InjectMocks
    private CategoryService categoryService;

    /**
     * Тест для метода findAllCategory().
     * Проверяет правильность возврата списка категорий.
     */
    @Test
    void testFindAllCategory() {
        // Создаем тестовые данные
        List<Category> categories = MockData.getMockedListOfCategories();
        List<CategoryDTO> categoryDTOs = MockData.getMockedListOfCategoriesDTO();

        // Настроим mock-репозиторий, чтобы возвращать тестовые данные при вызове findAll
        when(categoryRepository.findAll()).thenReturn(categories);

        // Настроим mock-mappers, чтобы возвращать ожидаемые CategoryDTO при вызове categoriesToCategoryDTOs
        when(mappers.categoriesToCategoryDTOs(categories)).thenReturn(categoryDTOs);

        // Вызываем метод, который мы хотим протестировать
        List<CategoryDTO> result = categoryService.findAllCategory();

        // Проверяем, что результат соответствует ожиданиям
        assertEquals(categoryDTOs.size(), result.size());
    }
}