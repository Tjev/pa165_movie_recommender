package cz.muni.fi.pa165.movie_recommender.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Entity that represents registered recommender system users.
 *
 * @author Tomas Jevocin
 */
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

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

    public void setId(Long id) {
        this.id = id;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;

        User user = (User) o;

        if (!getUsername().equals(user.getUsername())) return false;
        if (!getMailAddress().equals(user.getMailAddress())) return false;
        return Objects.equals(userRatings, user.userRatings);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                getUsername(),
                getMailAddress());
    }

}
