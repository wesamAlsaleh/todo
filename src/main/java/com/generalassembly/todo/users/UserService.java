package com.generalassembly.todo.users;

import com.generalassembly.todo.authentication.services.AuthenticationService;
import com.generalassembly.todo.global.exceptions.ResourceNotFoundException;
import com.generalassembly.todo.users.dtos.UserDetailsDto;
import com.generalassembly.todo.usersProfile.UserProfileMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {
    private final AuthenticationService authenticationService;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final UserProfileMapper userProfileMapper;

    // function to get the authenticated user id
    public Long getUserId() {
        return authenticationService.getUserId();
    }

    // function to get the authenticated user
    public User getUser() {
        // get the user
        return userRepository.findById(getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    // function to get the user details
    public UserDetailsDto getUserDetails() {
        // get the authenticated user with
        var user = getUser();

        // prepare and return the output as Dto
        return userProfileMapper.toDto(user);
    }
}
