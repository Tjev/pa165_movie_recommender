package cz.muni.fi.pa165.rest.controller;

import cz.muni.fi.pa165.dto.movie.MovieCreateDTO;
import cz.muni.fi.pa165.dto.movie.MovieDTO;
import cz.muni.fi.pa165.dto.movie.MovieDetailedDTO;
import cz.muni.fi.pa165.dto.person.PersonDetailedDTO;
import cz.muni.fi.pa165.exception.FacadeLayerException;
import cz.muni.fi.pa165.facade.MovieFacade;
import cz.muni.fi.pa165.rest.exception.DataSourceException;
import cz.muni.fi.pa165.rest.exception.InvalidParameterException;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Controller for managing movies.
 *
 * @author Radoslav Chudovsky
 */
@RestController
@RequestMapping("movies")
public class MovieController {

    final static Logger logger = getLogger(MovieController.class);

    @Inject
    private MovieFacade movieFacade;

    /**
     * Create given movie.
     *
     * curl -X POST -i -H "Content-Type: application/json" --data '{"title": "Dune", "releaseYear": "2021-10-01", "genres": ["SCIFI"]}' http://localhost:8080/pa165/rest/movies/create
     *
     * @param movieCreateDTO movie to be created
     * @return detailed DTO with id after create operation
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public final MovieDetailedDTO create(@RequestBody MovieCreateDTO movieCreateDTO) {
        logger.debug("rest create({})", movieCreateDTO);

        try {
            return movieFacade.create(movieCreateDTO);
        } catch (FacadeLayerException e) {
            throw new DataSourceException("The instance already exists.", e);
        } catch (IllegalArgumentException e) {
            throw new InvalidParameterException("Given parameters were invalid.", e);
        }
    }

    /**
     * Find a movie by its id.
     *
     * curl -X GET -i http://localhost:8080/pa165/rest/movies/1
     *
     * @param id id of the person to be found
     * @return detailed DTO of found movie
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final MovieDetailedDTO findById(@PathVariable long id) {
        logger.debug("rest findById({})", id);

        Optional<MovieDetailedDTO> result;
        try {
            result = movieFacade.findById(id);
        } catch (FacadeLayerException e) {
            throw new DataSourceException("Problem with the data source occurred.", e);
        } catch (IllegalArgumentException e) {
            throw new InvalidParameterException("Given parameters were invalid.", e);
        }

        if (result.isEmpty()) {
            throw new InvalidParameterException("Movie with given id has not been found.");
        }

        return result.get();
    }

    /**
     * Find movies by title.
     *
     * curl -X GET -i http://localhost:8080/pa165/rest/movies/find-by-title?title=Dune
     *
     * @param title title of movies to be found
     * @return list of detailed DTO of movies with the given method
     */
    @RequestMapping(value = "/find-by-title", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final List<MovieDetailedDTO> findByTitle(@RequestParam String title) {
        logger.debug("rest findByTitle({})", title);

        List<MovieDetailedDTO> result;
        try {
            result = movieFacade.findByTitle(title);
        } catch (FacadeLayerException e) {
            throw new DataSourceException("Problem with the data source occurred.", e);
        } catch (IllegalArgumentException e) {
            throw new InvalidParameterException("Given parameters were invalid.", e);
        }

        return result;
    }

    /**
     * Update the given movie.
     *
     * curl -X PUT -i -H "Content-Type: application/json" --data '{"id": "1", "title": "Dune", "releaseYear": "2021-10-01", "genres": ["SCIFI", "ACTION"]}' http://localhost:8080/pa165/rest/movies/update
     *
     * @param movieDetailedDTO detailed DTO of the movie to be updated
     * @return detailed DTO of the movie after update
     */
    @RequestMapping(value = "/update", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final MovieDetailedDTO update(@RequestBody MovieDetailedDTO movieDetailedDTO) {
        logger.debug("rest update({})", movieDetailedDTO);

        try {
            return movieFacade.update(movieDetailedDTO);
        } catch (FacadeLayerException e) {
            throw new DataSourceException("Problem with the data source occurred.", e);
        } catch (IllegalArgumentException e) {
            throw new InvalidParameterException("Given parameters were invalid.", e);
        }
    }

    /**
     * Remove the given movie from the data source.
     *
     * curl -X DELETE -i -H "Content-Type: application/json" --data '{"id": "1"}' http://localhost:8080/pa165/rest/movies/remove
     *
     * @param movieDTO movie to be removed
     */
    @RequestMapping(value = "/remove", method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public final void remove(@RequestBody MovieDTO movieDTO) {
        logger.debug("rest remove({})", movieDTO);

        try {
            movieFacade.remove(movieDTO);
        } catch (FacadeLayerException e) {
            throw new DataSourceException("Problem with the data source occurred.", e);
        } catch (IllegalArgumentException e) {
            throw new InvalidParameterException("Given parameters were invalid.", e);
        }
    }
}
