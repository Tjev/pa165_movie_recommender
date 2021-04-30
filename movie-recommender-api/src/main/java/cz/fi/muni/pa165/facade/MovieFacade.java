package cz.fi.muni.pa165.facade;

import cz.fi.muni.pa165.dto.*;

import java.util.List;

/**
 * Facade for interaction with movie data.
 *
 * @author Radoslav Chudovsky
 */
public interface MovieFacade {

    /**
     * Creates a new Movie.
     *
     * @param movieCreateDTO DTO with movie data
     * @return detailed DTO of created Movie
     */
    MovieDetailedDTO create(MovieCreateDTO movieCreateDTO);

    /**
     * Finds movie by id.
     *
     * @param id id of the movie in the data source
     * @return detailed DTO with movie data
     */
    MovieDetailedDTO findById(Long id);

    /**
     * Finds list of movies with the given title.
     *
     * @param title title of the movie in the data source
     * @return list od detailed DTOs with given title
     */
    List<MovieDetailedDTO> findByTitle(String title);

    /**
     * Updates the given Movie in the data source.
     *
     * @param movieDetailedDTO detailed DTO of the movie with updated attributes
     * @return detailed DTO of the updated movie
     */
    MovieDetailedDTO update(MovieDetailedDTO movieDetailedDTO);

    /**
     * Adds given actor to the given movie.
     *
     * @param movieDTO DTO of a movie that the person acted in
     * @param personDTO DTO of the actor
     * @return detailed DTO of the updated movie
     */
    MovieDetailedDTO addActor(MovieDTO movieDTO, PersonDTO personDTO);

    /**
     * Adds director to the given movie.
     *
     * @param movieDTO DTO of a movie that the person directed
     * @param personDTO DTO of the director
     * @return detailed DTO of the updated movie
     */
    MovieDetailedDTO addDirector(MovieDTO movieDTO, PersonDTO personDTO);

    /**
     * Adds given rating to the given movie.
     *
     * @param movieDTO DTO of a movie that was rated
     * @param ratingDTO DTO of the rating
     * @return detailed DTO of the updated movie
     */
    MovieDetailedDTO addRating(MovieDTO movieDTO, RatingDTO ratingDTO);

    /**
     * Removes the given movie.
     *
     * @param movieDTO DTO of the movie to be removed
     */
    void remove(MovieDTO movieDTO);
}
