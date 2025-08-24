package de.bsc_projekt.cloneflix.services;

import de.bsc_projekt.cloneflix.Models.FSK;
import de.bsc_projekt.cloneflix.Models.Movie;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.HashMap;

@SpringBootTest
class MovieServiceTest {

    @Autowired
    MovieService movieService;

    @Test
    void updateMovie() {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("fsk", "SIX");

        movieService.updateMovie("611aada5c1b9f08dc105ec60", hashMap);
        Movie movie = movieService.getMovieById("611aada5c1b9f08dc105ec60");
        Assert.isTrue(movie.getFsk().equals(FSK.SIX), "updateMovie funktioniert");
    }

    @Test
    void delete() {
        String[] strings = new String[] { "a", "b" };
        Date date = new Date();
        Movie movie = new Movie("Test", 100, strings, strings, strings, strings, strings, "Description", FSK.ZERO, date,
                8.8, date, "poster", "backdrop");
        movieService.addMovie(movie);
        movieService.deleteMovieById(movie.getId());
        Assert.isTrue(false, "OK");
    }
}