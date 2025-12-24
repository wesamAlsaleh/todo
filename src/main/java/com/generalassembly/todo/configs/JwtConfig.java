package com.generalassembly.todo.configs;

import io.jsonwebtoken.security.Keys;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.crypto.SecretKey;

@Configuration
@ConfigurationProperties(prefix = "spring.jwt") // Prefix to bind properties from application.yaml
@Data
public class JwtConfig {
    // ******** this properties will be loaded from application.yaml ********
    private String secretKey;
    private Long accessTokenValiditySeconds;
    private Long refreshTokenValiditySeconds;

    // function to get the securityKey object from the secret key
    public SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }
}
