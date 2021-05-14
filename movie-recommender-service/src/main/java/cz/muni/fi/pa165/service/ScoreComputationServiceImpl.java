package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.dao.MovieDao;
import cz.muni.fi.pa165.dao.RatingDao;
import cz.muni.fi.pa165.entity.Movie;
import cz.muni.fi.pa165.entity.Rating;
import cz.muni.fi.pa165.exception.ServiceLayerException;
import javax.inject.Inject;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.ToIntFunction;

/**
 * Implementation of ScoreComputationService.
 *
 * @author Kristian Tkacik
 */
@Service
public class ScoreComputationServiceImpl implements ScoreComputationService {

    private final RatingDao ratingDao;

    private final MovieDao movieDao;

    @Inject
    public ScoreComputationServiceImpl(RatingDao ratingDao, MovieDao movieDao) {
        this.ratingDao = ratingDao;
        this.movieDao = movieDao;
    }

    @Override
    public BigDecimal getOverallScoreForRating(Rating rating) {
        if (rating == null) {
            throw new IllegalArgumentException("Rating is null.");
        }

        Rating persistedRating;
        try {
            persistedRating = ratingDao.findById(rating.getId());
        } catch (Exception e) {
            throw new ServiceLayerException("Error occurred while finding given rating by its id.", e);
        }

        if (persistedRating == null) {
            throw new IllegalArgumentException("Rating is not in the database.");
        }

        return new BigDecimal(
                        persistedRating.getOriginality() +
                        persistedRating.getSoundtrack() +
                        persistedRating.getNarrative() +
                        persistedRating.getCinematography() +
                        persistedRating.getDepth()).divide(new BigDecimal("5"));
    }

    @Override
    public BigDecimal getOverallScoreForMovie(Movie movie) {
        List<Rating> ratings = getRatings(movie);

        BigDecimal score = BigDecimal.ZERO;

        for (Rating rating : ratings) {
            score = score.add(getOverallScoreForRating(rating));
        }

        return score.divide(new BigDecimal(ratings.size()));
    }

    @Override
    public BigDecimal getOriginalityScoreForMovie(Movie movie) {
        return computeAverage(movie, Rating::getOriginality);
    }

    @Override
    public BigDecimal getSoundtrackScoreForMovie(Movie movie) {
        return computeAverage(movie, Rating::getSoundtrack);
    }

    @Override
    public BigDecimal getNarrativeScoreForMovie(Movie movie) {
        return computeAverage(movie, Rating::getNarrative);
    }

    @Override
    public BigDecimal getCinematographyScoreForMovie(Movie movie) {
        return computeAverage(movie, Rating::getCinematography);
    }

    @Override
    public BigDecimal getDepthScoreForMovie(Movie movie) {
        return computeAverage(movie, Rating::getDepth);
    }

    private BigDecimal computeAverage(Movie movie, ToIntFunction<Rating> func) {
        List<Rating> ratings = getRatings(movie);

        int scoreSum = ratings.stream()
                .mapToInt(func)
                .sum();

        return new BigDecimal(scoreSum).divide(new BigDecimal(ratings.size()));
    }

    private List<Rating> getRatings(Movie movie) {
        if (movie == null) {
            throw new IllegalArgumentException("Movie is null.");
        }

        Movie persistedMovie;
        try {
            persistedMovie = movieDao.findById(movie.getId());
        } catch (Exception e) {
            throw new ServiceLayerException("Error occurred while finding movie by its id.", e);
        }

        if (persistedMovie == null) {
            throw new IllegalArgumentException("Movie is not in the database.");
        }

        List<Rating> ratings;
        try {
            ratings = ratingDao.findByMovie(movie);
        } catch (Exception e) {
            throw new ServiceLayerException("Error occurred while finding ratings for the given movie.", e);
        }
        return ratings;
    }
}
