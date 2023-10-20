package de.telran.pizza.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * Data Transfer Object (DTO) representing user login information.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "ДТО объект для входа пользователя в систему.")
public class LoginDTO {
    @NotBlank(message = "error.signup.login")
    @Schema(description = "Логин пользователя", example = "user")
    private String login;

    @Pattern(regexp = "^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,6}$",
            message = "error.signup.email")
    @Schema(description = "Электронная почта пользователя", example = "user@gmail.com")
    private String email;

    @NotBlank
    @Pattern(regexp = "^[a-z0-9._%+-]{3,10}$",
            message = "error.signup.password")
    @Schema(description = "Пароль пользователя", example = "qwerty")
    private String password;
}
