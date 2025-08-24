package de.bsc_projekt.cloneflix;

import de.bsc_projekt.cloneflix.Models.Movie;
import de.bsc_projekt.cloneflix.Models.Neo4JModels.GenreNeo;
import de.bsc_projekt.cloneflix.Models.Serie;
import de.bsc_projekt.cloneflix.Models.User;
import de.bsc_projekt.cloneflix.Repositories.MongoDB.MovieRepository;
import de.bsc_projekt.cloneflix.Repositories.MongoDB.SerieRepository;
import de.bsc_projekt.cloneflix.Repositories.Neo4J.GenreRecommendationRepository;
import de.bsc_projekt.cloneflix.services.MovieRecommendationService;
import de.bsc_projekt.cloneflix.services.MovieSerieServiceElastic;
import de.bsc_projekt.cloneflix.services.SeriesRecommendationService;
import de.bsc_projekt.cloneflix.services.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import java.util.ArrayList;
import java.util.Arrays;

/**
 * The LoadDatabaseMovie is created to load the data from MongoDB to neo4j (and
 * Elasticsearch)
 * @version 5.0
 * @since 2021-08-10
 */
@Configuration
public class LoadDatabaseMovie {

    @Bean
    CommandLineRunner initDatabase(GenreRecommendationRepository genreRecommendationRepository,
            MovieRepository movieRepository, SerieRepository serieRepository,
            MovieRecommendationService movieRecommendationService,
            SeriesRecommendationService seriesRecommendationService, UserService userService,
            MovieSerieServiceElastic movieSerieServiceElastic) {

        return args -> {

            ArrayList<Movie> moviesList = (ArrayList) movieRepository.findAll();

            System.out.println("movies: " + moviesList.size());

            uploadElasticsearch(moviesList, movieSerieServiceElastic);

            uploadNeo4j(moviesList,movieRecommendationService);

            ArrayList<User> userList1 = (ArrayList) userService.findAllUsers();


            System.out.println("users: " + userList1.size());
            for (int j = 0; j <= userList1.size() - 1; j++) {
                User user = userList1.get(j);
                movieRecommendationService.addUser(user.getId(), user.getUsername());
            }

            ArrayList<Serie> serieList = (ArrayList) serieRepository.findAll();
            System.out.println("series: " + serieList.size());
            for (int i = 0; i <= serieList.size() - 1; i++) {
                Serie serie = serieList.get(i);

                seriesRecommendationService.addSeries(serie.getId(), serie.getTitle());

               // movieSerieServiceElastic.deleteMovieSerie(serie.getId());

               movieSerieServiceElastic.addMovieSerie(serie.getId(), serie.getTitle(), serie.getGenres(),
                       serie.getStarring(), serie.getPosterPath(), "serie");
            }

            String[] genreList = new String[] { "Action", "Comedy", "Drama", "Thriller", "Romance", "Documentation",
                    "Science Fiction", "Horror", "Fantasy", "Animation", "Western", "Historical", "Crime", "Reality" };

            System.out.println("genres : " + genreList.length);

            for (int j = 0; j <= genreList.length - 1; j++) {
                if (genreRecommendationRepository.findById(genreList[j]).isEmpty()) {
                    GenreNeo genreNeo = new GenreNeo(genreList[j]);
                    genreRecommendationRepository.save(genreNeo);
                }
            }

            for (int j = 0; j <= moviesList.size() - 1; j++) {
                Movie movie = moviesList.get(j);
                String[] genre = movie.getGenres();
                System.out.println("movie: " + movie.getTitle() + "Genre : " + Arrays.toString(genre));
                for (String g : genre) {
                    genreRecommendationRepository.createMovieRelationshipInGenre(g, movie.getId());
                }
            }

            for (int j = 0; j <= serieList.size() - 1; j++) {
                Serie serie = serieList.get(j);
                String[] genre = serie.getGenres();
                System.out.println("series: " + serie.getTitle() + "Genre : " + Arrays.toString(genre));
                for (String g : genre) {
                    genreRecommendationRepository.createSeriesRelationshipInGenre(g, serie.getId());
                }
            }
            System.out.println("Finish!");
        };
    }

    void uploadElasticsearch(ArrayList<Movie> moviesList, MovieSerieServiceElastic movieSerieElasticService){
        for (int i = 0; i <= moviesList.size() - 1; i++) {
            Movie movie = moviesList.get(i);

           movieSerieElasticService.addMovieSerie(movie.getId(), movie.getTitle(), movie.getGenres(),
            movie.getStarring(), movie.getPosterPath(), "movie");

            //movieSerieElasticService.deleteMovieSerie(movie.getId());

        }
    }
    void uploadNeo4j(ArrayList<Movie> moviesList,  MovieRecommendationService  movieRecommendationService){
        for (int i = 0; i <= moviesList.size() - 1; i++)
        {
            Movie movie = moviesList.get(i);
            movieRecommendationService.addMovie(movie.getId(), movie.getTitle());
        }

    }

}
