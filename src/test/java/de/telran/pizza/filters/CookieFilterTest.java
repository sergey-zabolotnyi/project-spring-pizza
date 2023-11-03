package de.telran.pizza.filters;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.mockito.Mockito.*;

/**
 * Юнит-тест для класса CookieFilter.
 */
@SpringBootTest
class CookieFilterTest {

    /**
     * Мок объекта HttpServletRequest.
     */
    @Mock
    private HttpServletRequest request;

    /**
     * Мок объекта HttpServletResponse.
     */
    @Mock
    private HttpServletResponse response;

    /**
     * Мок объекта FilterChain.
     */
    @Mock
    private FilterChain filterChain;

    /**
     * Внедрение зависимостей для тестируемого CookieFilter.
     */
    @InjectMocks
    private CookieFilter cookieFilter;

    /**
     * Тест метода doFilter.
     *
     * @throws ServletException если произошла ошибка в сервлете
     * @throws IOException      если произошла ошибка ввода/вывода
     */
    @Test
    void testDoFilter() throws ServletException, IOException {

        // Подготовка данных для теста
        Cookie[] cookies = new Cookie[]{new Cookie("USER_STATUS", "NEW_USER")};
        when(request.getCookies()).thenReturn(cookies);

        // Вызов метода, который тестируется
        cookieFilter.doFilter(request, response, filterChain);

        // Проверка, что был добавлен новый cookie
        verify(response, times(1)).addCookie(any(Cookie.class));

        // Проверка, что был вызван doFilter у FilterChain
        verify(filterChain, times(1)).doFilter(request, response);
    }
}