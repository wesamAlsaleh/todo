package com.generalassembly.todo.configs;

import com.generalassembly.todo.authentication.AuthenticationFilter;
import com.generalassembly.todo.authentication.services.UserDetailsServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig {
    private final UserDetailsServiceImpl userDetailsService;
    private final AuthenticationFilter authenticationFilter;

    // bean to configure the app security configuration
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // disable CSRF
        http.csrf(AbstractHttpConfigurer::disable);

        // set session management to stateless (token-based authentication instead of session-based authentication)
        http.sessionManagement(sessionManagementConfigurer ->
                sessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );

        // define endpoint access rules
        http.authorizeHttpRequests(request ->
                request
                        // Public endpoints (no authentication required)
                        .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth/register").permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth/logout").permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth/refresh-access-token").permitAll()
                        // All other endpoints (authentication token required)
                        .anyRequest().authenticated()
        );

        // add custom filter before each request
        http.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class); // valid access token check

        // add custom security exceptions
        http.exceptionHandling(exceptionHandler -> {
                    // if the user is not logged in (no valid token) map the default AuthenticationException to http 401 response
                    exceptionHandler.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)); // Tell Spring Security to return 401

                    // if the user is logged in but not authorized (need specific rule (authorities in the authToken obj) send http 403 response
                    exceptionHandler.accessDeniedHandler(
                            (request, response, accessDeniedException) -> {
                                response.setStatus(HttpStatus.FORBIDDEN.value()); // Tell Spring Security to return 403
                            });
                }
        );

        // Build and return the configured SecurityFilterChain (Configuration object to be used by Spring Security at runtime)
        return http.build();
    }

    // bean to provide a PasswordEncoder at runtime to be used for hashing passwords in the application
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // bean to use the DaoAuthenticationProvider as the project provider
    @Bean
    public AuthenticationProvider authenticationProvider() {
        // create a new instance of DaoAuthenticationProvider
        DaoAuthenticationProvider DaoProvider = new DaoAuthenticationProvider(userDetailsService);

        // set the Dao AuthenticationProvider's tools: UserDetailsService (added above) and PasswordEncoder
        DaoProvider.setPasswordEncoder(passwordEncoder());

        // Return the configured DaoAuthenticationProvider
        return DaoProvider;
    }

    // Bean to provide an AuthenticationManager at runtime (to be used for authentication in the application)
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        // Return the AuthenticationManager from the AuthenticationConfiguration (spring security)
        return authConfig.getAuthenticationManager();
    }
}
