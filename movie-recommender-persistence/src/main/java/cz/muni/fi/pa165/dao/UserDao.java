package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entity.User;

import java.util.List;

/**
 * DAO for manipulation with {@link User} entities
 *
 * @author Tomas Jevocin.
 */
public interface UserDao {

    /**
     * Saves user into the data source.
     *
     * @param user - user to be saved
     */
    void create(User user);

    /**
     * Retrieves a list of all users from the data source.
     *
     * @return a list of all existing User entities
     */
    List<User> findAll();

    /**
     * Retrieves a user by its ID from the data source.
     *
     * @param id - id of the user
     * @return user entity matching the id
     */
    User findById(Long id);

    /**
     * Retrieves a user by its username from the data source.
     *
     * @param username - username on which the user should be matched
     * @return user entity matching the given username
     */
    User findByUsername(String username);

    /**
     * Retrieves a user by its email address from the data source.
     *
     * @param emailAddress - email address of the user
     * @return user entity matching the emailAddress
     */
    User findByEmailAddress(String emailAddress);

    /**
     * Merges user by its ID with its current representation in the data source.
     *
     * @param user - user to be updated
     */
    void update(User user);
}
