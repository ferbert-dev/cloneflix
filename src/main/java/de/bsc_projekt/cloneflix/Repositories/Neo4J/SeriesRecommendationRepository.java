package de.bsc_projekt.cloneflix.Repositories.Neo4J;

import de.bsc_projekt.cloneflix.Models.Neo4JModels.SeriesNeo;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

import java.util.ArrayList;
/**
 *Repository for users. Specifies how user information can be retrieved from
 * the Neo4j instance.
 * @version 2.0
 * @since   2021-07-18
 */
public interface SeriesRecommendationRepository extends Neo4jRepository<SeriesNeo, String> {

    @Query("MATCH (g:Genre)<-[:IN_Genre]-(s:Series)<-[:WATCHED]-(u:User{mongoUserId: $userId})\n"
            + "MATCH (g)<-[:IN_Genre]-(series:Series)\n"
            + "WITH collect(series) as series, collect(s) as s\n"
            + "RETURN [x in series WHERE not(x in s)]")
    ArrayList<SeriesNeo> getSeriesRecommendations(String userId);

    @Query("MATCH (g:Genre{id: $id}),(m:Movie{mongoSeriesId:$seriesID}) MERGE (g)-[:IN_Genre]->(m)")
    void createRelationship(String id, String seriesID);

    @Query("MATCH (n:Series) \n"
            + "WHERE (n.watched-n.liked)<=(n.watched/2) AND n.watched>0 \n"
            + "RETURN n ORDER BY n.liked DESC LIMIT 20")
    ArrayList<SeriesNeo> getTopTwentyFavoriteSerie();

    @Query("MATCH(s:Series{mongoSeriesId: $seriesId}) DETACH DELETE s")
    void deleteNode(String seriesId);


}
