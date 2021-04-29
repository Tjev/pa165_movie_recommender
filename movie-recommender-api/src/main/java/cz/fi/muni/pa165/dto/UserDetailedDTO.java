package cz.fi.muni.pa165.dto;

import java.util.List;
import java.util.Objects;

/**
 * DTO class representing User entities.
 *
 * @author Tomas Jevocin
 */
public class UserDetailedDTO {

    private Long id;
    private String username;
    private String passwordHash;
    private boolean admin;
    private String emailAddress;
    private List<RatingDTO> ratings;

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

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
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

    public List<RatingDTO> getRatings() {
        return ratings;
    }

    public void setRatings(List<RatingDTO> ratings) {
        this.ratings = ratings;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserDetailedDTO)) return false;

        UserDetailedDTO userDetailedDTO = (UserDetailedDTO) o;

        if (isAdmin() != userDetailedDTO.isAdmin()) return false;
        if (!getUsername().equals(userDetailedDTO.getUsername())) return false;
        if (!getPasswordHash().equals(userDetailedDTO.getPasswordHash())) return false;
        return getEmailAddress().equals(userDetailedDTO.getEmailAddress());
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                getUsername(),
                getPasswordHash(),
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
