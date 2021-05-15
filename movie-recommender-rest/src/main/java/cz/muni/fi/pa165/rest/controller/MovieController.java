package cz.muni.fi.pa165.rest.controller;

import cz.muni.fi.pa165.dto.movie.MovieCreateDTO;
import cz.muni.fi.pa165.dto.movie.MovieDTO;
import cz.muni.fi.pa165.dto.movie.MovieDetailedDTO;
import cz.muni.fi.pa165.facade.MovieFacade;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.List;

@RestController
@RequestMapping("movies")
public class MovieController {

    @Inject
    private MovieFacade movieFacade;

    @RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final MovieDetailedDTO create(@RequestBody MovieCreateDTO movieCreateDTO) {
        var detailedDTO = movieFacade.create(movieCreateDTO);
        return detailedDTO.get();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final MovieDetailedDTO findById(@PathVariable long id) {
        var movieDetailedDTO = movieFacade.findById(id);
        return movieDetailedDTO.get();
    }

    @RequestMapping(value = "/{title}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final List<MovieDetailedDTO> findByTitle(@PathVariable String title) {
        var movieDetailedDTO = movieFacade.findByTitle(title);
        return movieDetailedDTO.get();
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final MovieDetailedDTO update(@RequestBody MovieDetailedDTO movieDetailedDTO) {
        var updated = movieFacade.update(movieDetailedDTO);
        return updated.get();
    }

    @RequestMapping(value = "/remove", method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public final void remove(@RequestBody MovieDTO movieDTO) {
        movieFacade.remove(movieDTO);
    }
}
