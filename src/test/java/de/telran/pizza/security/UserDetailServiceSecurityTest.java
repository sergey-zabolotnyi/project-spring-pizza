package de.telran.pizza.security;

import de.telran.pizza.domain.entity.User;
import de.telran.pizza.service.UserService;
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
        UserService loginService = Mockito.mock(UserService.class);
        User mockLogin = new User();
        Mockito.when(loginService.findByUserLogin(anyString())).thenReturn(Optional.of(mockLogin));

        UserDetailServiceSecurity userDetailService = new UserDetailServiceSecurity(loginService);

        // Действие
        UserDetails userDetails = userDetailService.loadUserByUsername(username);

        // Проверка
        assertNotNull(userDetails);
        assertEquals(mockLogin, ((UserDetailSecurity) userDetails).getUser());
    }

    @Test
    public void testLoadUserByUsername_UserNotFound() {
        // Подготовка
        String username = "not_found_user";
        UserService loginService = Mockito.mock(UserService.class);
        Mockito.when(loginService.findByUserLogin(anyString())).thenReturn(Optional.empty());

        UserDetailServiceSecurity userDetailService = new UserDetailServiceSecurity(loginService);

        // Действие & Проверка
        assertThrows(UsernameNotFoundException.class, () -> userDetailService.loadUserByUsername(username));
    }
}