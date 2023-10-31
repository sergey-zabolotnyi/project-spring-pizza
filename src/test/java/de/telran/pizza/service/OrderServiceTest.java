package de.telran.pizza.service;

import de.telran.pizza.MockData;
import de.telran.pizza.config.MessageHelper;
import de.telran.pizza.domain.entity.*;
import de.telran.pizza.domain.entity.enums.Role;
import de.telran.pizza.domain.entity.enums.Status;
import de.telran.pizza.repository.CartRepository;
import de.telran.pizza.repository.OrdersRepository;
import de.telran.pizza.security.UserDetailSecurity;
import de.telran.pizza.service.mapper.Mappers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class OrderServiceTest {
    private OrdersRepository ordersRepository;
    private OrderService orderService;
    private CartRepository cartRepository;
    private Mappers mappers;
    private MessageHelper helper;

    @BeforeEach
    void setUp() {
        // Создаем mock для ordersRepository
        ordersRepository = mock(OrdersRepository.class);
        // Создаем mock для cartRepository
        cartRepository = mock(CartRepository.class);
        // Создаем mock для helper
        mappers = mock(Mappers.class);
        // Создаем mock для helper
        helper = mock(MessageHelper.class);
        // Создаем объект OrderService с передачей всех необходимых зависимостей
        orderService = new OrderService(ordersRepository, cartRepository, mappers, helper);
    }

    @Test
    void findAllUserOrders() {
        // Подготовка: Создаем объект пользователя
        Login user = MockData.getMockedUser();
        user.setId(1);

        // Создаем список заказов пользователя
        List<Orders> userOrders = MockData.getMockedListOfOrders();

        // Подготавливаем аутентификацию пользователя
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                new UserDetailSecurity(user), null, Collections.emptyList());

        // Устанавливаем аутентификацию в текущий контекст безопасности
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Настроим поведение мока ordersRepository
        when(ordersRepository.findOrdersByLoginId(user.getId())).thenReturn(userOrders);

        // Действие: вызываем метод, который мы тестируем
        List<Orders> result = orderService.findAllUserOrders();

        // Проверка: Убеждаемся, что результат не равен null
        assertNotNull(result);

        // Убеждаемся, что размер списка равен 2
        assertEquals(2, result.size());
    }

    @Test
    void findAllOrders() {
        // Подготовка: Создаем пользователя
        Login user = MockData.getMockedUser();
        // Устанавливаем пользователя в контекст безопасности
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(
                new UserDetailSecurity(user), null, Collections.emptyList()));

        // Создаем список заказов
        List<Orders> orders = MockData.getMockedListOfOrders();

        // Настраиваем мок ordersRepository
        when(ordersRepository.findOrdersByOrderByIdAsc()).thenReturn(orders);

        // Действие: вызываем метод, который тестируем
        List<Orders> actualOrders = orderService.findAllOrders();

        // Проверка: Убеждаемся, что размер списка совпадает с ожидаемым
        assertEquals(orders.size(), actualOrders.size());

        // Проверяем, что каждый элемент списка соответствует ожидаемому
        for (int i = 0; i < orders.size(); i++) {
            assertEquals(orders.get(i), actualOrders.get(i));
        }
    }

    @Test
    void payment_validOrder() {
        // Подготовка: Создаем пользователя
        Login user = MockData.getMockedUser();
        // Устанавливаем пользователя в контекст безопасности
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(
                new UserDetailSecurity(user), null, Collections.emptyList()));

        // Создаем заказ с состоянием "NEW"
        Orders order = MockData.getMockedOrder();

        // Настраиваем поведение мока ordersRepository
        when(ordersRepository.findByIdAndLoginIdAndStatus(order.getId(), user.getId(), order.getStatus()))
                .thenReturn(Optional.of(order));

        // Действие
        boolean result = orderService.payment(order.getId());

        // Проверка: Убеждаемся, что метод вернул true
        assertTrue(result);

        // Убеждаемся, что статус заказа изменился на "PAYED"
        assertEquals(Status.NEW, order.getStatus());

        // Проверяем, что метод updateStatus был вызван 1 раз с ожидаемыми аргументами
        verify(ordersRepository, times(1)).updateStatus(order.getId(), Status.PAYED);
    }

    @Test
    void payment_invalidOrder() {
        // Подготовка: Создаем пользователя
        Login user = MockData.getMockedUser();
        // Устанавливаем пользователя в контекст безопасности
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(
                new UserDetailSecurity(user), null, Collections.emptyList()));

        // Устанавливаем mock для helper
        MessageHelper helper = mock(MessageHelper.class);

        // Создаем ordersService с установленным helper
        OrderService ordersService = new OrderService(ordersRepository, cartRepository, mappers, helper);

        // Создаем заказ с состоянием "NEW"
        Orders order = MockData.getMockedOrder();

        // Настраиваем поведение мока ordersRepository
        when(ordersRepository.findByIdAndLoginIdAndStatus(order.getId(), user.getId(), Status.NEW))
                .thenReturn(Optional.empty());

        // Действие и проверка: Проверяем, что метод выбрасывает исключение NoSuchElementException
        assertThrows(NoSuchElementException.class, () -> ordersService.payment(order.getId()));

        // Проверяем, что метод updateStatus не был вызван
        verify(ordersRepository, never()).updateStatus(anyInt(), any(Status.class));
    }

    @Test
    void testGetAverageOrdersSum() {
        // Устанавливаем поведение мока ordersRepository для метода findAverageOrdersSum()
        when(ordersRepository.findAverageOrdersSum()).thenReturn(50.0);

        // Вызываем метод сервиса и сохраняем результат
        Double result = orderService.getAverageOrdersSum();

        // Проверяем, что результат равен 50
        assertEquals(50.0, result);
    }

    @Test
    void testGetTotalOrdersSum() {
        // Устанавливаем поведение мока ordersRepository для метода findTotalOrdersSum()
        when(ordersRepository.findTotalOrdersSum()).thenReturn(100.0);

        // Вызываем метод сервиса и сохраняем результат
        Double result = orderService.getTotalOrdersSum();

        // Проверяем, что результат равен 100
        assertEquals(100.0, result);
    }

    @Test
    void confirm_invalidOrder() {
        // Подготовка: Создаем пользователя с ролью ROLE_MANAGER
        Login user = new Login(1, "user", "123456", "user@user.com",
                Role.ROLE_MANAGER, LocalDateTime.now());

        // Подготовка: Подготовим аутентификацию пользователя
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                new UserDetailSecurity(user), null, Collections.emptyList());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Подготовка: Создаем заказ с состоянием "DONE"
        Orders order = new Orders(1, user, new BigDecimal("25.00"), Status.DONE, LocalDateTime.now());

        // Настраиваем поведение мока ordersRepository
        when(ordersRepository.findById(order.getId())).thenReturn(Optional.of(order));

        // Действие и Проверка: Убеждаемся, что метод выбросит NoSuchElementException
        assertThrows(NoSuchElementException.class, () -> orderService.confirm(order.getId()));

        // Проверяем, что метод updateStatus не был вызван
        verify(ordersRepository, never()).updateStatus(anyInt(), any(Status.class));
    }

    @Test
    void confirm_invalidRole() {
        // Подготовка: Создаем пользователя с ролью не ROLE_MANAGER
        Login user = new Login(1, "user", "123456", "user@user.com",
                Role.ROLE_CUSTOMER, LocalDateTime.now());

        // Подготовка: Подготовим аутентификацию пользователя
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                new UserDetailSecurity(user), null, Collections.emptyList());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Подготовка: Создаем заказ с состоянием "NEW"
        Orders order = new Orders(1, user, new BigDecimal("25.00"), Status.NEW, LocalDateTime.now());

        // Настраиваем поведение мока ordersRepository
        when(ordersRepository.findById(order.getId())).thenReturn(Optional.of(order));

        // Действие и Проверка: Убеждаемся, что метод выбросит NoSuchElementException
        assertThrows(NoSuchElementException.class, () -> orderService.confirm(order.getId()));

        // Проверяем, что метод updateStatus не был вызван
        verify(ordersRepository, never()).updateStatus(anyInt(), any(Status.class));
    }

    @Test
    void saveNewOrder_emptyCart() {
        // Подготовка: Создаем пользователя
        Login user = new Login("user", "123456");
        // Устанавливаем пользователя в контекст безопасности
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(
                new UserDetailSecurity(user), null, Collections.emptyList()));

        List<Cart> cartItems = new ArrayList<>(); // Создаем пустую корзину

        when(cartRepository.findAllByLoginId(user.getId())).thenReturn(cartItems);
        when(helper.getLogMessage(anyString())).thenReturn("Test Log Message");

        // Проверяем
        assertThrows(NoSuchElementException.class, () -> orderService.saveNewOrder());
        verify(cartRepository, never()).deleteByLoginId(anyInt());
        verify(ordersRepository, never()).save(any(Orders.class));
    }

    @Test
    void saveNewOrder_validCart() {
        // Подготовка: Создаем пользователя
        Login user = new Login("user", "123456");
        // Устанавливаем пользователя в контекст безопасности
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(
                new UserDetailSecurity(user), null, Collections.emptyList()));

        // Подготавливаем данные корзины
        List<Cart> cart = MockData.getMockedListOfCarts();

        // Мокирование поведения cartRepository
        when(cartRepository.findAllByLoginId(user.getId())).thenReturn(cart);

        // Мокирование поведения ordersRepository
        Orders savedOrder = new Orders(1, user, new BigDecimal("50.00"), Status.NEW, LocalDateTime.now());
        when(ordersRepository.save(any(Orders.class))).thenReturn(savedOrder);

        // Действие
        Orders result = null;
        try {
            result = orderService.saveNewOrder();
        } catch (NoSuchElementException e) {
            fail("Exception thrown unexpectedly: " + e.getMessage());
        }

        // Проверяем
        assertNotNull(result);
        assertEquals(user, result.getLogin());
        assertEquals(new BigDecimal("50.00"), result.getTotalPrice());
        assertEquals(Status.NEW, result.getStatus());
        assertNotNull(result.getTime());

        // Проверяем, что методы были вызваны ожидаемое количество раз
        verify(cartRepository, times(1)).deleteByLoginId(user.getId());
        verify(ordersRepository, times(1)).save(any(Orders.class));
    }
}