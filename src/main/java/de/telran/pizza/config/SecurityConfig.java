package de.telran.pizza.config;

import de.telran.pizza.domain.entity.enums.Role;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * Configuration class for setting up security configurations.
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    /**
     * Provides a BCryptPasswordEncoder for password encoding.
     *
     * @return Configured BCryptPasswordEncoder.
     */
    @Bean
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Configures security filters and rules for the application.
     *
     * @param http The HttpSecurity object to configure security.
     * @return The configured SecurityFilterChain.
     * @throws Exception If configuration encounters an error.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers(PERMIT_ALL_LIST).permitAll()
                .antMatchers("/manager/**").hasAuthority(Role.ROLE_MANAGER.name())
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login").permitAll()
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .permitAll()
                .and()
                .sessionManagement() // Добавляем настройки управления сессиями
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED) // Указываем, что сессия создается при необходимости
                .maximumSessions(1) // Максимальное количество активных сессий для одного пользователя
                .expiredUrl("/login?expired") // Перенаправление при истечении срока действия сессии
                .maxSessionsPreventsLogin(false); // Разрешить вход новой сессии, отключив старую;
        return http.build();
    }

    // permit list without Manager Role
    private static final String[] PERMIT_ALL_LIST = {
            "/",
            "/signup",
            "/api/get/**",
            "/css/*",
            "/js/*",
            "/pics/**",
            "/static/**",
            "/swagger-ui/**",
            "/v2/api-docs",
            "/v3/api-docs/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-resources",
            "/swagger-resources/**",
            "/swagger-ui.html",
            "/webjars/**"
    };
}
