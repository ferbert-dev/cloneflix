package de.bsc_projekt.cloneflix.services;

import de.bsc_projekt.cloneflix.Exception.ResourceNotFoundException;
import de.bsc_projekt.cloneflix.Models.FSK;
import de.bsc_projekt.cloneflix.Models.Movie;
import de.bsc_projekt.cloneflix.ProjectionDTOs.TitlePreview;
import de.bsc_projekt.cloneflix.Repositories.MongoDB.MovieRepository;
import lombok.AllArgsConstructor;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The MovieService to handle business logic
 * 
 * @version 5.0
 * @since 2021-09
 */
@Service
@AllArgsConstructor
public class MovieService {

    private final MovieRepository movieRepository;
    private final MovieRecommendationService movieRecommendationService;

    /**
     * This method returns a movie by its ID.
     * 
     * @param id of the movie that should be returned.
     * @return Movie from MongoDB.
     */
    public Movie getMovieById(String id) {
        return movieRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
    }

    /**
     * This method Adds a movie in MongoDB and creates a movie node in Neo4j.
     * 
     * @param movie that should be added.
     * @return HTTP response. CREATED if the movie was successfully added, else
     *         FORBIDDEN.
     */
    public ResponseEntity<Object> addMovie(Movie movie) {

        if (movie.getId() != null && movie.getTitle() != null) {
            movieRepository.save(movie);
            movieRecommendationService.addMovie(movie.getId(), movie.getTitle());
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    /**
     * This method returns all movies from MongoDB.
     * 
     * @return mongoMovies list of movies from MongoDB.
     */
    public List<Movie> getAll(int page) {
        Pageable pageable = PageRequest.of(page, 12);
        return movieRepository.findAll(pageable).getContent();
    }

    /**
     * Updates a movie that is saved in MongoDB.
     * 
     * @param id           of the movie that should be updated.
     * @param updatedMovie ist a Hashmap or JSON Document with details to change the
     *                     movie fields.
     * @return HTTP response. OK if the movie was successfully updated, else
     *         FORBIDDEN.
     */
    public ResponseEntity<Object> updateMovie(String id, HashMap<String, Object> updatedMovie) {
        Movie movie = getMovieById(id);

        for (Map.Entry<String, Object> entry : updatedMovie.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();

            switch (key) {
                case "title":
                    movie.setTitle((String) value);
                    break;
                case "directors":
                    movie.setDirectors((String[]) value);
                    break;
                case "producers":
                    movie.setProducers((String[]) value);
                    break;
                case "starring":
                    movie.setStarring((String[]) value);
                    break;
                case "genres":
                    movie.setGenres((String[]) value);
                    break;
                case "description":
                    movie.setDescription((String) value);
                    break;
                case "fsk":
                    if (value.equals("ZERO")) {
                        movie.setFsk(FSK.ZERO);
                    } else if (value.equals("SIX")) {
                        movie.setFsk(FSK.SIX);
                    } else if (value.equals("TWELVE")) {
                        movie.setFsk(FSK.TWELVE);
                    } else if (value.equals("SIXTEEN")) {
                        movie.setFsk(FSK.SIXTEEN);
                    } else {
                        movie.setFsk(FSK.EIGHTEEN);
                    }
                    break;
                case "releaseDate":
                    movie.setReleaseDate((Date) value);
                    break;
                case "cloneflixDate":
                    movie.setCloneflixDate((Date) value);
                    break;
                case "posterPath":
                    movie.setPosterPath((String) value);
                    break;
                default:
                    break;
            }
        }
        movieRepository.save(movie);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * This method deletes a movie that is saved in MongoDB and the Corresponding
     * Node in Neo4j
     * 
     * @param id of the movie that should be deleted.
     * @return true if the movie was successfully deleted, else false.
     */
    public boolean deleteMovieById(String id) {
        getMovieById(id);
        movieRepository.deleteById(id);
        movieRecommendationService.deleteMovie(id);
        return movieRepository.findById(id).isEmpty();
    }

    /**
     * This method returns a list of movies from a specific genre.
     * 
     * @param genre of the movies that should be returned.
     * @return List of movies that contain ids, titles and poster paths.
     */
    public List<TitlePreview> getMovieTileInfoForGenres(String genre) {
        return movieRepository.findByGenresContaining(genre, TitlePreview.class);
    }

    /**
     * This method returns a rating of a specific movie by giving its ID.
     * 
     * @param id of the movie.
     * @return rating as a double value.
     */
    public double getRating(String id) {
        Movie movie = getMovieById(id);
        return movie.getRating();
    }

}
