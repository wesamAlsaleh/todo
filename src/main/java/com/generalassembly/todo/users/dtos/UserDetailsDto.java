package com.generalassembly.todo.users.dtos;

import com.generalassembly.todo.usersProfile.dtos.UserProfileDto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;

@Data
@AllArgsConstructor
public class UserDetailsDto {
    private String userId;
    private String email;
    private String firstName;
    private String lastName;
    private String profileDescription;
    private Instant createdAt;
    private Instant updatedAt;
}
