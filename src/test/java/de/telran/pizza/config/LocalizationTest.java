package de.telran.pizza.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ContextConfiguration(classes = Localization.class)
class LocalizationTest {

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    void testLocaleResolverBean() {
        assertTrue(applicationContext.containsBean("localeResolver"));
    }

    @Test
    void testLocaleChangeInterceptorBean() {
        assertTrue(applicationContext.containsBean("localeChangeInterceptor"));
    }
}
