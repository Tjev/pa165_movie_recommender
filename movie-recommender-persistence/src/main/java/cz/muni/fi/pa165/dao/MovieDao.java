package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entity.Movie;

import java.util.List;

/**
 * Dao interface for manipulation with
 * Movie entity
 *
 * @author RadoslavChudovsky
 */
public interface MovieDao {

    /**
     * Saves movie entity into the data source
     *
     * @param movie movie to be saved
     */
    void create(Movie movie);


    /**
     * Retrieves a list of all movies from the
     * data source
     *
     * @return List of all Movies
     */
    List<Movie> findAll();

    /**
     * Retrieves a movie by its ID from the
     * data source
     *
     * @param id id of the movie
     * @return movie matching the id
     */
    Movie findById(long id);

    /**
     * Retrieves all movies from the data source
     * that match the given title
     *
     * @param title title of the movie
     * @return list of all movies matching
     * the title
     */
    List<Movie> findByTitle(String title);

    /**
     * Merges movie by its ID with its current
     * representation in the data source
     *
     * @param movie movie to be merged
     */
    void update(Movie movie);

    /**
     * Removes movie from the data source
     *
     * @param movie movie to be removed
     */
    void remove(Movie movie);
}