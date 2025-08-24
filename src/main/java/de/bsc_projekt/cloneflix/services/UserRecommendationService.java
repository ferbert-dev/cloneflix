package de.bsc_projekt.cloneflix.services;

import de.bsc_projekt.cloneflix.Repositories.Neo4J.UserRecommendationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
/**
 * The UserRecommendationService
 * to handle business logic
 *
 * @version 5.0
 * @since   2021-08-2
 */
@Service
@AllArgsConstructor
public class UserRecommendationService
{
    private final UserRecommendationRepository userRecommendationRepository;

    /**
     * this method check if the relationship Liked exists
     * This only works if the currently authenticated user is the owner of the account.
     * @param userId of the user account that should be checked.
     * @param movieId of the movie or series that should be checked.
     * @return boolean true if exist or false
     */
    public boolean isLiked (String userId, String movieId){
        return existRelationship(userId,movieId, "LIKED");
    }
    /**
     * This method check if the relationship exists
     * This only works if the currently authenticated user is the owner of the account.
     * @param userId of the user account that should be checked.
     * @param id of the movie or series that should be checked.
     * @param relation witch  shod be checked
     * @return boolean true if exist or false
     */
    public boolean existRelationship(String userId, String id,String relation){
    return userRecommendationRepository.existRelationship(userId,id).contains(relation);
    }
}
