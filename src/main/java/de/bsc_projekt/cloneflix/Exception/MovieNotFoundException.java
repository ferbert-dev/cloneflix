package de.bsc_projekt.cloneflix.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class MovieNotFoundException extends RuntimeException
{
    public MovieNotFoundException(String title) {
        super("Cann not find Movie :" +  title);
    }
}
