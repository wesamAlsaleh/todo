package com.generalassembly.todo.users.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Timestamp;
import java.time.Instant;

@Data
@AllArgsConstructor
public class UserDto {
    private Long id;
    private String email;
    private Instant createdAt;
    private Instant updatedAt;
}
