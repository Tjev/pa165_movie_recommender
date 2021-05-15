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

@RestController
@RequestMapping("movies")
public class MovieController {

    final static Logger logger = getLogger(MovieController.class);

    @Inject
    private MovieFacade movieFacade;

    @RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final MovieDetailedDTO create(@RequestBody MovieCreateDTO movieCreateDTO) {
        logger.debug("rest create({})", movieCreateDTO);

        var detailedDTO = movieFacade.create(movieCreateDTO);
        return detailedDTO.get();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final MovieDetailedDTO findById(@PathVariable long id) {
        logger.debug("rest findById({})", id);

        var movieDetailedDTO = movieFacade.findById(id);
        return movieDetailedDTO.get();
    }

    @RequestMapping(value = "/{title}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final List<MovieDetailedDTO> findByTitle(@PathVariable String title) {
        logger.debug("rest findByTitle({})", title);

        var movieDetailedDTO = movieFacade.findByTitle(title);
        return movieDetailedDTO.get();
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final MovieDetailedDTO update(@RequestBody MovieDetailedDTO movieDetailedDTO) {
        logger.debug("rest update({})", movieDetailedDTO);

        var updated = movieFacade.update(movieDetailedDTO);
        return updated.get();
    }

    @RequestMapping(value = "/remove", method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public final void remove(@RequestBody MovieDTO movieDTO) {
        logger.debug("rest remove({})", movieDTO);

        movieFacade.remove(movieDTO);
    }
}
