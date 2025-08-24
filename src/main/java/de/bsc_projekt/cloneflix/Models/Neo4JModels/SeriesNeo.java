package de.bsc_projekt.cloneflix.Models.Neo4JModels;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;
/**
 * The SeriesNeo models for Neo4j
 *
 * @version 5.0
 * @since   2021-07-20
 */
@Setter
@Getter
@Node("Series")
public class SeriesNeo {

    @Id // Id aus mongoDB
    private final String mongoSeriesId;

    @Property("title")
    private final String title;

    @Property("watched") // number of views in the Neo4j
    private long watchscount; // in the Model

    @Property("liked") // number of like in the Neo4j
    private long likecount; //  in the Model

    public SeriesNeo(String mongoSeriesId, String title) {
        this.mongoSeriesId = mongoSeriesId;
        this.title =title;
        this.watchscount = 0;
        this.likecount = 0;
    }
}