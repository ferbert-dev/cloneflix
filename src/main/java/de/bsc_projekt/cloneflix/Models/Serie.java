package de.bsc_projekt.cloneflix.Models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;
@Getter
@Setter
@Document(collection = "seriecollection")
public class Serie {

    @Id
    private String id;
    private String title;
    private String[] genres;
    private String description;
    private String[] starring;
    private Date firstAirDate;
    private Date cloneflixDate;
    private String posterPath;
    private String backdropPath;
    private String[] videos;
    private double voteAverage;
    private int voteCount;
    private int numberOfSeasons;
    private ArrayList<Season> seasons;

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setGenres(String[] genres) {
        this.genres = genres;
    }

    public String[] getGenres() {
        return genres;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String[] getStarring() {
        return starring;
    }

    public void setStarring(String[] starring) {
        this.starring = starring;
    }

    public Date getFirstAirDate() {
        return firstAirDate;
    }

    public void setFirstAirDate(Date firstAirDate) {
        this.firstAirDate = firstAirDate;
    }

    public Date getCloneflixDate() {
        return cloneflixDate;
    }

    public void setCloneflixDate(Date cloneflixDate) {
        this.cloneflixDate = cloneflixDate;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public String[] getVideos() {
        return videos;
    }

    public void setVideos(String[] videos) {
        this.videos = videos;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public int getNumberOfSeasons() {
        return numberOfSeasons;
    }

    public void setNumberOfSeasons(int numberOfSeasons) {
        this.numberOfSeasons = numberOfSeasons;
    }

    public ArrayList<Season> getSeasons() {
        return seasons;
    }

    public void setSeasons(ArrayList<Season> seasons) {
        this.seasons = seasons;
    }

    public Season getSeason(int seasonNumber) {
        return seasons.get(seasonNumber);
    }

    public void addSeason(Season season) {
        seasons.add(season);
    }
}
