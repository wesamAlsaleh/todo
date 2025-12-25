package com.generalassembly.todo.authentication.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthenticationTokenResponse {
    private String token;
}
