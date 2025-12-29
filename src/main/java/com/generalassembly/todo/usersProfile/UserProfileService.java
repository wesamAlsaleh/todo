package com.generalassembly.todo.usersProfile;

import com.generalassembly.todo.authentication.services.AuthenticationService;
import com.generalassembly.todo.global.exceptions.BadRequestException;
import com.generalassembly.todo.global.exceptions.ResourceNotFoundException;
import com.generalassembly.todo.users.UserRepository;
import com.generalassembly.todo.usersProfile.dtos.CreateUserProfileRequest;
import com.generalassembly.todo.usersProfile.dtos.UserProfileDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@AllArgsConstructor
public class UserProfileService {
    private final UserRepository userRepository;
    private final AuthenticationService authenticationService;
    private final UserProfileMapper userProfileMapper;
    private final UserProfileRepository userProfileRepository;


    // function to create user profile
    public UserProfileDto createUserProfile(CreateUserProfileRequest request) {
        // get the authenticated user
        var user = userRepository.findById(authenticationService.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        System.out.println("yey");

        // if the user has a profile return error
        if (userProfileRepository.existByUserId(authenticationService.getUserId())) {
            throw new BadRequestException("User has a profile that already exists");
        }

        // create user profile instance
        var userProfile = new UserProfile();

        // set the required fields
        userProfile.setUser(user);
        userProfile.setFirstName(request.getFirstName());
        userProfile.setLastName(request.getLastName());
        userProfile.setProfileDescription(request.getProfileDescription()); // this is an optional field

        // save the created entity
        userProfileRepository.save(userProfile);

        // update the user updated_at column
        user.setUpdatedAt(Instant.now());

        // update the changes
        userRepository.save(user);

        // convert and return the UserProfile object to Dto format
        return userProfileMapper.toDto(userProfile);
    }
}
