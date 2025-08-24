package de.bsc_projekt.cloneflix.Models.Neo4JModels;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;
/**
 * The MovieNeo models for Neo4j
 *
 * @version 5.0
 * @since   2021-07-15
 */
@Setter
@Getter
@Node("Movie")
public class MovieNeo {

    @Id // we take Id from MongoId
    private final String mongoMovieId;

    @Property("title") // name of movie in the Neo4j
    private String title; // in the Model

    @Property("watched") // number of views in the Neo4j
    private long watchscount; // in the Model

    @Property("liked") // number of like in the Neo4j
    private long likecount; //  in the Model

    public MovieNeo(String mongoMovieId, String title) {
        this.mongoMovieId = mongoMovieId;
        this.title = title;
        this.watchscount = 0;
        this.likecount = 0;
    }
}
