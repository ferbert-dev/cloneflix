package de.bsc_projekt.cloneflix.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class UserCanNotBeDeletedException extends RuntimeException
{
    public UserCanNotBeDeletedException(String id) {
        super("Could not be deleted :" + id);
    }
}
