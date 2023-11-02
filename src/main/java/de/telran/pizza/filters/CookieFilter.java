package de.telran.pizza.filters;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

/**
 * Класс представляет фильтр для обработки куки.
 */
@Component
@Order(1)
public class CookieFilter implements Filter {

    /**
     * Метод, выполняющий обработку запроса.
     *
     * @param servletRequest  объект запроса
     * @param servletResponse объект ответа
     * @param filterChain     цепочка фильтров
     * @throws ServletException если произошла ошибка в сервлете
     * @throws IOException      если произошла ошибка ввода/вывода
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws ServletException, IOException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        Cookie[] cookies = request.getCookies();
        Cookie customCookie = null;

        if (cookies != null) {
            customCookie = Arrays.stream(cookies)
                    .filter(x -> x.getName().equals("USER_STATUS"))
                    .findFirst()
                    .orElse(null);
        }

        if (customCookie == null) {
            customCookie = new Cookie("USER_STATUS", "NEW_USER");
        } else {
            customCookie.setValue("OLD_USER");
        }

        //System.out.println("User status = " + customCookie.getValue());
        response.addCookie(customCookie);

        filterChain.doFilter(request, response);
    }
}
