package de.telran.pizza.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import de.telran.pizza.domain.entity.enums.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.time.LocalDateTime;

/**
 * Класс представляет объект для входа пользователя в систему.
 * Этот объект используется для хранения данных о пользователях в системе.
 * @author szabolotnyi
 * @version 1.0.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "user")
@Schema(description = "Объект для входа пользователя в систему.")
public class User {

    /**
     * Идентификатор пользователя.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Schema(accessMode = Schema.AccessMode.READ_ONLY, description = "Идентификатор пользователя", example = "3")
    private int id;

    /**
     * Логин пользователя.
     */
    @Column(name = "user")
    @Schema(description = "Логин пользователя", example = "user")
    private String user;

    /**
     * Пароль пользователя.
     */
    @Column(name = "password")
    @Schema(description = "Пароль пользователя", example = "qwerty")
    private String password;

    /**
     * Электронная почта пользователя.
     */
    @Column(name = "email")
    @Schema(description = "Электронная почта пользователя", example = "user@gmail.com")
    @Email(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,6}$", message = "Invalid email address")
    private String email;

    /**
     * Роль пользователя.
     */
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    @Schema(description = "Роль пользователя", example = "MANAGER, CUSTOMER")
    private Role role;

    /**
     * Время добавления записи.
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "Время добавления записи", example = "2023-10-05 15:05:34")
    private LocalDateTime time;

    /**
     * Конструктор с параметрами user и password.
     *
     * @param user     Имя пользователя.
     * @param password Пароль пользователя.
     */
    public User(String user, String password) {
        this.user = user;
        this.password = password;
    }
}