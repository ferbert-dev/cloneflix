package de.bsc_projekt.cloneflix.registration.responses;
import de.bsc_projekt.cloneflix.Models.AppUserRole;
import de.bsc_projekt.cloneflix.Models.Payment;

import java.util.HashSet;
import java.util.Set;

/**
 * Model for the successful response one user.
 */

public class OneUserResponse
{
    private String id;
    private String username;
    private String email;
    private Payment payment_method;
    private String geburtsDatum;
    private Set<AppUserRole> appUserRoles= new HashSet<>();

    public OneUserResponse(String id, String username, String email,
            Payment payment_method, String geburtsDatum,
            Set<AppUserRole> appUserRoles)
    {
        this.id = id;
        this.username = username;
        this.email = email;
        this.payment_method = payment_method;
        this.geburtsDatum = geburtsDatum;
        this.appUserRoles = appUserRoles;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public Payment getPayment_method()
    {
        return payment_method;
    }

    public void setPayment_method(Payment payment_method)
    {
        this.payment_method = payment_method;
    }

    public String getGeburtsDatum()
    {
        return geburtsDatum;
    }

    public void setGeburtsDatum(String geburtsDatum)
    {
        this.geburtsDatum = geburtsDatum;
    }

    public Set<AppUserRole> getAppUserRoles()
    {
        return appUserRoles;
    }

    public void setAppUserRoles(Set<AppUserRole> appUserRoles)
    {
        this.appUserRoles = appUserRoles;
    }

}
