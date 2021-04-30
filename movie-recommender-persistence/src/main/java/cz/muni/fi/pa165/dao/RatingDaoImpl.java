package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entity.Movie;
import cz.muni.fi.pa165.entity.Rating;
import cz.muni.fi.pa165.entity.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Implementation of RatingDao.
 *
 * @author Kristian Tkacik
 */
@Repository
public class RatingDaoImpl implements RatingDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Rating create(Rating rating) {
        em.persist(rating);
        return rating;
    }

    @Override
    public List<Rating> findAll() {
        return em.createQuery("SELECT r FROM Rating r", Rating.class).getResultList();
    }

    @Override
    public Rating findById(Long id) {
        return em.find(Rating.class, id);
    }

    @Override
    public List<Rating> findByMovie(Movie movie) {
        return em.createQuery("SELECT r FROM Rating r WHERE r.movie = :movie", Rating.class)
                .setParameter("movie", movie)
                .getResultList();
    }

    @Override
    public List<Rating> findByUser(User user) {
        return em.createQuery("SELECT r FROM Rating r WHERE r.user = :user", Rating.class)
                .setParameter("user", user)
                .getResultList();
    }

    @Override
    public Rating update(Rating rating) {
        em.merge(rating);
        return rating;
    }

    @Override
    public void remove(Rating rating) {
        if (!em.contains(rating)) {
            rating = em.merge(rating);
        }
        em.remove(rating);
    }
}
