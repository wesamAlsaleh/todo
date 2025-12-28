package com.generalassembly.todo.authentication.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class RefreshAccessTokenResponse {
    private String accessToken;
}
