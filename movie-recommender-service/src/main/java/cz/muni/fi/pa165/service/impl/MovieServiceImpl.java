package cz.muni.fi.pa165.service.impl;

import cz.muni.fi.pa165.dao.MovieDao;
import cz.muni.fi.pa165.entity.Movie;
import cz.muni.fi.pa165.exception.ServiceLayerException;
import javax.inject.Inject;
import javax.persistence.PersistenceException;
import javax.validation.ConstraintViolationException;

import cz.muni.fi.pa165.service.MovieService;
import org.hibernate.validator.cfg.context.Constrainable;
import org.springframework.dao.DataAccessException;
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

    @Inject
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
        } catch (PersistenceException
                | ConstraintViolationException
                | DataAccessException e) {
            throw new ServiceLayerException("Error occurred while creating Movie.", e);
        }
    }

    @Override
    public List<Movie> findAll() {
        try {
            return movieDao.findAll();
        } catch (PersistenceException | DataAccessException e) {
            throw new ServiceLayerException("Error occurred while retrieving all movies.", e);
        }
    }

    @Override
    public Movie findById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Id parameter is null.");
        }

        try {
            return movieDao.findById(id);
        } catch (PersistenceException | DataAccessException e) {
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
        } catch (PersistenceException | DataAccessException e) {
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
        } catch (PersistenceException
                | ConstraintViolationException
                | DataAccessException e) {
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
        } catch (PersistenceException | DataAccessException e) {
            throw new ServiceLayerException("Error occurred while removing movie", e);
        }
    }
}
