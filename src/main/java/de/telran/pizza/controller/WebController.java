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

    /**
     * Handles requests to display the payment page.
     *
     * @return The logical view name "payment".
     */
    @RequestMapping("/payment")
    public String paymentPage() {
        return "payment";
    }

    /**
     * Request handler method for displaying the manager's dishes management page.
     *
     * @return The view name "dishes_manager".
     */
    @RequestMapping("manager/manage_dishes")
    public String managerDishesPage() {
        return "dishes_manager";
    }

    /**
     * Request handler method for displaying the dishes creation page in the manager's section.
     *
     * @return The view name "dishes_create".
     */
    @RequestMapping("manager/dishes/create")
    public String createDishes() {
        return "dishes_create";
    }

    /**
     * Request handler method for displaying the manager's orders management page.
     *
     * @return The view name "orders_manager".
     */
    @RequestMapping("/manager/manage_orders")
    public String managerOrdersPage() {
        return "orders_manager";
    }

    /**
     * Request handler method for displaying the dishes update page in the manager's section.
     *
     * @return The view name "dishes_update".
     */
    @RequestMapping("manager/dishes/update")
    public String updateDishes() {
        return "dishes_update";
    }

}
