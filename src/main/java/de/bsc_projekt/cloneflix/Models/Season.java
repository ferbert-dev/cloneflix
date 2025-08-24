package de.bsc_projekt.cloneflix.Models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Season {
    private String title;
    private int number;
    private String description;
    private String posterPath;
    private int numberOfEpisodes;
    private List<Episode> episodes;

}
