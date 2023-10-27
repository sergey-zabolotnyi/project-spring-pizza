package de.telran.pizza.service;

import de.telran.pizza.config.MessageHelper;
import de.telran.pizza.domain.dto.CartDTO;
import de.telran.pizza.domain.dto.ItemDTO;
import de.telran.pizza.domain.entity.Cart;
import de.telran.pizza.domain.entity.Category;
import de.telran.pizza.domain.entity.Dish;
import de.telran.pizza.domain.entity.Login;
import de.telran.pizza.domain.entity.enums.Role;
import de.telran.pizza.repository.CartRepository;
import de.telran.pizza.repository.DishRepository;
import de.telran.pizza.security.UserDetailSecurity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

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

    private CartService cartService;

    @BeforeEach
    void setUp() {
        cartRepository = mock(CartRepository.class);
        helper = mock(MessageHelper.class);
        cartService = new CartService(cartRepository, dishRepository, helper);
    }

    @Test
    void findAllDishes_validCart() {
        // Подготовка: Создаем пользователя
        Login user = new Login("user", "123456");
        // Устанавливаем пользователя в контекст безопасности
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(
                new UserDetailSecurity(user), null, Collections.emptyList()));

        // Подготовка: Создаем блюдо и элемент корзины с этим блюдом
        Dish dish = new Dish(1, "Pepperoni", "Пепперони", new BigDecimal(10), new Category(),
                LocalDateTime.now());
        List<Cart> cartItems = List.of(new Cart(1, user, dish));

        // Настраиваем мок cartRepository
        when(cartRepository.findAllByLoginId(anyInt())).thenReturn(cartItems);

        // Действие: Вызываем метод findAllDishes
        CartDTO cartDTO = cartService.findAllDishes();

        // Проверка: Убеждаемся, что результат не равен null
        assertNotNull(cartDTO);

        // Проверка: Убеждаемся, что список блюд не пустой
        assertFalse(cartDTO.getDishes().isEmpty());

        // Проверка: Убеждаемся, что общая стоимость равна 10 (по предположению, что корзина пуста)
        assertEquals(BigDecimal.TEN, cartDTO.getTotalPrice());
    }

//    @Test
//    void saveNewItem_validItem() {
//        //Подготовка: Создаем пользователя
//        Login user = new Login("user", "123456");
//        //Устанавливаем пользователя в контекст безопасности
//        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(
//                new UserDetailSecurity(user), null, Collections.emptyList()));
//
//        ItemDTO itemDTO = new ItemDTO(1);
//        Dish dish = new Dish(1, "Pepperoni", "Пепперони", new BigDecimal(10.50), new Category(), LocalDateTime.now());
//        when(dishRepository.findById(anyInt())).thenReturn(Optional.of(dish));
//        when(helper.getLogMessage(anyString())).thenReturn("Test Log Message");
//
//        // Act
//        Cart savedCart = cartService.saveNewItem(itemDTO);
//
//        // Assert
//        assertNotNull(savedCart);
//        assertEquals(user, savedCart.getLogin());
//        assertEquals(dish, savedCart.getDish());
//        verify(cartRepository, times(1)).save(any(Cart.class));
//    }

    @Test
    void saveNewItem_invalidItem() {
        // Подготовка: Создаем пользователя
        Login user = new Login("user", "123456");
        // Устанавливаем пользователя в контекст безопасности
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(
                new UserDetailSecurity(user), null, Collections.emptyList()));

        // Подготовка: Создаем объект ItemDTO с указанием несуществующего ID блюда
        ItemDTO itemDTO = new ItemDTO(1);

        // Настраиваем мок dishRepository для возврата Optional.empty() при вызове метода findById
        when(dishRepository.findById(anyInt())).thenReturn(Optional.empty());

        // Настраиваем мок helper для возврата "Test Log Message" при вызове метода getLogMessage
        when(helper.getLogMessage(anyString())).thenReturn("Test Log Message");

        // Действие и проверка
        // Проверяем, что при вызове метода saveNewItem с некорректным ItemDTO выбрасывается исключение NoSuchElementException
        assertThrows(NoSuchElementException.class, () -> cartService.saveNewItem(itemDTO));
    }

    @Test
    void delete_validId_cartDeleted() {
        // Подготовка: Устанавливаем условия для теста
        int dishId = 1;
        Cart cart = new Cart();
        List<Cart> cartList = List.of(cart);

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
        int dishId = 1;
        List<Cart> cartList = List.of();

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
        int userId = 1;

        // Действие: Вызываем метод, который хотим протестировать
        cartService.deleteByLogin(userId);

        // Проверка: Проверяем, что метод репозитория был вызван с правильным аргументом
        verify(cartRepository, times(1)).deleteByLoginId(userId);
    }
}