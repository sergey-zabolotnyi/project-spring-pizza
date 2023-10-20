package de.telran.pizza.controller;

import de.telran.pizza.config.MessageHelper;
import de.telran.pizza.domain.dto.ItemDTO;
import de.telran.pizza.domain.entity.Login;
import de.telran.pizza.domain.entity.Orders;
import de.telran.pizza.domain.entity.enums.Role;
import de.telran.pizza.service.OrderService;
import de.telran.pizza.utils.Utils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
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
        // todo
    }

    @Test
    void testCreate() {
        // todo
    }
    @Test
    void testConfirm() {
        // Arrange
        ItemDTO itemDTO = new ItemDTO(1L);

        // Act
        assertDoesNotThrow(() -> orderController.confirm(itemDTO));
    }

    @Test
    void testPayment() {
        // Arrange
        ItemDTO itemDTO = new ItemDTO(1L);

        // Act
        assertDoesNotThrow(() -> orderController.payment(itemDTO));
    }

    @Test
    void testGetOrdersCount() {
        // Arrange
        when(orderService.findAllOrders()).thenReturn(List.of(
                new Orders(), new Orders()
        ));

        // Act
        ResponseEntity<Integer> responseEntity = orderController.getOrdersCount();

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(2, responseEntity.getBody());
    }

    @Test
    void testGetAverageSum() {
        // Arrange
        when(orderService.getAverageOrdersSum()).thenReturn(50.0);

        // Act
        ResponseEntity<Double> responseEntity = orderController.getAverageSum();

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(50.0, responseEntity.getBody());
    }

    @Test
    void testGetAllOrdersAmount() {
        // Arrange
        when(orderService.getTotalOrdersSum()).thenReturn(500.0);

        // Act
        ResponseEntity<Double> responseEntity = orderController.getAllOrdersAmount();

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(500.0, responseEntity.getBody());
    }
}