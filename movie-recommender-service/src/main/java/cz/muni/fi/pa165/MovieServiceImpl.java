package cz.muni.fi.pa165;

import cz.muni.fi.pa165.dao.MovieDao;
import cz.muni.fi.pa165.entity.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of the {@link MovieService}. This class is part of the service
 * module of the application that provides the implementation of the business
 * logic (main logic of the application).
 *
 * @author Radoslav Chudovsky
 */
@Service
public class MovieServiceImpl implements MovieService {

    private final MovieDao movieDao;

    @Autowired
    public MovieServiceImpl(MovieDao movieDao) {
        this.movieDao = movieDao;
    }

    @Override
    public void create(Movie movie) {
        movieDao.create(movie);
    }

    @Override
    public List<Movie> findAll() {
        return movieDao.findAll();
    }

    @Override
    public Movie findById(long id) {
        return movieDao.findById(id);
    }

    @Override
    public List<Movie> findByTitle(String title) {
        return movieDao.findByTitle(title);
    }

    @Override
    public void update(Movie movie) {
        movieDao.update(movie);
    }

    @Override
    public void remove(Movie movie) {
        movieDao.remove(movie);
    }
}
