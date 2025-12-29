package com.generalassembly.todo.usersProfile;

import com.generalassembly.todo.global.exceptions.BadRequestException;
import com.generalassembly.todo.usersProfile.dtos.CreateUserProfileRequest;
import com.generalassembly.todo.usersProfile.dtos.UpdateUserProfileRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@AllArgsConstructor
@RequestMapping("/user")
public class UserProfileController {
    private final UserProfileService userProfileService;

    // create user profile endpoint
    @PostMapping("/create-profile")
    public ResponseEntity<?> createUserProfile(
            @Valid @RequestBody CreateUserProfileRequest request,
            UriComponentsBuilder uriBuilder
    ) {
        // try to create user profile
        try {
            // create the user profile
            var userProfileDto = userProfileService.createUserProfile(request);

            // create the URI (Best practice) to return it in the response body
            var uri = uriBuilder.path("/users/{id}").buildAndExpand(userProfileDto.getUserId()).toUri();

            // return the response with status 201 and the uri (location of the created entity)
            return ResponseEntity.created(uri).body(userProfileDto);
        } catch (Exception e) {
            throw new BadRequestException("Failed to create user profile");
        }
    }

    // update user profile endpoint
    @PutMapping("/update-profile")
    public ResponseEntity<?> updateUserProfile(
            @Valid @RequestBody UpdateUserProfileRequest request
    ) {
        // try to update user profile
        try {
            // update the user profile
            var userProfileDto = userProfileService.updateUserProfile(request);

            // return the response with status 201 and the uri (location of the created entity)
            return ResponseEntity.ok(userProfileDto);
        } catch (Exception e) {
            throw new BadRequestException("Failed to update user profile");
        }
    }
}
