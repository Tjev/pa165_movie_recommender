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
        Set<Rating> ratings = getRatings(movie);

        BigDecimal score = BigDecimal.ZERO;

        for (Rating rating : ratings) {
            score = score.add(getOverallScoreForRating(rating));
        }

        return score.divide(new BigDecimal(ratings.size()), RoundingMode.HALF_EVEN);
    }

    @Override
    public BigDecimal getOriginalityScoreForMovie(Movie movie) {
        Set<Rating> ratings = getRatings(movie);
        int score = ratings.stream().mapToInt(Rating::getOriginality).sum();

        return new BigDecimal(score).divide(new BigDecimal(ratings.size()), RoundingMode.HALF_EVEN);
    }

    @Override
    public BigDecimal getSoundtrackScoreForMovie(Movie movie) {
        Set<Rating> ratings = getRatings(movie);
        int score = ratings.stream().mapToInt(Rating::getSoundtrack).sum();

        return new BigDecimal(score).divide(new BigDecimal(ratings.size()), RoundingMode.HALF_EVEN);
    }

    @Override
    public BigDecimal getNarrativeScoreForMovie(Movie movie) {
        Set<Rating> ratings = getRatings(movie);
        int score = ratings.stream().mapToInt(Rating::getNarrative).sum();

        return new BigDecimal(score).divide(new BigDecimal(ratings.size()), RoundingMode.HALF_EVEN);
    }

    @Override
    public BigDecimal getCinematographyScoreForMovie(Movie movie) {
        Set<Rating> ratings = getRatings(movie);
        int score = ratings.stream().mapToInt(Rating::getCinematography).sum();

        return new BigDecimal(score).divide(new BigDecimal(ratings.size()), RoundingMode.HALF_EVEN);
    }

    @Override
    public BigDecimal getDepthScoreForMovie(Movie movie) {
        Set<Rating> ratings = getRatings(movie);
        int score = ratings.stream().mapToInt(Rating::getDepth).sum();

        return new BigDecimal(score).divide(new BigDecimal(ratings.size()), RoundingMode.HALF_EVEN);
    }

    private Set<Rating> getRatings(Movie movie) {
        return movieDao.findById(movie.getId()).getRatings();
    }
}
