package org.example.demo.config;

import org.example.demo.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Roles:
 *  - visitor            = not logged in
 *  - registered visitor = ROLE_USER  -> may see the Messages menu
 *  - admin              = ROLE_ADMIN -> may see the Messages and CRUD menus
 */
@Configuration
public class WebSecurityConfig {

    private final CustomUserDetailsService userDetailsService;

    public WebSecurityConfig(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authenticationProvider(authenticationProvider())
                .authorizeHttpRequests(auth -> auth
                        // menu items a not-logged-in visitor can open
                        .requestMatchers("/", "/database", "/contact", "/chart", "/rest",
                                "/register", "/login", "/error",
                                "/css/**", "/js/**", "/images/**").permitAll()
                        // Messages menu: registered visitor + admin
                        .requestMatchers("/messages").hasAnyRole("USER", "ADMIN")
                        // CRUD menu: admin only
                        .requestMatchers("/crud", "/crud/**").hasRole("ADMIN")
                        // TODO (teammate): add the rules of the RESTful API (e.g. /api/**) here
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        // go back to the page the user wanted (e.g. /messages), otherwise to the Mainpage
                        .defaultSuccessUrl("/", false)
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/")
                        .permitAll()
                )
                .exceptionHandling(exception -> exception
                        // logged in, but not allowed (e.g. a USER opens /crud) -> Mainpage with a warning.
                        // getContextPath() keeps this working when deployed as neptun.war on Tomcat
                        .accessDeniedHandler((request, response, accessDeniedException) ->
                                response.sendRedirect(request.getContextPath() + "/?denied"))
                );
        return http.build();
    }
}
