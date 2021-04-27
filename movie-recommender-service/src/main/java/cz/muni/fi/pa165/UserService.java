package cz.muni.fi.pa165;

import cz.muni.fi.pa165.entity.User;

import java.util.List;

/**
 * An interface that defines a service access to the {@link User} entity.
 *
 * @author Tomas Jevocin
 */
public interface UserService {

    /**
     * Passes the user to be saved into the persistence layer.
     *
     * @param user - user to be saved
     */
    void registerUser(User user, String password);

    /**
     * Retrieves a list of all users from the persistence layer.
     *
     * @return a list of all existing User entities
     */
    List<User> getAllUsers();

    /**
     * Retrieves a user by its ID from the persistence layer.
     *
     * @param id - id of the user
     * @return user entity matching the id
     */
    User findUserById(Long id);

    /**
     * Retrieves a user with matching email from the persistence layer.
     *
     * @param emailAddress - email address on which the user should be matched
     * @return user entity which matches the given email
     */
    User findUserByEmailAddress(String emailAddress);

    /**
     * Retrieves a user with matching username from the persistence layer.
     *
     * @param username - username on which the user should be matched
     * @return users entity which matches the given username
     */
    User findUserByUsername(String username);

    /**
     * Attempts to authenticate a user by comparing the given password hash with the one stored for the given user.
     *
     * @param user - user whose password is to be checked
     * @param passwordHash - password hash to be compared against the stored one
     * @return true if the password hashes match
     */
    boolean authenticate(User user, String passwordHash);

    /**
     * Checks whether given user has admin rights.
     *
     * @param user - user to be checked
     * @return true if user is admin
     */
    boolean isAdmin(User user);
}
