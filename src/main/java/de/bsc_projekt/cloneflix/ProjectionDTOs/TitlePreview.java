package de.bsc_projekt.cloneflix.ProjectionDTOs;

import lombok.Value;

/**
 * Used as a projection for requests that only need information for tiles.
 */
@Value
public class TitlePreview {
    String id, title, posterPath;
}
