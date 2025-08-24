package de.bsc_projekt.cloneflix.registration.requests;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Contains the request details from the authentication request.
 */
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class LoginRequest {

    private String username;

    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
