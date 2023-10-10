package de.telran.pizza.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

/**
 * Configuration class for setting up message and log resources.
 */
@Configuration
public class PizzaConfig {
    /**
     * Creates a ResourceBundleMessageSource for handling messages.
     * @return Created ResourceBundleMessageSource.
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
     * Creates a ResourceBundleMessageSource for handling logs.
     * @return Created ResourceBundleMessageSource.
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
