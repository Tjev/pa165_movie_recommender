package cz.muni.fi.pa165.facade;

import cz.muni.fi.pa165.dto.movie.MovieCreateDTO;
import cz.muni.fi.pa165.dto.movie.MovieDTO;
import cz.muni.fi.pa165.dto.movie.MovieDetailedDTO;
import cz.muni.fi.pa165.dto.person.PersonDTO;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

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
    Optional<MovieDetailedDTO> create(MovieCreateDTO movieCreateDTO);

    /**
     * Finds movie by id.
     *
     * @param id id of the movie in the data source
     * @return detailed DTO with movie data
     */
    Optional<MovieDetailedDTO> findById(Long id);

    /**
     * Finds list of movies with the given title.
     *
     * @param title title of the movie in the data source
     * @return list od detailed DTOs with given title
     */
    Optional<List<MovieDetailedDTO>> findByTitle(String title);

    /**
     * Updates the given Movie in the data source.
     *
     * @param movieDetailedDTO detailed DTO of the movie with updated attributes
     * @return detailed DTO of the updated movie
     */
    Optional<MovieDetailedDTO> update(MovieDetailedDTO movieDetailedDTO);

    /**
     * Adds given actor to the given movie.
     *
     * @param movieDTO DTO of a movie that the person acted in
     * @param personDTO DTO of the actor
     * @return detailed DTO of the updated movie
     */
    Optional<MovieDetailedDTO> addActor(MovieDTO movieDTO, PersonDTO personDTO);

    /**
     * Adds director to the given movie.
     *
     * @param movieDTO DTO of a movie that the person directed
     * @param personDTO DTO of the director
     * @return detailed DTO of the updated movie
     */
    Optional<MovieDetailedDTO> addDirector(MovieDTO movieDTO, PersonDTO personDTO);

    /**
     * Removes the given movie.
     *
     * @param movieDTO DTO of the movie to be removed
     * @return Boolean whether the operation succeeded
     */
    Boolean remove(MovieDTO movieDTO);

    /**
     * Gets overall score for the given movie computed as average
     * from the movie's ratings.
     *
     * @param movieDTO DTO of a movie to compute overall score for
     * @return the overall score for the given movie
     */
    Optional<BigDecimal> getOverallScore(MovieDTO movieDTO);

    /**
     * Gets originality score for the given movie computed as average
     * from the movie's ratings.
     *
     * @param movieDTO DTO of a movie to compute originality score for
     * @return the originality score for the given movie
     */
    Optional<BigDecimal> getOriginalityScore(MovieDTO movieDTO);

    /**
     * Gets soundtrack score for the given movie computed as average
     * from the movie's ratings.
     *
     * @param movieDTO DTO of a movie to compute soundtrack score for
     * @return the soundtrack score for the given movie
     */
    Optional<BigDecimal> getSoundtrackScore(MovieDTO movieDTO);

    /**
     * Gets narrative score for the given movie computed as average
     * from the movie's ratings.
     *
     * @param movieDTO DTO of a movie to compute narrative score for
     * @return the narrative score for the given movie
     */
    Optional<BigDecimal> getNarrativeScore(MovieDTO movieDTO);

    /**
     * Gets cinematography score for the given movie computed as average
     * from the movie's ratings.
     *
     * @param movieDTO DTO of a movie to compute cinematography score for
     * @return the cinematography score for the given movie
     */
    Optional<BigDecimal> getCinematographyScore(MovieDTO movieDTO);

    /**
     * Gets depth score for the given movie computed as average
     * from the movie's ratings.
     *
     * @param movieDTO DTO of a movie to compute depth score for
     * @return the depth score for the given movie
     */
    Optional<BigDecimal> getDepthScore(MovieDTO movieDTO);

    /**
     * Gets movie recommendations based on the given movie. Recommendations
     * are based on genres, users who have seen the same movie, and the
     * overall rating score of the movie.
     *
     * @param movieDTO DTO of a movie to get recommendations for
     * @param n max number of movie recommendations that will be returned
     * @return list of max n recommended movies
     */
    Optional<List<MovieDetailedDTO>> getRecommendations(MovieDTO movieDTO, Integer n);
}
