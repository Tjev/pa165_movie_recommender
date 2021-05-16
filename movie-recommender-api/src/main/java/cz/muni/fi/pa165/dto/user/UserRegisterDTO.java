package cz.muni.fi.pa165.dto.user;

import java.util.Objects;

/**
 * DTO class representing User entities.
 *
 * @author Tomas Jevocin
 */
public class UserRegisterDTO {

    private Long id;
    private String username;
    private String password;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

        if (!getUsername().equals(userDTO.getUsername())) return false;
        return getEmailAddress().equals(userDTO.getEmailAddress());
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                getUsername(),
                getEmailAddress());
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", emailAddress='" + emailAddress + '\'' +
                '}';
    }
}