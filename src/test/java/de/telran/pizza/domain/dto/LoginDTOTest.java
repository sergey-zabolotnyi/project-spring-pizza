package de.telran.pizza.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class LoginDTOTest {

    @Test
    void loginDTO_CreateLoginObject_ExpectCorrectData() {
        // Подготовка
        String expectedLogin = "user";
        String expectedEmail = "user@gmail.com";
        String expectedPassword = "qwerty";

        // Выполнение
        LoginDTO loginDTO = LoginDTO.builder()
                .login(expectedLogin)
                .email(expectedEmail)
                .password(expectedPassword)
                .build();

        // Проверка
        assertNotNull(loginDTO);
        assertEquals(expectedLogin, loginDTO.getLogin());
        assertEquals(expectedEmail, loginDTO.getEmail());
        assertEquals(expectedPassword, loginDTO.getPassword());
    }
    @Test
    void getLogin() {
        String login = "user";
        LoginDTO loginDTO = LoginDTO.builder().login(login).build();
        String result = loginDTO.getLogin();
        assertEquals(login, result);
    }

    @Test
    void getEmail() {
        String email = "user@gmail.com";
        LoginDTO loginDTO = LoginDTO.builder().email(email).build();
        String result = loginDTO.getEmail();
        assertEquals(email, result);
    }

    @Test
    void getPassword() {
        String password = "qwerty";
        LoginDTO loginDTO = LoginDTO.builder().password(password).build();
        String result = loginDTO.getPassword();
        assertEquals(password, result);
    }

    @Test
    void setLogin() {
        LoginDTO loginDTO = LoginDTO.builder().login("user").build();
        loginDTO.setLogin("user1");
        assertEquals("user1", loginDTO.getLogin());
    }

    @Test
    void setEmail() {
        LoginDTO loginDTO = LoginDTO.builder().email("user@gmail.com").build();
        loginDTO.setEmail("user1@gmail.com");
        assertEquals("user1@gmail.com", loginDTO.getEmail());
    }

    @Test
    void setPassword() {
        LoginDTO loginDTO = LoginDTO.builder().password("qwerty").build();
        loginDTO.setPassword("qwerty1");
        assertEquals("qwerty1", loginDTO.getPassword());
    }

    @Test
    void testEquals() {
        LoginDTO loginDTO1 = LoginDTO.builder().login("user").email("user@gmail.com").password("qwerty").build();
        LoginDTO loginDTO2 = LoginDTO.builder().login("user").email("user@gmail.com").password("qwerty").build();
        assertEquals(loginDTO1, loginDTO2);
    }

    @Test
    void canEqual() {
        LoginDTO loginDTO1 = LoginDTO.builder().login("user").email("user@gmail.com").password("qwerty").build();
        LoginDTO loginDTO2 = LoginDTO.builder().login("user").email("user@gmail.com").password("qwerty").build();
        assertTrue(loginDTO1.canEqual(loginDTO2));
    }

    @Test
    void testHashCode() {
        LoginDTO loginDTO1 = LoginDTO.builder().login("user").email("user@gmail.com").password("qwerty").build();
        LoginDTO loginDTO2 = LoginDTO.builder().login("user").email("user@gmail.com").password("qwerty").build();
        assertEquals(loginDTO1.hashCode(), loginDTO2.hashCode());
    }

    @Test
    void testToString() {
        LoginDTO loginDTO = LoginDTO.builder().login("user").email("user@gmail.com").password("qwerty").build();
        String result = loginDTO.toString();
        assertNotNull(result);
    }

    @Test
    void builder() {
        LoginDTO loginDTO = LoginDTO.builder().login("user").email("user@gmail.com").password("qwerty").build();
        assertNotNull(loginDTO);
    }
}