package de.bsc_projekt.cloneflix.registration.security.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * Implements {@link IAuthenticationFacade} to make the user authentication
 * available everywhere.
 */
@Component
public class AuthenticationFacade implements IAuthenticationFacade {
    /**
     * Retrieves the user authentication from the {@link SecurityContextHolder}.
     * 
     * @return The {@link Authentication} for the current user.
     */
    @Override
    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
}
