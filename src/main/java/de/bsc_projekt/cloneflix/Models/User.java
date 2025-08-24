package de.bsc_projekt.cloneflix.Models;

import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import java.util.*;
/**
 * The User models for MongoDB
 *
 * @version 5.0
 * @since   2021-07-15
 */
@NoArgsConstructor
@Document(collection = "userscollection")
public class User
{
    @Id private String id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String password;

    //@DBRef
    @Field("favorite")
    private ArrayList<String> favorite;

    private Payment payment_method;
    private String geburtsDatum;
    private Set<AppUserRole> appUserRoles = new HashSet<>();

    public User(String firstName, String lastName, String email, String password)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = firstName+" "+ lastName;
        this.email = email;
        this.password = password;
        this.favorite = new ArrayList<>();
        payment_method = Payment.PAYPAL;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public void setLastname(String lastName)
    {
        this.lastName = lastName;
    }

    public String getId()
    {
        return id;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public ArrayList<String> getFavorite()
    {
        return favorite;
    }

    public void setFavorite(ArrayList<String> favorite)
    {
        this.favorite = favorite;
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

    public Set<AppUserRole> getAppUserRole()
    {
        return appUserRoles;
    }

    public void setAppUserRole(Set<AppUserRole> appUserRoles)
    {
        this.appUserRoles = appUserRoles;
    }

}
