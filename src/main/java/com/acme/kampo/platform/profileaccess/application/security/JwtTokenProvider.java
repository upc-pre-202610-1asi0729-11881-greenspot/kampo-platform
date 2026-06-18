package com.acme.kampo.platform.profileaccess.application.security;

import com.acme.kampo.platform.profileaccess.domain.model.aggregates.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

/**
 * Infrastructure component responsible for generating and validating JWT tokens.
 *
 * <p>Used exclusively by {@link com.acme.kampo.platform.profileaccess.application.internal.commandservices.AuthCommandServiceImpl}.
 * Lives in the application layer because it bridges domain objects ({@link User})
 * with Spring Security infrastructure.</p>
 *
 * <p>Configuration via {@code application.properties}:
 * <pre>
 *   authorization.jwt.secret=your-256-bit-secret-here
 *   authorization.jwt.expiration.days=7
 * </pre>
 */
@Component
public class JwtTokenProvider {

    private final String secret;
    private final long   expirationMs;

    public JwtTokenProvider(
            @Value("${authorization.jwt.secret}") String secret,
            @Value("${authorization.jwt.expiration.days:7}") long expirationDays) {
        this.secret       = secret;
        this.expirationMs = expirationDays * 24 * 60 * 60 * 1000L;
    }

    /**
     * Generates a JWT token for a successfully authenticated user.
     *
     * @param user the authenticated user
     * @return a signed JWT token string
     */
    public String generateToken(User user) {
        var now     = new Date();
        var expiry  = new Date(now.getTime() + expirationMs);
        var key     = getSigningKey();

        return Jwts.builder()
                .setSubject(user.getEmail().getValue())
                .claim("userId", user.getId().getValue())
                .claim("firstName", user.getFirstName())
                .claim("lastName", user.getLastName())
                .setIssuedAt(now)
                .setExpiration(expiry)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Validates a JWT token.
     *
     * @param token the token to validate
     * @return {@code true} if the token is valid and not expired
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Extracts the user's email from a valid JWT token.
     *
     * @param token the JWT token
     * @return the email stored in the token's subject claim
     */
    public String getUserEmailFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }
}