package de.bsc_projekt.cloneflix;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
@EnableNeo4jRepositories(basePackages = "de.bsc_projekt.cloneflix.Repositories.Neo4J")
@EnableMongoRepositories(basePackages = "de.bsc_projekt.cloneflix.Repositories.MongoDB")
@SpringBootApplication
public class CloneflixApplication {

	public static void main(String[] args) {
		SpringApplication.run(CloneflixApplication.class, args);
	}
}
