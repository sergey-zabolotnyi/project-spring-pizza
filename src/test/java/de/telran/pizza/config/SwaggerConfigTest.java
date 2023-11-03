package de.telran.pizza.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/**
 * Тестирование класса SwaggerConfig.
 */
@SpringBootTest
@AutoConfigureMockMvc
class SwaggerConfigTest {

    @Autowired
    private MockMvc mockMvc;

    /**
     * Проверяет доступность страницы Swagger UI.
     */
    @Test
    void testSwaggerUIPage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/swagger-ui/index.html"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    /**
     * Проверяет доступность JSON-документации Swagger.
     */
    @Test
    void testSwaggerDocumentationJSON() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/v3/api-docs"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
