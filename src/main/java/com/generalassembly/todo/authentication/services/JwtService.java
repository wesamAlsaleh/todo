package com.generalassembly.todo.authentication.services;

import com.generalassembly.todo.configs.JwtConfig;
import com.generalassembly.todo.users.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@AllArgsConstructor
public class JwtService {
    private final JwtConfig jwtConfig;

    // function to generate a JWT token for a user
    private String buildJwtToken(User user, long tokenExpirationInSeconds) {
        // build and return the JWT token
        return Jwts.builder()
                .subject(String.valueOf(user.getId())) // Token subject
                .claim("email", user.getEmail()) // Bonus Claim
                .issuedAt(new Date()) // Token issue time
                .expiration(new Date(System.currentTimeMillis() + 1000L * tokenExpirationInSeconds)) // Expiry time in milliseconds (sec * 1000L)
                .signWith(jwtConfig.getSecretKey()) // Security signature using the secret key
                .compact();
    }

    // function get the token clams from a JWT token
    private Claims getClaimsFromToken(String token) {
        // parse the JWT token and return the claims
        return Jwts.parser()
                .verifyWith(jwtConfig.getSecretKey()) // Verify the token using the secret key
                .build() // Build the parser instance after setting the verification key
                .parseSignedClaims(token) // Parse the signed JWT token
                .getPayload(); // Get the payload (claims) from the token
    }

    // function to generate access token
    public String generateAccessToken(User user) {
        // get the expiration time in seconds
        final long tokenExpirationTime = jwtConfig.getAccessTokenValiditySeconds();

        // build and return the access token using the jwt builder
        return buildJwtToken(user, tokenExpirationTime);
    }

    // function to generate refresh token
    public String generateRefreshToken(User user) {
        // get the expiration time in seconds
        final long tokenExpirationTime = jwtConfig.getRefreshTokenValiditySeconds();

        // build and return the access token using the jwt builder
        return buildJwtToken(user, tokenExpirationTime);
    }

    // function to check if the token is expired
    public boolean isTokenExpired(String token) {
        try {
            // parse the token and extract the claims
            final Claims claims = getClaimsFromToken(token);

            // return true if the token is expired (expiration date is before the current date)
            return claims.getExpiration().before(new Date());
        } catch (JwtException e) {
            // if parsing the token fails, consider it expired
            return true;
        }
    }

    // function to fetch the user id from the token subject
    public Long getUserIdFromToken(String token) {
        // parse the token and extract the claims
        final Claims claims = getClaimsFromToken(token);

        // return the subject which is the user id from the claim
        return Long.valueOf(claims.getSubject());
    }

    // function to fetch the user email from the token claims
    public String getEmailFromToken(String token) {
        // parse the token and extract the claims
        final Claims claims = getClaimsFromToken(token);

        // return the user email from the token claims
        return claims.get("email").toString();
    }

}
