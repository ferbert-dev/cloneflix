package de.bsc_projekt.cloneflix.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class SeriesNotFoundException extends RuntimeException {

    public SeriesNotFoundException(String title) {
        super("Cann not find Series :" +  title);
    }
}
