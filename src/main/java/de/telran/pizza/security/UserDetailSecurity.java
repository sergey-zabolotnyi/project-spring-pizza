package de.telran.pizza.security;

import de.telran.pizza.domain.entity.User;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

/**
 * Реализация интерфейса UserDetails для целей безопасности.
 */

@Getter
@Setter
public class UserDetailSecurity implements UserDetails {

    private User user;

    /**
     * Конструктор класса UserDetailSecurity.
     * @param user Объект User, представляющий пользователя.
     */
    public UserDetailSecurity(User user) {
        this.user = user;
    }

    /**
     * Возвращает набор ролей, назначенных пользователю.
     *
     * @return Коллекция ролей.
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(user.getRole().name());
        return Collections.singletonList(authority);
    }

    /**
     * Возвращает пароль пользователя.
     *
     * @return Пароль пользователя.
     */
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    /**
     * Возвращает имя пользователя (логин).
     *
     * @return Имя пользователя.
     */
    @Override
    public String getUsername() {
        return user.getUser();
    }

    /**
     * Проверяет, не истек ли срок действия учетной записи пользователя.
     *
     * @return True, если срок действия учетной записи не истек, в противном случае false.
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Проверяет, не заблокирована ли учетная запись пользователя.
     *
     * @return True, если учетная запись не заблокирована, в противном случае false.
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Проверяет, не истек ли срок действия учетных данных пользователя.
     *
     * @return True, если срок действия учетных данных не истек, в противном случае false.
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Проверяет, включен ли пользователь.
     *
     * @return True, если пользователь включен, в противном случае false.
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}