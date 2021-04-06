package cz.muni.fi.pa165.movie_recommender.dao;

import cz.muni.fi.pa165.movie_recommender.PersistenceApplicationContext;
import cz.muni.fi.pa165.movie_recommender.entity.Genre;
import cz.muni.fi.pa165.movie_recommender.entity.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@ContextConfiguration(classes = PersistenceApplicationContext.class)
public class MovieDaoImplTest extends AbstractTestNGSpringContextTests {

    @PersistenceUnit
    private EntityManagerFactory emf;

    private static final Set<Genre> genres = Set.of(Genre.ACTION);

    Movie titanic;
    Movie suspiria;
    Movie suspiriaRemake;

    @Autowired
    private MovieDao movieDao;

    @BeforeMethod
    private void setUp() {

        titanic = new Movie("Titanic", null, LocalDate.of(1997, 2, 15), genres, null);
        suspiria = new Movie("Suspiria", null, LocalDate.of(1977, 2, 1), genres, null);
        suspiriaRemake = new Movie("Suspiria", null, LocalDate.of(2018, 11, 2), genres, null);

        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();
            em.persist(titanic);
            em.persist(suspiria);
            em.persist(suspiriaRemake);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Test
    private void createTest() {
        Movie braveheart = new Movie("Braveheart", null, LocalDate.of(1995, 5, 19), genres, null);

        movieDao.create(braveheart);

        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();
            Movie result = em.find(Movie.class, braveheart.getId());
            em.getTransaction().commit();

            Assert.assertEquals(braveheart, result);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Test
    private void findAllTest() {

        List<Movie> result = movieDao.findAll();

        assert (result.contains(titanic));
        assert (result.contains(suspiria));
        assert (result.contains(suspiriaRemake));
    }

    @Test
    private void findByIdTest() {

        Movie result = movieDao.findById(suspiria.getId());

        Assert.assertEquals(suspiria, result);
    }

    @Test
    private void findByTitleTest() {

        List<Movie> result = movieDao.findByTitle(titanic.getTitle());

        Assert.assertTrue(result.contains(titanic));
    }

    @Test
    private void findByTitleMultipleResultsTest() {

        List<Movie> result = movieDao.findByTitle("Suspiria");

        Assert.assertTrue(result.contains(suspiria));
        Assert.assertTrue(result.contains(suspiriaRemake));
    }

    @Test
    private void updateMovieTest() {
        Movie braveheart = new Movie("Braveheart", null, LocalDate.of(1995, 5, 19), genres, null);
        String bio = "It has Mel Gibson in it...";

        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();
            em.persist(braveheart);
            em.getTransaction().commit();

            braveheart.setBio(bio);

            movieDao.update(braveheart);

            em.getTransaction().begin();
            Movie result = em.find(Movie.class, braveheart.getId());
            em.getTransaction().commit();

            Assert.assertEquals(bio, result.getBio());
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Test
    private void removeMovieTest() {

        movieDao.remove(suspiria);

        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();
            List<Movie> result = em.createQuery("select m from Movie m", Movie.class).getResultList();
            em.getTransaction().commit();

            Assert.assertFalse(result.contains(suspiria));
            Assert.assertTrue(result.contains(titanic));
            Assert.assertTrue(result.contains(suspiriaRemake));
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @AfterMethod
    private void tearDown() {
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();
            em.createQuery("delete from Movie").executeUpdate();
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
}