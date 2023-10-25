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
import java.time.LocalDateTime;
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

    @Test
    void getId() {
        Login login = Login.builder().id(1).build();
        assertEquals(1, login.getId());
    }

    @Test
    void getLogin() {
        Login login = Login.builder().login("user").build();
        assertEquals("user", login.getLogin());
    }

    @Test
    void getPassword() {
        Login login = Login.builder().password("qwerty").build();
        assertEquals("qwerty", login.getPassword());
    }

    @Test
    void getEmail() {
        Login login = Login.builder().email("user@gmail.com").build();
        assertEquals("user@gmail.com", login.getEmail());
    }

    @Test
    void getRole() {
        Login login = Login.builder().role(Role.ROLE_MANAGER).build();
        assertEquals(Role.ROLE_MANAGER, login.getRole());
    }

    @Test
    void getTime() {
        LocalDateTime time = LocalDateTime.now();
        Login login = Login.builder().time(time).build();
        assertEquals(time, login.getTime());
    }

    @Test
    void setId() {
        Login login = Login.builder().build();
        login.setId(2);
        assertEquals(2, login.getId());
    }

    @Test
    void setLogin() {
        Login login = Login.builder().build();
        login.setLogin("user1");
        assertEquals("user1", login.getLogin());
    }

    @Test
    void setPassword() {
        Login login = Login.builder().build();
        login.setPassword("qwerty1");
        assertEquals("qwerty1", login.getPassword());
    }

    @Test
    void setEmail() {
        Login login = Login.builder().build();
        login.setEmail("email1@gmail.com");
        assertEquals("email1@gmail.com", login.getEmail());
    }

    @Test
    void setRole() {
        Login login = Login.builder().build();
        login.setRole(Role.ROLE_CUSTOMER);
        assertEquals(Role.ROLE_CUSTOMER, login.getRole());
    }

    @Test
    void setTime() {
        LocalDateTime newTime = LocalDateTime.now();
        Login login = Login.builder().build();
        login.setTime(newTime);
        assertEquals(newTime, login.getTime());
    }

    @Test
    void testEquals() {
        Login login1 = Login.builder().id(1).build();
        Login login2 = Login.builder().id(1).build();
        assertEquals(login1, login2);
    }

    @Test
    void canEqual() {
        Login login1 = Login.builder().id(1).build();
        Login login2 = Login.builder().id(1).build();
        assertTrue(login1.canEqual(login2));
    }

    @Test
    void testHashCode() {
        Login login1 = Login.builder().id(1).build();
        Login login2 = Login.builder().id(1).build();
        assertEquals(login1.hashCode(), login2.hashCode());
    }

    @Test
    void testToString() {
        Login login = Login.builder().id(1).build();
        String result = login.toString();
        assertNotNull(result);
    }

    @Test
    void builder() {
        Login login = Login.builder().id(1).build();
        assertNotNull(login);
    }
}