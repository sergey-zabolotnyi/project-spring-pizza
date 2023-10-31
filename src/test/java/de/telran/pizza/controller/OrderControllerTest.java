package de.telran.pizza.controller;

import de.telran.pizza.MockData;
import de.telran.pizza.config.MessageHelper;
import de.telran.pizza.domain.entity.Login;
import de.telran.pizza.domain.entity.Orders;
import de.telran.pizza.security.UserDetailSecurity;
import de.telran.pizza.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderControllerTest {
    @Mock
    private OrderService orderService;
    @Mock
    private MessageHelper helper;
    @InjectMocks
    private OrderController orderController;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetOrders() {
        // Подготовка: Пользователь
        Login user = MockData.getMockedUser();
        // Подготавливаем аутентификацию пользователя
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                new UserDetailSecurity(user), null, Collections.emptyList());
        // Устанавливаем аутентификацию в текущий контекст безопасности
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Мокирование orderService.findAllUserOrders()
        List<Orders> ordersList = MockData.getMockedListOfOrders();
        when(orderService.findAllUserOrders()).thenReturn(ordersList);

        // Вызов метода
        ResponseEntity<List<Orders>> responseEntity = orderController.getOrders();

        // Проверка результата
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(2, responseEntity.getBody().size());
    }

    @Test
    void testGetAllOrders() {
        // Подготовка: Пользователь
        Login user = MockData.getMockedUser();
        // Подготавливаем аутентификацию пользователя
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                new UserDetailSecurity(user), null, Collections.emptyList());
        // Устанавливаем аутентификацию в текущий контекст безопасности
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Мокирование orderService.findAllOrders()
        List<Orders> ordersList = MockData.getMockedListOfOrders();
        when(orderService.findAllOrders()).thenReturn(ordersList);

        // Вызов метода
        ResponseEntity<List<Orders>> responseEntity = orderController.getAllOrders();

        // Проверка результата
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(2, responseEntity.getBody().size());
    }
    @Test
    void create_successfulOrderCreation() {
        // Подготовка: Пользователь
        Login user = MockData.getMockedUser();
        // Подготавливаем аутентификацию пользователя
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                new UserDetailSecurity(user), null, Collections.emptyList());
        // Устанавливаем аутентификацию в текущий контекст безопасности
        SecurityContextHolder.getContext().setAuthentication(authentication);

        Orders orders = MockData.getMockedOrder();

        // Мокирование метода
        when(orderService.saveNewOrder()).thenReturn(orders);

        // Вызов метода
        ResponseEntity<Orders> responseEntity = orderController.create();

        // Проверка результата
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
    }

    @Test
    void create_orderCreationException() {
        // Подготовка: Пользователь
        Login user = MockData.getMockedUser();
        // Подготавливаем аутентификацию пользователя
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                new UserDetailSecurity(user), null, Collections.emptyList());
        // Устанавливаем аутентификацию в текущий контекст безопасности
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Мокирование метода
        when(orderService.saveNewOrder()).thenThrow(new RuntimeException("Some error message"));

        // Вызов метода
        ResponseStatusException thrown = assertThrows(ResponseStatusException.class, () -> orderController.create());

        // Проверка результата
        assertEquals(HttpStatus.BAD_REQUEST, thrown.getStatus());
        assertEquals("Some error message", thrown.getReason());
    }

    @Test
    void testConfirm() {
        // Подготовка
        int id = MockData.getMockedDish().getId();

        // Действие и проверка
        assertDoesNotThrow(() -> orderController.confirm(id));
    }

    @Test
    void testPayment() {
        // Подготовка
        int id = MockData.getMockedDish().getId();

        // Действие и проверка
        assertDoesNotThrow(() -> orderController.payment(id));
    }

    @Test
    void testGetOrdersCount() {
        // Подготовка
        when(orderService.findAllOrders()).thenReturn(MockData.getMockedListOfOrders());

        // Действие
        ResponseEntity<Integer> responseEntity = orderController.getOrdersCount();

        // Проверка
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(2, responseEntity.getBody());
    }

    @Test
    void testGetAverageSum() {
        // Подготовка
        when(orderService.getAverageOrdersSum()).thenReturn(50.0);

        // Действие
        ResponseEntity<Double> responseEntity = orderController.getAverageSum();

        // Проверка
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(50.0, responseEntity.getBody());
    }

    @Test
    void testGetAllOrdersAmount() {
        // Подготовка
        when(orderService.getTotalOrdersSum()).thenReturn(500.0);

        // Действие
        ResponseEntity<Double> responseEntity = orderController.getAllOrdersAmount();

        // Проверка
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(500.0, responseEntity.getBody());
    }

    @Test
    void payment_shouldNotThrowException() {
        int id = 1;

        assertDoesNotThrow(() -> orderController.payment(id));

        verify(orderService, times(1)).payment(id);
    }


}