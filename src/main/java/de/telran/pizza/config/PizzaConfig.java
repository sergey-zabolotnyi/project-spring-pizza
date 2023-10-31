package de.telran.pizza.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

/**
 * Класс конфигурации для настройки ресурсов сообщений и логов.
 */
@Configuration
public class PizzaConfig {
    /**
     * Создает ResourceBundleMessageSource для обработки сообщений.
     *
     * @return Созданный ResourceBundleMessageSource.
     */
    @Bean
    public ResourceBundleMessageSource messageSource() {
        ResourceBundleMessageSource source = new ResourceBundleMessageSource();
        source.setBasenames("messages");
        source.setDefaultEncoding("UTF-8");
        source.setUseCodeAsDefaultMessage(true);
        return source;
    }

    /**
     * Создает ResourceBundleMessageSource для обработки логов.
     *
     * @return Созданный ResourceBundleMessageSource.
     */
    @Bean
    public ResourceBundleMessageSource logSource() {
        ResourceBundleMessageSource source = new ResourceBundleMessageSource();
        source.setBasenames("log_messages");
        source.setDefaultEncoding("UTF-8");
        source.setUseCodeAsDefaultMessage(true);
        return source;
    }
}