package de.bsc_projekt.cloneflix.registration.requests;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

import lombok.ToString;

/**
 * Contains the registration details from the corresponding request.
 */
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class RegistrationRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String password;

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public String getEmail() {
        return this.email;
    }

    public String getPassword() {
        return this.password;
    }
}
