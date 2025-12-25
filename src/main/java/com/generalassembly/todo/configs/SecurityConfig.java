package com.generalassembly.todo.configs;

import com.generalassembly.todo.authentication.services.UserDetailsServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
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

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig {
    private UserDetailsServiceImpl userDetailsService;

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
                        .requestMatchers(HttpMethod.GET, "/auth/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth/register").permitAll()
                        .anyRequest().permitAll()
        );

        // Build and return the configured SecurityFilterChain (Configuration object to be used by Spring Security at runtime)
        return http.build();
    }

    // bean to provide a PasswordEncoder at runtime to be used for hashing passwords in the application
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

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
