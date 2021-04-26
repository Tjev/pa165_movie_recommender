package cz.muni.fi.pa165;

import cz.muni.fi.pa165.dao.MovieDao;
import cz.muni.fi.pa165.dao.RatingDao;
import cz.muni.fi.pa165.entity.Movie;
import cz.muni.fi.pa165.entity.Rating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Set;
import java.util.function.ToIntFunction;

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
        Set<Rating> ratings = movieDao.findById(movie.getId()).getRatings();

        BigDecimal score = BigDecimal.ZERO;

        for (Rating rating : ratings) {
            score = score.add(getOverallScoreForRating(rating));
        }

        return score.divide(new BigDecimal(ratings.size()), RoundingMode.HALF_EVEN);
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
        Set<Rating> ratings = movieDao.findById(movie.getId()).getRatings();

        int scoreSum = ratings.stream()
                .mapToInt(func)
                .sum();

        return new BigDecimal(scoreSum).divide(new BigDecimal(ratings.size()), RoundingMode.HALF_EVEN);
    }
}
