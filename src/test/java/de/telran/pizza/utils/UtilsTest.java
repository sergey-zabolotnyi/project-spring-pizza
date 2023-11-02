package de.telran.pizza.utils;

import de.telran.pizza.MockData;
import de.telran.pizza.domain.entity.User;
import de.telran.pizza.security.UserDetailSecurity;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UtilsTest {

    @Test
    void isLocaleEnglish_shouldReturnTrueForEnglishLocale() {
        // Подготовка (Arrange)
        LocaleContextHolder.setLocale(Locale.ENGLISH);

        // Действие (Act)
        boolean result = Utils.isLocaleEnglish();

        // Проверка (Assert)
        assertTrue(result);
    }

    @Test
    void isLocaleEnglish_shouldReturnFalseForNonEnglishLocale() {
        // Подготовка (Arrange)
        LocaleContextHolder.setLocale(Locale.JAPAN);

        // Действие (Act)
        boolean result = Utils.isLocaleEnglish();

        // Проверка (Assert)
        assertFalse(result);
    }

    @Test
    void getAuthorizedLogin_shouldReturnsAuthorizedLogin() {
        // Подготовка (Arrange)
        // Создаем ожидаемый объект User с определенными данными
        User user = MockData.getMockedUser();
        // Создаем объект UserDetailSecurity с ожидаемым User
        UserDetailSecurity userDetails = new UserDetailSecurity(user);

        // Мокируем объект Authentication
        Authentication authentication = Mockito.mock(Authentication.class);
        // Когда вызывается метод getPrincipal(), возвращаем userDetails
        when(authentication.getPrincipal()).thenReturn(userDetails);

        // Мокируем объект SecurityContext
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        // Когда вызывается метод getAuthentication(), возвращаем authentication
        when(securityContext.getAuthentication()).thenReturn(authentication);
        // Устанавливаем мок SecurityContext в SecurityContextHolder
        SecurityContextHolder.setContext(securityContext);

        // Действие (Act)
        User actualUser = Utils.getAuthorizedLogin();

        // Проверка (Assert)
        // Проверяем, что ожидаемый объект не null
        assertNotNull(actualUser);
        // Проверяем, что ожидаемый объект User возвращается
        assertEquals(user, actualUser);
    }
}