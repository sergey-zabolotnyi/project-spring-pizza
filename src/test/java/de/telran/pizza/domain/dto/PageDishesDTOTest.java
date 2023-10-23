package de.telran.pizza.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

public class PageDishesDTOTest {

    @Test
    void testCreatePageDishesDTO() {
        // Подготовка
        List<DishDTO> dishes = new ArrayList<>();
        List<CategoryDTO> categories = new ArrayList<>();
        int currentPage = 3;
        int totalPages = 10;
        String sortField = "Блюдо, Категория";
        String sortDirection = "asc, desc";
        Long categoryId = 2L;

        // Выполнение
        PageDishesDTO pageDishesDTO = PageDishesDTO.builder()
                .dishes(dishes)
                .categories(categories)
                .currentPage(currentPage)
                .totalPages(totalPages)
                .sortField(sortField)
                .sortDirection(sortDirection)
                .categoryId(categoryId)
                .build();

        // Проверка
        assertNotNull(pageDishesDTO);
        assertEquals(dishes, pageDishesDTO.getDishes());
        assertEquals(categories, pageDishesDTO.getCategories());
        assertEquals(currentPage, pageDishesDTO.getCurrentPage());
        assertEquals(totalPages, pageDishesDTO.getTotalPages());
        assertEquals(sortField, pageDishesDTO.getSortField());
        assertEquals(sortDirection, pageDishesDTO.getSortDirection());
        assertEquals(categoryId, pageDishesDTO.getCategoryId());
    }

    @Test
    void testSchemaDescription() {
        // Подготовка
        Class<PageDishesDTO> clazz = PageDishesDTO.class;

        // Проверка аннотации @Schema
        Schema schemaAnnotation = clazz.getAnnotation(Schema.class);
        assertNotNull(schemaAnnotation);
        assertEquals("ДТО объект блюд и категорий для главной страницы приложения", schemaAnnotation.description());

        // Проверка описаний полей
        try {
            java.lang.reflect.Field dishesField = clazz.getDeclaredField("dishes");
            Schema dishesAnnotation = dishesField.getAnnotation(Schema.class);
            assertNotNull(dishesAnnotation);
            assertEquals("Список блюд в корзине", dishesAnnotation.description());
        } catch (NoSuchFieldException e) {
            fail("Поле dishes не найдено");
        }

        // Проверка поля categories
        try {
            java.lang.reflect.Field categoriesField = clazz.getDeclaredField("categories");
            Schema categoriesAnnotation = categoriesField.getAnnotation(Schema.class);
            assertNotNull(categoriesAnnotation);
            assertEquals("Список категорий", categoriesAnnotation.description());
        } catch (NoSuchFieldException e) {
            fail("Поле categories не найдено");
        }

        // Проверка поля currentPage
        try {
            java.lang.reflect.Field currentPageField = clazz.getDeclaredField("currentPage");
            Schema currentPageAnnotation = currentPageField.getAnnotation(Schema.class);
            assertNotNull(currentPageAnnotation);
            assertEquals("Текущая страница", currentPageAnnotation.description());
            assertEquals("3", currentPageAnnotation.example());
        } catch (NoSuchFieldException e) {
            fail("Поле currentPage не найдено");
        }

        // Проверка поля totalPages
        try {
            java.lang.reflect.Field totalPagesField = clazz.getDeclaredField("totalPages");
            Schema totalPagesAnnotation = totalPagesField.getAnnotation(Schema.class);
            assertNotNull(totalPagesAnnotation);
            assertEquals("Всего страниц", totalPagesAnnotation.description());
            assertEquals("10", totalPagesAnnotation.example());
        } catch (NoSuchFieldException e) {
            fail("Поле totalPages не найдено");
        }

        // Проверка поля sortField
        try {
            java.lang.reflect.Field sortFieldField = clazz.getDeclaredField("sortField");
            Schema sortFieldAnnotation = sortFieldField.getAnnotation(Schema.class);
            assertNotNull(sortFieldAnnotation);
            assertEquals("Поле для сортировки", sortFieldAnnotation.description());
            assertEquals("Блюдо, Категория", sortFieldAnnotation.example());
        } catch (NoSuchFieldException e) {
            fail("Поле sortField не найдено");
        }

        // Проверка поля sortDirection
        try {
            java.lang.reflect.Field sortDirectionField = clazz.getDeclaredField("sortDirection");
            Schema sortDirectionAnnotation = sortDirectionField.getAnnotation(Schema.class);
            assertNotNull(sortDirectionAnnotation);
            assertEquals("Направление сортировки", sortDirectionAnnotation.description());
            assertEquals("asc, desc", sortDirectionAnnotation.example());
        } catch (NoSuchFieldException e) {
            fail("Поле sortDirection не найдено");
        }

        // Проверка поля categoryId
        try {
            java.lang.reflect.Field categoryIdField = clazz.getDeclaredField("categoryId");
            Schema categoryIdAnnotation = categoryIdField.getAnnotation(Schema.class);
            assertNotNull(categoryIdAnnotation);
            assertEquals("ID категории", categoryIdAnnotation.description());
            assertEquals("2", categoryIdAnnotation.example());
        } catch (NoSuchFieldException e) {
            fail("Поле categoryId не найдено");
        }
    }
}