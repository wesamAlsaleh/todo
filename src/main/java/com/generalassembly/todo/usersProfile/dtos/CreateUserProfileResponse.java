package com.generalassembly.todo.usersProfile.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateUserProfileResponse {
    private UserProfileDto userProfile;
}
