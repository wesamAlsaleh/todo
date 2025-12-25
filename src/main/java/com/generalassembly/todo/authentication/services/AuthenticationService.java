package com.generalassembly.todo.authentication.services;

import com.generalassembly.todo.authentication.dtos.RegisterUserRequest;
import com.generalassembly.todo.configs.JwtConfig;
import com.generalassembly.todo.global.exceptions.DuplicateResourceException;
import com.generalassembly.todo.users.User;
import com.generalassembly.todo.users.UserRepository;
import jakarta.servlet.http.Cookie;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final JwtConfig jwtConfig;


    // function to get the user id from the authentication token object in the security context holder
    private Long getSecurityContextPrincipal() {
        // get the authentication token object from the Security Context Holder
        var authenticationObject = SecurityContextHolder.getContext().getAuthentication(); // authentication is the object that holds the authentication information for the user

        // if the authentication token object is null return error
        if (authenticationObject == null) {
            // todo: add custom error
            throw new RuntimeException("Authentication object is null");
        }

        // extract and return the user ID from the authentication object principal (which we set in the AuthenticationFilter)
        return Long.parseLong(Objects.requireNonNull(authenticationObject.getPrincipal()).toString());
    }

    // function to register new user
    public User register(RegisterUserRequest request) {
        // check if the email is already in the database
//        if (userRepository.emailExists(request.getEmail())) {
////            throw new DuplicateResourceException("Email already exists");
//            System.out.println("Duplicate email exists: " + request.getEmail());
//        }

        // create new entity
        var user = new User();

        // set the user data
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        // save the record in the db
        userRepository.save(user);

        return user;
    }
}
