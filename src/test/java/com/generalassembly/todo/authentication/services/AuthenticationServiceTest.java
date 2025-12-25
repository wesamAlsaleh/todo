package com.generalassembly.todo.authentication.services;

import static org.junit.jupiter.api.Assertions.*;

import com.generalassembly.todo.authentication.dtos.LoginUserRequest;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.*;

import org.junit.jupiter.api.DisplayName;

import java.time.LocalDateTime;

@AllArgsConstructor
class AuthenticationServiceTest {
    AuthenticationService authenticationService;

    @Test
    @DisplayName("Should try to login and return access token")
    public void shouldAttemptToLoginAndReturnAccessToken() {
        authenticationService.login(new LoginUserRequest("wesam1@gmail.com", "wwwweeee44"));
    }

}