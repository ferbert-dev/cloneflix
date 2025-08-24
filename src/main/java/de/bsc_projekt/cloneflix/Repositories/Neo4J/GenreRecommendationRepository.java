package de.bsc_projekt.cloneflix.Repositories.Neo4J;

import de.bsc_projekt.cloneflix.Models.Neo4JModels.GenreNeo;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import java.util.Optional;
/**
 *Repository for users. Specifies how user information can be retrieved from
 * the Neo4j instance.
 * @author
 * @version 2.0
 * @since   2021-07-15
 */
public interface GenreRecommendationRepository extends Neo4jRepository<GenreNeo, String>
{
    Optional<GenreNeo> findById (String id);

    @Query("MATCH (g:Genre{id: $id}),(m:Movie{mongoMovieId:$movieID}) MERGE (m)-[:IN_Genre]->(g)")
    void createMovieRelationshipInGenre(String id, String movieID);

    @Query("MATCH (g:Genre{id: $id}),(s:Series{mongoSeriesId:$seriesID}) MERGE (s)-[:IN_Genre]->(g)")
    void createSeriesRelationshipInGenre(String id, String seriesID);

}
