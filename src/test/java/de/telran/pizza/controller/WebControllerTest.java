package de.telran.pizza.controller;

import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;
import org.springframework.validation.support.BindingAwareModelMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Юнит-тесты для класса WebController.
 */
class WebControllerTest {

    /**
     * Тест запроса главной страницы.
     */
    @Test
    public void testMainPage() {
        WebController controller = new WebController();
        Model model = new BindingAwareModelMap();
        String result = controller.mainPage();

        assertEquals("main", result);
    }

    /**
     * Тест запроса страницы входа.
     */
    @Test
    public void testLoginPage() {
        WebController controller = new WebController();
        Model model = new BindingAwareModelMap();
        String result = controller.loginPage();

        assertEquals("login", result);
    }

    /**
     * Тест запроса страницы регистрации.
     */
    @Test
    public void testSignupPage() {
        WebController controller = new WebController();
        Model model = new BindingAwareModelMap();
        String result = controller.signupPage();

        assertEquals("signup", result);
    }

    /**
     * Тест запроса страницы корзины.
     */
    @Test
    public void testCartPage() {
        WebController controller = new WebController();
        Model model = new BindingAwareModelMap();
        String result = controller.cartPage();

        assertEquals("cart", result);
    }

    /**
     * Тест запроса страницы заказов.
     */
    @Test
    public void testOrdersPage() {
        WebController controller = new WebController();
        Model model = new BindingAwareModelMap();
        String result = controller.ordersPage();

        assertEquals("orders", result);
    }

    /**
     * Тест запроса страницы заказа.
     */
    @Test
    public void testOrderPage() {
        WebController controller = new WebController();
        Model model = new BindingAwareModelMap();
        String result = controller.orderPage();

        assertEquals("order", result);
    }

    /**
     * Тест запроса страницы оплаты.
     */
    @Test
    public void testPaymentsPage() {
        WebController controller = new WebController();
        Model model = new BindingAwareModelMap();
        String result = controller.paymentPage();

        assertEquals("payment", result);
    }

    /**
     * Тест запроса страницы управления заказами.
     */
    @Test
    public void testManagerOrdersPage() {
        WebController controller = new WebController();
        Model model = new BindingAwareModelMap();
        String result = controller.managerOrdersPage();

        assertEquals("orders_manager", result);
    }

    /**
     * Тест запроса страницы управления блюдами.
     */
    @Test
    public void testManagerDishesPage() {
        WebController controller = new WebController();
        Model model = new BindingAwareModelMap();
        String result = controller.managerDishesPage();

        assertEquals("dishes_manager", result);
    }

    /**
     * Тест запроса страницы создания блюд.
     */
    @Test
    public void testCreateDishes() {
        WebController controller = new WebController();
        Model model = new BindingAwareModelMap();
        String result = controller.createDishes();

        assertEquals("dishes_create", result);
    }

    /**
     * Тест запроса страницы обновления блюд.
     */
    @Test
    public void testUpdateDishes() {
        WebController controller = new WebController();
        Model model = new BindingAwareModelMap();
        String result = controller.updateDishes();

        assertEquals("dishes_update", result);
    }

    /**
     * Тест запроса страницы статистики.
     */
    @Test
    public void testStatisticsPage() {
        WebController controller = new WebController();
        Model model = new BindingAwareModelMap();
        String result = controller.statistics();

        assertEquals("statistics", result);
    }
}
