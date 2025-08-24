package de.bsc_projekt.cloneflix.Controller;

import de.bsc_projekt.cloneflix.Models.Movie;
import de.bsc_projekt.cloneflix.Models.Serie;
import de.bsc_projekt.cloneflix.registration.security.services.UserDetailsImpl;
import de.bsc_projekt.cloneflix.registration.security.utils.IAuthenticationFacade;
import de.bsc_projekt.cloneflix.services.MovieRecommendationService;
import de.bsc_projekt.cloneflix.services.SeriesRecommendationService;
import de.bsc_projekt.cloneflix.services.UserRecommendationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("api/v1/recommendations")
public class RecommendationsController {

    private final IAuthenticationFacade authenticationFacade;

    private final MovieRecommendationService movieRecommendationService;

    private final SeriesRecommendationService seriesRecommendationService;

    private final UserRecommendationService userRecommendationService;

    public RecommendationsController(IAuthenticationFacade authenticationFacade,
            MovieRecommendationService movieRecommendationService,
            SeriesRecommendationService seriesRecommendationService,
            UserRecommendationService userRecommendationService) {
        this.authenticationFacade = authenticationFacade;
        this.movieRecommendationService = movieRecommendationService;
        this.seriesRecommendationService = seriesRecommendationService;
        this.userRecommendationService = userRecommendationService;
    }

    @GetMapping(value = "/watchedMovies")
    public ResponseEntity<Object> getWatchedMovies() {
        return checkUserResponseMovie(movieRecommendationService.getWatchedMovies(getCurrentUserID()));
    }

    @GetMapping(value = "/likedMovies")
    public ResponseEntity<Object> getLikedMovies() {
        return checkUserResponseMovie(movieRecommendationService.getLikedMovies(getCurrentUserID()));
    }

    @GetMapping(value = "{id}/isLiked")
    public boolean getLiked(@PathVariable String id) {
        return userRecommendationService.isLiked(getCurrentUserID(), id);
    }

    @GetMapping(value = "/watchedSeries")
    public ResponseEntity<Object> getWatchedSeries() {
        return checkUserResponseSeries(seriesRecommendationService.getWatchedSeries(getCurrentUserID()));
    }

    @GetMapping(value = "/likedSeries")
    public ResponseEntity<Object> getLikedSeries() {
        return checkUserResponseSeries(seriesRecommendationService.getLikedSeries(getCurrentUserID()));
    }

    @GetMapping(value = "/movies")
    public ResponseEntity<Object> getMoviesRecommendations() {
        String userID = getCurrentUserID();
        return checkUserResponseMovie(movieRecommendationService.getMoviesRecommendations(userID));
    }

    @GetMapping(value = "/series")
    public ResponseEntity<Object> getSeriesRecommendations() {
        String userID = getCurrentUserID();
        return checkUserResponseSeries(seriesRecommendationService.getSeriesRecommendations(userID));
    }

    @PutMapping(value = "/{movieID}/createWatchedMovie")
    public ResponseEntity<Object> createRelationshipWatchedMovie(@PathVariable String movieID) {
        String userID = getCurrentUserID();
        movieRecommendationService.createRelationshipWatched(userID, movieID);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(value = "/{movieID}/createLikedMovie")
    public ResponseEntity<Object> createRelationshipLikedMovie(@PathVariable String movieID) {
        String userID = getCurrentUserID();
        movieRecommendationService.createRelationshipLiked(userID, movieID);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(value = "/{seriesID}/createWatchedSeries")
    public ResponseEntity<Object> createRelationshipWatchedSeries(@PathVariable String seriesID) {
        String userID = getCurrentUserID();
        seriesRecommendationService.createRelationshipWatched(userID, seriesID);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(value = "/{seriesID}/createLikedSeries")
    public ResponseEntity<Object> createRelationshipLikedSeries(@PathVariable String seriesID) {
        String userID = getCurrentUserID();
        seriesRecommendationService.createRelationshipLiked(userID, seriesID);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/getTopFavoriteMovies")
    public ResponseEntity<Object> getTopTwentyFavoriteMovie() {
        return checkUserResponseMovie(movieRecommendationService.getTopTwentyFavoriteMovie());
    }

    @GetMapping(value = "/getTopFavoriteSeries")
    public ResponseEntity<Object> getTopTwentyFavouriteSeries() {
        return checkUserResponseSeries(seriesRecommendationService.getTopTwentyFavoriteSeries());
    }

    @DeleteMapping(value = "/{movieID}/deleteLikedMovies")
    public ResponseEntity<Object> removeRelationshipLikedMovie(@PathVariable String movieID) {
        String userID = getCurrentUserID();
        movieRecommendationService.removeRelationshipLiked(userID, movieID);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value = "/{seriesID}/deleteLikedSeries")
    public ResponseEntity<Object> removeRelationshipLikedSeries(@PathVariable String seriesID) {
        String userID = getCurrentUserID();
        seriesRecommendationService.removeRelationshipLiked(userID, seriesID);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private ResponseEntity<Object> checkUserResponseMovie(ArrayList<Movie> list) {
        String userId = getCurrentUserID();
        if (userId != null) {
            return new ResponseEntity<>(list, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("You are not authorized", HttpStatus.FORBIDDEN);
        }
    }

    private ResponseEntity<Object> checkUserResponseSeries(ArrayList<Serie> list) {
        String userId = getCurrentUserID();
        if (userId != null) {
            return new ResponseEntity<>(list, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("You are not authorized", HttpStatus.FORBIDDEN);
        }
    }

    private String getCurrentUserID() {
        Authentication authentication = authenticationFacade.getAuthentication();
        UserDetailsImpl user = (UserDetailsImpl) authentication.getPrincipal();
        return user.getId();
    }

}
