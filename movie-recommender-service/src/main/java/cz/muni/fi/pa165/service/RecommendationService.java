package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.entity.Movie;

import java.util.List;

/**
 * Interface for service that gives movie recommendations based on user data.
 *
 * @author Jiri Papousek
 */
public interface RecommendationService {

    /**
     * Gets all movies rated by users that rated the given movie
     *
     * @param movie for recommendation
     * @return list of movies
     */
    List<Movie> getRecommendationsBasedOnUsers(Movie movie);

    /**
     * Gets all movies that share at least one genre with the given movie
     *
     * @param movie for recommendation
     * @return list of movies
     */
    List<Movie> getRecommendationsBasedOnGenres(Movie movie);
}
