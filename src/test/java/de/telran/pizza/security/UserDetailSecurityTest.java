package de.telran.pizza.security;

import de.telran.pizza.domain.entity.Login;
import de.telran.pizza.domain.entity.enums.Role;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

public class UserDetailSecurityTest {

    @Test
    public void testGetAuthorities() {
        Login login = new Login();
        login.setRole(Role.ROLE_MANAGER);

        UserDetailSecurity userDetailSecurity = new UserDetailSecurity(login);
        Collection<? extends GrantedAuthority> authorities = userDetailSecurity.getAuthorities();

        assertNotNull(authorities);
        assertEquals(1, authorities.size());
        assertTrue(authorities.contains(new SimpleGrantedAuthority("ROLE_MANAGER")));
    }

    @Test
    public void testGetPassword() {
        Login login = new Login();
        login.setPassword("password123");

        UserDetailSecurity userDetailSecurity = new UserDetailSecurity(login);
        String password = userDetailSecurity.getPassword();

        assertEquals("password123", password);
    }

    @Test
    public void testGetUsername() {
        Login login = new Login();
        login.setLogin("ivan");

        UserDetailSecurity userDetailSecurity = new UserDetailSecurity(login);
        String username = userDetailSecurity.getUsername();

        assertEquals("ivan", username);
    }

    @Test
    public void testAccountNonExpired() {
        UserDetailSecurity userDetailSecurity = new UserDetailSecurity(new Login());
        assertTrue(userDetailSecurity.isAccountNonExpired());
    }

    @Test
    public void testAccountNonLocked() {
        UserDetailSecurity userDetailSecurity = new UserDetailSecurity(new Login());
        assertTrue(userDetailSecurity.isAccountNonLocked());
    }

    @Test
    public void testCredentialsNonExpired() {
        UserDetailSecurity userDetailSecurity = new UserDetailSecurity(new Login());
        assertTrue(userDetailSecurity.isCredentialsNonExpired());
    }

    @Test
    public void testIsEnabled() {
        UserDetailSecurity userDetailSecurity = new UserDetailSecurity(new Login());
        assertTrue(userDetailSecurity.isEnabled());
    }

    @Test
    void getLogin() {
        Login login = Login.builder().login("user1").build();
        UserDetailSecurity userDetailSecurity = new UserDetailSecurity(login);

        assertEquals(login, userDetailSecurity.getLogin());
    }

    @Test
    void setLogin() {
        Login initialLogin = Login.builder().login("user1").build();
        Login newLogin = Login.builder().login("user2").build();
        UserDetailSecurity userDetailSecurity = new UserDetailSecurity(initialLogin);
        userDetailSecurity.setLogin(newLogin);

        assertEquals(newLogin, userDetailSecurity.getLogin());
    }
}