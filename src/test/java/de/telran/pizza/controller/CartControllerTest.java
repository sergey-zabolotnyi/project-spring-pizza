package de.telran.pizza.controller;

import de.telran.pizza.MockData;
import de.telran.pizza.config.MessageHelper;
import de.telran.pizza.domain.dto.CartDTO;
import de.telran.pizza.domain.entity.Cart;
import de.telran.pizza.domain.entity.User;
import de.telran.pizza.security.UserDetailSecurity;
import de.telran.pizza.service.CartService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Тестирование класса CartController.
 */
class CartControllerTest {

    private CartController cartController;
    private CartService cartService;
    private MessageHelper helper;

    /**
     * Общая настройка для всех тестов.
     */
    @BeforeEach
    void setUp() {
        cartService = mock(CartService.class);
        helper = mock(MessageHelper.class);
        cartController = new CartController(cartService, helper);
    }

    /**
     * Тест получения блюд в корзине.
     */
    @Test
    void testGetDishes() {
        // Подготовка: Создаем пользователя
        User user = MockData.getMockedUser();
        // Устанавливаем пользователя в контекст безопасности
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(
                new UserDetailSecurity(user), null, Collections.emptyList()));

        // Мокирование cartService.findAllDishes()
        CartDTO cartDTO = MockData.getMockedCartDTO();
        when(cartService.findAllDishes()).thenReturn(cartDTO);

        // Вызов метода
        ResponseEntity<CartDTO> responseEntity = cartController.getDishes();

        // Проверка результата
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
    }

    /**
     * Тест создания нового элемента в корзине.
     */
    @Test
    void testCreate() {
        // Мокирование идентификатора блюда
        int id = MockData.getMockedDish().getId();

        // Мокирование cartService.saveNewItem()
        Cart cart = MockData.getMockedCart();
        when(cartService.saveNewItem(id)).thenReturn(cart);

        // Вызов метода
        ResponseEntity<Cart> responseEntity = cartController.create(id);

        // Проверка результата
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
    }

    /**
     * Тест исключения при создании элемента в корзине.
     */
    @Test
    void testCreateCartItemException() {
        // Готовим Mock данные
        int id = MockData.getMockedDish().getId();
        // Определите, какое исключение может быть брошено при выполнении cartService.saveNewItem(dishId)
        Mockito.when(cartService.saveNewItem(id)).thenThrow(new RuntimeException("Текст ошибки"));

        // Вызов метода
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            cartController.create(id);
        });

        // Проверка результата
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
    }

    /**
     * Тест удаления элемента из корзины.
     */
    @Test
    void testDelete() {
        // Мокирование ItemDTO
        int id = MockData.getMockedDish().getId();

        // Вызов метода
        assertDoesNotThrow(() -> cartController.delete(id));
    }

    /**
     * Тест исключения при удалении элемента из корзины.
     */
    @Test
    void testDeleteCartItemException() {
        // Готовим Mock данные
        int dishId = MockData.getMockedDish().getId();

        // Определите, какое исключение может быть брошено при выполнении cartService.delete(dishId)
        Mockito.doThrow(new RuntimeException("Текст ошибки")).when(cartService).delete(dishId);

        // Вызов метода
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            cartController.delete(dishId);
        });

        // Проверка результата
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
    }

    /**
     * Тест удаления всех элементов из корзины.
     */
    @Test
    void testDeleteAll() {
        // Подготовка: Создаем пользователя
        User user = MockData.getMockedUser();
        // Устанавливаем пользователя в контекст безопасности
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(
                new UserDetailSecurity(user), null, Collections.emptyList()));

        // Вызов метода
        assertDoesNotThrow(() -> cartController.deleteAll());
    }
}