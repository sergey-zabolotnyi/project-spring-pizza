package de.telran.pizza.service;

import de.telran.pizza.MockData;
import de.telran.pizza.config.MessageHelper;
import de.telran.pizza.domain.dto.UserDTO;
import de.telran.pizza.domain.entity.User;
import de.telran.pizza.domain.entity.enums.Role;
import de.telran.pizza.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private MessageHelper messageHelper;
    @InjectMocks
    private UserService userService;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }
    @Test
    void testFindByUserLogin() {
        // Подготовка: Устанавливаем входные данные
        String user = "testuser";
        User expectedLogin = new User(user, "123456");
        when(userRepository.findByUser(user)).thenReturn(Optional.of(expectedLogin));

        // Действие: Вызываем тестируемый метод
        Optional<User> result = userService.findByUserLogin(user);

        // Проверка: Убеждаемся, что результат присутствует и соответствует ожиданиям
        assertTrue(result.isPresent());
        assertEquals(expectedLogin, result.get());
    }

    @Test
    void testFindByUserLoginNotFound() {
        // Подготовка: Устанавливаем входные данные
        String user = "nonexistentuser";
        when(userRepository.findByUser(user)).thenReturn(Optional.empty());

        // Действие: Вызываем тестируемый метод
        Optional<User> result = userService.findByUserLogin(user);

        // Проверка: Убеждаемся, что результат отсутствует
        assertFalse(result.isPresent());
    }

    @Test
    void testSaveUser() {
        // Подготовка: Создаем объекты и настраиваем моки
        UserDTO userDTO = MockData.getMockedUserDTO();
        Role role = Role.ROLE_CUSTOMER;
        when(userRepository.save(any(User.class))).thenReturn(new User(1, "newUser", "123456",
                "newuser@test.com", role, LocalDateTime.now()));

        // Действие: Вызываем тестируемый метод
        User result = userService.saveUser(userDTO, role);

        // Проверка: Убеждаемся, что результат не является null и соответствует ожиданиям
        assertNotNull(result);
        assertEquals(role, result.getRole());
    }

    @Test
    void testSaveUserExistingLogin() {
        // Подготовка: Создаем объекты и настраиваем моки
        UserDTO userDTO = MockData.getMockedUserDTO();
        Role role = Role.ROLE_CUSTOMER;
        when(userRepository.save(any(User.class))).thenThrow(new IllegalArgumentException("nullexistinguser"));

        // Действие и Проверка: Убеждаемся, что исключение выбрасывается при вызове тестируемого метода
        assertThrows(IllegalArgumentException.class, () -> userService.saveUser(userDTO, role));
    }

    @Test
    void testGetAllUsers() {
        // Подготовка: Устанавливаем ожидаемый результат
        List<User> expectedUsers = MockData.getMockedListOfUsers();

        when(userRepository.findAll()).thenReturn(expectedUsers);

        // Действие: Вызываем тестируемый метод
        List<User> result = userService.getAllUsers();

        // Проверка: Убеждаемся, что размеры и содержимое списков совпадают
        assertEquals(expectedUsers.size(), result.size());
        assertEquals(expectedUsers, result);
    }
}