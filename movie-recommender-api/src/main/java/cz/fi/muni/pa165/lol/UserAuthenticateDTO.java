package cz.fi.muni.pa165.dto;

/**
 * DTO class representing authentication of User entities.
 *
 * @author Tomas Jevocin
 */
public class UserAuthenticateDTO {

    private Long userId;
    private String password;

    public Long getUserId()
    {
        return userId;
    }

    public void setUserId(Long userId)
    {
        this.userId = userId;
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
                "userId=" + userId +
                '}';
    }
}
