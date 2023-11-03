package de.telran.pizza.service;

import de.telran.pizza.MockData;
import de.telran.pizza.domain.dto.DishDTO;
import de.telran.pizza.domain.dto.PageDishesDTO;
import de.telran.pizza.domain.entity.Category;
import de.telran.pizza.domain.entity.Dish;
import de.telran.pizza.repository.DishRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Тесты для класса DishService.
 */
@SpringBootTest
class DishServiceTest {

    @Autowired
    private DishService dishService;

    @Autowired
    private DishRepository dishRepository;

    private static final int PAGE_NUM = 1;
    private static final String SORT = "id";
    private static final String DIRECTION = "asc";
    private static final int CATEGORY_ID = 0;
    private static final int ROWS_ON_PAGE = 10;

    /**
     * Тест для метода findAllDishesPaginated().
     * Проверяет правильность возвращенных данных.
     */
    @Test
    public void findAllDishesPaginated() {
        PageDishesDTO page = dishService.findAllDishesPage(
                PAGE_NUM, SORT, DIRECTION, CATEGORY_ID);

        assertEquals(ROWS_ON_PAGE, page.getDishes().size());
        assertTrue(page.getCategories().size() > 2);
        assertTrue(page.getTotalPages() >= page.getDishes().size() / 5);
        assertEquals(PAGE_NUM, page.getCurrentPage());
        assertEquals(SORT, page.getSortField());
        assertEquals(DIRECTION, page.getSortDirection());
        assertEquals(CATEGORY_ID, page.getCategoryId());
    }

    /**
     * Тест для метода validationSetDefault().
     * Проверяет корректное установление сортировки по порядку.
     */
    @Test
    void testValidationSetDefault_AscSort() {
        String sortField = "field";
        String sortDirection = "asc";
        Sort result = dishService.validationSetDefault(sortField, sortDirection);
        assertEquals(Sort.by("field").ascending(), result);
    }

    /**
     * Тест для метода validationSetDefault().
     * Проверяет корректное установление сортировки в обратном порядке.
     */
    @Test
    void testValidationSetDefault_DescSort() {
        String sortField = "field";
        String sortDirection = "desc";
        Sort result = dishService.validationSetDefault(sortField, sortDirection);
        assertEquals(Sort.by("field").descending(), result);
    }

    /**
     * Тест для метода findAllDishesPaginated() с фильтром.
     * Проверяет правильность возвращенных данных.
     */
    @Test
    public void findAllDishesPaginated_withFilter() {
        PageDishesDTO page = dishService.findAllDishesPage(
                PAGE_NUM, SORT, DIRECTION, 2);
        assertTrue(page.getDishes().size() > 0);
    }

    /**
     * Тест для метода findAllDishesPaginated() с установкой минусового значения категории.
     * Проверяет правильность возвращенных данных: default.
     */
    @Test
    public void findAllDishesPaginated_setDefault1() {
        PageDishesDTO page = dishService.findAllDishesPage(
                PAGE_NUM, SORT, DIRECTION, -3);
        assertEquals(ROWS_ON_PAGE, page.getDishes().size());
    }

    /**
     * Тесты для метода findAllDishesPaginated() с установкой значений null.
     * Проверяет правильность возвращенных данных: default.
     */
    @Test
    public void findAllDishesPaginated_setDefault2() {
        PageDishesDTO page = dishService.findAllDishesPage(
                PAGE_NUM, null, null, 0);
        assertEquals(ROWS_ON_PAGE, page.getDishes().size());
    }

    /**
     * Тесты для метода findAllDishesPaginated() с установкой неправильного значения направления сортировки.
     * Проверяет правильность возвращенных данных: default.
     */
    @Test
    public void findAllDishesPaginated_setDefault3() {
        PageDishesDTO page = dishService.findAllDishesPage(
                PAGE_NUM, SORT, "noname", CATEGORY_ID);
        assertEquals(ROWS_ON_PAGE, page.getDishes().size());
    }

    /**
     * Тест для метода findAllDishesPaginated() с исключениями.
     * Проверяет, что метод выбрасывает исключение при некорректных аргументах null.
     */
    @Test
    public void findAllDishesPaginated_Exception0() {
        assertThrows(Exception.class, () -> dishService.findAllDishesPage(
                null, SORT, DIRECTION, CATEGORY_ID));
    }

    /**
     * Тест для метода findAllDishesPaginated() с исключениями.
     * Проверяет, что метод выбрасывает исключение при отрицательном значении номера страницы.
     */
    @Test
    public void findAllDishesPaginated_Exception1() {
        assertThrows(Exception.class, () -> dishService.findAllDishesPage(
                -1, SORT, DIRECTION, CATEGORY_ID));
    }

    /**
     * Тест для метода findAllDishesPaginated() с исключениями.
     * Проверяет, что метод выбрасывает исключение при неправильном значении направления сортировки.
     */
    @Test
    public void findAllDishesPaginated_Exception2() {
        assertThrows(Exception.class, () -> dishService.findAllDishesPage(
                PAGE_NUM, "noname", DIRECTION, CATEGORY_ID));
    }

    /**
     * Тест для метода findAllDishes().
     * Проверяет правильность возвращенного списка блюд.
     */
    @Test
    public void findAllDishesTest() {
        List<DishDTO> list = dishService.findAllDishes();
        assertTrue(list.size() > 10);
    }

    /**
     * Тест для метода saveNewDish() с исключением.
     * Проверяет, что метод выбрасывает исключение при некорректных данных блюда.
     */
    @Test
    void saveNewDish_Exception() {
        Dish dish = new Dish();
        dish.setNameEn("Test Dish");
        dish.setPrice(new BigDecimal(10));
        Category nonExistentCategory = new Category();
        nonExistentCategory.setId(100); // Устанавливаем несуществующие данные
        dish.setCategory(nonExistentCategory);

        // Проверяем
        assertThrows(NoSuchElementException.class, () -> dishService.saveNewDish(dish));
    }

    /**
     * Тест для метода findById().
     * Проверяет правильность возвращенного блюда по ID.
     */
    @Test
    void findById() {
        Dish dish = dishService.findById(1);
        assertNotNull(dish);
    }

    /**
     * Тест для метода findById() с исключением.
     * Проверяет, что метод выбрасывает исключение при некорректном ID.
     */
    @Test
    void findById_Exception() {
        assertThrows(NoSuchElementException.class, () -> dishService.findById(-1));
    }

    /**
     * Тест для метода saveNewDish().
     * Проверяет успешное создание тестового блюда.
     */
    @Test
    public void a_saveNewDish() {
        Dish dish = dishService.saveNewDish(Dish.builder()
                .nameEn("test")
                .nameRu("тест")
                .price(new BigDecimal(10))
                .category(Category.builder().id(1).build())
                .build());
        assertNotNull(dish);
    }

    /**
     * Тест для метода update().
     * Проверяет успешное обновление данных блюда.
     */
    @Test
    void b_update() {
        Dish dish = dishRepository.findByNameEn("test").orElse(null);
        if (dish == null) return;

        dishService.update(Dish.builder()
                .id(dish.getId())
                .nameEn("test1")
                .nameRu("тест1")
                .price(new BigDecimal(11))
                .category(Category.builder().id(1).build())
                .build());
    }

    /**
     * Тест для метода delete().
     * Проверяет успешное удаление блюда.
     */
    @Test
    void c_delete() {
        Dish dish = dishRepository.findByNameEn("test1").orElse(null);
        if (dish == null) return;

        dishService.delete(dish.getId());
    }
}