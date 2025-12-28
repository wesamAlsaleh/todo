package com.generalassembly.todo.authentication.services;

import com.generalassembly.todo.authentication.AuthenticationMapper;
import com.generalassembly.todo.authentication.dtos.AuthenticationTokensResponse;
import com.generalassembly.todo.authentication.UserDetailsImpl;
import com.generalassembly.todo.authentication.dtos.LoginUserRequest;
import com.generalassembly.todo.authentication.dtos.RegisterUserRequest;
import com.generalassembly.todo.configs.JwtConfig;
import com.generalassembly.todo.global.exceptions.ResourceNotFoundException;
import com.generalassembly.todo.users.User;
import com.generalassembly.todo.users.UserRepository;
import com.generalassembly.todo.users.dtos.UserDto;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@AllArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationMapper authenticationMapper;
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
    public UserDto register(RegisterUserRequest request) {
        // create new entity
        var user = new User();

        // set the user data
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        // save the record in the db
        userRepository.save(user);

        // return the user as UserDto object
        return authenticationMapper.toDto(user);
    }

    // function to log in a user
    public AuthenticationTokensResponse login(LoginUserRequest request) {
        // try to sign in the user by providing the email and password to the manager, if failed it will throw an exception
        var authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        // get the UserDetails object from the context
        var userObj = (UserDetailsImpl) authentication.getPrincipal();

        // get the user object
        assert userObj != null;
        var user = userObj.getUser();

        // generate an access token (JWT) for the authenticated user
        var accessToken = jwtService.generateAccessToken(user);

        // generate a refresh token (JWT) for the authenticated user
        var refreshToken = jwtService.generateRefreshToken(user);

        // TODO: save the refresh token in the database (if you want to implement refresh token revocation)

        // wrap and return the token in a AuthenticationTokensResponse object {accessToken:"abc", refreshToken:"xyz"}
        return new AuthenticationTokensResponse(accessToken, refreshToken);
    }

    // function to get the current user details
    public UserDto me() {
        // get the user by id (id is the principal in the security context holder)
        var user = userRepository.findById(getSecurityContextPrincipal())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        // return the user as UserDto format
        return authenticationMapper.toDto(user);
    }


    // function to update the access token by refresh token
    public String refreshAccessToken(String refreshToken) {
        // if the refresh token is expired return unauthorized
        if (jwtService.isTokenExpired(refreshToken)) {
            // todo: change the exception to proper one
            throw new BadCredentialsException("Please login again");
        }

        // get the user by id
        var user = userRepository.findById(jwtService.getUserIdFromToken(refreshToken))
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        // generate and return the new access token
        return jwtService.generateAccessToken(user);
    }

}
