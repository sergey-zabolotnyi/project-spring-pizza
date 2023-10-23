package de.telran.pizza.utils;

import de.telran.pizza.domain.entity.Login;
import de.telran.pizza.security.UserDetailSecurity;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UtilsTest {

    @Test
    void isLocaleEnglish_shouldReturnTrueForEnglishLocale() {
        LocaleContextHolder.setLocale(Locale.ENGLISH);
        assertTrue(Utils.isLocaleEnglish());
    }

    @Test
    void isLocaleEnglish_shouldReturnFalseForNonEnglishLocale() {
        LocaleContextHolder.setLocale(Locale.JAPAN);
        assertFalse(Utils.isLocaleEnglish());
    }

    @Test
    void getAuthorizedLogin_shouldReturnLogin() {
        // Мокируем объект Authentication
        Authentication authentication = mock(Authentication.class);

        // Мокируем объект UserDetailSecurity
        UserDetailSecurity userDetails = mock(UserDetailSecurity.class);
        Login expectedLogin = new Login("sidor", "qwerty");
        when(userDetails.getLogin()).thenReturn(expectedLogin);

        // Устанавливаем SecurityContextHolder для возвращения мокированного объекта Authentication
        SecurityContextHolder.getContext().setAuthentication(authentication);
        when(authentication.getPrincipal()).thenReturn(userDetails);

        // Проверяем, что ожидаемый объект Login возвращается
        assertEquals(expectedLogin, Utils.getAuthorizedLogin());
    }
}