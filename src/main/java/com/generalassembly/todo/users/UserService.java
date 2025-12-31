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

    // function to get the user details
    public UserDetailsDto getUserDetails() {
        // get the user with
        var user = userRepository.findById(authenticationService.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        // prepare and return the output as Dto
        return userProfileMapper.toDto(user);
    }
}
