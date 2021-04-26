package cz.muni.fi.pa165;

import cz.muni.fi.pa165.dao.MovieDao;
import cz.muni.fi.pa165.dao.RatingDao;
import cz.muni.fi.pa165.entity.Movie;
import cz.muni.fi.pa165.entity.Rating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

@Service
public class ScoreComputationServiceImpl implements ScoreComputationService {

    private final RatingDao ratingDao;

    private final MovieDao movieDao;

    @Autowired
    public ScoreComputationServiceImpl(RatingDao ratingDao, MovieDao movieDao) {
        this.ratingDao = ratingDao;
        this.movieDao = movieDao;
    }

    @Override
    public BigDecimal getOverallScoreForRating(Rating rating) {
        Rating persistedRating = ratingDao.findById(rating.getId());
        return new BigDecimal(
                        persistedRating.getOriginality() +
                        persistedRating.getSoundtrack() +
                        persistedRating.getNarrative() +
                        persistedRating.getCinematography() +
                        persistedRating.getDepth()).divide(new BigDecimal("5"), RoundingMode.HALF_EVEN);
    }

    @Override
    public BigDecimal getOverallScoreForMovie(Movie movie) {
        return null;
    }

    @Override
    public BigDecimal getOriginalityScoreForMovie(Movie movie) {
        Movie persistedMovie = movieDao.findById(movie.getId());
        Set<Rating> ratings = persistedMovie.getRatings();
        int score = ratings.stream().map(Rating::getOriginality).mapToInt(i -> i).sum();
        return new BigDecimal(score).divide(new BigDecimal(ratings.size()), RoundingMode.HALF_EVEN);
    }

    @Override
    public BigDecimal getSoundtrackScoreForMovie(Movie movie) {
        return null;
    }

    @Override
    public BigDecimal getNarrativeScoreForMovie(Movie movie) {
        return null;
    }

    @Override
    public BigDecimal getCinematographyScoreForMovie(Movie movie) {
        return null;
    }

    @Override
    public BigDecimal getDepthScoreForMovie(Movie movie) {
        return null;
    }
}
