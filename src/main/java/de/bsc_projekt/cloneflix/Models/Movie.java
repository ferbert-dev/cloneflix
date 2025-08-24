package de.bsc_projekt.cloneflix.Models;

import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Date;

@NoArgsConstructor
@Document(collection = "moviecollection")
public class Movie {
    @Id
    private String id;
    private String title;
    private int runtime;
    private String[] directors;
    private String[] producers;
    private String[] starring;
    private String[] genres;
    private String[] videos;
    private String description;
    private FSK fsk;
    private Date releaseDate;
    private double voteAverage;
    private Date cloneflixDate;
    private String posterPath;
    private String backdropPath;

    public Movie(String title, int runtime, String[] directors, String[] producers, String[] starring, String[] genres,
            String[] videos, String description, FSK fsk, Date releaseDate, double voteAverage, Date cloneflixDate,
            String posterPath, String backdropPath) {
        this.title = title;
        this.runtime = runtime;
        this.directors = directors;
        this.producers = producers;
        this.starring = starring;
        this.genres = genres;
        this.videos = videos;
        this.description = description;
        this.fsk = fsk;
        this.releaseDate = releaseDate;
        this.voteAverage = voteAverage;
        this.cloneflixDate = cloneflixDate;
        this.posterPath = posterPath;
        this.backdropPath = backdropPath;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String poster_path) {
        this.posterPath = poster_path;
    }

    public String getBackdrop() {
        return backdropPath;
    }

    public void setBackdrop(String backdrop_path) {
        this.backdropPath = backdrop_path;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date release_date) {
        this.releaseDate = release_date;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getRuntime() {
        return runtime;
    }

    public String[] getDirectors() {
        return directors;
    }

    public String[] getStarring() {
        return starring;
    }

    public String[] getGenres() {
        return genres;
    }

    public FSK getFsk() {
        return fsk;
    }

    public String[] getVideos() {
        return videos;
    }

    public String getDescription() {
        return description;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }

    public void setDirectors(String[] directors) {
        this.directors = directors;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setVideos(String[] videos) {
        this.videos = videos;
    }

    public String[] getProducers() {
        return producers;
    }

    public void setProducers(String[] producers) {
        this.producers = producers;
    }

    public void setStarring(String[] starring) {
        this.starring = starring;
    }

    public void setGenres(String[] genres) {
        this.genres = genres;
    }

    public void setFsk(FSK fsk) {
        this.fsk = fsk;
    }

    public double getRating() {
        return voteAverage;
    }

    public void setRating(double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public Date getCloneflixDate() {
        return cloneflixDate;
    }

    public void setCloneflixDate(Date cloneflixDate) {
        this.cloneflixDate = cloneflixDate;
    }
}
