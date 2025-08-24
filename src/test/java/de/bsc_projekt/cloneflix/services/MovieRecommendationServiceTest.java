package de.bsc_projekt.cloneflix.services;

import de.bsc_projekt.cloneflix.Models.FSK;
import de.bsc_projekt.cloneflix.Models.Movie;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class MovieRecommendationServiceTest
{
    @Autowired
    MovieService movieService;

    @Autowired
    MovieRecommendationService movieRecommendationService;

    @Test
    void getTopTwentyFavoriteMovie()
    {
        ArrayList<Movie> list = movieRecommendationService.getTopTwentyFavoriteMovie();
        for (Movie s:list)
        {
            Assert.isTrue(!s.getPosterPath().isEmpty(),"PosterPath is not null");
        }

    }
}