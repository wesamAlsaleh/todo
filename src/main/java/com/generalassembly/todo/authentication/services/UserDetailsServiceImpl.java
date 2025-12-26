package com.generalassembly.todo.authentication.services;

import com.generalassembly.todo.authentication.UserDetailsImpl;
import com.generalassembly.todo.global.exceptions.ResourceNotFoundException;
import com.generalassembly.todo.users.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

// Note that UserDetailsService is a core interface in Spring Security, and it expects exception to UsernameNotFoundException, not custom exceptions

// Note: UserDetails

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private UserRepository userRepository;

    @Override
    // This method should return the user details by email
    public org.springframework.security.core.userdetails.UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // get the user by email or throw exception if not found
        var user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("User with email %s not found", email)));

        // if the user found return the userDetails object using spring security User Class!
//        return new org.springframework.security.core.userdetails.User(
//                user.getEmail(), // user email
//                user.getPassword(), // user hashed password
//                Collections.emptyList() // no authorities
//        );

        // if the user found return the custom userDetails object
        return new UserDetailsImpl(user);

    }
}
