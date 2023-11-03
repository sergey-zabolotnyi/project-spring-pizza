package de.telran.pizza.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/**
 * Тестирование класса SecurityConfig.
 */
@SpringBootTest
@AutoConfigureMockMvc
class SecurityConfigTest {

    @Autowired
    private MockMvc mockMvc;

    /**
     * Проверяет доступность страницы входа в систему.
     */
    @Test
    void testLoginPageAccessible() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/login"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    /**
     * Проверяет, что главная страница не доступна для неавторизованных пользователей.
     */
    @Test
    void testHomePageNotAccessible() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    /**
     * Проверяет, что страница управления заказами доступна для пользователя с ролью MANAGER.
     */
    @Test
    @WithMockUser(authorities = "ROLE_MANAGER")
    void testManagerPageAccessibleForManager() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/manager/manage_orders"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    /**
     * Проверяет, что страница управления заказами не доступна для пользователя с ролью USER.
     */
    @Test
    @WithMockUser(authorities = "ROLE_USER")
    void testManagerPageNotAccessibleForUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/manager/manage_orders"))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }
}
