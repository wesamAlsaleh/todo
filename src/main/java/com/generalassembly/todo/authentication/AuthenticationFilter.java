package com.generalassembly.todo.authentication;

import com.generalassembly.todo.authentication.services.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

// NOTE: A filter that is executed once per request. It will run before any controller is called.
// This filter will check if the request contain a valid JWT token in the Authorization header.
// If the token is valid, it will check if the user has access to the requested resource.

// NOTE: SecurityContextHolder store the authentication information of the current user in a thread-local storage.
// This means that the authentication information is stored in a variable that is specific to the current thread
// and is not shared with other threads. This is important because each request is handled by a different thread.
// By using thread-local storage, we can ensure that the authentication information is only accessible
// to the thread that is handling the current request. This prevents any potential security issues that could arise
// from sharing authentication information between different requests or users.

@Component // register this filter as a Spring Component so that Spring can manage its lifecycle and dependencies
@AllArgsConstructor
public class AuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;

    // function to filter the request to check if the user can access protected resources
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // extract the JWT token from the authorization header "Bearer A2C4"
        var authorizationHeader = request.getHeader("Authorization");

        // if the authorization header is missing or does not start with "Bearer " then skip the filter
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            // if so, skip JWT validation and continue with the next filter in the chain
            filterChain.doFilter(request, response);

            // exit the current filter and the spring security will handle the request
            return;
        }

        // extract the token from the header
        var token = authorizationHeader.replace("Bearer ", ""); // Remove "Bearer " from "Bearer A2C4"

        // if the token is expired then skip the filter
        if (jwtService.isTokenExpired(token)) {
            // if so, skip JWT validation and continue with the next filter in the chain
            filterChain.doFilter(request, response);

            // exit the current filter and the spring security will handle the request
            return;
        }

        // get the user id from the token
        var userId = jwtService.getUserIdFromToken(token);

        // build an authentication token object with the user id (the token object is used by Spring Security to represent the authenticated user)
        var authenticationTokenObject = new UsernamePasswordAuthenticationToken(
                userId, // user id as principal
                null, // no credentials because we are using JWT token for authentication (not username and password)
                Collections.emptyList() // no roles in this project to declare
        );

        // set the request details in the authentication token object (IP address, session ID, etc.) `boilerplate code!`
        authenticationTokenObject.setDetails(
                new WebAuthenticationDetailsSource().buildDetails(request) // set the request details in the authentication token to be used by Spring Security
        );

        // set the authentication token object in the security context holder (store the authentication information for the current request) `boilerplate code!`
        SecurityContextHolder.getContext().setAuthentication(authenticationTokenObject); // This will mark the user as authenticated in the current request context

        // Continue with the next filter in the chain
        filterChain.doFilter(request, response); // Pass the control to the next filter method
    }
}
