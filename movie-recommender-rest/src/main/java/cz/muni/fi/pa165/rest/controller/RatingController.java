package cz.muni.fi.pa165.rest.controller;

import cz.muni.fi.pa165.dto.movie.MovieDTO;
import cz.muni.fi.pa165.dto.rating.RatingCreateDTO;
import cz.muni.fi.pa165.dto.rating.RatingDTO;
import cz.muni.fi.pa165.dto.user.UserDTO;
import cz.muni.fi.pa165.exception.FacadeLayerException;
import cz.muni.fi.pa165.facade.RatingFacade;
import cz.muni.fi.pa165.rest.exception.DataSourceException;
import cz.muni.fi.pa165.rest.exception.InvalidParameterException;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.slf4j.LoggerFactory.*;

/**
 * Controller for managing ratings.
 *
 * @author Kristian Tkacik
 */
@RestController
@RequestMapping(value = "/ratings")
public class RatingController {

    final static Logger logger = getLogger(RatingController.class);

    private final RatingFacade ratingFacade;

    @Inject
    public RatingController(RatingFacade ratingFacade) {
        this.ratingFacade = ratingFacade;
    }

    /**
     * Create a given rating.
     *
     * curl -X POST -i -H "Content-Type: application/json" --data '{"user":{"id":1},"movie":{"id":1},"originality":1,"soundtrack":1,"narrative":1,"cinematography":1,"depth":1}' http://localhost:8080/pa165/rest/ratings/create
     *
     * @param ratingCreateDTO rating to be created
     * @return DTO of the rating after create operation
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public final RatingDTO create(@RequestBody RatingCreateDTO ratingCreateDTO) {
        logger.debug("rest create({})", ratingCreateDTO);

        try {
            return ratingFacade.create(ratingCreateDTO);
        } catch (FacadeLayerException e) {
            throw new DataSourceException("The instance already exists.", e);
        } catch (IllegalArgumentException e) {
            throw new InvalidParameterException("Given parameters were invalid.", e);
        }
    }

    /**
     * Update the given rating.
     *
     * curl -X PUT -i -H "Content-Type: application/json" --data '{"id":1,"user":{"id":1},"movie":{"id":1},"originality":5,"soundtrack":5,"narrative":5,"cinematography":5,"depth":5}' http://localhost:8080/pa165/rest/ratings/update
     *
     * @param ratingDTO DTO of the rating to be updated
     * @return DTO of the rating after update operation
     */
    @RequestMapping(value = "/update", method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final RatingDTO update(@RequestBody RatingDTO ratingDTO) {
        logger.debug("rest update({})", ratingDTO);

        try {
            return ratingFacade.update(ratingDTO);
        } catch (FacadeLayerException e) {
            throw new DataSourceException("Problem with the data source occurred.", e);
        } catch (IllegalArgumentException e) {
            throw new InvalidParameterException("Given parameters were invalid.", e);
        }
    }

    /**
     * Remove the given rating from the data source.
     *
     * curl -X DELETE -i -H "Content-Type: application/json" --data '{"id":1}' http://localhost:8080/pa165/rest/ratings/remove
     *
     * @param ratingDTO rating to be removed
     */
    @RequestMapping(value = "/remove", method = RequestMethod.DELETE,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final void remove(@RequestBody RatingDTO ratingDTO) {
        logger.debug("rest remove({})", ratingDTO);

        try {
            ratingFacade.remove(ratingDTO);
        } catch (FacadeLayerException e) {
            throw new DataSourceException("Problem with the data source occurred.", e);
        } catch (IllegalArgumentException e) {
            throw new InvalidParameterException("Given parameters were invalid.", e);
        }
    }

    /**
     * Find a rating by its id.
     *
     * curl -X GET -i http://localhost:8080/pa165/rest/ratings/1
     *
     * @param id id of the rating to be found
     * @return DTO of the found rating
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final RatingDTO findById(@PathVariable("id") Long id) {
        logger.debug("rest findById({})", id);

        Optional<RatingDTO> result;
        try {
            result = ratingFacade.findById(id);
        } catch (FacadeLayerException e) {
            throw new DataSourceException("Problem with the data source occurred.", e);
        } catch (IllegalArgumentException e) {
            throw new InvalidParameterException("Given parameters were invalid.", e);
        }

        if (result.isEmpty()) {
            throw new InvalidParameterException("Rating with given id has not been found.");
        }

        return result.get();
    }

    /**
     * Find ratings by their user.
     *
     * curl -X GET -i http://localhost:8080/pa165/rest/ratings/find-by-user?id=1
     *
     * @param id of the user to find ratings by
     * @return list of ratingDTOs of the found ratings
     */
    @RequestMapping(value = "/find-by-user", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final List<RatingDTO> findByUser(@RequestParam Long id) {
        logger.debug("rest findByUser({})", id);

        UserDTO userDTO = new UserDTO();
        userDTO.setId(id);
        List<RatingDTO> result;
        try {
            result = ratingFacade.findByUser(userDTO);
        } catch (FacadeLayerException e) {
            throw new DataSourceException("Problem with the data source occurred.", e);
        } catch (IllegalArgumentException e) {
            throw new InvalidParameterException("Given parameters were invalid.", e);
        }

        return result;
    }

    /**
     * Find ratings by their movie.
     *
     * curl -X GET -i http://localhost:8080/pa165/rest/ratings/find-by-movie?id=1
     *
     * @param id of the user to find ratings by
     * @return list of ratingDTOs of the found ratings
     */
    @RequestMapping(value = "/find-by-movie", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final List<RatingDTO> findByMovie(@RequestParam Long id) {
        logger.debug("rest findByMovie({})", id);

        MovieDTO movieDTO = new MovieDTO();
        movieDTO.setId(id);
        List<RatingDTO> result;
        try {
            result = ratingFacade.findByMovie(movieDTO);
        } catch (FacadeLayerException e) {
            throw new DataSourceException("Problem with the data source occurred.", e);
        } catch (IllegalArgumentException e) {
            throw new InvalidParameterException("Given parameters were invalid.", e);
        }

        return result;
    }

    /**
     * Get overall score for the given rating.
     *
     * curl -X GET -i http://localhost:8080/pa165/rest/ratings/1/overall-score
     *
     * @param id of the rating to get overall score for
     * @return the overall score of the rating
     */
    @RequestMapping(value = "/{id}/overall-score", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final BigDecimal getOverallScore(@PathVariable("id") Long id) {
        logger.debug("rest getOverallScore({})", id);

        RatingDTO ratingDTO = new RatingDTO();
        ratingDTO.setId(id);
        try {
            return ratingFacade.getOverallScore(ratingDTO);
        } catch (FacadeLayerException e) {
            throw new DataSourceException("Problem with the data source occurred.", e);
        } catch (IllegalArgumentException e) {
            throw new InvalidParameterException("Given parameters were invalid.", e);
        }
    }
}
