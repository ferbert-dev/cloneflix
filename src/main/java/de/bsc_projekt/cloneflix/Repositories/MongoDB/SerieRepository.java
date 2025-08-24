package de.bsc_projekt.cloneflix.Repositories.MongoDB;

import de.bsc_projekt.cloneflix.Models.Serie;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import java.util.List;
public interface SerieRepository extends MongoRepository<Serie, String> {

    /**
     * Retrieves the tile information for the first ten movies by genre. Different
     * projection DTOs can be created and used as type.
     */
    <T> List<T> findByGenresContaining(@Param("genre") String genre, Class<T> type);
}