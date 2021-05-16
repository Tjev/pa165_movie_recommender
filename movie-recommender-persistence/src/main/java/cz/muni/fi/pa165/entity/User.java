package cz.muni.fi.pa165.entity;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
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
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotEmpty
    @Column(name = "username", nullable = false, unique = true)
    private String username;

    private String passwordHash;

    private boolean admin;

    private boolean disabled;

    @NotEmpty
    @Email(message = "Should be a valid email address")
    @Column(name = "email_address", nullable = false, unique = true)
    private String emailAddress;

    @OneToMany(mappedBy = "user")
    @Column(name = "user_ratings")
    private List<Rating> userRatings = new ArrayList<>();

    public User() {
    }

    public User(String username, String emailAddress) {
        this.username = username;
        this.emailAddress = emailAddress;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) { this.id = id; }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public List<Rating> getUserRatings() {
        return Collections.unmodifiableList(userRatings);
    }

    public void addRating(Rating rating) {
        userRatings.add(rating);
        rating.setUser(this);
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public boolean isDisabled() {
        return disabled;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;

        User user = (User) o;

        if (!getUsername().equals(user.getUsername())) return false;
        return getEmailAddress().equals(user.getEmailAddress());
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                getUsername(),
                getEmailAddress());
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", admin=" + admin +
                ", disabled=" + disabled +
                ", emailAddress='" + emailAddress + '\'' +
                '}';
    }
}
