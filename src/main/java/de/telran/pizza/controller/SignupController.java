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
 * Контроллер для регистрации новых пользователей.
 */
@Slf4j
@Controller
@RequestMapping("/signup")
public class SignupController {
    private LoginService loginService;
    private MessageHelper helper;

    /**
     * Конструктор класса.
     *
     * @param loginService Сервис для работы с пользователями.
     * @param helper Помощник для обработки сообщений.
     */
    public SignupController(LoginService loginService, MessageHelper helper) {
        this.loginService = loginService;
        this.helper = helper;
    }

    /**
     * Метод для регистрации нового пользователя.
     *
     * @param loginDTO ДТО с данными для регистрации.
     * @param response Ответ сервера.
     */
    @Operation(
            summary = "Регистрация нового пользователя",
            description = "Регистрация нового пользователя, сохранение в базе данных")
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
     * Метод для получения количества пользователей.
     *
     * @return ResponseEntity с количеством пользователей.
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
