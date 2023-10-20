package de.telran.pizza.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
@Import(MessageHelper.class)
@TestPropertySource(properties = {
        "spring.messages.basename=messages",
        "spring.messages.encoding=UTF-8"
})
class MessageHelperTest {

    @Autowired
    private MessageHelper messageHelper;

    @Test
    void getMessage() {
        MessageSource messageSource = mock(MessageSource.class);
        when(messageSource.getMessage("test.message", null, Locale.getDefault()))
                .thenReturn("Test Message");

        String message = messageHelper.getMessage("test.message");

        assertEquals("test.message", message);
    }

    @Test
    void getLogMessage() {
        MessageSource logSource = mock(MessageSource.class);
        when(logSource.getMessage("test.log.message", null, Locale.ENGLISH))
                .thenReturn("Test Log Message");

        String logMessage = messageHelper.getLogMessage("test.log.message");

        assertEquals("test.log.message", logMessage);
    }
}
