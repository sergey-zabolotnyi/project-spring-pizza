package de.telran.pizza.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.ui.Model;
import org.springframework.validation.support.BindingAwareModelMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(WebController.class)
class WebControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser // Аннотация для аутентификации пользователя
    void mainPage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("main"));
    }
    @Test
    public void testMainPage() {
        WebController controller = new WebController();
        Model model = new BindingAwareModelMap();
        String result = controller.mainPage();

        assertEquals("main", result);
    }
    @Test
    @WithMockUser
    void loginPage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/login"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"));
    }
    @Test
    public void testLoginPage() {
        WebController controller = new WebController();
        Model model = new BindingAwareModelMap();
        String result = controller.loginPage();

        assertEquals("login", result);
    }
    @Test
    @WithMockUser // Аннотация для аутентификации пользователя
    void signUpPage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/signup"))
                .andExpect(status().isOk())
                .andExpect(view().name("signup"));
    }
    @Test
    public void testSignupPage() {
        WebController controller = new WebController();
        Model model = new BindingAwareModelMap();
        String result = controller.signupPage();

        assertEquals("signup", result);
    }
    @Test
    @WithMockUser // Аннотация для аутентификации пользователя
    void cartPage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/cart"))
                .andExpect(status().isOk())
                .andExpect(view().name("cart"));
    }
    @Test
    public void testCartPage() {
        WebController controller = new WebController();
        Model model = new BindingAwareModelMap();
        String result = controller.cartPage();

        assertEquals("cart", result);
    }
    @Test
    @WithMockUser // Аннотация для аутентификации пользователя
    void ordersPage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/orders"))
                .andExpect(status().isOk())
                .andExpect(view().name("orders"));
    }
    @Test
    public void testOrdersPage() {
        WebController controller = new WebController();
        Model model = new BindingAwareModelMap();
        String result = controller.ordersPage();

        assertEquals("orders", result);
    }
    @Test
    @WithMockUser // Аннотация для аутентификации пользователя
    void paymentsPage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/payment"))
                .andExpect(status().isOk())
                .andExpect(view().name("payment"));
    }
    @Test
    public void testPaymentsPage() {
        WebController controller = new WebController();
        Model model = new BindingAwareModelMap();
        String result = controller.paymentPage();

        assertEquals("payment", result);
    }
    @Test
    @WithMockUser // Аннотация для аутентификации пользователя
    void managerOrdersPage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/manager/manage_orders"))
                .andExpect(status().isOk())
                .andExpect(view().name("orders_manager"));
    }

    @Test
    public void testManagerOrdersPage() {
        WebController controller = new WebController();
        Model model = new BindingAwareModelMap();
        String result = controller.managerOrdersPage();

        assertEquals("orders_manager", result);
    }
    @Test
    @WithMockUser // Аннотация для аутентификации пользователя
    void managerDishesPage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/manager/manage_dishes"))
                .andExpect(status().isOk())
                .andExpect(view().name("dishes_manager"));
    }

    @Test
    public void testManagerDishesPage() {
        WebController controller = new WebController();
        Model model = new BindingAwareModelMap();
        String result = controller.managerDishesPage();

        assertEquals("dishes_manager", result);
    }

    @Test
    @WithMockUser // Аннотация для аутентификации пользователя
    void createDishesPage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/manager/dishes/create"))
                .andExpect(status().isOk())
                .andExpect(view().name("dishes_create"));
    }
    @Test
    public void testCreateDishes() {
        WebController controller = new WebController();
        Model model = new BindingAwareModelMap();
        String result = controller.createDishes();

        assertEquals("dishes_create", result);
    }
    @Test
    @WithMockUser // Аннотация для аутентификации пользователя
    void updateDishesPage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/manager/dishes/update"))
                .andExpect(status().isOk())
                .andExpect(view().name("dishes_update"));
    }

    @Test
    public void testUpdateDishes() {
        WebController controller = new WebController();
        Model model = new BindingAwareModelMap();
        String result = controller.updateDishes();

        assertEquals("dishes_update", result);
    }
    @Test
    @WithMockUser // Аннотация для аутентификации пользователя
    void statisticsPage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/manager/statistics"))
                .andExpect(status().isOk())
                .andExpect(view().name("statistics"));
    }
    @Test
    public void testStatisticsPage() {
        WebController controller = new WebController();
        Model model = new BindingAwareModelMap();
        String result = controller.statistics();

        assertEquals("statistics", result);
    }

}
