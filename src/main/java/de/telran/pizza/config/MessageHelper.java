package de.telran.pizza.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.util.Locale;

/**
 * Класс конфигурации для управления ресурсами сообщений и логов.
 */
@Configuration
public class MessageHelper {
    private MessageSource messageSource;
    private MessageSource logSource;

    /**
     * Конструктор с двумя источниками сообщений.
     *
     * @param messageSource Источник сообщений.
     * @param logSource     Источник логов.
     */
    public MessageHelper(MessageSource messageSource, MessageSource logSource) {
        this.messageSource = messageSource;
        this.logSource = logSource;
    }

    /**
     * Получает сообщение, соответствующее указанному ключу на текущей локали.
     *
     * @param message Ключ сообщения.
     * @return Соответствующее сообщение.
     */
    public String getMessage(String message) {
        return messageSource.getMessage(message, null, LocaleContextHolder.getLocale());
    }

    /**
     * Получает лог-сообщение, соответствующее указанному ключу на английском.
     *
     * @param message Ключ лог-сообщения.
     * @return Соответствующее лог-сообщение.
     */
    public String getLogMessage(String message) {
        return logSource.getMessage(message, null, Locale.ENGLISH);
    }

    /**
     * Предоставляет LocalValidatorFactoryBean для валидации объектов.
     *
     * @return Настроенный LocalValidatorFactoryBean.
     */
    @Bean
    public LocalValidatorFactoryBean getValidator() {
        LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
        bean.setValidationMessageSource(messageSource);
        return bean;
    }
}
