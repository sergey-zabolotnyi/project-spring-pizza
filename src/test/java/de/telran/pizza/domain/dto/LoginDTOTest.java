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
    void loginDTO_SchemaDescriptionMatches() {
        // Подготовка
        Class<LoginDTO> clazz = LoginDTO.class;

        // Проверка аннотации @Schema
        Schema schemaAnnotation = clazz.getAnnotation(Schema.class);
        assertNotNull(schemaAnnotation);
        assertEquals("ДТО объект для входа пользователя в систему.", schemaAnnotation.description());

        // Проверка поля login
        try {
            java.lang.reflect.Field loginField = clazz.getDeclaredField("login");
            Schema loginFieldAnnotation = loginField.getAnnotation(Schema.class);
            assertNotNull(loginFieldAnnotation);
            assertEquals("Логин пользователя", loginFieldAnnotation.description());
            assertEquals("user", loginFieldAnnotation.example());
        } catch (NoSuchFieldException e) {
            fail("Field 'login' not found");
        }

        // Проверка поля email
        try {
            java.lang.reflect.Field emailField = clazz.getDeclaredField("email");
            Schema emailFieldAnnotation = emailField.getAnnotation(Schema.class);
            assertNotNull(emailFieldAnnotation);
            assertEquals("Электронная почта пользователя", emailFieldAnnotation.description());
            assertEquals("user@gmail.com", emailFieldAnnotation.example());
        } catch (NoSuchFieldException e) {
            fail("Field 'email' not found");
        }

        // Проверка поля password
        try {
            java.lang.reflect.Field passwordField = clazz.getDeclaredField("password");
            Schema passwordFieldAnnotation = passwordField.getAnnotation(Schema.class);
            assertNotNull(passwordFieldAnnotation);
            assertEquals("Пароль пользователя", passwordFieldAnnotation.description());
            assertEquals("qwerty", passwordFieldAnnotation.example());
        } catch (NoSuchFieldException e) {
            fail("Field 'password' not found");
        }
    }
}