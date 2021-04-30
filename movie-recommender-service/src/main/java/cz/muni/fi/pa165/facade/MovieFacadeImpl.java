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
import java.util.List;
import java.util.Optional;

/**
 * Implementation of MovieFacade.
 *
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
    public Optional<MovieDetailedDTO> create(MovieCreateDTO movieCreateDTO) {
        if (movieCreateDTO == null) {
            throw new IllegalArgumentException("MovieCreateDTO parameter is null.");
        }

        try {
            Movie movie = movieMapper.movieCreateDTOToMovie(movieCreateDTO);
            Movie created = movieService.create(movie);
            return Optional.ofNullable(movieMapper.movieToMovieDetailedDTO(created));
        } catch (ServiceLayerException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<MovieDetailedDTO> findById(Long id) {
        try {
            Movie movie = movieService.findById(id);
            return Optional.ofNullable(movieMapper.movieToMovieDetailedDTO(movie));
        } catch (ServiceLayerException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<List<MovieDetailedDTO>> findByTitle(String title) {
        try {
            List<Movie> movies = movieService.findByTitle(title);
            return Optional.ofNullable(movieMapper.movieListToMovieDetailedDTOList(movies));
        } catch (ServiceLayerException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<MovieDetailedDTO> update(MovieDetailedDTO movieDetailedDTO) {
        if (movieDetailedDTO == null) {
            throw new IllegalArgumentException("MovieDetailedDTO parameter is null.");
        }

        try {
            Movie movie = movieMapper.movieDetailedDTOToMovie(movieDetailedDTO);
            Movie updated = movieService.update(movie);
            return Optional.ofNullable(movieMapper.movieToMovieDetailedDTO(updated));
        } catch (ServiceLayerException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<MovieDetailedDTO> addActor(MovieDTO movieDTO, PersonDTO personDTO) {
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

            return Optional.ofNullable(movieMapper.movieToMovieDetailedDTO(updated));
        } catch (ServiceLayerException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<MovieDetailedDTO> addDirector(MovieDTO movieDTO, PersonDTO personDTO) {
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

            return Optional.ofNullable(movieMapper.movieToMovieDetailedDTO(updated));
        } catch (ServiceLayerException e) {
            return Optional.empty();
        }
    }

    @Override
    public Boolean remove(MovieDTO movieDTO) {
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
