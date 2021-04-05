package cz.muni.fi.pa165.movie_recommender.dao;

import cz.muni.fi.pa165.movie_recommender.entity.User;

import java.util.List;

/**
 * DAO for manipulation with User entities
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
     * Retrieve a user by its ID from the data source.
     *
     * @param id - id of the user
     * @return user entity matching the id
     */
    User findById(Long id);

    /**
     * Retrieve a user with matching username from the data source.
     *
     * @param username - username on which the user should be matched
     * @return a user entity which matches the given username
     */
    User findByUsername(String username);

    /**
     * Merges user by its ID with its current representation in the data source.
     *
     * @param user
     */
    void update(User user);

    /**
     * Remove user from the data source.
     *
     * @param user - user to be removed
     */
    void remove(User user);
}
