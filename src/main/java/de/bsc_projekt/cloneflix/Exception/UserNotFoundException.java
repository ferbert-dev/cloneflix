package de.bsc_projekt.cloneflix.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException
{
   public UserNotFoundException(String id) {
        super("Could not find user :" + id);
    }
}
