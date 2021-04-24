package cz.muni.fi.pa165.entity;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Entity that represents registered recommender system users.
 *
 * @author Tomas Jevocin
 */
@Entity
@Table(name = "app_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @Column(nullable = false, unique = true)
    private String username;

    @NotEmpty
    @Email(message = "Should be a valid email address")
    @Column(nullable = false, unique = true)
    private String mailAddress;

    @OneToMany(mappedBy = "user")
    private List<Rating> userRatings = new ArrayList<>();

    public User() {
    }

    public User(String username, String mailAddress) {
        this.username = username;
        this.mailAddress = mailAddress;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getMailAddress() {
        return mailAddress;
    }

    public void setMailAddress(String mailAddress) {
        this.mailAddress = mailAddress;
    }

    public List<Rating> getUserRatings() {
        return Collections.unmodifiableList(userRatings);
    }

    public void addRating(Rating rating) {
        userRatings.add(rating);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;

        User user = (User) o;

        if (!getUsername().equals(user.getUsername())) return false;
        return getMailAddress().equals(user.getMailAddress());
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                getUsername(),
                getMailAddress());
    }
}
