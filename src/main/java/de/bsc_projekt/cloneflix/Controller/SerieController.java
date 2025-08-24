package de.bsc_projekt.cloneflix.Controller;

import de.bsc_projekt.cloneflix.Models.Season;
import de.bsc_projekt.cloneflix.Models.Serie;
import de.bsc_projekt.cloneflix.ProjectionDTOs.TitlePreview;
import de.bsc_projekt.cloneflix.services.SeriesService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("api/v1/series")
@AllArgsConstructor
public class SerieController {

    private final SeriesService seriesService;

    @PostMapping
    public ResponseEntity<Object> addSeries(@RequestBody Serie serie) {
        return seriesService.addSeries(serie);
    }

    @GetMapping(value = "/{id}")
    public Serie getOne(@PathVariable String id) {
        return seriesService.getOne(id);
    }

    @GetMapping
    public List<Serie> getAll() {
        return seriesService.getAll();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Object> updateSeries(@PathVariable String id,
            @RequestBody HashMap<String, Object> updatedSeries) {
        return seriesService.updateSeries(id, updatedSeries);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> deleteSeriesById(@PathVariable String id) {
        if (seriesService.deleteSeriesById(id)){
            return new ResponseEntity<>("Series with id : "
                    + id
                    + " is deleted", HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping(value = "/{id}/season/{seasonNumber}")
    public Season getSeason(@PathVariable String id, @PathVariable int seasonNumber) {
        return seriesService.getSeason(id, seasonNumber);
    }

    @GetMapping("/preview/{genre}")
    public List<TitlePreview> getSeriesTileInfoForGenres(@PathVariable String genre){
        return seriesService.getSeriesTileInfoForGenres(genre);
    }

    @GetMapping(value = "/genre/{genre}")
    public List<TitlePreview> getSeriesByGenres(@PathVariable String genre) {
        return seriesService.getSeriesByGenres(genre);
    }


    @PostMapping(value="/{id}/season")
    public ResponseEntity<Object> addSeasonToSeries(
            @PathVariable String id,
            @RequestBody Season season) {
       return seriesService.addSeasonToSeries(id, season);
    }

}