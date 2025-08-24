package de.bsc_projekt.cloneflix.services;

import de.bsc_projekt.cloneflix.Exception.ResourceNotFoundException;
import de.bsc_projekt.cloneflix.Models.Season;
import de.bsc_projekt.cloneflix.Models.Serie;
import de.bsc_projekt.cloneflix.ProjectionDTOs.TitlePreview;
import de.bsc_projekt.cloneflix.Repositories.MongoDB.SerieRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PutMapping;
import java.util.*;
/**
 * The SeriesService
 * to handle business logic
 * @version 5.0
 * @since   2021-08-2
 */
@Service
@AllArgsConstructor
public class SeriesService {

    private final SerieRepository serieRepository;
    private final SeriesRecommendationService seriesRecommendationService;

    /**
     * This method returns a seres by its ID.
     * @param id of the series that should be returned.
     * @return series from MongoDB.
     */
    public Serie getOne(String id) {
        return serieRepository.findById(id).orElseThrow(
                ResourceNotFoundException::new);
    }

    /**
     * This method Adds a series in MongoDB and creates a series node in Neo4j.
     * @param series that should be added.
     * @return HTTP response. CREATED if the series was successfully added,
     * else FORBIDDEN.
     */
    public ResponseEntity<Object> addSeries(Serie series) {

        if(series.getId()!=null&&series.getTitle()!=null){
            serieRepository.save(series);
            seriesRecommendationService.addSeries(
                    series.getId(),
                    series.getTitle());
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    /**
     * This method returns all series from MongoDB.
     * @return mongoSeries list of series from MongoDB.
     */
    public List<Serie> getAll() {
        return serieRepository.findAll();
    }

    /**
     * Updates a series that is saved in MongoDB.
     * @param id of the series that should be updated.
     * @param updatedSeries ist a Hashmap or JSON Document with details to change the series fields.
     * @return HTTP response. OK if the series was successfully updated, else FORBIDDEN.
     */
    @PutMapping(value = "/{id}")
    public ResponseEntity<Object> updateSeries(String id, HashMap<String, Object> updatedSeries) {
        Serie serie = getOne(id);

        for (Map.Entry<String, Object> entry : updatedSeries.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();

            switch (key) {
                case "title":
                    serie.setTitle((String) value);
                    break;
                case "starring":
                    serie.setStarring((String[]) value);
                    break;
                case "genres":
                    serie.setGenres((String[]) value);
                    break;
                case "description":
                    serie.setDescription((String) value);
                    break;
                case "cloneflixDate":
                    serie.setCloneflixDate((Date) value);
                    break;
                case "posterPath":
                    serie.setPosterPath((String) value);
                    break;
                case "voteAverage":
                    serie.setVoteAverage((double) value);
                    break;
                case "numberOfSeasons":
                    serie.setNumberOfSeasons((int) value);
                    break;
                case "seasons":
                    serie.setSeasons((ArrayList<Season>) value);
                    break;
                default:
                    break;
            }
        }
        serieRepository.save(serie);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * This method deletes a series that is saved in MongoDB and the Corresponding Node in Neo4j
     * @param id of the series that should be deleted.
     * @return true if the series was successfully deleted, else false.
     */
    public boolean deleteSeriesById(String id) {
        getOne(id);
        serieRepository.deleteById(id);
        seriesRecommendationService.deleteSeries(id);
        return serieRepository.findById(id).isEmpty();
    }

    /**
     * This method returns a list of series from a specific genre.
     * @param genre of the series that should be returned.
     * @return List of series that contain ids, titles and poster paths.
     */
    public List<TitlePreview> getSeriesTileInfoForGenres(String genre) {
        return serieRepository.findByGenresContaining(genre, TitlePreview.class);
    }

    /**
     * This method returns a list of series from a specific genre.
     * @param genre of the series that should be returned.
     * @return List of series that contain ids, titles and poster paths.
     */
    public List<TitlePreview> getSeriesByGenres(String genre) {
        return serieRepository.findByGenresContaining(genre, TitlePreview.class);
    }

    /**
     * This method adds a season to a series.
     * @param id of the series that the season should be added to.
     * @param season that should be added.
     * @return HTTP response. ACCEPTED if the season was successfully added, else FORBIDDEN.
     */
    public ResponseEntity<Object> addSeasonToSeries(String id, Season season) {
        Serie serie = getOne(id);
        serie.addSeason(season);
        serieRepository.save(serie);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    /**
     * This method returns season information of a series.
     * @param id of the series that has the season.
     * @param seasonNumber of the series.
     * @return season that we are looking for.
     */
    public Season getSeason(String id, int seasonNumber) {
        Serie serie =  getOne(id);
        if(serie.getNumberOfSeasons() >= seasonNumber){
            return serie.getSeason(seasonNumber);
        }
        return null;
    }
}
