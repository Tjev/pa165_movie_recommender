package cz.muni.fi.pa165;

import cz.muni.fi.pa165.dao.RatingDao;
import cz.muni.fi.pa165.entity.Rating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
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

    private RatingDao ratingDao;

    @Autowired
    public RatingServiceImpl(RatingDao ratingDao) {
        this.ratingDao = ratingDao;
    }

    @Override
    public void create(Rating rating) {
        ratingDao.create(rating);
    }

    @Override
    public List<Rating> findAll() {
        return ratingDao.findAll();
    }

    @Override
    public Rating findById(Long id) {
        return ratingDao.findById(id);
    }

    @Override
    public void update(Rating rating) {
        ratingDao.update(rating);
    }

    @Override
    public void remove(Rating rating) {
        ratingDao.remove(rating);
    }
}