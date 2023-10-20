package de.telran.pizza.controller;

import de.telran.pizza.config.MessageHelper;
import de.telran.pizza.domain.dto.LoginDTO;
import de.telran.pizza.domain.entity.enums.Role;
import de.telran.pizza.service.LoginService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * Controller class for handling user signup requests.
 */
@Slf4j
@Controller
@RequestMapping("/signup")
public class SignupController {
    private LoginService loginService;
    private MessageHelper helper;

    public SignupController(LoginService loginService, MessageHelper helper) {
        this.loginService = loginService;
        this.helper = helper;
    }
    /**
     * Handles a POST request to create a new user.
     *
     * @param loginDTO The LoginDTO containing user registration information.
     * @param response The HttpServletResponse object for redirecting after successful signup.
     */
    @PostMapping
    public void signup(@Valid @RequestBody LoginDTO loginDTO, HttpServletResponse response) {
        log.info(helper.getLogMessage("create.user.log"));
        try {
            loginService.saveUser(loginDTO, Role.ROLE_CUSTOMER);
            response.sendRedirect("login");
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
    /**
     * Gets users count for Statistics.
     *
     * @return A ResponseEntity containing a users count in DB.
     */
    @Operation(
            summary = "Получение количества пользователей",
            description = "Получение количества пользователей, хранящихся в базе данных")
    @GetMapping("/count")
    @ResponseBody // Добавляем аннотацию, чтобы возвращать тело ответа напрямую
    public ResponseEntity<Integer> getAllUsersCount() {
        log.info(helper.getLogMessage("users.count.log"));
        return ResponseEntity.ok(loginService.getAllUsers().size());
    }

}
