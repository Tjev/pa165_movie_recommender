package cz.muni.fi.pa165.movie_recommender.dao;

import cz.muni.fi.pa165.movie_recommender.entity.Rating;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Implementation of RatingDao.
 *
 * @author Kristian Tkacik
 */
@Repository
public class RatingDaoImpl implements RatingDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void create(Rating rating) {
        entityManager.persist(rating);
    }

    @Override
    public List<Rating> findAll() {
        return entityManager.createQuery("select r from Rating r", Rating.class).getResultList();
    }

    @Override
    public Rating findById(Long id) {
        return entityManager.find(Rating.class, id);
    }

    @Override
    public void update(Rating rating) {
        entityManager.merge(rating);
    }

    @Override
    public void remove(Rating rating) {
        entityManager.remove(rating);
    }
}
