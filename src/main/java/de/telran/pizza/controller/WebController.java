package de.telran.pizza.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WebController {

    @RequestMapping("/")
    public String mainPage() {
        return "main";
    }

    @RequestMapping("/login")
    public String loginPage() {
        return "login";
    }

    @RequestMapping("/signup")
    public String signupPage() {
        return "signup";
    }

    @RequestMapping("/basket")
    public String cartPage() {
        return "cart";
    }

    @RequestMapping("/orders")
    public String ordersPage() {
        return "orders";
    }
    // todo other webpages
}
