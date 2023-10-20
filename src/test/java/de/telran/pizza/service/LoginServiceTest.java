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
        // Arrange
        String login = "testuser";
        Login expectedLogin = new Login(1L, login, "123456", "test@test.com",
                Role.ROLE_CUSTOMER, LocalDateTime.now());
        when(loginRepository.findByLogin(login)).thenReturn(Optional.of(expectedLogin));

        // Act
        Optional<Login> result = loginService.findByUserLogin(login);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(expectedLogin, result.get());
    }

    @Test
    void testFindByUserLoginNotFound() {
        // Arrange
        String login = "nonexistentuser";
        when(loginRepository.findByLogin(login)).thenReturn(Optional.empty());

        // Act
        Optional<Login> result = loginService.findByUserLogin(login);

        // Assert
        assertFalse(result.isPresent());
    }

    @Test
    void testSaveUser() {
        // Arrange
        LoginDTO loginDTO = new LoginDTO("newuser", "newuser@test.com", "123456");
        Role role = Role.ROLE_CUSTOMER;
        when(loginRepository.save(any(Login.class))).thenReturn(new Login(1L, "newuser", "123456",
                "newuser@test.com", role, LocalDateTime.now()));

        // Act
        Login result = loginService.saveUser(loginDTO, role);

        // Assert
        assertNotNull(result);
        assertEquals(role, result.getRole());
    }

    @Test
    void testSaveUserExistingLogin() {
        // Arrange
        LoginDTO loginDTO = new LoginDTO("existinguser", "existinguser@test.com", "123456");
        Role role = Role.ROLE_CUSTOMER;
        when(loginRepository.save(any(Login.class))).thenThrow(new IllegalArgumentException("nullexistinguser"));

        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> loginService.saveUser(loginDTO, role));
    }

    @Test
    void testGetAllUsers() {
        // Arrange
        List<Login> expectedUsers = List.of(
                new Login(1L, "user1", "123456", "user1@test.com", Role.ROLE_CUSTOMER, LocalDateTime.now()),
                new Login(2L, "user2", "123456", "user2@test.com", Role.ROLE_MANAGER, LocalDateTime.now())
        );
        when(loginRepository.findAll()).thenReturn(expectedUsers);

        // Act
        List<Login> result = loginService.getAllUsers();

        // Assert
        assertEquals(expectedUsers.size(), result.size());
        assertEquals(expectedUsers, result);
    }
}