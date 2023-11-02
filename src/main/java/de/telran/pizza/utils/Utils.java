package de.telran.pizza.utils;

import de.telran.pizza.domain.entity.User;
import de.telran.pizza.security.UserDetailSecurity;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Locale;

/**
 * Служебный класс для доступа к информации, связанной с контекстом приложения.
 */
public class Utils {

    /**
     * Проверяет, установлен ли текущий язык на английский.
     *
     * @return true, если текущий язык - английский, в противном случае false.
     */
    public static boolean isLocaleEnglish() {
        return LocaleContextHolder.getLocale().equals(Locale.ENGLISH);
    }

    /**
     * Получает авторизованный логин, связанный с текущим аутентифицированным пользователем.
     *
     * @return Авторизованный логин.
     */
    public static User getAuthorizedLogin() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetailSecurity userDetails = (UserDetailSecurity) auth.getPrincipal();

        return userDetails.getUser();
    }
}
