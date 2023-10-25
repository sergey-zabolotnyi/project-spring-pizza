package de.telran.pizza.controller;

import de.telran.pizza.config.MessageHelper;
import de.telran.pizza.domain.dto.CartDTO;
import de.telran.pizza.domain.dto.ItemDTO;
import de.telran.pizza.domain.entity.Cart;
import de.telran.pizza.domain.entity.Login;
import de.telran.pizza.security.UserDetailSecurity;
import de.telran.pizza.service.CartService;
import de.telran.pizza.utils.Utils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CartControllerTest {
    private CartController cartController;
    private CartService cartService;
    private MessageHelper helper;

    // Общая настройка для всех тестов
    @BeforeEach
    void setUp() {
        cartService = mock(CartService.class);
        helper = mock(MessageHelper.class);
        cartController = new CartController(cartService, helper);
    }

    // Тест получения блюд в корзине
    @Test
    void testGetDishes() {
        // Мокирование контекста безопасности
        SecurityContext securityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);

        // Мокирование объекта аутентификации
        Authentication authentication = mock(Authentication.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);

        // Мокирование объекта UserDetailSecurity
        UserDetailSecurity userDetails = mock(UserDetailSecurity.class);
        when(authentication.getPrincipal()).thenReturn(userDetails);

        // Мокирование объекта Login
        Login login = new Login("sidor", "123456");
        when(userDetails.getLogin()).thenReturn(login);

        // Мокирование cartService.findAllDishes()
        CartDTO cartDTO = new CartDTO();
        when(cartService.findAllDishes()).thenReturn(cartDTO);

        // Вызов метода
        ResponseEntity<CartDTO> responseEntity = cartController.getDishes();

        // Проверка результата
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
    }

    // Тест создания нового элемента в корзине
    @Test
    void testCreate() {
        // Мокирование ItemDTO
        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setItemId(1);

        // Мокирование cartService.saveNewItem()
        Cart cart = new Cart();
        when(cartService.saveNewItem(itemDTO)).thenReturn(cart);

        // Вызов метода
        ResponseEntity<Cart> responseEntity = cartController.create(itemDTO);

        // Проверка результата
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
    }

    // Тест удаления элемента из корзины
    @Test
    void testDelete() {
        // Мокирование ItemDTO
        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setItemId(1);

        // Вызов метода
        assertDoesNotThrow(() -> cartController.delete(itemDTO));
    }

    // Тест удаления всех элементов из корзины
    @Test
    void testDeleteAll() {
        // Mocking SecurityContext
        SecurityContext securityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);

        // Mocking Authentication object
        Authentication authentication = mock(Authentication.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);

        // Mocking UserDetailSecurity object
        UserDetailSecurity userDetails = mock(UserDetailSecurity.class);
        when(authentication.getPrincipal()).thenReturn(userDetails);

        // Mocking Login object
        Login login = new Login("username", "password");
        when(userDetails.getLogin()).thenReturn(login);

        // Mocking Utils.getAuthorizedLogin().getId()
        when(Utils.getAuthorizedLogin()).thenReturn(login);

        // Calling the method
        assertDoesNotThrow(() -> cartController.deleteAll());
    }
}