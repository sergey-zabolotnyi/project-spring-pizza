package de.telran.pizza.config;

import de.telran.pizza.domain.entity.enums.Role;
import de.telran.pizza.security.UserDetailServiceSecurity;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * Класс конфигурации для настройки безопасности.
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
    private final UserDetailServiceSecurity userDetailServiceSecurity;

    /**
     * Конфигурация безопасности (конструктор).
     *
     * @param userDetailServiceSecurity Сервис пользовательских данных для безопасности.
     */
    public SecurityConfig(UserDetailServiceSecurity userDetailServiceSecurity) {
        this.userDetailServiceSecurity = userDetailServiceSecurity;
    }

    /**
     * Предоставляет BCryptPasswordEncoder для кодирования пароля.
     *
     * @return Настроенный BCryptPasswordEncoder.
     */
    @Bean
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Настраивает фильтры и правила безопасности для приложения.
     *
     * @param http Объект HttpSecurity для настройки безопасности.
     * @return Настроенный SecurityFilterChain.
     * @throws Exception Если возникает ошибка во время конфигурации.
     */
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .authorizeRequests()
//                .antMatchers(PERMIT_ALL_LIST).permitAll()
//                .antMatchers("/manager/**").hasAuthority(Role.ROLE_MANAGER.name())
//                .anyRequest().authenticated()
//                .and()
//                .formLogin()
//                .loginPage("/login").permitAll()
//                .and()
//                .logout()
//                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
//                .permitAll()
//                .and()
//                .sessionManagement() // Добавляем настройки управления сессиями
//                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED) // Указываем, что сессия создается при необходимости
//                .maximumSessions(1) // Максимальное количество активных сессий для одного пользователя
//                .expiredUrl("/login") // Перенаправление при истечении срока действия сессии
//                .maxSessionsPreventsLogin(false); // Разрешить вход новой сессии, отключив старую;
//        return http.build();
//    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
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
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .maximumSessions(1)
                .expiredUrl("/login")
                .maxSessionsPreventsLogin(false);

    }

    /**
     * Настраивает AuthenticationManagerBuilder для управления аутентификацией.
     *
     * @param auth Объект AuthenticationManagerBuilder.
     * @throws Exception Если возникает ошибка во время конфигурации.
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailServiceSecurity)
                .passwordEncoder(encoder());
    }

    // Список разрешенных URL без роли Manager
    private static final String[] PERMIT_ALL_LIST = {
            "/",
            "/signup",
            "/signup/get",
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
