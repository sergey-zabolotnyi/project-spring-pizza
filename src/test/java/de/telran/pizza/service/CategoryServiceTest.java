package de.telran.pizza.service;

import de.telran.pizza.domain.dto.CategoryDTO;
import de.telran.pizza.domain.entity.Category;
import de.telran.pizza.repository.CategoryRepository;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import org.mockito.Mockito;

import java.util.List;

public class CategoryServiceTest {

    @Test
    public void testFindAllCategory() {
        // Подготовка (Arrange)
        // Создаем заглушку для репозитория категорий
        CategoryRepository categoryRepository = Mockito.mock(CategoryRepository.class);
        // Создаем сервис категорий с использованием заглушки
        CategoryService categoryService = new CategoryService(categoryRepository);

        // Имитация поведения (Mock)
        // Задаем список категорий для возвращения при вызове categoryRepository.findAll()
        List<Category> categories = List.of(
                new Category(1, "Category1", "Категория1"),
                new Category(2, "Category2", "Категория2")
        );
        Mockito.when(categoryRepository.findAll()).thenReturn(categories);

        // Действие (Act)
        // Вызываем метод, который тестируем
        List<CategoryDTO> result = categoryService.findAllCategory();

        // Проверка (Assert)
        // Проверяем ожидаемый результат
        assertEquals(2, result.size());
        assertEquals("Категория1", result.get(0).getCategory());
        assertEquals("Категория2", result.get(1).getCategory());
    }
    @Test
    public void testFindAllCategoryEmptyList() {
        // Подготовка: Создаем заглушку для репозитория категорий
        CategoryRepository categoryRepository = Mockito.mock(CategoryRepository.class);
        // Создаем сервис категорий с использованием заглушки
        CategoryService categoryService = new CategoryService(categoryRepository);

        // Имитация поведения (Mock)
        // Задаем пустой список для возвращения при вызове categoryRepository.findAll()
        Mockito.when(categoryRepository.findAll()).thenReturn(List.of());

        // Действие: Вызываем метод, который тестируем
        List<CategoryDTO> result = categoryService.findAllCategory();

        // Проверка: Проверяем, что результат является пустым списком
        assertTrue(result.isEmpty());
    }
}