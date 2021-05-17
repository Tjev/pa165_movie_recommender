package cz.muni.fi.pa165.dto.user;

/**
 * DTO class representing authentication of User entities.
 *
 * @author Tomas Jevocin
 */
public class UserAuthenticateDTO {

    private String emailAddress;
    private String password;

    public String getEmailAddress() { return emailAddress; }

    public void setEmailAddress(String emailAddress) { this.emailAddress = emailAddress; }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }
}
