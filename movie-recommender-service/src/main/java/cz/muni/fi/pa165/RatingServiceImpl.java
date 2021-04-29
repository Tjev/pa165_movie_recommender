package cz.muni.fi.pa165;

import cz.muni.fi.pa165.dao.RatingDao;
import cz.muni.fi.pa165.entity.Movie;
import cz.muni.fi.pa165.entity.Rating;
import cz.muni.fi.pa165.entity.User;
import cz.muni.fi.pa165.exceptions.ServiceLayerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of the {@link RatingService}. This class is part of the service
 * module of the application that provides the implementation of the business
 * logic (main logic of the application).
 *
 * @author Kristian Tkacik
 */
@Service
public class RatingServiceImpl implements RatingService {

    private final RatingDao ratingDao;

    @Autowired
    public RatingServiceImpl(RatingDao ratingDao) {
        this.ratingDao = ratingDao;
    }

    @Override
    public void create(Rating rating) {
        if (rating == null) {
            throw new IllegalArgumentException("Rating parameter is null.");
        }

        try {
            ratingDao.create(rating);
        } catch (Exception e) {
            throw new ServiceLayerException("Error occurred while creating Rating.", e);
        }
    }

    @Override
    public List<Rating> findAll() {
        try {
            return ratingDao.findAll();
        } catch (Exception e) {
            throw new ServiceLayerException("Error occurred while retrieving all ratings.");
        }
    }

    @Override
    public Rating findById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Id parameter is null.");
        }

        try {
            return ratingDao.findById(id);
        } catch (Exception e) {
            throw new ServiceLayerException("Error occurred while retrieving rating by id.", e);
        }
    }

    @Override
    public List<Rating> findByMovie(Movie movie) {
        if (movie == null) {
            throw new IllegalArgumentException("Movie parameter is null.");
        }

        try {
            return ratingDao.findByMovie(movie);
        } catch (Exception e) {
            throw new ServiceLayerException("Error occurred while retrieving rating by movie.", e);
        }
    }

    @Override
    public List<Rating> findByUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User parameter is null.");
        }

        try {
            return ratingDao.findByUser(user);
        } catch (Exception e) {
            throw new ServiceLayerException("Error occurred while retrieving rating by user.", e);
        }
    }

    @Override
    public void update(Rating rating) {
        if (rating == null) {
            throw new IllegalArgumentException("Rating parameter is null");
        }

        Rating found = findById(rating.getId());
        if (found == null) {
            throw new IllegalArgumentException("Rating is not in the database.");
        }

        try {
            ratingDao.update(rating);
        } catch (Exception e) {
            throw new ServiceLayerException("Error occurred while updating rating", e);
        }
    }

    @Override
    public void remove(Rating rating) {
        if (rating == null) {
            throw new IllegalArgumentException("Rating parameter is null");
        }

        Rating found = findById(rating.getId());
        if (found == null) {
            throw new IllegalArgumentException("Rating is not in the database.");
        }

        try {
            ratingDao.remove(rating);
        } catch (Exception e) {
            throw new ServiceLayerException("Error occurred while removing rating", e);
        }
    }
}
