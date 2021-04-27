package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entity.Movie;
import cz.muni.fi.pa165.entity.Rating;
import cz.muni.fi.pa165.entity.User;

import java.util.List;
import java.util.Set;

/**
 * Dao for manipulation with Rating entities.
 *
 * @author Kristian Tkacik
 */
public interface RatingDao {

    /**
     * Saves Rating into the data source.
     *
     * @param rating to be saved
     */
    void create(Rating rating);

    /**
     * Retrieves a list of ratings from the data source.
     *
     * @return list of all ratings
     */
    List<Rating> findAll();

    /**
     * Retrieves a rating by its ID from the data source.
     *
     * @param id of the rating
     * @return rating matching the id
     */
    Rating findById(Long id);

    /**
     * Retrieves ratings for the movie from the data source
     *
     * @param movie for which ratings should be retrieved
     * @return list of ratings
     */
    List<Rating> findByMovie(Movie movie);

    /**
     * Retrieves ratings for the user from the data source
     *
     * @param user for which ratings should be retrieved
     * @return list of ratings
     */
    List<Rating> findByUser(User user);

    /**
     * Merges the rating by its ID with its current representation in the data source.
     *
     * @param rating to be merged
     */
    void update(Rating rating);

    /**
     * Removes the rating from the data source.
     *
     * @param rating to be removed
     */
    void remove(Rating rating);
}
