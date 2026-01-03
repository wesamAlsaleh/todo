package com.generalassembly.todo.users;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    // get the full details of the authenticated user endpoint
    @GetMapping
    public ResponseEntity<?> getUserDetails() {
        // fetch the user details
        var userDetails = userService.getUserDetails();

        return ResponseEntity.ok().body(userDetails);
    }
}
