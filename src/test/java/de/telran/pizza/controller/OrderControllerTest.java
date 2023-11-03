package de.telran.pizza.controller;

import de.telran.pizza.MockData;
import de.telran.pizza.config.MessageHelper;
import de.telran.pizza.domain.dto.DishDTO;
import de.telran.pizza.domain.entity.User;
import de.telran.pizza.domain.entity.Orders;
import de.telran.pizza.security.UserDetailSecurity;
import de.telran.pizza.service.OrderService;
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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Тестирование класса OrderController.
 */
class OrderControllerTest {

    @Mock
    private OrderService orderService;

    @Mock
    private MessageHelper helper;

    @InjectMocks
    private OrderController orderController;

    /**
     * Общая настройка для всех тестов.
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    /**
     * Тестирование метода getOrders в классе OrderController.
     */
    @Test
    void testGetOrders() {
        // Подготовка: Создаем объект пользователя
        User user = MockData.getMockedUser();
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

    /**
     * Тестирование метода getAllOrders в классе OrderController.
     */
    @Test
    void testGetAllOrders() {
        // Подготовка: Создаем объект пользователя
        User user = MockData.getMockedUser();
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

    /**
     * Тестирование успешного создания заказа в методе create класса OrderController.
     */
    @Test
    void create_successfulOrderCreation() {
        // Подготовка: Создаем объект пользователя
        User user = MockData.getMockedUser();
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

    /**
     * Тестирование создания заказа с исключением в методе create класса OrderController.
     */
    @Test
    void create_orderCreationException() {
        // Подготовка: Создаем объект пользователя
        User user = MockData.getMockedUser();
        // Подготавливаем аутентификацию пользователя
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                new UserDetailSecurity(user), null, Collections.emptyList());
        // Устанавливаем аутентификацию в текущий контекст безопасности
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Мокирование метода, который бросает исключение
        when(orderService.saveNewOrder()).thenThrow(new RuntimeException("Some error message"));

        // Вызов метода и ожидаем исключение
        ResponseStatusException thrown = assertThrows(ResponseStatusException.class, () -> orderController.create());

        // Проверка результата
        assertEquals(HttpStatus.BAD_REQUEST, thrown.getStatus());
        assertEquals("Some error message", thrown.getReason());
    }

    /**
     * Тестирование метода confirm в классе OrderController.
     */
    @Test
    void testConfirm() {
        // Подготовка
        int id = MockData.getMockedDish().getId();
        // Действие и проверка
        assertDoesNotThrow(() -> orderController.confirm(id));
        // Определяем, что при вызове orderService.confirm(id) будет брошено исключение
        Mockito.doThrow(new NoSuchElementException("Order not found")).when(orderService).confirm(id);
        // Вызываем метод контроллера и ожидаем исключение
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            orderController.confirm(id); });

        // Проверяем статус и сообщение исключения
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        assertEquals("Order not found", exception.getReason());
    }

    /**
     * Тестирование метода payment в классе OrderController.
     */
    @Test
    void testPayment() {
        // Подготовка: Получаем ID блюда из Mock данных
        int id = MockData.getMockedDish().getId();

        // Действие и проверка
        assertDoesNotThrow(() -> orderController.payment(id));
    }

    /**
     * Тестирование метода payment с возникновением исключения в классе OrderController.
     */
    @Test
    void testProcessPaymentException() {
        // Подготовка: Готовим Mock данные
        int id = MockData.getMockedUser().getId();

        // Определяем, что при вызове orderService.payment(id) будет брошено исключение
        Mockito.doThrow(new NoSuchElementException("Order not found")).when(orderService).payment(id);

        // Вызываем метод контроллера и ожидаем исключение
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            orderController.payment(id);
        });

        // Проверяем статус и сообщение исключения
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        assertEquals("Order not found", exception.getReason());
    }

    /**
     * Тестирование метода getOrdersCount в классе OrderController.
     */
    @Test
    void testGetOrdersCount() {
        // Подготовка: Мокируем метод findAllOrders
        when(orderService.findAllOrders()).thenReturn(MockData.getMockedListOfOrders());

        // Действие: Вызываем метод контроллера
        ResponseEntity<Integer> responseEntity = orderController.getOrdersCount();

        // Проверка результата
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(2, responseEntity.getBody());
    }

    /**
     * Тестирование метода getAverageSum в классе OrderController.
     */
    @Test
    void testGetAverageSum() {
        // Подготовка: Мокируем метод getAverageOrdersSum
        when(orderService.getAverageOrdersSum()).thenReturn(50.0);

        // Действие: Вызываем метод контроллера
        ResponseEntity<Double> responseEntity = orderController.getAverageSum();

        // Проверка результата
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(50.0, responseEntity.getBody());
    }

    /**
     * Тестирование метода getAllOrdersAmount в классе OrderController.
     */
    @Test
    void testGetAllOrdersAmount() {
        // Подготовка: Мокируем метод getTotalOrdersSum
        when(orderService.getTotalOrdersSum()).thenReturn(500.0);

        // Действие: Вызываем метод контроллера
        ResponseEntity<Double> responseEntity = orderController.getAllOrdersAmount();

        // Проверка результата
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(500.0, responseEntity.getBody());
    }

    /**
     * Тестирование метода payment в классе OrderController.
     * Убеждаемся, что при вызове метода не возникает исключений.
     */
    @Test
    void payment_shouldNotThrowException() {
        // Подготовка: Получаем ID блюда из Mock данных
        int id = MockData.getMockedDish().getId();

        // Действие и проверка
        assertDoesNotThrow(() -> orderController.payment(id));

        // Проверяем, что метод payment вызван один раз
        verify(orderService, times(1)).payment(id);
    }

    /**
     * Тестирование метода getDishesByOrderId в классе OrderController.
     */
    @Test
    void testGetDishesByOrderId() {
        // Подготовка: Готовим Mock данные
        int orderId = MockData.getMockedOrder().getId();
        List<DishDTO> expectedDishes = MockData.getMockedListOfDishesDTO();

        // Определяем, что возвращается при вызове orderService.getDishesByOrderId(orderId)
        Mockito.when(orderService.getDishesByOrderId(orderId)).thenReturn(expectedDishes);

        // Вызываем метод контроллера
        ResponseEntity<List<DishDTO>> responseEntity = orderController.getDishesByOrderId(orderId);

        // Проверяем результат
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedDishes, responseEntity.getBody());
    }
}