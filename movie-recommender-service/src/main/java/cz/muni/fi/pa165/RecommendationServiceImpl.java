package cz.muni.fi.pa165;

import cz.muni.fi.pa165.dao.MovieDao;
import cz.muni.fi.pa165.dao.RatingDao;
import cz.muni.fi.pa165.entity.Genre;
import cz.muni.fi.pa165.entity.Movie;
import cz.muni.fi.pa165.entity.Rating;
import cz.muni.fi.pa165.entity.User;
import cz.muni.fi.pa165.exceptions.ServiceLayerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Implementation of RecommendationService.
 *
 * @author Jiri Papousek
 */
@Service
public class RecommendationServiceImpl implements RecommendationService {

    private final MovieDao movieDao;
    private final RatingDao ratingDao;

    @Autowired
    public RecommendationServiceImpl(MovieDao movieDao, RatingDao ratingDao) {
        this.movieDao = movieDao;
        this.ratingDao = ratingDao;
    }

    @Override
    public List<Movie> getRecommendationsBasedOnUsers(Movie movie) {
        if (movie == null) {
            throw new IllegalArgumentException("Given movie is null");
        }
        try {
            Movie persistedMovie = movieDao.findById(movie.getId());

            if (persistedMovie == null) {
                throw new IllegalArgumentException("Movie does not exist in the database.");
            }

            return findMoviesWithSameViewers(persistedMovie);

        } catch (IllegalArgumentException e) {
            throw e;
        }
        catch (Exception e) {
            throw new ServiceLayerException("Error while getting recommendations based on users.", e);
        }
    }

    @Override
    public List<Movie> getRecommendationsBasedOnGenres(Movie movie) {
        if (movie == null) {
            throw new IllegalArgumentException("Given movie parameter is null.");
        }

        try {
            Movie persistedMovie = movieDao.findById(movie.getId());

            if (persistedMovie == null) {
                throw new IllegalArgumentException("Movie does not exist in the database.");
            }

            List<Movie> allMovies = movieDao.findAll();
            Set<Genre> criteriaGenres = persistedMovie.getGenres();
            List<Movie> recommendations = new ArrayList<>();

            for (Movie candidateMovie : allMovies) {
                if (candidateMovie == movie || recommendations.contains(candidateMovie)) {
                    continue;
                }

                Set<Genre> candidateGenres = new HashSet<>(candidateMovie.getGenres());
                candidateGenres.retainAll(criteriaGenres);
                if (!candidateGenres.isEmpty()) {
                    recommendations.add(candidateMovie);
                }
            }

            return recommendations;

        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceLayerException("Error while getting recommendations based on genres.", e);
        }

    }

    private List<Movie> findMoviesWithSameViewers(Movie movie) {
        List<User> users = ratingDao.findByMovie(movie).stream()
                .map(Rating::getUser)
                .collect(Collectors.toList());

        List<Movie> movies = new ArrayList<>();
        for (var user : users) {
            List<Rating> ratings = ratingDao.findByUser(user);
            for (var rating : ratings) {
                var ratedMovie = rating.getMovie();
                if (!movies.contains(ratedMovie) && ratedMovie != movie) {
                    movies.add(rating.getMovie());
                }
            }
        }
        return movies;
    }
}
