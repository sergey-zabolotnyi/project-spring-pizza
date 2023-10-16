package de.telran.pizza.service;

import de.telran.pizza.config.MessageHelper;
import de.telran.pizza.domain.entity.Login;
import de.telran.pizza.domain.entity.dto.LoginDTO;
import de.telran.pizza.domain.entity.enums.Role;
import de.telran.pizza.repository.LoginRepository;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.LifecycleState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * Service class for managing customer logins.
 */
@Service
@Slf4j
public class LoginService {
    @Autowired
    private LoginRepository loginRepository;
    @Autowired
    private MessageHelper helper;

    /**
     * Finds a user by their login.
     *
     * @param login The login of the user to be retrieved.
     * @return An Optional containing the Login entity with the specified login, if found.
     */
    public Optional<Login> findByUserLogin(@NonNull String login) {
        return loginRepository.findByLogin(login);
    }

    /**
     * Saves a new user to the database with the specified loginDTO and role.
     *
     * @param loginDTO The LoginDTO containing user details (login, email, password).
     * @param role     The role of the user.
     * @return The saved Login entity.
     * @throws NoSuchElementException if an error occurs during the save operation.
     */
    public Login saveUser(@NonNull LoginDTO loginDTO, Role role) throws NoSuchElementException {
        try {
            return loginRepository.save(Login.builder()
                    .login(loginDTO.getLogin())
                    .email(loginDTO.getEmail())
                    .role(role)
                    .time(LocalDateTime.now())
                    .password(new BCryptPasswordEncoder().encode(loginDTO.getPassword())).build());
        } catch (Exception e) {
            String message = "error.signup.login.exists";
            log.warn(helper.getLogMessage(message) + loginDTO.getLogin());
            throw new IllegalArgumentException(helper.getMessage(message) + loginDTO.getLogin());
        }
    }
    /**
     * Retrieves a list of all users in DataBase.
     *
     * @return A list of Login(users) objects.
     */
    public List<Login> getAllUsers(){
        return loginRepository.findAll();
    }
}
