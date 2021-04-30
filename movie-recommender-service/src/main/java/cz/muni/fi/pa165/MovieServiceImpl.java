package cz.muni.fi.pa165;

import cz.muni.fi.pa165.dao.MovieDao;
import cz.muni.fi.pa165.entity.Movie;
import cz.muni.fi.pa165.exceptions.ServiceLayerException;
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
    public Movie create(Movie movie) {
        if (movie == null) {
            throw new IllegalArgumentException("Movie parameter is null.");
        }

        try {
            return movieDao.create(movie);
        } catch (Exception e) {
            throw new ServiceLayerException("Error occurred while creating Movie.", e);
        }
    }

    @Override
    public List<Movie> findAll() {
        try {
            return movieDao.findAll();
        } catch (Exception e) {
            throw new ServiceLayerException("Error occurred while retrieving all movies.");
        }
    }

    @Override
    public Movie findById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Id parameter is null.");
        }

        try {
            return movieDao.findById(id);
        } catch (Exception e) {
            throw new ServiceLayerException("Error occurred while retrieving movie by id.", e);
        }
    }

    @Override
    public List<Movie> findByTitle(String title) {
        if (title == null) {
            throw new IllegalArgumentException("Title parameter is null.");
        }

        if (title.isEmpty()) {
            throw new IllegalArgumentException("Title cannot be empty.");
        }

        try {
            return movieDao.findByTitle(title);
        } catch (Exception e) {
            throw new ServiceLayerException("Error occurred while retrieving movie by title.", e);
        }
    }

    @Override
    public Movie update(Movie movie) {
        if (movie == null) {
            throw new IllegalArgumentException("Movie parameter is null");
        }

        Movie found = findById(movie.getId());
        if (found == null) {
            throw new IllegalArgumentException("Movie is not in the database.");
        }

        try {
            return movieDao.update(movie);
        } catch (Exception e) {
            throw new ServiceLayerException("Error occurred while updating movie", e);
        }
    }

    @Override
    public void remove(Movie movie) {
        if (movie == null) {
            throw new IllegalArgumentException("Movie parameter is null");
        }

        Movie found = findById(movie.getId());
        if (found == null) {
            throw new IllegalArgumentException("Movie is not in the database.");
        }

        try {
            movieDao.remove(movie);
        } catch (Exception e) {
            throw new ServiceLayerException("Error occurred while removing movie", e);
        }
    }
}
