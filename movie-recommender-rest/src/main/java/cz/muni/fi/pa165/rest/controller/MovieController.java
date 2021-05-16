package cz.muni.fi.pa165.rest.controller;

import cz.muni.fi.pa165.dto.movie.MovieCreateDTO;
import cz.muni.fi.pa165.dto.movie.MovieDTO;
import cz.muni.fi.pa165.dto.movie.MovieDetailedDTO;
import cz.muni.fi.pa165.facade.MovieFacade;
import org.slf4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.List;

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
    public final MovieDetailedDTO create(@RequestBody MovieCreateDTO movieCreateDTO) {
        logger.debug("rest create({})", movieCreateDTO);

        var detailedDTO = movieFacade.create(movieCreateDTO);
        return detailedDTO.get();
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

        var movieDetailedDTO = movieFacade.findById(id);
        return movieDetailedDTO.get();
    }

    /**
     * Find movies by name.
     *
     * curl -X GET -i http://localhost:8080/pa165/rest/movies/find?title=Dune
     *
     * @param title title of movies to be found
     * @return list of detailed DTOs of found movies
     */
    @RequestMapping(value = "/find", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final List<MovieDetailedDTO> findByTitle(@RequestParam String title) {
        logger.debug("rest findByTitle({})", title);

        var movieDetailedDTO = movieFacade.findByTitle(title);
        return movieDetailedDTO.get();
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

        var updated = movieFacade.update(movieDetailedDTO);
        return updated.get();
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

        movieFacade.remove(movieDTO);
    }
}
