package cz.muni.fi.pa165.facade;

import cz.fi.muni.pa165.dto.MovieCreateDTO;
import cz.fi.muni.pa165.dto.MovieDTO;
import cz.fi.muni.pa165.dto.MovieDetailedDTO;
import cz.fi.muni.pa165.dto.PersonDTO;
import cz.fi.muni.pa165.facade.MovieFacade;
import cz.muni.fi.pa165.MovieService;
import cz.muni.fi.pa165.entity.Movie;
import cz.muni.fi.pa165.entity.Person;
import cz.muni.fi.pa165.exceptions.ServiceLayerException;
import cz.muni.fi.pa165.mapper.MovieMapper;
import cz.muni.fi.pa165.mapper.PersonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;

/**
 * @author Radoslav Chudovsky
 */
@Service
@Transactional
public class MovieFacadeImpl implements MovieFacade {

    private final MovieService movieService;

    private final MovieMapper movieMapper;

    private final PersonMapper personMapper;

    @Autowired
    public MovieFacadeImpl(MovieService movieService, MovieMapper movieMapper, PersonMapper personMapper) {
        this.movieService = movieService;
        this.movieMapper = movieMapper;
        this.personMapper = personMapper;
    }

    @Override
    public MovieDetailedDTO create(MovieCreateDTO movieCreateDTO) {
        if (movieCreateDTO == null) {
            throw new IllegalArgumentException("MovieCreateDTO parameter is null.");
        }

        try {
            Movie movie = movieMapper.movieCreateDTOToMovie(movieCreateDTO);
            Movie created = movieService.create(movie);
            return movieMapper.movieToMovieDetailedDTO(created);
        } catch (ServiceLayerException e) {
            return null;
        }
    }

    @Override
    public MovieDetailedDTO findById(Long id) {
        try {
            Movie movie = movieService.findById(id);
            if (movie == null) {
                return null;
            }
            return movieMapper.movieToMovieDetailedDTO(movie);
        } catch (ServiceLayerException e) {
            return null;
        }
    }

    @Override
    public List<MovieDetailedDTO> findByTitle(String title) {
        try {
            var movies = movieService.findByTitle(title);
            return movieMapper.movieListToMovieDetailedDTOList(movies);
        } catch (ServiceLayerException e) {
            return Collections.emptyList();
        }

    }

    @Override
    public MovieDetailedDTO update(MovieDetailedDTO movieDetailedDTO) {
        if (movieDetailedDTO == null) {
            throw new IllegalArgumentException("MovieDetailedDTO parameter is null.");
        }

        try {
            Movie movie = movieMapper.movieDetailedDTOToMovie(movieDetailedDTO);
            Movie updated = movieService.update(movie);
            return movieMapper.movieToMovieDetailedDTO(updated);
        } catch (ServiceLayerException e) {
            return null;
        }
    }

    @Override
    public MovieDetailedDTO addActor(MovieDTO movieDTO, PersonDTO personDTO) {
        if (movieDTO == null) {
            throw new IllegalArgumentException("MovieDTO parameter is null.");
        }

        if (personDTO == null) {
            throw new IllegalArgumentException("PersonDTO parameter is null.");
        }

        try {
            Movie movie = movieMapper.movieDTOToMovie(movieDTO);
            Person actor = personMapper.personDTOToPerson(personDTO);

            movie.addActor(actor);
            Movie updated = movieService.update(movie);

            return movieMapper.movieToMovieDetailedDTO(updated);
        } catch (ServiceLayerException e) {
            return null;
        }
    }

    @Override
    public MovieDetailedDTO addDirector(MovieDTO movieDTO, PersonDTO personDTO) {
        if (movieDTO == null) {
            throw new IllegalArgumentException("MovieDTO parameter is null.");
        }

        if (personDTO == null) {
            throw new IllegalArgumentException("PersonDTO parameter is null.");
        }

        try {
            Movie movie = movieMapper.movieDTOToMovie(movieDTO);
            Person director = personMapper.personDTOToPerson(personDTO);

            movie.addDirector(director);
            Movie updated = movieService.update(movie);

            return movieMapper.movieToMovieDetailedDTO(updated);
        } catch (ServiceLayerException e) {
            return null;
        }
    }

    @Override
    public boolean remove(MovieDTO movieDTO) {
        if (movieDTO == null) {
            throw new IllegalArgumentException("MovieDTO parameter is null.");
        }

        try {
            Movie movie = movieMapper.movieDTOToMovie(movieDTO);
            movieService.remove(movie);
            return true;
        } catch (ServiceLayerException e) {
            return false;
        }
    }
}
