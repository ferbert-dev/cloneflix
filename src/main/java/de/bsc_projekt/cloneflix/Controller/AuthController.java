package de.bsc_projekt.cloneflix.Controller;

import de.bsc_projekt.cloneflix.Models.AppUserRole;
import de.bsc_projekt.cloneflix.Models.User;
import de.bsc_projekt.cloneflix.Repositories.MongoDB.UserRepository;
import de.bsc_projekt.cloneflix.registration.requests.LoginRequest;
import de.bsc_projekt.cloneflix.registration.requests.RegistrationRequest;
import de.bsc_projekt.cloneflix.registration.responses.JwtResponse;
import de.bsc_projekt.cloneflix.registration.responses.MessageResponse;
import de.bsc_projekt.cloneflix.registration.security.jwt.JwtUtils;
import de.bsc_projekt.cloneflix.registration.security.services.UserDetailsImpl;
import de.bsc_projekt.cloneflix.registration.tools.EmailValidator;
import de.bsc_projekt.cloneflix.services.MovieRecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Handles user authentication and registration.
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    EmailValidator emailValidator;

    @Autowired
    UserRepository userRepository;

    @Autowired
    public MovieRecommendationService movieRecommendationService;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    /**
     * Signs in a user with username and password.
     * 
     * @param {@link LoginRequest} contains username and password.
     * @return {@link JwtResponse} containing the jwt token and user details.
     */
    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Validated @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);


        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream().map(
                GrantedAuthority::getAuthority)
                .collect(Collectors.toList());


        createNewUserNeo(userDetails.getId(),userDetails.getUsername());

        return ResponseEntity.ok(
                new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(), userDetails.getUsername(), roles));
    }
    /**Create new User in Neo4je if not exist
     *
     *@param userId String User id in MongoDb, easy find
     *@param userName String User
     *
     * */
    @Async
    void createNewUserNeo(String userId,String userName){
        movieRecommendationService.addUser(userId,userName);
    }
    /**
     * Registers a user.
     * 
     * @param {@link RegistrationRequest} containing the corresponding user
     *               information.
     * @return {@link MessageResponse} if registration was successful.
     */
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Validated @RequestBody RegistrationRequest signUpRequest) {

        if (!emailValidator.test(signUpRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is not valid!"));
        }
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
        }

        // Create new user's account
        User user = new User(signUpRequest.getFirstName(),
                signUpRequest.getLastName(), signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()));

        // Set<String> strRoles = signUpRequest.getRole();
        Set<AppUserRole> roles = new HashSet<>();
        roles.add(AppUserRole.USER);

        user.setAppUserRole(roles);
        userRepository.save(user);


        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
}
