package cz.muni.fi.pa165;

import cz.muni.fi.pa165.entity.Movie;
import cz.muni.fi.pa165.entity.Rating;

import java.math.BigDecimal;

/**
 * An interface that defines a service for computing scores.
 *
 * @author Kristian Tkacik
 */
public interface ScoreComputationService {

    BigDecimal getOverallScoreForRating(Rating rating);

    BigDecimal getOverallScoreForMovie(Movie movie);

    BigDecimal getOriginalityScoreForMovie(Movie movie);

    BigDecimal getSoundtrackScoreForMovie(Movie movie);

    BigDecimal getNarrativeScoreForMovie(Movie movie);

    BigDecimal getCinematographyScoreForMovie(Movie movie);

    BigDecimal getDepthScoreForMovie(Movie movie);
}
