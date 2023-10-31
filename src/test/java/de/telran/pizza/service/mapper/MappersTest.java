package de.telran.pizza.service.mapper;

import de.telran.pizza.MockData;
import de.telran.pizza.domain.dto.CategoryDTO;
import de.telran.pizza.domain.dto.DishDTO;
import de.telran.pizza.domain.entity.Cart;
import de.telran.pizza.domain.entity.Category;
import de.telran.pizza.domain.entity.Dish;
import de.telran.pizza.domain.entity.Login;
import de.telran.pizza.utils.Utils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class MappersTest {

    private MappersImpl mappers;

    @BeforeEach
    void setUp() {
        mappers = new MappersImpl();
    }

    @Test
    void testDishesToDishDTOs() {
        // Создаем тестовые данные
        List<Dish> dishes = Arrays.asList(new Dish(1, "Pepperoni", "Пепперони",
                new BigDecimal("10.00"), new Category(), LocalDateTime.now()));
        List<DishDTO> dishDTOs = Arrays.asList(new DishDTO(1, "Пепперони", new BigDecimal("10.00"),
                new CategoryDTO()));

        // Вызываем метод, который мы хотим протестировать
        List<DishDTO> result = mappers.dishesToDishDTOs(dishes);

        // Проверяем, что результат соответствует ожиданиям
        assertEquals(dishDTOs, result);
    }

    @Test
    void testCategoriesToCategoryDTOs() {
        // Создаем тестовые данные
        List<Category> categories = Arrays.asList(new Category(1, "Pizza", "Пицца"),
                new Category(2, "Salad", "Салат"));
        List<CategoryDTO> categoryDTOs = Arrays.asList(new CategoryDTO(1, "Пицца"),
                new CategoryDTO(2, "Салат"));

        // Вызываем метод, который мы хотим протестировать
        List<CategoryDTO> result = mappers.categoriesToCategoryDTOs(categories);

        // Проверяем, что результат соответствует ожиданиям
        assertEquals(categoryDTOs, result);
    }

    @Test
    void testCalculateTotalPrice() {
        // Создаем тестовые данные
        List<DishDTO> dishDTOList = MockData.getMockedListOfDishesDTO();

        // Вызываем метод, который мы хотим протестировать
        BigDecimal result = mappers.calculateTotalPrice(dishDTOList);

        // Ожидаемый результат
        BigDecimal expectedTotalPrice = new BigDecimal("21.00");

        // Проверяем, что результат соответствует ожиданиям
        assertEquals(expectedTotalPrice, result);
    }

    @Test
    void testCartListToDishDTOList() {
        // Создаем тестовые данные
        Login user = MockData.getMockedUser();
        List<Cart> cartList = Arrays.asList(new Cart(1, user, new Dish(1, "Pepperoni", "Пепперони",
                new BigDecimal("10.00"), new Category(), LocalDateTime.now())));
        List<DishDTO> dishDTOs = Arrays.asList(new DishDTO(1, "Пепперони", new BigDecimal("10.00"),
                new CategoryDTO()));

        // Вызываем метод, который мы хотим протестировать
        List<DishDTO> result = mappers.cartListToDishDTOList(cartList);

        // Проверяем, что результат соответствует ожиданиям
        assertEquals(dishDTOs, result);
    }

    @Test
    void testCategoryToDTO() {
        // Создаем тестовые данные
        Category category = MockData.getMockedCategory();

        // Вызываем метод, который мы хотим протестировать
        CategoryDTO result = mappers.categoryToDTO(category);

        // Ожидаемый результат
        CategoryDTO expectedCategoryDTO = CategoryDTO.builder()
                .id(category.getId())
                .category(category.getCategoryRu())
                .build();

        // Проверяем, что результат соответствует ожиданиям
        assertEquals(expectedCategoryDTO, result);
    }

    @Test
    void testMapCategory() {
        // Создаем тестовые данные
        Category category = MockData.getMockedCategory();

        // Вызываем метод, который мы хотим протестировать
        String result = mappers.mapCategory(category);

        // Ожидаемый результат
        String expectedCategory;

        if (Utils.isLocaleEnglish()) {
            expectedCategory = category.getCategoryEn();
        } else {
            expectedCategory = category.getCategoryRu();
        }
        // Проверяем, что результат соответствует ожиданиям
        assertEquals(expectedCategory, result);
    }

    @Test
    void testMapDish() {
        // Создаем тестовые данные
        Dish dish = MockData.getMockedDish();

        // Вызываем метод, который мы хотим протестировать
        String result = mappers.mapDish(dish);

        // Ожидаемый результат
        String expectedDish;

        if (Utils.isLocaleEnglish()) {
            expectedDish = dish.getNameEn();
        } else {
            expectedDish = dish.getNameRu();
        }
        // Проверяем, что результат соответствует ожиданиям
        assertEquals(expectedDish, result);
    }
}