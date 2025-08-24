package de.bsc_projekt.cloneflix.registration.security.jwt;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import de.bsc_projekt.cloneflix.registration.security.services.UserDetailsImpl;
import io.jsonwebtoken.*;

/**
 * Provides utilities for jwt tokens.
 */
@Component
public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    // @Value("${jwtSecret}")
    private String jwtSecret = "test";

    // @Value("${jwtExpirationMs}")
    private int jwtExpirationMs = 8645000;

    /**
     * Generates a jwt token from the Authentication process.
     * 
     * @param authentication Is an {@link Authentication} created by.
     * @return The jwt token for the authentication request.
     */
    public String generateJwtToken(Authentication authentication) {

        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

        return Jwts.builder().setSubject((userPrincipal.getUsername())).setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS512, jwtSecret).compact();
    }

    /**
     * Retrieves the username from the jwt token.
     * 
     * @param token (jwt token).
     * @return The username as String.
     */
    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }

    /**
     * Validates the given jwt token.
     * 
     * @param authToken to be validated.
     * @return true if the validation succeeds, otherwise false.
     */
    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            logger.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }

        return false;
    }
}
