package cz.muni.fi.pa165.facade;

import cz.muni.fi.pa165.dto.MovieCreateDTO;
import cz.muni.fi.pa165.dto.MovieDTO;
import cz.muni.fi.pa165.dto.MovieDetailedDTO;
import cz.muni.fi.pa165.dto.PersonDTO;
import cz.muni.fi.pa165.MovieService;
import cz.muni.fi.pa165.RecommendationService;
import cz.muni.fi.pa165.ScoreComputationService;
import cz.muni.fi.pa165.entity.Movie;
import cz.muni.fi.pa165.entity.Person;
import cz.muni.fi.pa165.exceptions.ServiceLayerException;
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
    public Optional<MovieDetailedDTO> create(MovieCreateDTO movieCreateDTO) {
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
        try {
            Movie movie = movieMapper.movieDTOToMovie(movieDTO);
            movieService.remove(movie);
            return true;
        } catch (ServiceLayerException e) {
            return false;
        }
    }

    @Override
    public Optional<BigDecimal> getOverallScore(MovieDTO movieDTO) {
        try {
            Movie movie = movieMapper.movieDTOToMovie(movieDTO);
            return Optional.of(scoreService.getOverallScoreForMovie(movie));
        } catch (ServiceLayerException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<BigDecimal> getOriginalityScore(MovieDTO movieDTO) {
        try {
            Movie movie = movieMapper.movieDTOToMovie(movieDTO);
            return Optional.of(scoreService.getOriginalityScoreForMovie(movie));
        } catch (ServiceLayerException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<BigDecimal> getSoundtrackScore(MovieDTO movieDTO) {
        try {
            Movie movie = movieMapper.movieDTOToMovie(movieDTO);
            return Optional.of(scoreService.getSoundtrackScoreForMovie(movie));
        } catch (ServiceLayerException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<BigDecimal> getNarrativeScore(MovieDTO movieDTO) {
        try {
            Movie movie = movieMapper.movieDTOToMovie(movieDTO);
            return Optional.of(scoreService.getNarrativeScoreForMovie(movie));
        } catch (ServiceLayerException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<BigDecimal> getCinematographyScore(MovieDTO movieDTO) {
        try {
            Movie movie = movieMapper.movieDTOToMovie(movieDTO);
            return Optional.of(scoreService.getCinematographyScoreForMovie(movie));
        } catch (ServiceLayerException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<BigDecimal> getDepthScore(MovieDTO movieDTO) {
        try {
            Movie movie = movieMapper.movieDTOToMovie(movieDTO);
            return Optional.of(scoreService.getDepthScoreForMovie(movie));
        } catch (ServiceLayerException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<List<MovieDetailedDTO>> getRecommendations(MovieDTO movieDTO, Integer n) {
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

            var recommendationsDTO = movieMapper.movieListToMovieDetailedDTOList(recommendations);
            return Optional.ofNullable(recommendationsDTO);

        } catch (ServiceLayerException e) {
            return Optional.empty();
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
