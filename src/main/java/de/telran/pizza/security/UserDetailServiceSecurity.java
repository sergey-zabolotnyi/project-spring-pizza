package de.telran.pizza.security;

import de.telran.pizza.domain.entity.Login;
import de.telran.pizza.service.LoginService;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Custom UserDetailsService implementation for security purposes.
 */
@Slf4j
@Service
public class UserDetailServiceSecurity implements UserDetailsService {

    private LoginService loginService;
    public UserDetailServiceSecurity(LoginService loginService) {
        this.loginService = loginService;
    }

    /**
     * Loads a user based on the provided username.
     *
     * @param username The username for which to retrieve user details.
     * @return A UserDetails object representing the user.
     * @throws UsernameNotFoundException If no user with the given username is found.
     */
    @Override
    public UserDetails loadUserByUsername(@NonNull String username) throws UsernameNotFoundException {
        Login login = loginService.findByUserLogin(username).orElseThrow(
                () -> new UsernameNotFoundException("Could not find the user: " + username)
        );
        log.info(login.getLogin());

        return new UserDetailSecurity(login);
    }
}
