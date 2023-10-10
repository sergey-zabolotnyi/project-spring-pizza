package de.telran.pizza.controller;

import de.telran.pizza.config.MessageHelper;
import de.telran.pizza.domain.entity.dto.LoginDTO;
import de.telran.pizza.domain.entity.enums.Role;
import de.telran.pizza.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
    @Autowired
    private LoginService loginService;
    @Autowired
    private MessageHelper helper;

    /**
     * Handles a POST request to create a new user.
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

}
