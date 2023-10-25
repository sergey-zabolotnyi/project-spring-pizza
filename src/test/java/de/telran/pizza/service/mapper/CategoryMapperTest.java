package de.telran.pizza.service.mapper;

import de.telran.pizza.domain.dto.CategoryDTO;
import de.telran.pizza.domain.entity.Category;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

class CategoryMapperTest {

    @Test
    void categoryListToDtoList() {
        // Создаем фиктивные данные для теста
        Category category1 = new Category(1, "Category1En", "Категория1Ру");
        Category category2 = new Category(2, "Category2En", "Категория2Ру");
        List<Category> categoryList = Arrays.asList(category1, category2);

        // Вызываем метод, который мы хотим протестировать
        List<CategoryDTO> categoryDTOList = CategoryMapper.categoryListToDtoList(categoryList);

        // Проверяем, что результат соответствует ожидаемому
        assertEquals(categoryList.size(), categoryDTOList.size());

        // Проверяем, что каждый элемент соответствует ожидаемому
        for (int i = 0; i < categoryList.size(); i++) {
            Category category = categoryList.get(i);
            CategoryDTO categoryDTO = categoryDTOList.get(i);
            assertEquals(category.getId(), categoryDTO.getId());
            assertEquals(category.getCategoryRu(), categoryDTO.getCategory());
        }
    }
    @Test
    void categoryListToDtoList_ConvertsList() {
        // Создаем фиктивные данные для теста
        Category category1 = new Category(1, "Category1En", "Категория1Ру");
        Category category2 = new Category(2, "Category2En", "Категория2Ру");
        List<Category> categoryList = Arrays.asList(category1, category2);

        // Вызываем метод, который мы хотим протестировать
        List<CategoryDTO> categoryDTOList = CategoryMapper.categoryListToDtoList(categoryList);

        // Assert
        assertEquals(2, categoryDTOList.size());
        assertEquals("Категория1Ру", categoryDTOList.get(0).getCategory());
        assertEquals("Категория2Ру", categoryDTOList.get(1).getCategory());
    }

    @Test
    void categoryToDto() {
        // Создаем фиктивную категорию
        Category category = new Category(1, "CategoryEn", "КатегорияРу");

        // Вызываем метод, который мы хотим протестировать
        CategoryDTO categoryDTO = CategoryMapper.categoryToDto(category);

        // Проверяем, что результат соответствует ожидаемому
        assertEquals(category.getId(), categoryDTO.getId());
        assertEquals(category.getCategoryRu(), categoryDTO.getCategory());
    }
    @Test
    void categoryToDto_ConvertsSingleCategory() {
        // Создаем фиктивную категорию
        Category category = new Category(1, "Category1", "Категория1");

        // Вызываем метод, который мы хотим протестировать
        CategoryDTO categoryDTO = CategoryMapper.categoryToDto(category);

        // Assert
        assertNotNull(categoryDTO);
        assertEquals(1, categoryDTO.getId());
        assertEquals("Категория1", categoryDTO.getCategory());
    }
}
