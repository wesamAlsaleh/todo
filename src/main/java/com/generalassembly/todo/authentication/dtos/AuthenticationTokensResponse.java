package com.generalassembly.todo.authentication.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthenticationTokensResponse {
    private String accessToken;
    private String refreshToken;
}
