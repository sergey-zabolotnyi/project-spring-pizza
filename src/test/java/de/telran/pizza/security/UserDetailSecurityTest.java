package de.telran.pizza.security;

import de.telran.pizza.MockData;
import de.telran.pizza.domain.entity.User;
import de.telran.pizza.domain.entity.enums.Role;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Юнит-тест для класса UserDetailSecurity.
 */
public class UserDetailSecurityTest {

    /**
     * Тест метода getAuthorities.
     */
    @Test
    public void testGetAuthorities() {
        User user = MockData.getMockedUser();
        user.setRole(Role.ROLE_MANAGER);

        UserDetailSecurity userDetailSecurity = new UserDetailSecurity(user);
        Collection<? extends GrantedAuthority> authorities = userDetailSecurity.getAuthorities();

        assertNotNull(authorities);
        assertEquals(1, authorities.size());
        assertTrue(authorities.contains(new SimpleGrantedAuthority("ROLE_MANAGER")));
    }

    /**
     * Тест для проверки корректности возврата пароля пользователя.
     */
    @Test
    public void testGetPassword() {
        User user = MockData.getMockedUser();
        user.setPassword("password123");

        UserDetailSecurity userDetailSecurity = new UserDetailSecurity(user);
        String password = userDetailSecurity.getPassword();

        assertEquals("password123", password);
    }

    /**
     * Тест для проверки корректности возврата имени пользователя.
     */
    @Test
    public void testGetUsername() {
        User user = MockData.getMockedUser();
        user.setUser("ivan");

        UserDetailSecurity userDetailSecurity = new UserDetailSecurity(user);
        String username = userDetailSecurity.getUsername();

        assertEquals("ivan", username);
    }

    /**
     * Тест для проверки корректности состояния аккаунта (не истек ли срок).
     */
    @Test
    public void testAccountNonExpired() {
        UserDetailSecurity userDetailSecurity = new UserDetailSecurity(new User());
        assertTrue(userDetailSecurity.isAccountNonExpired());
    }

    /**
     * Тест для проверки корректности состояния аккаунта (не заблокирован ли).
     */
    @Test
    public void testAccountNonLocked() {
        UserDetailSecurity userDetailSecurity = new UserDetailSecurity(new User());
        assertTrue(userDetailSecurity.isAccountNonLocked());
    }

    /**
     * Тест для проверки корректности состояния учетных данных (не истек ли срок действия пароля).
     */
    @Test
    public void testCredentialsNonExpired() {
        UserDetailSecurity userDetailSecurity = new UserDetailSecurity(new User());
        assertTrue(userDetailSecurity.isCredentialsNonExpired());
    }

    /**
     * Тест для проверки корректности активации аккаунта.
     */
    @Test
    public void testIsEnabled() {
        UserDetailSecurity userDetailSecurity = new UserDetailSecurity(new User());
        assertTrue(userDetailSecurity.isEnabled());
    }

    /**
     * Тест для проверки получения пользователя.
     */
    @Test
    void getUser() {
        User user = User.builder().user("user1").build();
        UserDetailSecurity userDetailSecurity = new UserDetailSecurity(user);

        assertEquals(user, userDetailSecurity.getUser());
    }

    /**
     * Тест для проверки установки нового пользователя.
     */
    @Test
    void setUser() {
        User initialUser = User.builder().user("user1").build();
        User newLogin = User.builder().user("user2").build();
        UserDetailSecurity userDetailSecurity = new UserDetailSecurity(initialUser);
        userDetailSecurity.setUser(newLogin);

        assertEquals(newLogin, userDetailSecurity.getUser());
    }
}