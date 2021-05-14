package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.entity.Movie;
import cz.muni.fi.pa165.entity.Rating;

import java.math.BigDecimal;

/**
 * An interface that defines a service for computing scores.
 *
 * @author Kristian Tkacik
 */
public interface ScoreComputationService {

    /**
     * Gets overall score for the given rating computed as average
     * from its sub-scores.
     *
     * @param rating to compute overall score for
     * @return the overall score for the given rating
     */
    BigDecimal getOverallScoreForRating(Rating rating);

    /**
     * Gets overall score for the given movie computed as average
     * from the movie's ratings.
     *
     * @param movie to compute overall score for
     * @return the overall score for the given movie
     */
    BigDecimal getOverallScoreForMovie(Movie movie);

    /**
     * Gets originality score for the given movie computed as average
     * from the movie's ratings.
     *
     * @param movie to compute originality score for
     * @return the originality score for the given movie
     */
    BigDecimal getOriginalityScoreForMovie(Movie movie);

    /**
     * Gets soundtrack score for the given movie computed as average
     * from the movie's ratings.
     *
     * @param movie to compute soundtrack score for
     * @return the soundtrack score for the given movie
     */
    BigDecimal getSoundtrackScoreForMovie(Movie movie);

    /**
     * Gets narrative score for the given movie computed as average
     * from the movie's ratings.
     *
     * @param movie to compute narrative score for
     * @return the narrative score for the given movie
     */
    BigDecimal getNarrativeScoreForMovie(Movie movie);

    /**
     * Gets cinematography score for the given movie computed as average
     * from the movie's ratings.
     *
     * @param movie to compute cinematography score for
     * @return the cinematography score for the given movie
     */
    BigDecimal getCinematographyScoreForMovie(Movie movie);

    /**
     * Gets depth score for the given movie computed as average
     * from the movie's ratings.
     *
     * @param movie to compute depth score for
     * @return the depth score for the given movie
     */
    BigDecimal getDepthScoreForMovie(Movie movie);
}
