package de.telran.pizza.controller;

import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;
import org.springframework.validation.support.BindingAwareModelMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

class WebControllerTest {

    @Test
    public void testMainPage() {
        WebController controller = new WebController();
        Model model = new BindingAwareModelMap();
        String result = controller.mainPage();

        assertEquals("main", result);
    }
    @Test
    public void testLoginPage() {
        WebController controller = new WebController();
        Model model = new BindingAwareModelMap();
        String result = controller.loginPage();

        assertEquals("login", result);
    }

    @Test
    public void testSignupPage() {
        WebController controller = new WebController();
        Model model = new BindingAwareModelMap();
        String result = controller.signupPage();

        assertEquals("signup", result);
    }

    @Test
    public void testCartPage() {
        WebController controller = new WebController();
        Model model = new BindingAwareModelMap();
        String result = controller.cartPage();

        assertEquals("cart", result);
    }

    @Test
    public void testOrdersPage() {
        WebController controller = new WebController();
        Model model = new BindingAwareModelMap();
        String result = controller.ordersPage();

        assertEquals("orders", result);
    }

    @Test
    public void testPaymentsPage() {
        WebController controller = new WebController();
        Model model = new BindingAwareModelMap();
        String result = controller.paymentPage();

        assertEquals("payment", result);
    }

    @Test
    public void testManagerOrdersPage() {
        WebController controller = new WebController();
        Model model = new BindingAwareModelMap();
        String result = controller.managerOrdersPage();

        assertEquals("orders_manager", result);
    }

    @Test
    public void testManagerDishesPage() {
        WebController controller = new WebController();
        Model model = new BindingAwareModelMap();
        String result = controller.managerDishesPage();

        assertEquals("dishes_manager", result);
    }

    @Test
    public void testCreateDishes() {
        WebController controller = new WebController();
        Model model = new BindingAwareModelMap();
        String result = controller.createDishes();

        assertEquals("dishes_create", result);
    }

    @Test
    public void testUpdateDishes() {
        WebController controller = new WebController();
        Model model = new BindingAwareModelMap();
        String result = controller.updateDishes();

        assertEquals("dishes_update", result);
    }

    @Test
    public void testStatisticsPage() {
        WebController controller = new WebController();
        Model model = new BindingAwareModelMap();
        String result = controller.statistics();

        assertEquals("statistics", result);
    }

}
