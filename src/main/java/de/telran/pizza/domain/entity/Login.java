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
 * Represents a user login entity in the application.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "login")
@Schema(description = "Объект для входа пользователя в систему.")
public class Login {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Schema(accessMode = Schema.AccessMode.READ_ONLY, description = "Идентификатор пользователя", example = "3")
    private Long id;

    @Column(name = "login")
    @Schema(description = "Логин пользователя", example = "user")
    private String login;

    @Column(name = "password")
    @Schema(description = "Пароль пользователя", example = "qwerty")
    private String password;

    @Column(name = "email")
    @Schema(description = "Электронная почта пользователя", example = "user@gmail.com")
    @Email(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,6}$", message = "Invalid email address")
    private String email;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    @Schema(description = "Роль пользователя", example = "MANAGER, CUSTOMER")
    private Role role;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "Время добавления записи", example = "2023-10-05 15:05:34")
    private LocalDateTime time;

    public Login(String login, String password) {
        this.login = login;
        this.password = password;
    }
}
