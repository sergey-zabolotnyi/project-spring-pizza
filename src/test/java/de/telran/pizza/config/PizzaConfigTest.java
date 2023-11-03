package de.telran.pizza.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Тестирование класса PizzaConfig.
 */
@SpringBootTest
@Import(PizzaConfig.class)
@TestPropertySource(properties = {
        "spring.messages.basename=messages",
        "spring.messages.encoding=UTF-8"
})
class PizzaConfigTest {

    @Autowired
    private ResourceBundleMessageSource messageSource;

    @Autowired
    private ResourceBundleMessageSource logSource;

    /**
     * Проверяет корректность получения сообщения из ресурсов.
     */
    @Test
    void messageSource() {
        String message = messageSource.getMessage("test.message", null, null);
        assertEquals("test.message", message);
    }

    /**
     * Проверяет корректность получения лог-сообщения из ресурсов.
     */
    @Test
    void logSource() {
        String logMessage = logSource.getMessage("test.log.message", null, null);
        assertEquals("test.log.message", logMessage);
    }
}
