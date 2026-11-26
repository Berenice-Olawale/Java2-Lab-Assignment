
package org.example.demo.config;

import org.example.demo.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

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
        DaoAuthenticationProvider provider =
                new DaoAuthenticationProvider(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception {

        http
                .authenticationProvider(authenticationProvider())

                .authorizeHttpRequests(auth -> auth
                        // Public pages and resources
                        .requestMatchers(
                                "/", "/database", "/contact", "/chart", "/rest",
                                "/register", "/login", "/error",
                                "/css/**", "/js/**", "/images/**"
                        ).permitAll()

                        // Messages: registered users and admins
                        .requestMatchers("/messages")
                        .hasAnyRole("USER", "ADMIN")

                        // CRUD: admins only
                        .requestMatchers("/crud", "/crud/**")
                        .hasRole("ADMIN")

                        // REST API: GET requests are public
                        .requestMatchers(HttpMethod.GET, "/api/**")
                        .permitAll()

                        // REST API: all other requests require admin
                        .requestMatchers("/api/**")
                        .hasRole("ADMIN")

                        .anyRequest().authenticated()
                )

                // Disable CSRF protection for REST API requests
                .csrf(csrf ->
                        csrf.ignoringRequestMatchers("/api/**")
                )

                // Browser login
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/", false)
                        .permitAll()
                )

                // REST API authentication
                .httpBasic(Customizer.withDefaults())

                .logout(logout -> logout
                        .logoutSuccessUrl("/")
                        .permitAll()
                )

                .exceptionHandling(exception -> exception
                        .accessDeniedHandler((request, response,
                                              accessDeniedException) ->
                                response.sendRedirect(
                                        request.getContextPath() + "/?denied"
                                )
                        )
                );

        return http.build();
    }
}
