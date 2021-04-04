package cz.muni.fi.pa165.movie_recommender.dao;

import cz.muni.fi.pa165.movie_recommender.entity.Rating;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Implementation of RatingDao.
 *
 * @author Kristian Tkacik
 */
@Repository
public class RatingDaoImpl implements RatingDao {

    @Override
    public void create(Rating rating) {

    }

    @Override
    public List<Rating> findAll() {
        return null;
    }

    @Override
    public Rating findById(Long id) {
        return null;
    }

    @Override
    public void update(Rating rating) {

    }

    @Override
    public void remove(Rating rating) {

    }
}
