package de.bsc_projekt.cloneflix.registration.security.services;

import de.bsc_projekt.cloneflix.Models.User;
import de.bsc_projekt.cloneflix.Repositories.MongoDB.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Implements {@link UserDetailsService}.
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

        private final static String USER_NOT_FOUND_MSG = "user with email %s not found";

        @Autowired
        private UserRepository userRepository;

        /**
         * Retrieves a user from the {@link UserRepository}.
         * 
         * @param email identifying the user.
         * @return {@link UserDetails} for the user.
         */
        @Override
        public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
                User user = userRepository.findByEmail(email).orElseThrow(
                                () -> new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, email)));
                return UserDetailsImpl.build(user);
        }
}
