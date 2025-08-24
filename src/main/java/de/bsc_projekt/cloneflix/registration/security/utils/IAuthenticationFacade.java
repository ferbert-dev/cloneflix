package de.bsc_projekt.cloneflix.registration.security.utils;

import org.springframework.security.core.Authentication;

/**
 * Interface to make the current user {@link Authentication} available
 * everywhere.
 */
public interface IAuthenticationFacade {
    Authentication getAuthentication();
}
