package de.telran.pizza.controller;

import de.telran.pizza.config.MessageHelper;
import de.telran.pizza.domain.dto.LoginDTO;
import de.telran.pizza.domain.entity.Login;
import de.telran.pizza.domain.entity.enums.Role;
import de.telran.pizza.service.LoginService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static de.telran.pizza.domain.entity.enums.Role.ROLE_CUSTOMER;
import static de.telran.pizza.domain.entity.enums.Role.ROLE_MANAGER;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SignupControllerTest {

    @Mock
    private LoginService loginService;
    @Mock
    private MessageHelper helper;
    @InjectMocks
    private SignupController signupController;
    @Captor
    private ArgumentCaptor<LoginDTO> loginDTOCaptor;
    @Mock
    private HttpServletResponse response;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testSignup() throws Exception {
        // Создаем фиктивные данные для запроса
        LoginDTO loginDTO = new LoginDTO("name", "name@example.com", "123456");

        // Вызываем метод контроллера
        signupController.signup(loginDTO, response);

        // Проверяем, что метод сервиса был вызван с ожидаемыми аргументами
        verify(loginService).saveUser(loginDTOCaptor.capture(), eq(Role.ROLE_CUSTOMER));

        // Проверяем, что запрос был перенаправлен на "login"
        verify(response).sendRedirect("login");

        // Проверяем, что аргументы, переданные в сервис, соответствуют ожидаемым
        LoginDTO capturedLoginDTO = loginDTOCaptor.getValue();
        assertNotNull(capturedLoginDTO);
        assertEquals("name", capturedLoginDTO.getLogin());
        assertEquals("name@example.com", capturedLoginDTO.getEmail());
        assertEquals("123456", capturedLoginDTO.getPassword());
    }
    @Test
    void testSignupException() throws Exception {
        HttpServletResponse response = mock(HttpServletResponse.class);

        // Устанавливаем поведение сервиса при возникновении исключения
        doThrow(new RuntimeException("Error")).when(loginService).saveUser(any(), any());

        // Вызываем метод контроллера и ожидаем исключение
        assertThrows(ResponseStatusException.class, () -> {
            signupController.signup(new LoginDTO(), response);
        });
    }

    @Test
    void testGetAllUsersCount() {
        // Создаем фиктивные данные для возвращения из сервиса
        List<Login> users = Arrays.asList(
                new Login(1,"user","qwerty","user@gmail.com", ROLE_CUSTOMER, LocalDateTime.now()),
                new Login(2,"manager","qwerty","manager@gmail.com", ROLE_MANAGER, LocalDateTime.now()));

        // Устанавливаем поведение сервиса при вызове метода
        when(loginService.getAllUsers()).thenReturn(users);

        // Вызываем метод контроллера
        ResponseEntity<Integer> response = signupController.getAllUsersCount();

        // Проверяем, что ответ не равен null
        assertNotNull(response);

        // Проверяем, что статус ответа - 200 (OK)
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // Проверяем, что количество пользователей соответствует ожидаемому
        Integer usersCount = response.getBody();
        assertNotNull(usersCount);
        assertEquals(users.size(), usersCount);
    }
    @Test
    void signup_ValidLoginDTO_RedirectsToLogin() throws Exception {
        // Arrange
        LoginDTO loginDTO = new LoginDTO();
        HttpServletResponse response = mock(HttpServletResponse.class);

        // Mock логирования
        when(helper.getLogMessage(anyString())).thenReturn("Some log message");

        // Act
        signupController.signup(loginDTO, response);

        // Assert
        verify(loginService).saveUser(loginDTO, Role.ROLE_CUSTOMER);
        verify(response).sendRedirect("login");
    }

    @Test
    void signup_InvalidLoginDTO_ThrowsBadRequestException() {
        // Arrange
        LoginDTO invalidLoginDTO = new LoginDTO();
        HttpServletResponse response = mock(HttpServletResponse.class);

        // Mock логирования
        when(helper.getLogMessage(anyString())).thenReturn("Some log message");

        // Mock LoginService, чтобы выбросить исключение
        doThrow(new RuntimeException("Some error message")).when(loginService).saveUser(any(), any());

        // Act & Assert
        assertThrows(ResponseStatusException.class, () -> signupController.signup(invalidLoginDTO, response));
    }
}
