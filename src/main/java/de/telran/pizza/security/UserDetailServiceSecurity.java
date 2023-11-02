package de.telran.pizza.security;

import de.telran.pizza.domain.entity.User;
import de.telran.pizza.service.UserService;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Реализация интерфейса UserDetailsService для целей безопасности.
 */
@Slf4j
@Service
public class UserDetailServiceSecurity implements UserDetailsService {

    private UserService userService;

    /**
     * Конструктор класса UserDetailServiceSecurity.
     * @param userService Сервис для работы с пользователями.
     */
    public UserDetailServiceSecurity(UserService userService) {
        this.userService = userService;
    }

    /**
     * Загружает пользователя на основе предоставленного имени пользователя.
     *
     * @param username Имя пользователя, для которого нужно получить данные пользователя.
     * @return Объект UserDetails, представляющий пользователя.
     * @throws UsernameNotFoundException Если пользователь с указанным именем не найден.
     */
    @Override
    public UserDetails loadUserByUsername(@NonNull String username) throws UsernameNotFoundException {
        User user = userService.findByUserLogin(username).orElseThrow(
                () -> new UsernameNotFoundException("Could not find the user: " + username)
        );
        log.info(user.getUser());

        return new UserDetailSecurity(user);
    }
}
