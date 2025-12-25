package com.generalassembly.todo.authentication;

import com.generalassembly.todo.authentication.dtos.RegisterUserRequest;
import com.generalassembly.todo.authentication.services.AuthenticationService;
import com.generalassembly.todo.global.dtos.ErrorDto;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@AllArgsConstructor
@RequestMapping("/auth")
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    private final AuthenticationMapper authenticationMapper;

    @GetMapping("/")
    public ResponseEntity<?> hello() {
        return ResponseEntity.ok("Hello World");
    }

    // register user endpoint
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(
            @Valid @RequestBody RegisterUserRequest registerUserRequest,
            UriComponentsBuilder uriBuilder
    ) {
        try {
            // create new user
            var newUser = authenticationService.register(registerUserRequest); // through exception if something went wrong

            // prepare the response body using the userDto format
            var userDto = authenticationMapper.toDto(newUser);

            // create the URI (Best practice) to return it in the response body
            var uri = uriBuilder.path("/users/{id}").buildAndExpand(userDto.getId()).toUri();

            // return the response with status 201 and the uri (location of the created entity)
            return ResponseEntity.created(uri).body(userDto);
        } catch (DataIntegrityViolationException e) {
            System.out.println(e);
            // if the error contain 23505 then return conflict response
            if (e.getMostSpecificCause().getMessage().contains("violates unique")) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorDto("User already exists"));
            }

            // return general error
            return ResponseEntity.badRequest().body(new ErrorDto("Error creating user"));
        }
    }
}
