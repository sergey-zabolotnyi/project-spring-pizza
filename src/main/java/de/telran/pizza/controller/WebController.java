package de.telran.pizza.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller class responsible for handling web page requests.
 */
@Controller
public class WebController {

    /**
     * Returns the main page.
     *
     * @return The name of the main page template.
     */
    @RequestMapping("/")
    public String mainPage() {
        return "main";
    }

    /**
     * Returns the login page.
     *
     * @return The name of the login page template.
     */
    @RequestMapping("/login")
    public String loginPage() {
        return "login";
    }

    /**
     * Returns the signup page.
     *
     * @return The name of the signup page template.
     */
    @RequestMapping("/signup")
    public String signupPage() {
        return "signup";
    }

    /**
     * Returns the cart page.
     *
     * @return The name of the cart page template.
     */
    @RequestMapping("/cart")
    public String cartPage() {
        return "cart";
    }

    /**
     * Returns the orders page.
     *
     * @return The name of the orders page template.
     */
    @RequestMapping("/orders")
    public String ordersPage() {
        return "orders";
    }

    // TODO: Add mappings for other web pages as needed.

}
