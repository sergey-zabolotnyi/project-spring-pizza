package de.telran.pizza.service;

import de.telran.pizza.config.MessageHelper;
import de.telran.pizza.domain.dto.LoginDTO;
import de.telran.pizza.domain.entity.Login;
import de.telran.pizza.domain.entity.enums.Role;
import de.telran.pizza.repository.LoginRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LoginServiceTest {
    @Mock
    private LoginRepository loginRepository;
    @Mock
    private MessageHelper messageHelper;
    @InjectMocks
    private LoginService loginService;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }
    @Test
    void testFindByUserLogin() {
        // Подготовка: Устанавливаем входные данные
        String login = "testuser";
        Login expectedLogin = new Login(login, "123456");
        when(loginRepository.findByLogin(login)).thenReturn(Optional.of(expectedLogin));

        // Действие: Вызываем тестируемый метод
        Optional<Login> result = loginService.findByUserLogin(login);

        // Проверка: Убеждаемся, что результат присутствует и соответствует ожиданиям
        assertTrue(result.isPresent());
        assertEquals(expectedLogin, result.get());
    }

    @Test
    void testFindByUserLoginNotFound() {
        // Подготовка: Устанавливаем входные данные
        String login = "nonexistentuser";
        when(loginRepository.findByLogin(login)).thenReturn(Optional.empty());

        // Действие: Вызываем тестируемый метод
        Optional<Login> result = loginService.findByUserLogin(login);

        // Проверка: Убеждаемся, что результат отсутствует
        assertFalse(result.isPresent());
    }

    @Test
    void testSaveUser() {
        // Подготовка: Создаем объекты и настраиваем моки
        LoginDTO loginDTO = new LoginDTO("newuser", "newuser@test.com", "123456");
        Role role = Role.ROLE_CUSTOMER;
        when(loginRepository.save(any(Login.class))).thenReturn(new Login(1, "newuser", "123456",
                "newuser@test.com", role, LocalDateTime.now()));

        // Действие: Вызываем тестируемый метод
        Login result = loginService.saveUser(loginDTO, role);

        // Проверка: Убеждаемся, что результат не является null и соответствует ожиданиям
        assertNotNull(result);
        assertEquals(role, result.getRole());
    }

    @Test
    void testSaveUserExistingLogin() {
        // Подготовка: Создаем объекты и настраиваем моки
        LoginDTO loginDTO = new LoginDTO("existinguser", "existinguser@test.com", "123456");
        Role role = Role.ROLE_CUSTOMER;
        when(loginRepository.save(any(Login.class))).thenThrow(new IllegalArgumentException("nullexistinguser"));

        // Действие и Проверка: Убеждаемся, что исключение выбрасывается при вызове тестируемого метода
        assertThrows(IllegalArgumentException.class, () -> loginService.saveUser(loginDTO, role));
    }

    @Test
    void testGetAllUsers() {
        // Подготовка: Устанавливаем ожидаемый результат
        List<Login> expectedUsers = List.of(
                new Login("user1", "123456"),
                new Login("user2", "123456"));

        when(loginRepository.findAll()).thenReturn(expectedUsers);

        // Действие: Вызываем тестируемый метод
        List<Login> result = loginService.getAllUsers();

        // Проверка: Убеждаемся, что размеры и содержимое списков совпадают
        assertEquals(expectedUsers.size(), result.size());
        assertEquals(expectedUsers, result);
    }
}