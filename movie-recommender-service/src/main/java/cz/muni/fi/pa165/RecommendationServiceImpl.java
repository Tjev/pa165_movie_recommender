package cz.muni.fi.pa165;

import cz.muni.fi.pa165.dao.MovieDao;
import cz.muni.fi.pa165.dao.RatingDao;
import cz.muni.fi.pa165.entity.Genre;
import cz.muni.fi.pa165.entity.Movie;
import cz.muni.fi.pa165.entity.Rating;
import cz.muni.fi.pa165.entity.User;
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
        Movie persistedMovie = movieDao.findById(movie.getId());

        List<User> users = ratingDao.findByMovie(persistedMovie).stream()
                .map(Rating::getUser)
                .collect(Collectors.toList());

        List<Movie> movies = new ArrayList<>();
        for (var user : users) {
            List<Rating> ratings = ratingDao.findByUser(user);
            for (var rating : ratings) {
                movies.add(rating.getMovie());
            }
        }
        return movies;
    }

    @Override
    public List<Movie> getRecommendationsBasedOnGenres(Movie movie) {
        Movie persistedMovie = movieDao.findById(movie.getId());

        List<Movie> movies = movieDao.findAll();
        Set<Genre> criteriaGenres = persistedMovie.getGenres();
        List<Movie> candidates = new ArrayList<>();

        for (Movie m : movies) {
            Set<Genre> candidateGenres = new HashSet<>(m.getGenres());
            candidateGenres.retainAll(criteriaGenres);
            if (!candidateGenres.isEmpty()) {
                candidates.add(m);
            }
        }

        return candidates;
    }
}
