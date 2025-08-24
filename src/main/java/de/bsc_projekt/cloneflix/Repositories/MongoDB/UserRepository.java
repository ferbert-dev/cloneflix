package de.bsc_projekt.cloneflix.Repositories.MongoDB;

import de.bsc_projekt.cloneflix.Models.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import java.util.Optional;

/**
 *Repository for users. Specifies how user information can be retrieved from
 * the mongoDB instance.
 *
 * @version 2.0
 * @since   2021-06-01
 */

public interface UserRepository extends MongoRepository<User, String> {

    /**
     * Retrieves a user by its email.
     * 
     * @param email of the user
     * @return User the user found by the email
     */
    Optional <User> findByEmail(@Param("email") String email);

    Boolean existsByEmail(String email);

    /**
     * Retrieves a user by its id.
     * 
     * @param id of the user
     * @return the user found by the id
     */
    Optional<User> findById(@Param("id") String id);
}
