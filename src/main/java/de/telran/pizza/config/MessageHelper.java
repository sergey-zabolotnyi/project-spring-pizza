package de.telran.pizza.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.util.Locale;

/**
 * Configuration class for managing message and log resources.
 */
@Configuration
public class MessageHelper {
    @Autowired
    private MessageSource messageSource;
    @Autowired
    private MessageSource logSource;

    /**
     * Gets the message corresponding to the provided key based on the current locale.
     *
     * @param message The message key.
     * @return The corresponding message.
     */
    public String getMessage(String message) {
        return messageSource.getMessage(message, null, LocaleContextHolder.getLocale());
    }

    /**
     * Gets the log message corresponding to the provided key in English.
     *
     * @param message The log message key.
     * @return The corresponding log message.
     */
    public String getLogMessage(String message) {
        return logSource.getMessage(message, null, Locale.ENGLISH);
    }

    /**
     * Provides a LocalValidatorFactoryBean for object validation.
     *
     * @return Configured LocalValidatorFactoryBean.
     */
    @Bean
    public LocalValidatorFactoryBean getValidator() {
        LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
        bean.setValidationMessageSource(messageSource);
        return bean;
    }
}
