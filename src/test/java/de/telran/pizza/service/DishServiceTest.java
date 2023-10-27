package de.telran.pizza.service;

import de.telran.pizza.config.MessageHelper;
import de.telran.pizza.domain.dto.DishDTO;
import de.telran.pizza.domain.dto.PageDishesDTO;
import de.telran.pizza.domain.entity.Category;
import de.telran.pizza.domain.entity.Dish;
import de.telran.pizza.repository.CategoryRepository;
import de.telran.pizza.repository.DishRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DishServiceTest {
    @Mock
    private DishRepository dishRepository;
    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private MessageHelper helper;
    @InjectMocks
    private DishService dishService;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

//    @Test
//    void findAllDishesPages() {
//        // Создаем тестовые данные
//        Integer pageNum = 1;
//        String sortField = "name";
//        String sortDirection = "asc";
//        int categoryId = 0;
//
//        Page<Dish> mockPage = mock(Page.class);
//        List<Dish> dishes = Arrays.asList(new Dish(1,"Pepperoni", "Пепперони",
//                        new BigDecimal("11.30"), new Category(), LocalDateTime.now()),
//                new Dish(2,"Margarita", "Маргарита", new BigDecimal("10.50"), new Category(),
//                        LocalDateTime.now()));
//        List<Category> categoryList = Arrays.asList(new Category(1, "Pizza", "Пицца"),
//                new Category(2, "Salad", "Салат"));
//
//        // Настроим mock-репозиторий, чтобы возвращать тестовые данные
//        when(mockPage.getContent()).thenReturn(dishes);
//        when(mockPage.getTotalPages()).thenReturn(3);
//        when(dishRepository.findAll(any(Pageable.class))).thenReturn(mockPage);
//        when(categoryRepository.findAll()).thenReturn(categoryList);
//
//        // Вызываем метод, который мы хотим протестировать
//        PageDishesDTO result = dishService.findAllDishesPage(pageNum, sortField, sortDirection, categoryId);
//
//        // Проверяем, что результат соответствует ожиданиям
//        assertNotNull(result);
//        assertEquals(dishes.size(), result.getDishes().size());
//        assertEquals(3, result.getTotalPages());
//    }
    @Test
    void testValidationSetDefault_AscSort() {
        String sortField = "field";
        String sortDirection = "asc";
        Sort result = dishService.validationSetDefault(sortField, sortDirection);
        assertEquals(Sort.by("field").ascending(), result);
    }

    @Test
    void testValidationSetDefault_DescSort() {
        String sortField = "field";
        String sortDirection = "desc";
        Sort result = dishService.validationSetDefault(sortField, sortDirection);
        assertEquals(Sort.by("field").descending(), result);
    }
    @Test
    void testFindAllDishes() {
        // Создаем тестовые данные
        List<Dish> dishes = Arrays.asList(
                new Dish(1,"Pepperoni", "Пепперони", new BigDecimal("11.30"), new Category(),
                        LocalDateTime.now()),
                new Dish(2,"Margarita", "Маргарита", new BigDecimal("10.50"), new Category(),
                        LocalDateTime.now())
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
        Dish dish = new Dish(1,"Pizza", "Пицца", new BigDecimal("12.30"), new Category(),
                LocalDateTime.now());

        // Настроим mock-репозиторий, чтобы возвращать тестовый dish при вызове findById
        when(dishRepository.findById(dish.getId())).thenReturn(Optional.of(dish));

        // Вызываем метод, который мы хотим протестировать
        Dish result = dishService.findById(dish.getId());

        // Проверяем, что результат соответствует ожиданиям
        assertEquals(dish, result);
    }

    @Test
    void testSaveNewDish() {
        // Создаем тестовые данные
        Dish dish = new Dish(1,"Pizza", "Пицца", new BigDecimal("12.30"), new Category(),
                LocalDateTime.now());

        when(categoryRepository.findById(anyInt())).thenReturn(Optional.of(new Category()));
        when(dishRepository.save(any(Dish.class))).thenReturn(dish);

        Dish savedDish = null;
        try {
            savedDish = dishService.saveNewDish(dish);
        } catch (NoSuchElementException e) {
            e.printStackTrace(); // Обработка ошибки, если нужно
        }

        // Проверяем, что результат соответствует ожиданиям
        assertNotNull(savedDish);
        assertNotNull(savedDish.getTime());
        verify(categoryRepository, times(1)).findById(anyInt());
        verify(dishRepository, times(1)).save(any(Dish.class));
    }
    @Test
    void update_ShouldSetCurrentTimeAndSaveToRepository() {
        // Arrange
        LocalDateTime initialTime = LocalDateTime.of(2022, 1, 1, 12, 0);
        Dish dish = new Dish(1,"Pizza", "Пицца", new BigDecimal("12.30"), new Category(),
                LocalDateTime.now());

        dishService.update(dish);

        ArgumentCaptor<Dish> captor = ArgumentCaptor.forClass(Dish.class);
        verify(dishRepository).save(captor.capture());

        Dish capturedDish = captor.getValue();
        assertNotNull(capturedDish);
        assertEquals(dish.getId(), capturedDish.getId());
        assertEquals(dish.getNameEn(), capturedDish.getNameEn());
        assertEquals(dish.getNameRu(), capturedDish.getNameRu());
        assertEquals(dish.getPrice(), capturedDish.getPrice());

        // Проверяем, что время было обновлено
        assertNotNull(capturedDish.getTime());
        assertNotEquals(initialTime, capturedDish.getTime());
    }

    @Test
    public void delete() {
        Dish dish = dishRepository.findByNameEn("testNew").orElse(null);
        Dish dish1 = dishRepository.findByNameEn("test").orElse(null);
        if (dish == null || dish1 == null) return;

        dishService.delete(dish.getId());
        dishService.delete(dish1.getId());
    }
}
