package de.bsc_projekt.cloneflix.Models.Neo4JModels;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.ArrayList;
/**
 * The UserNeo models for Neo4j
 *
 * @version 5.0
 * @since   2021-07-15
 */
@Getter
@Setter
@Node("User")
public class UserNeo {

    @Id // UserId from mongoDB
    private String mongoUserId;

    @Property("name") // name of user in the Neo4j
    private String name; // in the Model

    @Relationship(type = "LIKED", direction = Relationship.Direction.OUTGOING)
    private ArrayList<MovieNeo> likedMovieNeos;

    @Relationship(type = "WATCHED", direction = Relationship.Direction.OUTGOING)
    private ArrayList<MovieNeo> watchedMovieNeos;

    @Relationship(type = "LIKED", direction = Relationship.Direction.OUTGOING)
    private ArrayList<SeriesNeo> likedSeriesNeos;

    @Relationship(type = "WATCHED", direction = Relationship.Direction.OUTGOING)
    private ArrayList<SeriesNeo> watchedSeriesNeos;

    public UserNeo(String mongoUserId, String name) {
        this.mongoUserId = mongoUserId;
        this.name=name;
        this.likedMovieNeos = new ArrayList<>();
        this.watchedMovieNeos = new ArrayList<>();
    }

}
