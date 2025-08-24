package de.bsc_projekt.cloneflix.Models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Episode {
    private String title;
    private String description;
    private String posterPath;
    private int number;
    private String videos;
    private FSK fsk;

    public Episode(String title, String description, String posterPath, int number, String videos, FSK fsk) {
        this.title = title;
        this.description = description;
        this.number = number;
        this.posterPath = posterPath;
        this.videos = videos;
        this.fsk = fsk;
    }
}
