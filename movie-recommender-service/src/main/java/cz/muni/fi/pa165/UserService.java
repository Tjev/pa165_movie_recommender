package cz.muni.fi.pa165;

import cz.muni.fi.pa165.entity.Movie;
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
     * @return user after registration
     */
    User register(User user, String password);

    /**
     * Retrieves a list of all users from the persistence layer.
     *
     * @return a list of all existing User entities
     */
    List<User> findAll();

    /**
     * Retrieves a user by its ID from the persistence layer.
     *
     * @param id - id of the user
     * @return user entity matching the id
     */
    User findById(Long id);

    /**
     * Retrieves a user with matching email from the persistence layer.
     *
     * @param emailAddress - email address on which the user should be matched
     * @return user entity which matches the given email
     */
    User findByEmailAddress(String emailAddress);

    /**
     * Retrieves a user with matching username from the persistence layer.
     *
     * @param username - username on which the user should be matched
     * @return users entity which matches the given username
     */
    User findByUsername(String username);

    /**
     * Attempts to authenticate a user by hashing and comparing the given password with the one stored for the given user.
     *
     * @param user - user whose password is to be checked
     * @param password - password to be hashed and compared against the stored hash
     * @return true if the password hashes match
     */
    boolean authenticate(User user, String password);

    /**
     * Checks whether given user has admin rights.
     *
     * @param user - user to be checked
     * @return true if user is admin
     */
    boolean isAdmin(User user);

    /**
     * Disables the user account for given user entity.
     *
     * @param user - user to be disabled
     * @return disabled user
     */
    User disable(User user);

    /**
     * Checks whether the given user account is disabled.
     *
     * @param user - user to be checked
     * @return true if user is disabled
     */
    boolean isDisabled(User user);

    /**
     * Updates corresponding user entity in the persistence layer.
     *
     * @param user - user to be updated
     * @return person after update operation
     */
    User update(User user);
}
