package de.telran.pizza.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Тестирование конфигурации локализации.
 */
@SpringBootTest
@ContextConfiguration(classes = Localization.class)
class LocalizationTest {

    @Autowired
    private ApplicationContext applicationContext;

    /**
     * Проверяет наличие бина "localeResolver" в контексте приложения.
     */
    @Test
    void testLocaleResolverBean() {
        assertTrue(applicationContext.containsBean("localeResolver"));
    }

    /**
     * Проверяет наличие бина "localeChangeInterceptor" в контексте приложения.
     */
    @Test
    void testLocaleChangeInterceptorBean() {
        assertTrue(applicationContext.containsBean("localeChangeInterceptor"));
    }
}
