package de.telran.pizza.controller;

import de.telran.pizza.config.MessageHelper;
import de.telran.pizza.domain.dto.CategoryDTO;
import de.telran.pizza.domain.dto.UserDTO;
import de.telran.pizza.domain.entity.User;
import de.telran.pizza.domain.entity.enums.Role;
import de.telran.pizza.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

/**
 * Контроллер для регистрации новых пользователей.
 */
@Slf4j
@Controller
@RequestMapping("/signup")
public class SignupController {
    private UserService userService;
    private MessageHelper helper;

    /**
     * Конструктор класса.
     *
     * @param userService Сервис для работы с пользователями.
     * @param helper Помощник для обработки сообщений.
     */
    public SignupController(UserService userService, MessageHelper helper) {
        this.userService = userService;
        this.helper = helper;
    }

    /**
     * Метод для регистрации нового пользователя.
     *
     * @param userDTO ДТО с данными для регистрации.
     * @param response Ответ сервера.
     */
    @Operation(
            summary = "Регистрация нового пользователя",
            description = "Регистрация нового пользователя, сохранение в базе данных")
    @PostMapping
    public void signup(@Valid @RequestBody UserDTO userDTO, HttpServletResponse response) {
        log.info(helper.getLogMessage("create.user.log"));
        try {
            userService.saveUser(userDTO, Role.ROLE_CUSTOMER);
            response.sendRedirect("login");
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    /**
     * Получает всеx пользователей.
     * @return Список объектов User.
     */
    @Operation(summary = "Получение всех пользователей",
            description = "Получение всех пользователей Пиццерии, хранящихся в БД")
    @ApiResponse(responseCode = "200", description = "Успешно получены категории.")
    @GetMapping("/get")
    public ResponseEntity<List<User>> getAllUsers(){
        log.info(helper.getLogMessage("all.user.log"));
        return ResponseEntity.ok(userService.getAllUsers());
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
        return ResponseEntity.ok(userService.getAllUsers().size());
    }
}
