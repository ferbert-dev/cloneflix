package de.bsc_projekt.cloneflix.registration.tools;

import org.springframework.stereotype.Service;

import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/***
 * EmailValidator a Service to validate an users email
 *
 */
@Service
public class EmailValidator implements Predicate<String>
{
    private static final Pattern EMAIL_REGEX = Pattern.compile("^[a-zA-Z0-9.!#$%&'*+\\/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$");
    @Override public boolean test(String email)
    {
        final Matcher matcher = EMAIL_REGEX.matcher(email);
        return matcher.matches();
    }
}
