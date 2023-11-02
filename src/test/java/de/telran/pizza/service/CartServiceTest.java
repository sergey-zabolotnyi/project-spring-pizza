package de.telran.pizza.service;

import de.telran.pizza.MockData;
import de.telran.pizza.config.MessageHelper;
import de.telran.pizza.domain.dto.CartDTO;
import de.telran.pizza.domain.dto.DishDTO;
import de.telran.pizza.domain.entity.Cart;
import de.telran.pizza.domain.entity.User;
import de.telran.pizza.repository.CartRepository;
import de.telran.pizza.repository.DishRepository;
import de.telran.pizza.security.UserDetailSecurity;
import de.telran.pizza.service.mapper.Mappers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@SpringBootTest
class CartServiceTest {
    @Mock
    private CartRepository cartRepository;
    @Mock
    private DishRepository dishRepository;
    @Mock
    private MessageHelper helper;
    @Mock
    private Mappers mappers;

    private CartService cartService;

    @BeforeEach
    void setUp() {
        cartRepository = mock(CartRepository.class);
        helper = mock(MessageHelper.class);
        cartService = new CartService(cartRepository, dishRepository, mappers, helper);
    }

    @Test
    void testFindAllDishes() {
        // Подготовка: Создаем пользователя
        User user = MockData.getMockedUser();
        // Устанавливаем пользователя в контекст безопасности
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(
                new UserDetailSecurity(user), null, Collections.emptyList()));
        List<Cart> cartList = MockData.getMockedListOfCarts();
        List<DishDTO> dishDTOList = MockData.getMockedListOfDishesDTO();
        BigDecimal totalPrice = new BigDecimal("25.00");

        // Настроим mock-репозитории, чтобы возвращать тестовые данные при вызове findAllByLoginId
        when(cartRepository.findAllByUserId(anyInt())).thenReturn(cartList);

        // Настроим mock-mappers, чтобы возвращать ожидаемый результат при вызове cartListToDishDTOList и calculateTotalPrice
        when(mappers.cartListToDishDTOList(cartList)).thenReturn(dishDTOList);
        when(mappers.calculateTotalPrice(dishDTOList)).thenReturn(totalPrice);

        // Вызываем метод, который мы хотим протестировать
        CartDTO result = cartService.findAllDishes();

        // Проверяем, что результат соответствует ожиданиям
        assertEquals(dishDTOList, result.getDishes());
        assertEquals(totalPrice, result.getTotalPrice());
    }

    @Test
    void saveNewItem() {
        // Подготовка: Создаем пользователя
        User user = MockData.getMockedUser();
        // Устанавливаем пользователя в контекст безопасности
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(
                new UserDetailSecurity(user), null, Collections.emptyList()));
        int id = MockData.getMockedDish().getId();

        // Настраиваем
        when(dishRepository.findById(anyInt())).thenReturn(Optional.empty());
        when(helper.getLogMessage(anyString())).thenReturn("Test Log Message");

        // Действие и проверка
        assertThrows(NoSuchElementException.class, () -> cartService.saveNewItem(id));

        // Проверяем, что dishRepository.findById был вызван ровно один раз с правильным ID
        verify(dishRepository, times(1)).findById(id);

        // Проверяем, что helper.getLogMessage был вызван ровно один раз с правильным сообщением
        verify(helper, times(1)).getLogMessage(anyString());
    }

    @Test
    void delete_validId_cartDeleted() {
        // Подготовка: Устанавливаем условия для теста
        int dishId = MockData.getMockedCart().getId();
        Cart cart = MockData.getMockedCart();
        List<Cart> cartList = Arrays.asList(cart);

        when(cartRepository.findCartByDish_Id(dishId)).thenReturn(cartList);
        doNothing().when(cartRepository).delete(cart);

        // Действие: Вызываем метод, который хотим протестировать
        cartService.delete(dishId);

        // Проверка: Проверяем, что методы репозитория были вызваны правильное количество раз
        verify(cartRepository, times(1)).findCartByDish_Id(dishId);
        verify(cartRepository, times(1)).delete(cart);
    }

    @Test
    void delete_invalidId_exceptionThrown() {
        // Подготовка: Устанавливаем условия для теста
        int dishId = MockData.getMockedCart().getId();
        List<Cart> cartList = Arrays.asList();

        when(cartRepository.findCartByDish_Id(dishId)).thenReturn(cartList);
        when(helper.getLogMessage(anyString())).thenReturn("Test Log Message");

        // Действие и Проверка: Проверяем, что метод выбрасывает исключение с правильным сообщением
        NoSuchElementException exception = assertThrows(
                NoSuchElementException.class,
                () -> cartService.delete(dishId)
        );

        assertEquals("Test Log Message", exception.getMessage());
    }

    @Test
    void deleteByLogin_validId_cartDeleted() {
        // Подготовка: Устанавливаем условия для теста
        int userId = MockData.getMockedCart().getId();

        // Действие: Вызываем метод, который хотим протестировать
        cartService.deleteByLogin(userId);

        // Проверка: Проверяем, что метод репозитория был вызван с правильным аргументом
        verify(cartRepository, times(1)).deleteByUserId(userId);
    }
}