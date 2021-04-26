package cz.muni.fi.pa165;

import cz.muni.fi.pa165.entity.Movie;
import cz.muni.fi.pa165.entity.User;

import java.util.List;

/**
 * Interface for service that gives movie recommendations based on user data.
 *
 * @author Jiri Papousek
 */
public interface RecommendationService {

    List<Movie> getRecommendations(User user, int n);
    List<Movie> getRecommendations(Movie movie, int n);
}
