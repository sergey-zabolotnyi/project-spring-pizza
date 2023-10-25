package de.telran.pizza.controller;

import de.telran.pizza.config.MessageHelper;
import de.telran.pizza.domain.dto.ItemDTO;
import de.telran.pizza.domain.entity.Login;
import de.telran.pizza.domain.entity.Orders;
import de.telran.pizza.domain.entity.enums.Role;
import de.telran.pizza.domain.entity.enums.Status;
import de.telran.pizza.security.UserDetailSecurity;
import de.telran.pizza.service.OrderService;
import de.telran.pizza.utils.Utils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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
        Login user = new Login(1, "user", "123456", "user@user.com",
                Role.ROLE_MANAGER, LocalDateTime.now());
        // Подготавливаем аутентификацию пользователя
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                new UserDetailSecurity(user), null, Collections.emptyList());
        // Устанавливаем аутентификацию в текущий контекст безопасности
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Мокирование orderService.findAllUserOrders()
        List<Orders> ordersList = List.of(new Orders(), new Orders());
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
        Login user = new Login(1, "user", "123456", "user@user.com",
                Role.ROLE_MANAGER, LocalDateTime.now());
        // Подготавливаем аутентификацию пользователя
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                new UserDetailSecurity(user), null, Collections.emptyList());
        // Устанавливаем аутентификацию в текущий контекст безопасности
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Мокирование orderService.findAllOrders()
        List<Orders> ordersList = List.of(new Orders(), new Orders());
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
        Login user = new Login(1, "user", "123456", "user@user.com",
                Role.ROLE_MANAGER, LocalDateTime.now());
        // Подготавливаем аутентификацию пользователя
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                new UserDetailSecurity(user), null, Collections.emptyList());
        // Устанавливаем аутентификацию в текущий контекст безопасности
        SecurityContextHolder.getContext().setAuthentication(authentication);

        Orders orders = new Orders(1, user, new BigDecimal(25.00), Status.NEW, LocalDateTime.now());

        // Мокирование метода
        when(orderService.saveNewOrder()).thenReturn(orders); // Replace someOrder with an actual Orders object

        // Вызов метода
        ResponseEntity<Orders> responseEntity = orderController.create();

        // Проверка результата
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
    }

    @Test
    void create_orderCreationException() {
        // Подготовка: Пользователь
        Login user = new Login(1, "user", "123456", "user@user.com",
                Role.ROLE_MANAGER, LocalDateTime.now());
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
        ItemDTO itemDTO = new ItemDTO(1);

        // Действие и проверка
        assertDoesNotThrow(() -> orderController.confirm(itemDTO));
    }

    @Test
    void testPayment() {
        // Подготовка
        ItemDTO itemDTO = new ItemDTO(1);

        // Действие и проверка
        assertDoesNotThrow(() -> orderController.payment(itemDTO));
    }

    @Test
    void testGetOrdersCount() {
        // Подготовка
        when(orderService.findAllOrders()).thenReturn(List.of(
                new Orders(), new Orders()
        ));

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
}