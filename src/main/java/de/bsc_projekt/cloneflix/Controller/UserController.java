package de.bsc_projekt.cloneflix.Controller;

import com.sun.istack.NotNull;
import de.bsc_projekt.cloneflix.Models.Movie;
import de.bsc_projekt.cloneflix.Models.Serie;
import de.bsc_projekt.cloneflix.registration.security.services.UserDetailsImpl;
import de.bsc_projekt.cloneflix.registration.security.utils.IAuthenticationFacade;
import de.bsc_projekt.cloneflix.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

/**
 * The UserController to handle with user data and responsible for preparing a
 * model and mapping with data and write directly to the response stream and
 * complete the request.
 *
 * @version 5.0
 * @since 2021-06-01
 */
@RestController
@RequestMapping("api/v1/users")
@AllArgsConstructor
public class UserController {


   final private UserService userService;
   final private IAuthenticationFacade authenticationFacade;

    /**
     * Get User data account from Mongo Db. This only works if the currently
     * authenticated user is the owner of the account.
     *
     * @param id of the user account that should be queried.
     * @return HTTP custom response. OK with user data that cann be release, else
     *         FORBIDDEN.
     */

    @GetMapping("/{id}")
    public ResponseEntity<Object> one(@PathVariable String id) {

        String currentUserId = getCurrentUserID();

        if (currentUserId.equals(id)) {
            return new ResponseEntity<>(userService.userDetails(id), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    /**
     * Deletes an user account. This only works if the currently authenticated user
     * is the owner of the account.
     *
     * @param id of the user account that should be deleted.
     * @return HTTP response. OK if the user account was deleted, else FORBIDDEN.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable String id) {
        String currentUserId = getCurrentUserID();
        if (currentUserId.equals(id) && userService.removeUserMongo(id))
        {
            return new ResponseEntity<>("User with id : "
                    + id
                    + " is deleted", HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    /**
     * Update user account in Mongo DB. This only works if the currently
     * authenticated user is the owner of the account.
     *
     * @param id     of the user account that should be changed.
     * @param update ist ein Hashmap or JSON Document with with datails to change be
     *               user account.
     * @return HTTP response. OK if the user account was changed, else FORBIDDEN.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateUserInfo(@NotNull @PathVariable String id,
            @RequestBody HashMap<String, Object> update) {
        return userService.updateUserInfo(id, update);
    }

    /**
     * Put title to the user's favorite list in Mongo DB.
     *
     * @param titleId
     */
    @PutMapping("/favorite/{titleId}")
    ResponseEntity<String> putInFavorite(@PathVariable String titleId) {
        String currentUserId = getCurrentUserID();
        return userService.putInFavorite(currentUserId, titleId);
    }

    /**
     * Get all favorite movies from the favorite list of the current user.
     *
     * @return List with movies favoured by the user.
     */
    @GetMapping("/favorite/movies")
    List<Movie> getAllFavoriteMovies() {
        String currentUserId = getCurrentUserID();
        return userService.getAllFavoriteMovies(currentUserId);
    }

    /**
     * Get the users favorite series.
     * 
     * @return Users favorite series.
     */
    @GetMapping("/favorite/series")
    List<Serie> getAllFavoriteSeries() {
        String currentUserId = getCurrentUserID();
        return userService.getAllFavoriteSeries(currentUserId);
    }

    /**
     * Checks if a user has favoured a title.
     *
     * @param titleId
     * @return true or false
     */
    @GetMapping("/favorite/check/{titleId}")
    Boolean checkIfIsInFavorites(@PathVariable String titleId) {
        String currentUserId = getCurrentUserID();
        return userService.checkIfInFavorites(currentUserId, titleId);
    }

    /**
     * Delete movie reference from the current users favorites list.
     *
     * @param id The ID of the movie that is to be deleted.
     * @return HTTP response. OK if the movie id was found and deleted. NOT_FOUND
     *         when the Movie is not in the users favorites.
     */
    @DeleteMapping("/favorite/{id}")
    ResponseEntity<String> deleteAusFavorite(@PathVariable String id) {
        String currentUserId = getCurrentUserID();
        return userService.deleteAusFavorite(currentUserId, id);
    }

    /**
     * Get current user id . That method works if the current user authenticated.
     * 
     * @return String id. String with current user id.
     */
    private String getCurrentUserID() {
        Authentication authentication = authenticationFacade.getAuthentication();
        UserDetailsImpl user = (UserDetailsImpl) authentication.getPrincipal();
        return user.getId();
    }

}