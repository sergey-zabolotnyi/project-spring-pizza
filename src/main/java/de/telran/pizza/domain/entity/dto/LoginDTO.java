package de.telran.pizza.domain.entity.dto;

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
public class LoginDTO {
    @NotBlank(message = "error.signup.login")
    private String login;

    @Pattern(regexp = "^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,6}$",
            message = "error.signup.email")
    private String email;

    @NotBlank
    @Pattern(regexp = "^[a-z0-9._%+-]{3,10}$",
            message = "error.signup.password")
    private String password;
}
