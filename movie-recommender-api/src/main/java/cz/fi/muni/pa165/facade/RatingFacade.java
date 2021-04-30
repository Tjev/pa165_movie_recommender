package cz.fi.muni.pa165.facade;

import cz.fi.muni.pa165.dto.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * Facade for interaction with rating data.
 *
 * @author Kristian Tkacik
 */
public interface RatingFacade {

    /**
     * Creates a rating.
     *
     * @param ratingCreateDTO of the rating to be created
     * @return DTO of the created rating
     */
    Optional<RatingDTO> create(RatingCreateDTO ratingCreateDTO);

    /**
     * Update a rating.
     *
     * @param ratingDTO DTO of rating with desired attributes
     * @return DTO of the rating from the database after update operation
     */
    Optional<RatingDTO> update(RatingDTO ratingDTO);

    /**
     * Delete a rating.
     *
     * @param ratingDTO of the rating to be deleted.
     * @return true if the rating was removed successfully
     */
    Boolean remove(RatingDTO ratingDTO);

    /**
     * Finds rating with the given id.
     *
     * @param id of the rating to be found
     * @return DTO of the rating
     */
    Optional<RatingDTO> findById(Long id);

    /**
     * Finds ratings with the given movie.
     *
     * @param movieDTO of the movie to find ratings by
     * @return list of DTOs of the found ratings
     */
    Optional<List<RatingDTO>> findByMovie(MovieDTO movieDTO);

    /**
     * Finds ratings with the given user.
     *
     * @param userDTO of the user to find ratings by
     * @return list of DTOs of the found ratings
     */
    Optional<List<RatingDTO>> findByUser(UserDTO userDTO);

    /**
     * Gets overall score for the given rating computed as average
     * from its sub-scores.
     *
     * @param ratingDTO of rating to compute overall score for
     * @return the overall score for the rating
     */
    Optional<BigDecimal> getOverallScore(RatingDTO ratingDTO);
}
