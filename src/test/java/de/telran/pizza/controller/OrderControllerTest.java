package de.telran.pizza.controller;

import de.telran.pizza.config.MessageHelper;
import de.telran.pizza.domain.dto.ItemDTO;
import de.telran.pizza.domain.entity.Login;
import de.telran.pizza.domain.entity.Orders;
import de.telran.pizza.domain.entity.enums.Role;
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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;

import java.time.LocalDateTime;
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
        // Мокирование SecurityContext
        SecurityContext securityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);

        // Мокирование объекта Authentication
        Authentication authentication = mock(Authentication.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);

        // Мокирование объекта UserDetailSecurity
        UserDetailSecurity userDetails = mock(UserDetailSecurity.class);
        when(authentication.getPrincipal()).thenReturn(userDetails);

        // Мокирование объекта Login
        Login login = new Login("sidor", "123456");
        when(userDetails.getLogin()).thenReturn(login);

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
        // Мокирование SecurityContext
        SecurityContext securityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);

        // Мокирование объекта Authentication
        Authentication authentication = mock(Authentication.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);

        // Мокирование объекта UserDetailSecurity
        UserDetailSecurity userDetails = mock(UserDetailSecurity.class);
        when(authentication.getPrincipal()).thenReturn(userDetails);

        // Мокирование объекта Login
        Login login = new Login("sidor", "123456");
        when(userDetails.getLogin()).thenReturn(login);

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
    void testConfirm() {
        // Подготовка
        ItemDTO itemDTO = new ItemDTO(1L);

        // Действие и проверка
        assertDoesNotThrow(() -> orderController.confirm(itemDTO));
    }

    @Test
    void testPayment() {
        // Подготовка
        ItemDTO itemDTO = new ItemDTO(1L);

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