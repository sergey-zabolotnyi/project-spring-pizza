package de.telran.pizza.utils;

import de.telran.pizza.domain.entity.Login;
import de.telran.pizza.security.UserDetailSecurity;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Locale;

/**
 * Helper class for accessing information related to the application context.
 */
public class ContextHelper {

    /**
     * Checks if the current locale is set to English.
     * @return True if the current locale is English, otherwise false.
     */
    public static boolean isLocaleEnglish(){
        return LocaleContextHolder.getLocale().equals(Locale.ENGLISH);
    }

    /**
     * Gets the authorized login associated with the current authenticated user.
     * @return The authorized login.
     */
    public static Login getAuthorizedLogin() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetailSecurity userDetails = (UserDetailSecurity) auth.getPrincipal();

        return userDetails.getLogin();
    }
}
