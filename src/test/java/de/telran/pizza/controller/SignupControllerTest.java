package de.telran.pizza.controller;

import de.telran.pizza.MockData;
import de.telran.pizza.config.MessageHelper;
import de.telran.pizza.domain.dto.UserDTO;
import de.telran.pizza.domain.entity.User;
import de.telran.pizza.domain.entity.enums.Role;
import de.telran.pizza.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Тестирование класса SignupController.
 */
class SignupControllerTest {
    @Mock
    private UserService userService;
    @Mock
    private MessageHelper helper;
    @InjectMocks
    private SignupController signupController;
    @Captor
    private ArgumentCaptor<UserDTO> userDTOCaptor;
    @Mock
    private HttpServletResponse response;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    /**
     * Тестирование метода SignupController.signup().
     * Проверяет корректность обработки успешной регистрации пользователя.
     * @throws Exception если произошла ошибка во время выполнения теста.
     */
    @Test
    void testSignup() throws Exception {
        // Создаем фиктивные данные для запроса
        UserDTO userDTO = MockData.getMockedUserDTO();

        // Вызываем метод контроллера
        signupController.signup(userDTO, response);

        // Проверяем, что метод сервиса был вызван с ожидаемыми аргументами
        verify(userService).saveUser(userDTOCaptor.capture(), eq(Role.ROLE_CUSTOMER));

        // Проверяем, что запрос был перенаправлен на "login"
        verify(response).sendRedirect("login");

        // Проверяем, что аргументы, переданные в сервис, соответствуют ожидаемым
        UserDTO capturedLoginDTO = userDTOCaptor.getValue();
        assertNotNull(capturedLoginDTO);
        assertEquals("newUser", capturedLoginDTO.getUser());
        assertEquals("newuser@test.com", capturedLoginDTO.getEmail());
        assertEquals("123456", capturedLoginDTO.getPassword());
    }

    /**
     * Тестирование метода SignupController.signup().
     * Проверяет корректность обработки исключения при регистрации пользователя.
     * @throws Exception если произошла ошибка во время выполнения теста.
     */
    @Test
    void testSignupException() throws Exception {
        HttpServletResponse response = mock(HttpServletResponse.class);

        // Устанавливаем поведение сервиса при возникновении исключения
        doThrow(new RuntimeException("Error")).when(userService).saveUser(any(), any());

        // Вызываем метод контроллера и ожидаем исключение
        assertThrows(ResponseStatusException.class, () -> {
            signupController.signup(new UserDTO(), response);
        });
    }

    /**
     * Тестирование метода SignupController.getAllUsersCount().
     * Проверяет корректность возвращаемого количества пользователей.
     */
    @Test
    void testGetAllUsersCount() {
        // Создаем фиктивные данные для возвращения из сервиса
        List<User> users = MockData.getMockedListOfUsers();

        // Устанавливаем поведение сервиса при вызове метода
        when(userService.getAllUsers()).thenReturn(users);

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

    /**
     * Тестирование метода SignupController.signup() с валидным UserDTO.
     * Проверяет, что после успешной регистрации происходит перенаправление на страницу "login".
     * @throws Exception если произошла ошибка во время выполнения теста.
     */
    @Test
    void signup_ValidLoginDTO_RedirectsToLogin() throws Exception {
        // Создаем фиктивные данные для возвращения из сервиса
        UserDTO userDTO = MockData.getMockedUserDTO();
        HttpServletResponse response = mock(HttpServletResponse.class);

        // Mock логирования
        when(helper.getLogMessage(anyString())).thenReturn("Some log message");

        // Вызываем метод
        signupController.signup(userDTO, response);

        // Проверяем
        verify(userService).saveUser(userDTO, Role.ROLE_CUSTOMER);
        verify(response).sendRedirect("login");
    }

    /**
     * Тестирование метода SignupController.signup() с невалидным UserDTO.
     * Проверяет, что при возникновении исключения выбрасывается ResponseStatusException.
     */
    @Test
    void signup_InvalidLoginDTO_ThrowsBadRequestException() {
        // Создаем фиктивные данные для возвращения из сервиса
        UserDTO invalidUserDTO = MockData.getMockedUserDTO();
        HttpServletResponse response = mock(HttpServletResponse.class);

        // Mock логирования
        when(helper.getLogMessage(anyString())).thenReturn("Some log message");

        // Mock UserService, чтобы выбросить исключение
        doThrow(new RuntimeException("Some error message")).when(userService).saveUser(any(), any());

        // Вызываем метод и проверяем
        assertThrows(ResponseStatusException.class, () -> signupController.signup(invalidUserDTO, response));
    }
}
