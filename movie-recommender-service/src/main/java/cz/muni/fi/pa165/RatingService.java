package cz.muni.fi.pa165;

import cz.muni.fi.pa165.entity.Rating;

import java.math.BigDecimal;
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

    /**
     * Gets the overall score of the rating computed from all other scores.
     *
     * @param rating to get the overall score for
     * @return the overall score of the rating
     */
    BigDecimal getOverallScore(Rating rating);
}
