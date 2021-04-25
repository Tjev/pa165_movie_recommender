package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entity.Movie;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * MovieDao interface implementations that works with
 * in-memory data source
 *
 * @author Radoslav Chudovsky
 */
@Repository
@Transactional
public class MovieDaoImpl implements MovieDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void create(Movie movie) {
        em.persist(movie);
    }

    @Override
    public List<Movie> findAll() {
        return em.createQuery("select m from Movie m", Movie.class).getResultList();
    }

    @Override
    public Movie findById(long id) {
        return em.find(Movie.class, id);
    }

    @Override
    public List<Movie> findByTitle(String title) {
        return em.createQuery("select m from Movie m where m.title=:title", Movie.class)
                .setParameter("title", title)
                .getResultList();
    }

    @Override
    public void update(Movie movie) {
        em.merge(movie);
    }

    @Override
    public void remove(Movie movie) {
        if (!em.contains(movie)) {
            movie = em.merge(movie);
        }
        em.remove(movie);
    }
}
