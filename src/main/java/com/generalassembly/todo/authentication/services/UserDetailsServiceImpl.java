package com.generalassembly.todo.authentication.services;

import com.generalassembly.todo.users.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

// Note that UserDetailsService is a core interface in Spring Security, and it expects exception to UsernameNotFoundException, not custom exceptions

// Note: UserDetails

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private UserRepository userRepository;

    @Override
    // This method should return the user details by email
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // get the user by email or todo: throw exception if not found
        var user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(email));

        // if the user found return the user object using spring security User Class!
        return new User(
                user.getEmail(), // user email
                user.getPassword(), // user hashed password
                Collections.emptyList() // no authorities
        );
    }
}
