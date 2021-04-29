package cz.fi.muni.pa165.facade;

import cz.fi.muni.pa165.dto.MovieDTO;
import cz.fi.muni.pa165.dto.RatingCreateDTO;
import cz.fi.muni.pa165.dto.RatingDTO;
import cz.fi.muni.pa165.dto.UserDTO;

import java.util.List;

public interface RatingFacade {

    /**
     * Creates a rating and returns its id.
     *
     * @param rating to be created
     * @return id of the rating
     */
    Long create(RatingCreateDTO rating);

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
    void delete(Long id);

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
