package com.generalassembly.todo.authentication;

import com.generalassembly.todo.authentication.dtos.LoginUserRequest;
import com.generalassembly.todo.authentication.dtos.LoginUserResponse;
import com.generalassembly.todo.authentication.dtos.RegisterUserRequest;
import com.generalassembly.todo.authentication.services.AuthenticationService;
import com.generalassembly.todo.configs.JwtConfig;
import com.generalassembly.todo.global.dtos.ErrorDto;
import com.generalassembly.todo.global.exceptions.BadRequestException;
import com.generalassembly.todo.global.exceptions.ResourceNotFoundException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.naming.AuthenticationException;

@RestController
@AllArgsConstructor
@RequestMapping("/auth")
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    private final JwtConfig jwtConfig;

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
            var userDto = authenticationService.register(registerUserRequest); // through exception if something went wrong

            // create the URI (Best practice) to return it in the response body
            var uri = uriBuilder.path("/users/{id}").buildAndExpand(userDto.getId()).toUri();

            // return the response with status 201 and the uri (location of the created entity)
            return ResponseEntity.created(uri).body(userDto);
        } catch (DataIntegrityViolationException exception) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorDto("User already exists"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ErrorDto("Error while registering new user")); // return general error
        }
    }

    // login user endpoint
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(
            @Valid @RequestBody LoginUserRequest request,
            HttpServletResponse response
    ) {
        try {
            // attempt to sign in
            var tokens = authenticationService.login(request);

            // store the refresh token in an HTTP-only secure cookie
            var cookie = new Cookie("refreshToken", tokens.getRefreshToken());
            cookie.setHttpOnly(true); // prevent access to the cookie via JavaScript (mitigates XSS)
            cookie.setPath("/auth/refresh-access-token"); // cookie will be sent only to /auth/refresh-access-token endpoint
            cookie.setMaxAge(Math.toIntExact(jwtConfig.getRefreshTokenValiditySeconds())); // set cookie expiration (same as refresh token)
            cookie.setSecure(true); // send cookie only over HTTPS

            // add the configured cookie to the response
            response.addCookie(cookie);

            // return the response entity with HTTP status 200 and the token payload
            return ResponseEntity.ok().body(new LoginUserResponse(tokens.getAccessToken()));
        } catch (BadCredentialsException | InternalAuthenticationServiceException exception) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorDto("Invalid email or password"));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(new ErrorDto("Failed to login"));
        }
    }

    // get current user endpoint
    @GetMapping("/me")
    public ResponseEntity<?> me() {
        try {
            // try to fetch the user
            var userDto = authenticationService.me();

            // return the user details with OK response
            return ResponseEntity.ok(userDto);
        } catch (ResourceNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorDto(exception.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(new ErrorDto("Failed to fetch user details"));
        }
    }

    // logout endpoint
    @PostMapping("/logout")
    public ResponseEntity<?> logoutUser(HttpServletResponse response) {
        try {
            // delete the cookie
            var cookie = new Cookie("refreshToken", "");

            // make the refresh token in the browser cookies to be expired
            cookie.setMaxAge(0); // set age to 0 to trigger immediate deletion
            cookie.setPath("/auth/refresh-access-token"); // cookie will be sent only to /auth/refresh-access-token endpoint
            cookie.setHttpOnly(true);
            cookie.setSecure(true);

            // add the configured cookie to the response
            response.addCookie(cookie);

            return ResponseEntity.ok().build();
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
