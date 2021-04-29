package cz.fi.muni.pa165.facade;

import cz.fi.muni.pa165.dto.*;

import java.util.List;

/**
 * Facade for interaction with rating data.
 *
 * @author Kristian Tkacik
 */
public interface RatingFacade {

    /**
     * Creates a rating.
     *
     * @param rating to be created
     * @return DTO of the created rating
     */
    RatingDTO create(RatingCreateDTO rating);

    /**
     * Update a rating.
     *
     * @param rating DTO of rating with desired attributes
     * @return DTO of the rating from the database after update operation
     */
    RatingDTO update(RatingDTO rating);

    /**
     * Delete a rating.
     *
     * @param id of the rating to be deleted.
     */
    void remove(Long id);

    /**
     * Finds rating with the given id.
     *
     * @param id of the rating to be found
     * @return DTO of the rating
     */
    RatingDTO findById(Long id);

    /**
     * Finds ratings with the given movie.
     *
     * @param movie to find ratings by
     * @return list of DTOs of the found ratings
     */
    List<RatingDTO> findByMovie(MovieDTO movie);

    /**
     * Finds ratings with the given user.
     *
     * @param user to find ratings by
     * @return list of DTOs of the found ratings
     */
    List<RatingDTO> findByUser(UserDTO user);
}
