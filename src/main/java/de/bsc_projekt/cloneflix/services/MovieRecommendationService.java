package de.bsc_projekt.cloneflix.services;

import de.bsc_projekt.cloneflix.Exception.ResourceNotFoundException;
import de.bsc_projekt.cloneflix.Exception.UserNotFoundException;
import de.bsc_projekt.cloneflix.Models.Movie;
import de.bsc_projekt.cloneflix.Models.Neo4JModels.MovieNeo;
import de.bsc_projekt.cloneflix.Models.Neo4JModels.UserNeo;
import de.bsc_projekt.cloneflix.Repositories.MongoDB.MovieRepository;
import de.bsc_projekt.cloneflix.Repositories.Neo4J.MovieRecommendationRepository;
import de.bsc_projekt.cloneflix.Repositories.Neo4J.UserRecommendationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * The MovieRecommendationService
 * to handle business logic
 *
 * @version 5.0
 * @since   2021-08-2
 */
@Service
@AllArgsConstructor
public class MovieRecommendationService
{
    private final MovieRepository movieRepository;
    private final MovieRecommendationRepository movieRecommendationRepository;
    private final UserRecommendationRepository userRecommendationRepository;


    public ArrayList<Movie> getTopTwentyFavoriteMovie(){
       return converterNeoMongoMovie(movieRecommendationRepository.getTopTwentyFavoriteMovie());
    }

    /**
     *Converter changes the neoMovie to mongoMovie and give the changed list back
     *
     * @param neoMovies list of movies Neo4j.
     * @return mongoMovies list of movies MongoDB.
     */
    private ArrayList<Movie> converterNeoMongoMovie(ArrayList <MovieNeo> neoMovies){

        ArrayList <Movie> mongoMovies = new ArrayList<>();
        for (MovieNeo m : neoMovies){
                mongoMovies.add(movieRepository.findById(m.getMongoMovieId())
                        .orElseThrow(ResourceNotFoundException::new));

                if(mongoMovies.size()>=20){
                    return mongoMovies;
                }
        }
        return mongoMovies;
    }

    /** AddUser creates new user in Neo4je, with id from MongoDB
     * to make search simple. It will be checking if user exist.
     * without duplicate
     * @param userId user id in MongoDB.
     * @param name user name in MongoDB.
     */
    public void addUser(String userId,String name) {
        if(userRecommendationRepository.findById(userId).isEmpty())
        {
            UserNeo userNeo = new UserNeo(userId,name);
            userRecommendationRepository.save(userNeo);
        }
    }

    /** AddMovie creates new Movie in Neo4je, with id from MongoDB
     * to make search simple. It will be checking if movie exist.
     * without duplicate
     * @param movieId movie id in MongoDB.
     * @param title movie title in MongoDB.
     */
    public void addMovie(String movieId, String title)
    {
        if(movieRecommendationRepository.findById(movieId).isEmpty()){
            MovieNeo movieNeo = new MovieNeo(movieId,title);
            movieRecommendationRepository.save(movieNeo);
        }
    }

    /**
     * This method deletes a movie Node that is saved in Neo4j
     * @param movieId of the movie that should be deleted.
     */
    public void deleteMovie(String movieId){
        if(movieRecommendationRepository.findById(movieId).isPresent()){
            movieRecommendationRepository.deleteNode(movieId);
        }
    }

    /** This method gives the list of movies that user liked
     * @param userId user id in MongoDB.
     * @return mongoMovies list of movies from MongoDB.
     */
    public ArrayList <Movie> getLikedMovies(String userId) {

        UserNeo newUserNeo = userRecommendationRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        return converterNeoMongoMovie(newUserNeo.getLikedMovieNeos());
    }

    /** This method gives the list of movie that user watched
     * @param userId user id in MongoDB.
     * @return mongoMovies list of movies from MongoDB.
     */
    public ArrayList<Movie> getWatchedMovies(String userId) {

        UserNeo UserNeo = userRecommendationRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        return converterNeoMongoMovie(UserNeo.getWatchedMovieNeos());
    }

    /** This method gives the list of movie that are recommended user with id,
     * with use of a special algorithm in Neo4j.
     * @param userId user id in MongoDB.
     *@return mongoMovies list of movies from MongoDB.
     */
    public  ArrayList<Movie> getMoviesRecommendations(String userId) {
        return converterNeoMongoMovie(movieRecommendationRepository.getMoviesRecommendations(userId));
    }

    /** This method creates new relationship Watched in Neo4j with user and movie,
     * if user watched the movie
     * This relationship will be created only once
     * with use special algorithm in Neo4j
     * without duplicate
     * @param userId user id in MongoDB.
     * @param movieId movie id in MongoDB.
     */
    public void createRelationshipWatched(String userId, String movieId){
        incrementWatched(userId, movieId);
        userRecommendationRepository.createRelationshipWatched(userId,movieId);
    }

    /** This method creates new relationship liked in Neo4j with user and movie,
     * This relationship will be created only once
     * with use special algorithm in Neo4j
     * @param userId user id in MongoDB.
     * @param movieId movie id in MongoDB.
     */
    public void createRelationshipLiked(String userId, String movieId){
        incrementLiked(userId, movieId);
        userRecommendationRepository.createRelationshipLiked(userId,movieId);
    }
    /** This method removes exists relationship liked from Neo4j,
     * with use special algorithm in Neo4je
     * @param userId user id in MongoDB.
     * @param movieId movie id in MongoDB.
     */
    public void removeRelationshipLiked(String userId, String movieId){
        decrementLiked(userId,movieId);
        userRecommendationRepository.removeRelationshipLiked(userId,movieId);
    }

    /** This method adds one to param watched to movie node in Neo4j,
     * if relationship watched does not exist
     * @param userId user id in MongoDB.
     * @param movieId movie id in MongoDB.
     */
    private void incrementWatched(String userId, String movieId){
        MovieNeo movieNeo = movieRecommendationRepository.findById(movieId)
                .orElseThrow(() -> new UserNotFoundException(movieId));

        if(!existRelationship(userId , movieId,"WATCHED"))
        {
            movieNeo.setWatchscount(movieNeo.getWatchscount() + 1);
        }

        movieRecommendationRepository.save(movieNeo);
    }

    /** This method adds one to param liked to movie node in Neo4j,
     * if relationship liked does not exist else it will be not changed
     * @param userId user id in MongoDB.
     * @param movieId movie id in MongoDB.
     */
    public void incrementLiked(String userId, String movieId){
        MovieNeo movieNeo = movieRecommendationRepository.findById(movieId)
                .orElseThrow(() -> new UserNotFoundException(movieId));

       if (!existRelationship(userId,movieId,"LIKED")){
            movieNeo.setLikecount(movieNeo.getLikecount() + 1);
        }
        movieRecommendationRepository.save(movieNeo);
    }

    /** This method removes one to param liked to movie node in Neo4j,
     * if relationship liked exists else it will be not changed
     * @param userId user id in MongoDB.
     * @param movieId movie id in MongoDB.
     */
    public void decrementLiked(String userId, String movieId){
        MovieNeo movieNeo = movieRecommendationRepository.findById(movieId)
                .orElseThrow(() -> new UserNotFoundException(movieId));
       if (movieNeo.getLikecount() >= 1 && existRelationship(userId,movieId,"LIKED" )){
            movieNeo.setLikecount(movieNeo.getLikecount() - 1);
        }
        movieRecommendationRepository.save(movieNeo);
    }
    /** This method check if some relationship exists between a movie and a user
     * @param userId user id in MongoDB.
     * @param movieId movie id in MongoDB.
     * @param relation relationship id in MongoDB.
     * @return true if exists or false
     */
    private boolean existRelationship(String userId, String movieId, String relation){
       return userRecommendationRepository.existRelationship(userId,movieId).contains(relation);
    }
}
