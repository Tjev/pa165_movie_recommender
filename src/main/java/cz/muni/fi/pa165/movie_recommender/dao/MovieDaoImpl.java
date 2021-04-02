package cz.muni.fi.pa165.movie_recommender.dao;

import cz.muni.fi.pa165.movie_recommender.entity.Movie;

import java.util.List;

/**
 * MovieDao interface implementations that works with
 * in-memory data source
 *
 * @author Radoslav Chudovsky
 *
 */
public class MovieDaoImpl implements MovieDao {

    @Override
    public void create(Movie movie) {

    }

    @Override
    public List<Movie> findAll() {
        return null;
    }

    @Override
    public Movie findById(long id) {
        return null;
    }

    @Override
    public List<Movie> findByTitle(String title) {
        return null;
    }

    @Override
    public void update(Movie movie) {

    }

    @Override
    public void remove(Movie movie) {

    }
}
