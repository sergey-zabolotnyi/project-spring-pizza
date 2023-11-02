package de.telran.pizza.service;

import de.telran.pizza.config.MessageHelper;
import de.telran.pizza.domain.entity.User;
import de.telran.pizza.domain.dto.UserDTO;
import de.telran.pizza.domain.entity.enums.Role;
import de.telran.pizza.repository.UserRepository;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * Сервисный класс для управления данными пользователей.
 * Этот класс используется создания и управления пользователями в системе.
 * @author szabolotnyi
 * @version 1.0.0
 */
@Service
@Slf4j
public class UserService {

    private UserRepository userRepository;
    private MessageHelper helper;

    public UserService(UserRepository userRepository, MessageHelper helper) {
        this.userRepository = userRepository;
        this.helper = helper;
    }

    /**
     * Находит пользователя по его логину.
     *
     * @param user Логин пользователя, которого нужно получить.
     * @return Optional с объектом User с указанным логином, если найден.
     */
    public Optional<User> findByUserLogin(@NonNull String user) {
        return userRepository.findByUser(user);
    }

    /**
     * Сохраняет нового пользователя в базу данных с указанными данными о логине и роли.
     *
     * @param userDTO Объект UserDTO с данными пользователя (логин, email, пароль).
     * @param role    Роль пользователя.
     * @return Сохраненный объект User.
     * @throws NoSuchElementException если произошла ошибка во время операции сохранения.
     */
    public User saveUser(@NonNull UserDTO userDTO, Role role) throws NoSuchElementException {
        try {
            return userRepository.save(User.builder()
                    .user(userDTO.getUser())
                    .email(userDTO.getEmail())
                    .role(role)
                    .time(LocalDateTime.now())
                    .password(new BCryptPasswordEncoder().encode(userDTO.getPassword())).build());
        } catch (Exception e) {
            String message = "error.signup.login.exists";
            log.warn(helper.getLogMessage(message) + userDTO.getUser());
            throw new IllegalArgumentException(helper.getMessage(message) + userDTO.getUser());
        }
    }

    /**
     * Получает список всех пользователей в базе данных.
     *
     * @return Список объектов User(users).
     */
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }
}