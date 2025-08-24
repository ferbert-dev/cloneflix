package de.bsc_projekt.cloneflix.services;

import de.bsc_projekt.cloneflix.Exception.ResourceNotFoundException;
import de.bsc_projekt.cloneflix.Exception.SeriesNotFoundException;
import de.bsc_projekt.cloneflix.Exception.UserNotFoundException;
import de.bsc_projekt.cloneflix.Models.Neo4JModels.SeriesNeo;
import de.bsc_projekt.cloneflix.Models.Neo4JModels.UserNeo;
import de.bsc_projekt.cloneflix.Models.Serie;
import de.bsc_projekt.cloneflix.Repositories.MongoDB.SerieRepository;
import de.bsc_projekt.cloneflix.Repositories.Neo4J.SeriesRecommendationRepository;
import de.bsc_projekt.cloneflix.Repositories.Neo4J.UserRecommendationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * The SereisRecommendationService
 * to handle business logic
 * @version 5.0
 * @since   2021-08-2
 */
@Service
@AllArgsConstructor
public class SeriesRecommendationService {

    private final SerieRepository seriesRepository;
    private final UserRecommendationService userRecommendationService;
    private final UserRecommendationRepository userRecommendationRepository;
    private final SeriesRecommendationRepository seriesRecommendationsRepository;


    public ArrayList<Serie> getTopTwentyFavoriteSeries(){
        return converterNeoMongoSeries(seriesRecommendationsRepository.getTopTwentyFavoriteSerie());
    }

    /**
     *Converter changes the neoSeries to mongoSeries and give the changed list back
     *
     * @param neoSeries list of movies Neo4j.
     * @return mongoSeries list of movies MongoDB.
     */
    private ArrayList<Serie> converterNeoMongoSeries(ArrayList<SeriesNeo> neoSeries){
        ArrayList<Serie> mongoSeries = new ArrayList<>();
        for (SeriesNeo m: neoSeries){
            if(seriesRepository.findById(m.getMongoSeriesId()).isPresent()){
                mongoSeries.add(seriesRepository.findById(m.getMongoSeriesId()).orElseThrow(
                        ResourceNotFoundException::new));
            }
        }
        return mongoSeries;
    }

    /** addSeries creates new series in Neo4je, with id from MongoDB
     * to make search simple. It will be checking if series exist.
     * without duplicate
     * @param seriesId movie id in MongoDB.
     * @param title movie title in MongoDB.
     */
    public void addSeries(String seriesId, String title){
        if (seriesRecommendationsRepository.findById(seriesId).isEmpty()){
            SeriesNeo seriesNeo = new SeriesNeo(seriesId, title);
            seriesRecommendationsRepository.save(seriesNeo);
        }
    }

    /**
     * This method deletes a series Node that is saved in Neo4j
     * @param seriesId of the series that should be deleted.
     */
    public void deleteSeries(String seriesId){
        if(seriesRecommendationsRepository.findById(seriesId).isPresent()){
            seriesRecommendationsRepository.deleteNode(seriesId);
        }
    }


    /** This method gives the list of series that user liked
     * @param userId user id in MongoDB.
     * @return mongoSeries list of series from MongoDB.
     */
    public ArrayList<Serie> getLikedSeries(String userId) {

        UserNeo newUserNeo = userRecommendationRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        return converterNeoMongoSeries(newUserNeo.getLikedSeriesNeos());
    }

    /** This method gives the list of series that user watched
     * @param userId user id in MongoDB.
     * @return mongoSeries list of series from MongoDB.
     */
    public ArrayList<Serie> getWatchedSeries(String userId) {

        UserNeo UserNeo = userRecommendationRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        return converterNeoMongoSeries(UserNeo.getWatchedSeriesNeos());
    }

    /** This method gives the list of series that are recommended user with id
     * with use of a special algorithm in Neo4j.
     * @param userId user id in MongoDB.
     *@return mongoSeries list of series from MongoDB.
     */
    public ArrayList<Serie> getSeriesRecommendations(String userId) {
        return converterNeoMongoSeries(seriesRecommendationsRepository.getSeriesRecommendations(userId));
    }

    /** This method creates new relationship Watched in Neo4j with user and series,
     * if user watched the series
     * This relationship will be created only once
     * with use special algorithm in Neo4j
     * without duplicate
     * @param userId user id in MongoDB.
     * @param seriesId series id in MongoDB.
     */
    public void createRelationshipWatched(String userId, String seriesId){
        incrementWatched(userId, seriesId);
        userRecommendationRepository.createRelationshipWatchedSeries(userId,seriesId);
    }

    /** This method creates new relationship liked in Neo4j with user and series,
     * This relationship will be created only once
     * with use special algorithm in Neo4je
     * @param userId user id in MongoDB.
     * @param seriesId series id in MongoDB.
     */
    public void createRelationshipLiked(String userId, String seriesId){
        incrementLiked(userId, seriesId);
        userRecommendationRepository.createRelationshipLikedSeries(userId,seriesId);
    }

    /** This method removes exists relationship liked from Neo4j,
     * with use special algorithm in Neo4j
     * @param userId user id in MongoDB.
     * @param seriesId series id in MongoDB.
     */
    public void removeRelationshipLiked(String userId, String seriesId){
        decrementLiked(userId,seriesId);
        userRecommendationRepository.removeRelationshipLikedSeries(userId,seriesId);
    }

    /** This method adds one to param watched to series node in Neo4j,
     * if relationship watched does not exist.
     * @param userId user id in MongoDB.
     * @param seriesId series id in MongoDB.
     */
    public void incrementWatched(String userId, String seriesId){
        SeriesNeo seriesNeo = seriesRecommendationsRepository.findById(seriesId)
                .orElseThrow(() -> new SeriesNotFoundException(seriesId));
        if(!existRelationship(userId,seriesId,"WATCHED")){

            seriesNeo.setWatchscount(seriesNeo.getWatchscount()+1);
        }

        seriesRecommendationsRepository.save(seriesNeo);
    }

    /** This method adds one to param liked to series node in Neo4j,
     * if relationship watched does not exist.
     * @param userId user id in MongoDB.
     * @param seriesId series id in MongoDB.
     */
    public void incrementLiked(String userId, String seriesId){
        SeriesNeo seriesNeo = seriesRecommendationsRepository.findById(seriesId)
                .orElseThrow(() -> new SeriesNotFoundException(seriesId));
        if(!existRelationship(userId,seriesId,"LIKED")){
            seriesNeo.setLikecount(seriesNeo.getLikecount() +1);
        }
        seriesRecommendationsRepository.save(seriesNeo);
    }

    /** This method removes one to param liked to series node in Neo4j,
     * if relationship liked exists else it will be not changed
     * @param userId user id in MongoDB.
     * @param seriesId series id in MongoDB.
     */
    public void decrementLiked(String userId, String seriesId){
        SeriesNeo seriesNeo = seriesRecommendationsRepository.findById(seriesId)
                .orElseThrow(() -> new SeriesNotFoundException(seriesId));
        if(seriesNeo.getLikecount() >= 1 && userRecommendationService
                .existRelationship(userId,seriesId,"LIKED")){
            seriesNeo.setLikecount(seriesNeo.getLikecount() -1);
        }
        seriesRecommendationsRepository.save(seriesNeo);
    }

    /** This method check if some relationship exists between a series and a user
     * @param userId user id in MongoDB.
     * @param seriesId movie id in MongoDB.
     * @param relation relationship id in MongoDB.
     * @return true if exists or false
     */
    private boolean existRelationship(String userId, String seriesId, String relation){
        return userRecommendationRepository.existRelationship(userId,seriesId).contains(relation);
    }

}
