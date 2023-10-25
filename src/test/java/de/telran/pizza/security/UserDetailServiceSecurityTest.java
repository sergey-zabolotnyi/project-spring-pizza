package de.telran.pizza.security;

import de.telran.pizza.domain.entity.Login;
import de.telran.pizza.service.LoginService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;

class UserDetailServiceSecurityTest {

    @Test
    public void testLoadUserByUsername_UserFound() {
        // Подготовка
        String username = "sidor";
        LoginService loginService = Mockito.mock(LoginService.class);
        Login mockLogin = new Login();
        Mockito.when(loginService.findByUserLogin(anyString())).thenReturn(Optional.of(mockLogin));

        UserDetailServiceSecurity userDetailService = new UserDetailServiceSecurity(loginService);

        // Действие
        UserDetails userDetails = userDetailService.loadUserByUsername(username);

        // Проверка
        assertNotNull(userDetails);
        assertEquals(mockLogin, ((UserDetailSecurity) userDetails).getLogin());
    }

    @Test
    public void testLoadUserByUsername_UserNotFound() {
        // Подготовка
        String username = "not_found_user";
        LoginService loginService = Mockito.mock(LoginService.class);
        Mockito.when(loginService.findByUserLogin(anyString())).thenReturn(Optional.empty());

        UserDetailServiceSecurity userDetailService = new UserDetailServiceSecurity(loginService);

        // Действие & Проверка
        assertThrows(UsernameNotFoundException.class, () -> userDetailService.loadUserByUsername(username));
    }
}