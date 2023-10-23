package de.telran.pizza.domain.entity;

import static org.junit.jupiter.api.Assertions.*;

import com.fasterxml.jackson.annotation.JsonFormat;
import de.telran.pizza.domain.entity.enums.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import org.junit.jupiter.api.Test;

import javax.persistence.Column;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import java.lang.reflect.Field;
import java.util.Set;

public class LoginTest {

    @Test
    void testLoginValidation() {
        Login login = Login.builder()
                .login("user")
                .password("qwerty")
                .email("user@gmail.com")
                .role(Role.ROLE_MANAGER)
                .build();

        Set<ConstraintViolation<Login>> violations = Validation.buildDefaultValidatorFactory().getValidator().validate(login);
        assertTrue(violations.isEmpty());
    }

    @Test
    void testLoginValidationInvalidEmail() {
        Login login = Login.builder()
                .login("user")
                .password("qwerty")
                .email("invalid-email")
                .role(Role.ROLE_MANAGER)
                .build();

        Set<ConstraintViolation<Login>> violations = Validation.buildDefaultValidatorFactory().getValidator().validate(login);
        assertEquals(1, violations.size());
        assertEquals("Invalid email address", violations.iterator().next().getMessage());
    }

    @Test
    void testLoginAnnotations() throws NoSuchFieldException {
        Field idField = Login.class.getDeclaredField("id");
        assertNotNull(idField.getAnnotation(Id.class));
        assertNotNull(idField.getAnnotation(GeneratedValue.class));
        assertNotNull(idField.getAnnotation(Column.class));
        assertNotNull(idField.getAnnotation(Schema.class));

        Field loginField = Login.class.getDeclaredField("login");
        assertNotNull(loginField.getAnnotation(Column.class));
        assertNotNull(loginField.getAnnotation(Schema.class));

        Field passwordField = Login.class.getDeclaredField("password");
        assertNotNull(passwordField.getAnnotation(Column.class));
        assertNotNull(passwordField.getAnnotation(Schema.class));

        Field emailField = Login.class.getDeclaredField("email");
        assertNotNull(emailField.getAnnotation(Column.class));
        assertNotNull(emailField.getAnnotation(Schema.class));

        Field roleField = Login.class.getDeclaredField("role");
        assertNotNull(roleField.getAnnotation(Column.class));
        assertNotNull(roleField.getAnnotation(Enumerated.class));
        assertNotNull(roleField.getAnnotation(Schema.class));

        Field timeField = Login.class.getDeclaredField("time");
        assertNotNull(timeField.getAnnotation(Schema.class));
        assertNotNull(timeField.getAnnotation(JsonFormat.class));
    }
}