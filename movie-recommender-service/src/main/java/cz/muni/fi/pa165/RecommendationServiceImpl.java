package cz.muni.fi.pa165;

import cz.muni.fi.pa165.dao.MovieDao;
import cz.muni.fi.pa165.entity.Genre;
import cz.muni.fi.pa165.entity.Movie;
import cz.muni.fi.pa165.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class RecommendationServiceImpl implements RecommendationService {

    private final MovieDao movieDao;

    @Autowired
    public RecommendationServiceImpl(MovieDao movieDao) {
        this.movieDao = movieDao;
    }

    @Override
    public List<Movie> getRecommendations(User user, int n) {
        return null;
    }

    @Override
    public List<Movie> getRecommendations(Movie movie, int n) {
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

        return candidates.subList(0, n);
    }
}
