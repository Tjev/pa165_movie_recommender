package cz.fi.muni.pa165.dto;

import java.util.List;
import java.util.Objects;

/**
 * DTO class representing User entities.
 *
 * @author Tomas Jevocin
 */
public class UserDTO {

    private Long id;
    private String username;
    private String passwordHash;
    private boolean admin;
    private String emailAddress;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserDTO)) return false;

        UserDTO userDTO = (UserDTO) o;

        if (isAdmin() != userDTO.isAdmin()) return false;
        if (!getUsername().equals(userDTO.getUsername())) return false;
        if (!getPasswordHash().equals(userDTO.getPasswordHash())) return false;
        return getEmailAddress().equals(userDTO.getEmailAddress());
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
        return "UserDTO{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", admin=" + admin +
                ", emailAddress='" + emailAddress + '\'' +
                '}';
    }
}
