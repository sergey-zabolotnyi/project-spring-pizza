package de.telran.pizza;

import de.telran.pizza.domain.dto.*;
import de.telran.pizza.domain.entity.*;
import de.telran.pizza.domain.entity.enums.Status;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class MockData {

    public static Dish getMockedDish() {
        return new Dish(1,"Pepperoni", "Пепперони", new BigDecimal("12.00"), getMockedCategory(),
                LocalDateTime.now());
    }
    public static DishDTO getMockedDishDTO(){
        return new DishDTO(1, "Pepperoni", new BigDecimal("10.00"), getMockedCategoryDTO());
    }

    public static List<Dish> getMockedListOfDishes() {
        return Arrays.asList(getMockedDish(),
                new Dish(2,"Margarita", "Маргарита", new BigDecimal("10.50"), getMockedCategory(),
                        LocalDateTime.now()));
    }
    public static List<DishDTO> getMockedListOfDishesDTO() {
        return Arrays.asList(getMockedDishDTO(), new DishDTO(2, "Margherita", new BigDecimal("11.00"),
                getMockedCategoryDTO()));
    }

    public static Category getMockedCategory() {
        return new Category(1, "Pizza", "Пицца");
    }
    private static CategoryDTO getMockedCategoryDTO() {
        return new CategoryDTO(1, "Pizza");
    }

    public static List<Category> getMockedListOfCategories() {
        return Arrays.asList(getMockedCategory(), new Category(2, "Salad", "Салат"));
    }

    public static List<CategoryDTO> getMockedListOfCategoriesDTO() {
        return Arrays.asList(getMockedCategoryDTO(), new CategoryDTO(2, "Salad"));
    }

    public static Login getMockedUser() {
        return new Login("user", "123456");
    }
    public static LoginDTO getMockedLoginDTO() {
        return new LoginDTO("newUser", "newuser@test.com", "123456");
    }

    public static List<Login> getMockedListOfLogins() {
        return Arrays.asList(getMockedUser(), new Login("user2", "123456"));
    }

    public static Orders getMockedOrder(){
        return new Orders(1, getMockedUser(), new BigDecimal("25.00"), Status.NEW, LocalDateTime.now());
    }

    public static List<Orders> getMockedListOfOrders() {
        return Arrays.asList(getMockedOrder(),
                new Orders(2, getMockedUser(), new BigDecimal("35.00"), Status.NEW, LocalDateTime.now()));
    }

    public static Cart getMockedCart(){
        return new Cart(1, getMockedUser(), getMockedDish());
    }
    public static CartDTO getMockedCartDTO(){
        return new CartDTO(getMockedListOfDishesDTO(), new BigDecimal("45.00"));
    }
    public static List<Cart> getMockedListOfCarts() {
        return Arrays.asList(getMockedCart(), new Cart(2, getMockedUser(), getMockedDish()));
    }
}

