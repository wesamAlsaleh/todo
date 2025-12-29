package com.generalassembly.todo.usersProfile.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class UserProfileDto {
    private Long userId;
    private String firstName;
    private String lastName;
    private String profileDescription;
}
