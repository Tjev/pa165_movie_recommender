package cz.muni.fi.pa165.dto.user;

/**
 * DTO class representing authentication of User entities.
 *
 * @author Tomas Jevocin
 */
public class UserAuthenticateDTO {

    private Long id;
    private String password;

    public Long getId()
    {
        return id;
    }

    public void setId(Long userId)
    {
        this.id = userId;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserAuthenticateDTO{" +
                "userId=" + id +
                '}';
    }
}
