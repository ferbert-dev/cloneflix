package de.bsc_projekt.cloneflix.Repositories.Neo4J;

import de.bsc_projekt.cloneflix.Models.Neo4JModels.MovieNeo;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

import java.util.ArrayList;

/**
 *Repository for users. Specifies how user information can be retrieved from
 * the Neo4j instance.
 * @version 2.0
 * @since   2021-07-15
 */
public interface MovieRecommendationRepository extends Neo4jRepository<MovieNeo, String> {

    @Query("MATCH (g2:Genre)<-[:IN_Genre]-(m:Movie)<-[:WATCHED]-(u:User{mongoUserId: $userId})\n"
            + "MATCH (g2)<-[:IN_Genre]-(movies:Movie)\n"
            + "WITH collect(movies) as movies, collect(m) as m\n"
            + "RETURN [x in movies WHERE not(x in m)]\n")
    ArrayList<MovieNeo> getMoviesRecommendations(String userId);

    @Query("MATCH (m:Movie) \n"
            + "WHERE (m.watched-m.liked)<=(m.watched/2) AND m.watched>0 \n"
            + "RETURN m ORDER BY m.liked DESC LIMIT 20")
    ArrayList<MovieNeo> getTopTwentyFavoriteMovie();

    @Query("MATCH(m:Movie{mongoMovieId: $movieId}) DETACH DELETE m")
    void deleteNode(String movieId);

}

