package cz.muni.fi.pa165.facade;

import cz.muni.fi.pa165.dto.movie.MovieCreateDTO;
import cz.muni.fi.pa165.dto.movie.MovieDTO;
import cz.muni.fi.pa165.dto.movie.MovieDetailedDTO;
import cz.muni.fi.pa165.dto.person.PersonDTO;
import cz.muni.fi.pa165.exception.FacadeLayerException;
import cz.muni.fi.pa165.service.MovieService;
import cz.muni.fi.pa165.service.RecommendationService;
import cz.muni.fi.pa165.service.ScoreComputationService;
import cz.muni.fi.pa165.entity.Movie;
import cz.muni.fi.pa165.entity.Person;
import cz.muni.fi.pa165.exception.ServiceLayerException;
import cz.muni.fi.pa165.mapper.MovieMapper;
import cz.muni.fi.pa165.mapper.PersonMapper;
import javax.inject.Inject;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.*;

/**
 * Implementation of {@link MovieFacade}.
 *
 * @author Radoslav Chudovsky
 */
@Service
@Transactional
public class MovieFacadeImpl implements MovieFacade {

    private final MovieService movieService;

    private final RecommendationService recommendationService;

    private final ScoreComputationService scoreService;

    private final MovieMapper movieMapper;

    private final PersonMapper personMapper;

    @Inject
    public MovieFacadeImpl(MovieService movieService, RecommendationService recommendationService,
                           ScoreComputationService scoreService, MovieMapper movieMapper, PersonMapper personMapper) {
        this.movieService = movieService;
        this.recommendationService = recommendationService;
        this.scoreService = scoreService;
        this.movieMapper = movieMapper;
        this.personMapper = personMapper;
    }

    @Override
    public MovieDetailedDTO create(MovieCreateDTO movieCreateDTO) {
        try {
            Movie movie = movieMapper.movieCreateDTOToMovie(movieCreateDTO);
            Movie created = movieService.create(movie);
            return movieMapper.movieToMovieDetailedDTO(created);
        } catch (ServiceLayerException e) {
            throw new FacadeLayerException("Error at service layer occurred", e);
        }
    }

    @Override
    public Optional<MovieDetailedDTO> findById(Long id) {
        try {
            Movie movie = movieService.findById(id);
            return Optional.ofNullable(movieMapper.movieToMovieDetailedDTO(movie));
        } catch (ServiceLayerException e) {
            throw new FacadeLayerException("Error at service layer occurred", e);
        }
    }

    @Override
    public List<MovieDetailedDTO> findByTitle(String title) {
        try {
            List<Movie> movies = movieService.findByTitle(title);
            return movieMapper.movieListToMovieDetailedDTOList(movies);
        } catch (ServiceLayerException e) {
            throw new FacadeLayerException("Error at service layer occurred", e);
        }
    }

    @Override
    public MovieDetailedDTO update(MovieDetailedDTO movieDetailedDTO) {
        try {
            Movie movie = movieMapper.movieDetailedDTOToMovie(movieDetailedDTO);
            Movie updated = movieService.update(movie);
            return movieMapper.movieToMovieDetailedDTO(updated);
        } catch (ServiceLayerException e) {
            throw new FacadeLayerException("Error at service layer occurred", e);
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
            throw new FacadeLayerException("Error at service layer occurred", e);
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
            throw new FacadeLayerException("Error at service layer occurred", e);
        }
    }

    @Override
    public void remove(MovieDTO movieDTO) {
        try {
            Movie movie = movieMapper.movieDTOToMovie(movieDTO);
            movieService.remove(movie);
        } catch (ServiceLayerException e) {
            throw new FacadeLayerException("Error at service layer occurred", e);
        }
    }

    @Override
    public BigDecimal getOverallScore(MovieDTO movieDTO) {
        try {
            Movie movie = movieMapper.movieDTOToMovie(movieDTO);
            return scoreService.getOverallScoreForMovie(movie);
        } catch (ServiceLayerException e) {
            throw new FacadeLayerException("Error at service layer occurred", e);
        }
    }

    @Override
    public BigDecimal getOriginalityScore(MovieDTO movieDTO) {
        try {
            Movie movie = movieMapper.movieDTOToMovie(movieDTO);
            return scoreService.getOriginalityScoreForMovie(movie);
        } catch (ServiceLayerException e) {
            throw new FacadeLayerException("Error at service layer occurred", e);
        }
    }

    @Override
    public BigDecimal getSoundtrackScore(MovieDTO movieDTO) {
        try {
            Movie movie = movieMapper.movieDTOToMovie(movieDTO);
            return scoreService.getSoundtrackScoreForMovie(movie);
        } catch (ServiceLayerException e) {
            throw new FacadeLayerException("Error at service layer occurred", e);
        }
    }

    @Override
    public BigDecimal getNarrativeScore(MovieDTO movieDTO) {
        try {
            Movie movie = movieMapper.movieDTOToMovie(movieDTO);
            return scoreService.getNarrativeScoreForMovie(movie);
        } catch (ServiceLayerException e) {
            throw new FacadeLayerException("Error at service layer occurred", e);
        }
    }

    @Override
    public BigDecimal getCinematographyScore(MovieDTO movieDTO) {
        try {
            Movie movie = movieMapper.movieDTOToMovie(movieDTO);
            return scoreService.getCinematographyScoreForMovie(movie);
        } catch (ServiceLayerException e) {
            throw new FacadeLayerException("Error at service layer occurred", e);
        }
    }

    @Override
    public BigDecimal getDepthScore(MovieDTO movieDTO) {
        try {
            Movie movie = movieMapper.movieDTOToMovie(movieDTO);
            return scoreService.getDepthScoreForMovie(movie);
        } catch (ServiceLayerException e) {
            throw new FacadeLayerException("Error at service layer occurred", e);
        }
    }

    @Override
    public List<MovieDetailedDTO> getRecommendations(MovieDTO movieDTO, Integer n) {
        try {
            Movie movie = movieMapper.movieDTOToMovie(movieDTO);
            var moviesSimilarGenres = recommendationService.getRecommendationsBasedOnGenres(movie);
            var moviesSimilarViewers = recommendationService.getRecommendationsBasedOnUsers(movie);

            List<Movie> recommendations = getSortedIntersection(moviesSimilarGenres, moviesSimilarViewers);

            if (recommendations.size() < n) {
                recommendations.addAll(getSortedDisjunctiveUnion(moviesSimilarGenres, moviesSimilarViewers));
            }

            if (recommendations.size() > n) {
                recommendations = recommendations.subList(0, n);
            }

            return movieMapper.movieListToMovieDetailedDTOList(recommendations);

        } catch (ServiceLayerException e) {
            throw new FacadeLayerException("Error at service layer occurred", e);
        }
    }

    private List<Movie> getSortedIntersection(List<Movie> l1, List<Movie> l2) {
        List<Movie> intersection = new ArrayList<>();

        for (var movie : l1) {
            if (l2.contains(movie)) {
                intersection.add(movie);
            }
        }

        intersection.sort(
                Comparator.comparing(scoreService::getOverallScoreForMovie)
        );

        return intersection;
    }

    private List<Movie> getSortedDisjunctiveUnion(List<Movie> l1, List<Movie> l2) {
        Set<Movie> set = new HashSet<>();

        set.addAll(l1);
        set.addAll(l2);

        set.removeAll(getSortedIntersection(l1, l2));

        List<Movie> disjunctiveUnion = new ArrayList<>(set);
        disjunctiveUnion.sort(
                Comparator.comparing(scoreService::getOverallScoreForMovie)
        );

        return disjunctiveUnion;
    }
}
