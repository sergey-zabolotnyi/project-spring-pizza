package de.telran.pizza;

import de.telran.pizza.domain.dto.*;
import de.telran.pizza.domain.entity.*;
import de.telran.pizza.domain.entity.enums.Role;
import de.telran.pizza.domain.entity.enums.Status;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Класс, предоставляющий моковые данные для тестирования.
 */
public class MockData {

    /**
     * Возвращает мок объекта блюда.
     * @return Мок объекта блюда
     */
    public static Dish getMockedDish() {
        return new Dish(1, "Pepperoni", "Пепперони", new BigDecimal("12.00"),
                getMockedCategory(), LocalDateTime.now());
    }

    /**
     * Возвращает мок объекта блюда в формате Data Transfer Object.
     * @return Мок объекта блюда в формате DTO
     */
    public static DishDTO getMockedDishDTO() {
        return new DishDTO(1, "Pepperoni", new BigDecimal("10.00"), getMockedCategoryDTO());
    }

    /**
     * Возвращает список моков блюд.
     * @return Список моков блюд
     */
    public static List<Dish> getMockedListOfDishes() {
        return Arrays.asList(getMockedDish(),
                new Dish(2, "Margarita", "Маргарита", new BigDecimal("10.50"), getMockedCategory(),
                        LocalDateTime.now()));
    }

    /**
     * Возвращает список моков блюд в формате Data Transfer Object.
     * @return Список моков блюд в формате DTO
     */
    public static List<DishDTO> getMockedListOfDishesDTO() {
        return Arrays.asList(getMockedDishDTO(), new DishDTO(2, "Margherita", new BigDecimal("11.00"),
                getMockedCategoryDTO()));
    }

    /**
     * Возвращает мок объекта категории блюда.
     * @return Мок объекта категории блюда
     */
    public static Category getMockedCategory() {
        return new Category(1, "Pizza", "Пицца");
    }

    /**
     * Возвращает мок объекта категории блюда в формате Data Transfer Object.
     * @return Мок объекта категории блюда в формате DTO
     */
    private static CategoryDTO getMockedCategoryDTO() {
        return new CategoryDTO(1, "Pizza");
    }

    /**
     * Возвращает список моков категорий блюд.
     * @return Список моков категорий блюд
     */
    public static List<Category> getMockedListOfCategories() {
        return Arrays.asList(getMockedCategory(), new Category(2, "Salad", "Салат"));
    }

    /**
     * Возвращает список моков категорий блюд в формате Data Transfer Object.
     * @return Список моков категорий блюд в формате DTO
     */
    public static List<CategoryDTO> getMockedListOfCategoriesDTO() {
        return Arrays.asList(getMockedCategoryDTO(), new CategoryDTO(2, "Salad"));
    }

    /**
     * Возвращает мок объекта пользователя.
     * @return Мок объекта пользователя
     */
    public static User getMockedUser() {
        return new User(1, "user", "123456", "user@user.com",
                Role.ROLE_MANAGER, LocalDateTime.now());
    }

    /**
     * Возвращает мок объекта пользователя в формате Data Transfer Object.
     * @return Мок объекта пользователя в формате DTO
     */
    public static UserDTO getMockedUserDTO() {
        return new UserDTO("newUser", "newuser@test.com", "123456");
    }

    /**
     * Возвращает список моков пользователей.
     * @return Список моков пользователей
     */
    public static List<User> getMockedListOfUsers() {
        return Arrays.asList(getMockedUser(), new User(2, "user1", "123456", "user1@user.com",
                Role.ROLE_MANAGER, LocalDateTime.now()));
    }

    /**
     * Возвращает мок объекта заказа.
     * @return Мок объекта заказа
     */
    public static Orders getMockedOrder() {
        return new Orders(1, getMockedUser(), new BigDecimal("25.00"), Status.NEW, LocalDateTime.now(), new ArrayList<>());
    }

    /**
     * Возвращает список моков заказов.
     * @return Список моков заказов
     */
    public static List<Orders> getMockedListOfOrders() {
        return Arrays.asList(getMockedOrder(),
                new Orders(2, getMockedUser(), new BigDecimal("35.00"), Status.NEW, LocalDateTime.now(), new ArrayList<>()));
    }

    /**
     * Возвращает мок объекта корзины.
     * @return Мок объекта корзины
     */
    public static Cart getMockedCart() {
        return new Cart(1, getMockedUser(), getMockedDish());
    }

    /**
     * Возвращает мок объекта корзины в формате Data Transfer Object.
     * @return Мок объекта корзины в формате DTO
     */
    public static CartDTO getMockedCartDTO() {
        return new CartDTO(getMockedListOfDishesDTO(), new BigDecimal("45.00"));
    }

    /**
     * Возвращает список моков корзин.
     * @return Список моков корзин
     */
    public static List<Cart> getMockedListOfCarts() {
        return Arrays.asList(getMockedCart(), new Cart(2, getMockedUser(), getMockedDish()));
    }

    /**
     * Возвращает мок объекта отзыва.
     * @return Мок объекта отзыва
     */
    public static Review getMockedReview() {
        return new Review(1, getMockedUser(),"pizza super!!!", 10, LocalDateTime.now());
    }

    /**
     * Возвращает список моков блюд.
     * @return Список моков блюд
     */
    public static List<Review> getMockedListOfReviews() {
        return Arrays.asList(getMockedReview(),
                new Review(2, getMockedUser(), "I like this pizzeria!", 9, LocalDateTime.now()));
    }
}

