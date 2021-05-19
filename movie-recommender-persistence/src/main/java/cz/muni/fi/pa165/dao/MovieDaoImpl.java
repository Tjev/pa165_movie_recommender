package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entity.Movie;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Locale;

/**
 * MovieDao interface implementations that works with
 * in-memory data source
 *
 * @author Radoslav Chudovsky
 */
@Repository
public class MovieDaoImpl implements MovieDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Movie create(Movie movie) {
        em.persist(movie);
        return movie;
    }

    @Override
    public List<Movie> findAll() {
        return em.createQuery("SELECT m FROM Movie m", Movie.class).getResultList();
    }

    @Override
    public Movie findById(Long id) {
        return em.find(Movie.class, id);
    }

    @Override
    public List<Movie> findByTitle(String title) {
        return em.createQuery("SELECT m FROM Movie m WHERE LOWER(m.title) LIKE :title", Movie.class)
                .setParameter("title", "%" + title.toLowerCase() + "%")
                .getResultList();
    }

    @Override
    public Movie update(Movie movie) {
        em.merge(movie);
        return movie;
    }

    @Override
    public void remove(Movie movie) {
        if (!em.contains(movie)) {
            movie = em.merge(movie);
        }
        em.remove(movie);
    }
}
