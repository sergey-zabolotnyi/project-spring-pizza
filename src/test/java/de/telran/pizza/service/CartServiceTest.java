package de.telran.pizza.service;

import de.telran.pizza.MockData;
import de.telran.pizza.config.MessageHelper;
import de.telran.pizza.domain.dto.CartDTO;
import de.telran.pizza.domain.dto.DishDTO;
import de.telran.pizza.domain.entity.Cart;
import de.telran.pizza.domain.entity.Dish;
import de.telran.pizza.domain.entity.Login;
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
        Login user = MockData.getMockedUser();
        // Устанавливаем пользователя в контекст безопасности
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(
                new UserDetailSecurity(user), null, Collections.emptyList()));
        List<Cart> cartList = MockData.getMockedListOfCarts();
        List<DishDTO> dishDTOList = MockData.getMockedListOfDishesDTO();
        BigDecimal totalPrice = new BigDecimal("25.00");

        // Настроим mock-репозитории, чтобы возвращать тестовые данные при вызове findAllByLoginId
        when(cartRepository.findAllByLoginId(anyInt())).thenReturn(cartList);

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
    void saveNewItem_invalidItem() {
        // Подготовка: Создаем пользователя
        Login user = MockData.getMockedUser();
        // Устанавливаем пользователя в контекст безопасности
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(
                new UserDetailSecurity(user), null, Collections.emptyList()));

        // Подготовка: Создаем идентификатор блюда
        int id = 1;

        // Настраиваем мок dishRepository для возврата Optional.empty() при вызове метода findById
        when(dishRepository.findById(anyInt())).thenReturn(Optional.empty());

        // Настраиваем мок helper для возврата "Test Log Message" при вызове метода getLogMessage
        when(helper.getLogMessage(anyString())).thenReturn("Test Log Message");

        // Действие и проверка
        // Проверяем, что при вызове метода saveNewItem с некорректным ItemDTO выбрасывается исключение NoSuchElementException
        assertThrows(NoSuchElementException.class, () -> cartService.saveNewItem(id));
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
        verify(cartRepository, times(1)).deleteByLoginId(userId);
    }
}