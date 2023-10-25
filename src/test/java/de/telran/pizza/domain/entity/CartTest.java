package de.telran.pizza.domain.entity;

import static org.junit.jupiter.api.Assertions.*;
import io.swagger.v3.oas.annotations.media.Schema;
import org.junit.jupiter.api.Test;

public class CartTest {
    @Test
    void cartBuilder_CreateCart_ReturnsCartWithCorrectValues() {
        // Подготовка (Arrange)
        int expectedId = 1;
        int expectedLoginId = 2;
        int expectedDishId = 3;

        // Действие (Act)
        Cart cart = Cart.builder()
                .id(expectedId)
                .login(Login.builder().id(expectedLoginId).build())
                .dish(Dish.builder().id(expectedDishId).build())
                .build();

        // Проверка (Assert)
        assertNotNull(cart);
        assertEquals(expectedId, cart.getId());
        assertNotNull(cart.getLogin());
        assertEquals(expectedLoginId, cart.getLogin().getId());
        assertNotNull(cart.getDish());
        assertEquals(expectedDishId, cart.getDish().getId());
    }

    @Test
    void cartSchemaDescription_IsCorrect() {
        Class<Cart> cartClass = Cart.class;
        Schema schemaAnnotation = cartClass.getAnnotation(Schema.class);
        assertNotNull(schemaAnnotation);
        assertEquals("Объект корзины пользователя", schemaAnnotation.description());
    }

    @Test
    void cartId_IsGenerated() {
        Cart cart = new Cart();
        cart.setId(1);
        assertEquals(1, cart.getId());
    }

    @Test
    void getId() {
        int id = 1;
        Cart cart = Cart.builder().id(id).build();
        int result = cart.getId();
        assertEquals(id, result);
    }

    @Test
    void getLogin() {
        Login login = new Login();
        Cart cart = Cart.builder().login(login).build();
        Login result = cart.getLogin();
        assertEquals(login, result);
    }

    @Test
    void getDish() {
        Dish dish = new Dish();
        Cart cart = Cart.builder().dish(dish).build();
        Dish result = cart.getDish();
        assertEquals(dish, result);
    }

    @Test
    void setId() {
        int id = 1;
        Cart cart = Cart.builder().build();
        cart.setId(id);
        assertEquals(id, cart.getId());
    }

    @Test
    void setLogin() {
        Login login = new Login();
        Cart cart = Cart.builder().build();
        cart.setLogin(login);
        assertEquals(login, cart.getLogin());
    }

    @Test
    void setDish() {
        Dish dish = new Dish();
        Cart cart = Cart.builder().build();
        cart.setDish(dish);
        assertEquals(dish, cart.getDish());
    }

    @Test
    void testEquals() {
        Cart cart1 = Cart.builder().build();
        Cart cart2 = Cart.builder().build();
        assertEquals(cart1, cart2);
    }

    @Test
    void canEqual() {
        Cart cart1 = Cart.builder().build();
        Cart cart2 = Cart.builder().build();
        assertTrue(cart1.canEqual(cart2));
    }

    @Test
    void testHashCode() {
        Cart cart1 = Cart.builder().build();
        Cart cart2 = Cart.builder().build();
        assertEquals(cart1.hashCode(), cart2.hashCode());
    }

    @Test
    void testToString() {
        Cart cart = Cart.builder().build();
        String result = cart.toString();
        assertNotNull(result);
    }

    @Test
    void builder() {
        Cart cart = Cart.builder().build();
        assertNotNull(cart);
    }
}