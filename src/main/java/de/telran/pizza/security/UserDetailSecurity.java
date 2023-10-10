package de.telran.pizza.security;

import de.telran.pizza.domain.entity.Login;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
/**
 * Custom UserDetails implementation for security purposes.
 */
@Data
@Getter
@Setter
@Builder
@NoArgsConstructor
public class UserDetailSecurity implements UserDetails {

    private Login login;

    public UserDetailSecurity(Login login) {
        this.login = login;
    }
    /**
     * Retrieves the authorities (roles) assigned to the user.
     * @return A collection of authorities.
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(login.getRole().name());
        return Collections.singletonList(authority);
    }
    /**
     * Gets the user's password.
     * @return The user's password.
     */
    @Override
    public String getPassword() {
        return login.getPassword();
    }
    /**
     * Gets the user's username (login).
     * @return The user's username.
     */
    @Override
    public String getUsername() {
        return login.getLogin();
    }
    /**
     * Checks if the user's account is not expired.
     * @return True if the account is not expired, otherwise false.
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    /**
     * Checks if the user's account is not locked.
     * @return True if the account is not locked, otherwise false.
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    /**
     * Checks if the user's credentials are not expired.
     * @return True if the credentials are not expired, otherwise false.
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    /**
     * Checks if the user is enabled.
     * @return True if the user is enabled, otherwise false.
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}
