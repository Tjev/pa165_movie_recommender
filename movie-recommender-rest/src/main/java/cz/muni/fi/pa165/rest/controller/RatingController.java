package cz.muni.fi.pa165.rest.controller;

import cz.muni.fi.pa165.dto.movie.MovieDTO;
import cz.muni.fi.pa165.dto.rating.RatingCreateDTO;
import cz.muni.fi.pa165.dto.rating.RatingDTO;
import cz.muni.fi.pa165.dto.user.UserDTO;
import cz.muni.fi.pa165.facade.RatingFacade;
import org.slf4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;

import java.math.BigDecimal;
import java.util.List;

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
     * @param ratingCreateDTO person to be created
     * @return DTO of the rating after create operation
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final RatingDTO create(@RequestBody RatingCreateDTO ratingCreateDTO) {
        logger.debug("rest create({})", ratingCreateDTO);

        return ratingFacade.create(ratingCreateDTO).get();
    }

    /**
     * Update the given rating.
     *
     * @param ratingDTO DTO of the person to be updated
     * @return DTO of the rating after update operation
     */
    @RequestMapping(value = "/update", method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final RatingDTO update(@RequestBody RatingDTO ratingDTO) {
        logger.debug("rest update({})", ratingDTO);

        return ratingFacade.update(ratingDTO).get();
    }

    /**
     * Remove the given person from the data source.
     *
     * @param ratingDTO rating to be removed
     */
    @RequestMapping(value = "/remove", method = RequestMethod.DELETE,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final void remove(@RequestBody RatingDTO ratingDTO) {
        logger.debug("rest remove({})", ratingDTO);

        ratingFacade.remove(ratingDTO);
    }

    /**
     * Find a rating by its id.
     *
     * @param id id of the rating to be found
     * @return DTO of the found rating
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final RatingDTO findById(@PathVariable("id") long id) {
        logger.debug("rest findById({})", id);

        return ratingFacade.findById(id).get();
    }

    /**
     * Find a ratings by their user.
     *
     * @param userDTO of the user to find ratings by
     * @return list of ratingDTOs of the found ratings
     */
    @RequestMapping(value = "/find", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final List<RatingDTO> findByUser(@RequestBody UserDTO userDTO) {
        logger.debug("rest findByUser({})", userDTO);

        return ratingFacade.findByUser(userDTO).get();
    }

    /**
     * Find a ratings by their movie.
     *
     * @param movieDTO of the user to find ratings by
     * @return list of ratingDTOs of the found ratings
     */
    @RequestMapping(value = "/find", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final List<RatingDTO> findByMovie(@RequestBody MovieDTO movieDTO) {
        logger.debug("rest findByMovie({})", movieDTO);

        return ratingFacade.findByMovie(movieDTO).get();
    }

    /**
     * Get overall score for the given rating.
     *
     * @param ratingDTO of the rating to get overall score for
     * @return the overall score of the rating
     */
    @RequestMapping(value = "/overall_score", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final BigDecimal getOverallScore(@RequestBody RatingDTO ratingDTO) {
        logger.debug("rest getOverallScore({})", ratingDTO);

        return ratingFacade.getOverallScore(ratingDTO).get();
    }
}
