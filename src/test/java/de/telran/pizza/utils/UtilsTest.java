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

/**
 * Класс с набором тестов для утилитного класса {@link Utils}.
 */
class UtilsTest {

    /**
     * Проверяет, что метод {@link Utils#isLocaleEnglish()} возвращает true при установленной локали английского языка.
     * После этого проверяет, что метод возвращает false при установленной другой локали (например, русской).
     */
    @Test
    void testIsLocaleEnglish() {
        // Устанавливаем локаль на английский
        LocaleContextHolder.setLocale(Locale.ENGLISH);
        // Проверяем, что метод isLocaleEnglish() возвращает true
        assertEquals(true, Utils.isLocaleEnglish());

        // Устанавливаем локаль на другой язык (например, русский)
        LocaleContextHolder.setLocale(new Locale("ru", "RU"));
        // Проверяем, что метод isLocaleEnglish() возвращает false
        assertEquals(false, Utils.isLocaleEnglish());
    }

    /**
     * Проверяет, что метод {@link Utils#isLocaleEnglish()} возвращает false для неанглийской локали.
     */
    @Test
    void isLocaleEnglish_shouldReturnFalseForNonEnglishLocale() {
        // Устанавливаем локаль на другом языке
        LocaleContextHolder.setLocale(Locale.JAPAN);

        // Действие (Act)
        boolean result = Utils.isLocaleEnglish();

        // Проверяем
        assertFalse(result);
    }

    /**
     * Проверяет, что метод {@link Utils#getAuthorizedLogin()} возвращает ожидаемого пользователя,
     * когда пользователь авторизован.
     */
    @Test
    void getAuthorizedLogin_shouldReturnsAuthorizedLogin() {
        // Подготовка. Создаем ожидаемый объект User с определенными данными
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

        // Проверяем, что ожидаемый объект не null
        assertNotNull(actualUser);
        // Проверяем, что ожидаемый объект User возвращается
        assertEquals(user, actualUser);
    }
}