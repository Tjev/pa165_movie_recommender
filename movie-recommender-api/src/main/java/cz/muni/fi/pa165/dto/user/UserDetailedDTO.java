package cz.muni.fi.pa165.dto.user;

import cz.muni.fi.pa165.dto.rating.RatingDTO;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Objects;

/**
 * DTO class representing User entities.
 *
 * @author Tomas Jevocin
 */
public class UserDetailedDTO {

    @NotNull
    private Long id;

    @NotEmpty
    private String username;

    private boolean admin;

    @Email
    private String emailAddress;

    private ArrayList<RatingDTO> ratings;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public ArrayList<RatingDTO> getRatings() {
        return ratings;
    }

    public void setRatings(ArrayList<RatingDTO> ratings) {
        this.ratings = ratings;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserDetailedDTO)) return false;

        UserDetailedDTO userDetailedDTO = (UserDetailedDTO) o;

        if (isAdmin() != userDetailedDTO.isAdmin()) return false;
        if (!getUsername().equals(userDetailedDTO.getUsername())) return false;
        return getEmailAddress().equals(userDetailedDTO.getEmailAddress());
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                getUsername(),
                isAdmin(),
                getEmailAddress());
    }

    @Override
    public String toString() {
        return "UserDetailedDTO{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", admin=" + admin +
                ", emailAddress='" + emailAddress + '\'' +
                '}';
    }
}
