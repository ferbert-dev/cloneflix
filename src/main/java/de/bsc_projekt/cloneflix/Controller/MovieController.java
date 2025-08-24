package de.bsc_projekt.cloneflix.Controller;

import com.sun.istack.NotNull;
import de.bsc_projekt.cloneflix.Models.Movie;
import de.bsc_projekt.cloneflix.ProjectionDTOs.TitlePreview;
import de.bsc_projekt.cloneflix.services.MovieService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("api/v1/movies")
@AllArgsConstructor
public class MovieController {

    @Autowired
    private final MovieService movieService;

    @GetMapping(value = "/{id}")
    public Movie getMovieById(@PathVariable String id) {
        return movieService.getMovieById(id);
    }

    @PostMapping
    public ResponseEntity<Object> addMovie(@RequestBody Movie movie) {
        return movieService.addMovie(movie);
    }

    @GetMapping
    public List<Movie> getAll(@RequestParam int page) {
        return movieService.getAll(page);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Object> updateMovie(@NotNull @PathVariable String id,
            @RequestBody HashMap<String, Object> updatedMovie) {
        return movieService.updateMovie(id, updatedMovie);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteMovieById(@PathVariable String id) {
        if (movieService.deleteMovieById(id)) {
            return new ResponseEntity<>("Movie with id : " + id + " is deleted", HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    /**
     * Answers requests with a limited number of movies containing a limited number
     * of properties.
     * 
     * @param genre
     * @return List of movies with {@link TitlePreview} as a projection.
     */
    @GetMapping("/preview/{genre}")
    public List<TitlePreview> getMovieTileInfoForGenres(@PathVariable String genre) {
        return movieService.getMovieTileInfoForGenres(genre);
    }

    @GetMapping(value = "rating/{id}")
    public double getRating(@PathVariable String id) {
        return movieService.getRating(id);
    }

}