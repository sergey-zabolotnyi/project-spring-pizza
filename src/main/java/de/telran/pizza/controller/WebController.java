package de.telran.pizza.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Контроллер, отвечающий за обработку запросов веб-страниц.
 */
@Controller
public class WebController {

    /**
     * Возвращает главную страницу.
     *
     * @return Имя шаблона главной страницы.
     */
    @RequestMapping("/")
    public String mainPage() {
        return "main";
    }

    /**
     * Возвращает страницу для авторизации пользователей.
     *
     * @return Имя шаблона страницы входа.
     */
    @RequestMapping("/login")
    public String loginPage() {
        return "login";
    }

    /**
     * Возвращает страницу регистрации пользователей.
     *
     * @return Имя шаблона страницы регистрации.
     */
    @RequestMapping("/signup")
    public String signupPage() {
        return "signup";
    }

    /**
     * Возвращает страницу корзины.
     *
     * @return Имя шаблона страницы корзины.
     */
    @RequestMapping("/cart")
    public String cartPage() {
        return "cart";
    }

    /**
     * Возвращает страницу заказов.
     *
     * @return Имя шаблона страницы заказов.
     */
    @RequestMapping("/orders")
    public String ordersPage() {
        return "orders";
    }
    /**
     * Возвращает страницу заказa.
     *
     * @return Имя шаблона страницы заказов.
     */
    @RequestMapping("/order")
    public String orderPage() {
        return "order";
    }

    /**
     * Обрабатывает запросы на отображение страницы оплаты.
     *
     * @return Логическое имя представления "оплата".
     */
    @RequestMapping("/payment")
    public String paymentPage() {
        return "payment";
    }

    /**
     * Метод обработки запросов на отображение страницы управления блюдами менеджера.
     *
     * @return Имя представления "управление_блюдами".
     */
    @RequestMapping("manager/manage_dishes")
    public String managerDishesPage() {
        return "dishes_manager";
    }

    /**
     * Метод обработки запросов на отображение страницы создания блюд в разделе менеджера.
     *
     * @return Имя представления "создание_блюд".
     */
    @RequestMapping("manager/dishes/create")
    public String createDishes() {
        return "dishes_create";
    }

    /**
     * Метод обработки запросов на отображение страницы управления заказами менеджера.
     *
     * @return Имя представления "управление_заказами".
     */
    @RequestMapping("/manager/manage_orders")
    public String managerOrdersPage() {
        return "orders_manager";
    }

    /**
     * Метод обработки запросов на отображение страницы обновления блюд в разделе менеджера.
     *
     * @return Имя представления "обновление_блюд".
     */
    @RequestMapping("manager/dishes/update")
    public String updateDishes() {
        return "dishes_update";
    }

    /**
     * Метод обработки запросов на отображение страницы статистики в разделе менеджера.
     *
     * @return Имя представления "статистика".
     */
    @RequestMapping("manager/statistics")
    public String statistics() {
        return "statistics";
    }

}
