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

@SpringBootTest
class CookieFilterTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    @InjectMocks
    private CookieFilter cookieFilter;

    @Test
    void testDoFilter() throws ServletException, IOException {

        Cookie[] cookies = new Cookie[]{new Cookie("USER_STATUS", "NEW_USER")};
        when(request.getCookies()).thenReturn(cookies);

        cookieFilter.doFilter(request, response, filterChain);

        verify(response, times(1)).addCookie(any(Cookie.class));
        verify(filterChain, times(1)).doFilter(request, response);
    }
}