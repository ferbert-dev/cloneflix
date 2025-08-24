package de.bsc_projekt.cloneflix.services;

import de.bsc_projekt.cloneflix.Exception.UserNotFoundException;
import de.bsc_projekt.cloneflix.Models.Movie;
import de.bsc_projekt.cloneflix.Models.Payment;
import de.bsc_projekt.cloneflix.Models.Serie;
import de.bsc_projekt.cloneflix.Models.User;
import de.bsc_projekt.cloneflix.Repositories.MongoDB.MovieRepository;
import de.bsc_projekt.cloneflix.Repositories.MongoDB.SerieRepository;
import de.bsc_projekt.cloneflix.Repositories.MongoDB.UserRepository;
import de.bsc_projekt.cloneflix.registration.responses.OneUserResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The UserService to handle business logic
 *
 * @version 5.0
 * @since 2021-07-5
 */
@Service
@AllArgsConstructor
public class UserService {

    final private UserRepository userRepository;
    final private MovieRepository movieRepository;
    final private SerieRepository serieRepository;

    /**
     * get user account details in Mongo DB. This only works if the currently
     * authenticated user is the owner of the account.
     *
     * @param id of the user account that should be changed.
     * @return OneUserResponse response.
     */
    public OneUserResponse userDetails(String id) {
        User DbUser = findMongoUser(id);
        return new OneUserResponse(DbUser.getId(),
                DbUser.getUsername(),
                DbUser.getEmail(),
                DbUser.getPayment_method(),
                DbUser.getGeburtsDatum(),
                DbUser.getAppUserRole());
    }

    /**
     * remove user account from Mongo DB. This only works if the currently
     * authenticated user is the owner of the account.
     *
     * @param id of the user account that should be changed.
     * @return boolean true or false
     */
    public boolean removeUserMongo(String id) {
        userRepository.deleteById(id);
        return userRepository.findById(id).isEmpty();
    }

    /**
     * Update user account in Mongo DB. This only works if the currently
     * authenticated user is the owner of the account.
     *
     * @param id     of the user account that should be changed.
     * @param update is a Hashmap or JSON Document with details to change the user
     *               account.
     * @return HTTP response. OK if the user account was changed, else FORBIDDEN.
     */
    public ResponseEntity<Object> updateUserInfo(String id, HashMap<String, Object> update) {
        User user = findMongoUser(id);
        if (user != null) {
            for (Map.Entry<String, Object> entry : update.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();

                switch (key) {
                    case "firstName":
                        user.setFirstName((String) value);
                        break;
                    case "lastName":
                        user.setLastname((String) value);
                        break;
                    case "geburtsDatum":
                        user.setGeburtsDatum((String) value);
                        break;
                    case "payment_method":
                        if (value.equals("GOOGLE_PAY")) {
                            user.setPayment_method(Payment.GOOGLE_PAY);
                        } else if (value.equals("PAYPAL")) {
                            user.setPayment_method(Payment.PAYPAL);
                        } else {
                            user.setPayment_method(Payment.CREDIT_CARD);
                        }
                        break;
                    default:
                        break;
                }
            }
            user.setUsername(user.getFirstName() + " " + user.getLastName());
            userRepository.save(user);
            return new ResponseEntity<>(id, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    /**
     * check Movie/series with id in favorites list of user with userId exist in
     * MongoDB. This only works if the currently authenticated user is the owner of
     * the account.
     *
     * @param userId of the user account
     * @param id     of the user account that should be changed.
     * @return boolean true or false
     */
    public Boolean checkIfInFavorites(String userId, String id) {
        User user = findMongoUser(userId);
        return user.getFavorite().contains(id);
    }

    /**
     * Delete Movie/Series with id from favorites list of user with userId in
     * MongoDB. This only works if the currently authenticated user is the owner of
     * the account.
     *
     * @param userId of the user account
     * @param id     of the user account that should be changed.
     * @return boolean true or false
     */
    public ResponseEntity<String> deleteAusFavorite(String userId, String id) {

        User user = findMongoUser(userId);

        if (user.getFavorite().contains(id)) {
            user.getFavorite().remove(id);
            userRepository.save(user);
            return new ResponseEntity<>(id, HttpStatus.OK);
        }

        return new ResponseEntity<>("Movie/Series " + id +
                " not found in favorites list.", HttpStatus.NOT_FOUND);
    }

    /**
     * give a list of favorite Movies for user with userId in MongoDB. This only
     * works if the currently authenticated user is the owner of the account.
     * 
     * @param userId of the user account
     * @return List<Movie> with movie
     */
    public List<Movie> getAllFavoriteMovies(String userId) {
        User user = findMongoUser(userId);
        return (List<Movie>) movieRepository.findAllById(user.getFavorite());
    }
    /**
     * give a list of favorite Series for user with userId in MongoDB. This only
     * works if the currently authenticated user is the owner of the account.
     *
     * @param userId of the user account
     * @return List<Serie> with movie
     */
    public List<Serie> getAllFavoriteSeries(String userId) {
        User user = findMongoUser(userId);
        return (List<Serie>) serieRepository.findAllById(user.getFavorite());
    }

    /**
     * put Movie with id to the favorites list of user with userId in Mongo DB.
     * This only works if the currently authenticated user is the owner of the
     * account without duplicate!
     * 
     * @param userId of the user account
     * @param Id     of the user account that should be changed.
     * @return HTTP response. OK if the user account was changed, else FORBIDDEN.
     */
    public ResponseEntity<String> putInFavorite(String userId, String Id) {
        User user = findMongoUser(userId);
        if (user.getFavorite().contains(Id)) {
            return new ResponseEntity<>(Id, HttpStatus.FOUND);
        }
        user.getFavorite().add(Id);
        userRepository.save(user);

        return new ResponseEntity<>(Id, HttpStatus.OK);
    }

    /**
     * find user from Mongo DB. This only works if the currently authenticated user
     * is the owner of the account. it works if user exists
     * 
     * @param userId of the user account that should be changed.
     * @return User
     */
    private User findMongoUser(String userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
    }

    /**
     * find all users in Mongo DB. give a list back
     * 
     * @return User List<User>
     */
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }
}
