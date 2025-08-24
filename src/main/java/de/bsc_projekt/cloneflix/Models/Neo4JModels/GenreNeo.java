package de.bsc_projekt.cloneflix.Models.Neo4JModels;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
/**
 * The GenreNeo models for Neo4j
 *
 * @version 5.0
 * @since   2021-07-15
 */
@Setter
@Getter
@Node("Genre")
@AllArgsConstructor
public class GenreNeo
{
    @Id
    private final String id;

}
