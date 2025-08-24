package de.bsc_projekt.cloneflix.Repositories.Neo4J;

import de.bsc_projekt.cloneflix.Models.Neo4JModels.UserNeo;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

import java.util.ArrayList;
/**
 *Repository for users. Specifies how user information can be retrieved from
 * the Neo4j instance.
 *
 * @version 2.0
 * @since   2021-07-01
 */
public interface UserRecommendationRepository extends Neo4jRepository<UserNeo, String>
{
    @Query("MATCH (m:Movie{mongoMovieId:$movieID}),(u:User{mongoUserId: $userID}) MERGE (u)-[:WATCHED]->(m)")
    void createRelationshipWatched(String userID, String movieID);

    @Query("MATCH (u:User{mongoUserId: $userID}),(m:Movie{mongoMovieId:$movieID}) MERGE (u)-[:LIKED]->(m)")
    void createRelationshipLiked(String userID, String movieID);

    @Query("MATCH (u:User {mongoUserId:$userID})-[r:LIKED]->(m:Movie{mongoMovieId:$movieID}) DELETE r")
    void removeRelationshipLiked(String userID,String movieID);

    @Query("MATCH (s:Series{mongoSeriesId: $seriesID}),(u:User{mongoUserId: $userID}) MERGE (u)-[:WATCHED]->(s)")
    void createRelationshipWatchedSeries(String userID, String seriesID);

    @Query("MATCH (u:User{mongoUserId: $userID}),(s:Series{mongoSeriesId: $seriesID}) MERGE (u)-[:LIKED]->(s)")
    void createRelationshipLikedSeries(String userID, String seriesID);

    @Query("MATCH (u:User {mongoUserId:$userID})-[r:LIKED]->(s:Series{mongoSeriesId: $seriesID}) DELETE r")
    void removeRelationshipLikedSeries(String userID,String seriesID);

    @Query("MATCH(u:User{mongoUserId: $userID})-[r]->(m:Movie{mongoMovieId: $id}) return type(r)" +
            "UNION"+
            " MATCH(u:User{mongoUserId:$userID})-[r]->(s:Series{mongoSeriesId:$id}) return type(r)")
    ArrayList<String> existRelationship(String userID, String id);

}
