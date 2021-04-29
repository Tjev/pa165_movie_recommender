package cz.muni.fi.pa165;

import cz.muni.fi.pa165.entity.Movie;
import cz.muni.fi.pa165.entity.Rating;
import cz.muni.fi.pa165.entity.User;

import java.util.List;

/**
 * An interface that defines a service access to the {@link Rating} entity.
 *
 * @author Kristian Tkacik
 */
public interface RatingService {

    /**
     * Saves Rating into the persistence layer.
     *
     * @param rating to be saved
     */
    void create(Rating rating);

    /**
     * Retrieves a list of ratings from the persistence layer.
     *
     * @return list of all ratings
     */
    List<Rating> findAll();

    /**
     * Retrieves a rating by its ID from the persistence layer.
     *
     * @param id of the rating
     * @return rating matching the id
     */
    Rating findById(Long id);

    /**
     * Retrieves ratings for the movie from the persistence layer.
     *
     * @param movie for which ratings should be found
     * @return list of ratings
     */
    List<Rating> findByMovie(Movie movie);

    /**
     * Retrieves ratings for the user from the persistence layer.
     *
     * @param user for which ratings should be found
     * @return list of ratings
     */
    List<Rating> findByUser(User user);

    /**
     * Merges the rating by its ID with its current representation in the persistence layer.
     *
     * @param rating to be merged
     */
    void update(Rating rating);

    /**
     * Removes the rating from the persistence layer.
     *
     * @param rating to be removed
     */
    void remove(Rating rating);
}
