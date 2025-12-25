package com.generalassembly.todo.authentication.dtos;

import com.generalassembly.todo.users.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegisterUserResponse {
    private User user;
    private String accessToken;
}
