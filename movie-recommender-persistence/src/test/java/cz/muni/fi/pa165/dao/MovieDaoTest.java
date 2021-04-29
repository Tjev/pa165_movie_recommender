package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.PersistenceApplicationContext;
import cz.muni.fi.pa165.entity.Genre;
import cz.muni.fi.pa165.entity.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@ContextConfiguration(classes = PersistenceApplicationContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class MovieDaoTest extends AbstractTestNGSpringContextTests {

    @PersistenceContext
    private EntityManager em;

    private static final Set<Genre> genres = new HashSet<>(Arrays.asList(Genre.ACTION));

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

        em.persist(titanic);
        em.persist(suspiria);
        em.persist(suspiriaRemake);
    }

    @Test
    public void createTest() {

        Movie braveheart = new Movie("Braveheart", null, LocalDate.of(1995, 5, 19), genres, null);

        movieDao.create(braveheart);

        Movie result = em.find(Movie.class, braveheart.getId());

        Assert.assertEquals(braveheart, result);
    }

    @Test(expectedExceptions = ConstraintViolationException.class)
    public void nullTitleTest() {

        Movie movie = new Movie(null, null, LocalDate.of(2000, 1, 1), genres, null);

        movieDao.create(movie);
    }

    @Test(expectedExceptions = ConstraintViolationException.class)
    public void emptyTitleTest() {

        Movie movie = new Movie("", null, LocalDate.of(2000, 1, 1), genres, null);

        movieDao.create(movie);
    }

    @Test(expectedExceptions = ConstraintViolationException.class)
    public void nullDateTest() {

        Movie braveheart = new Movie("Braveheart", null, null, genres, null);

        movieDao.create(braveheart);
    }

    @Test
    public void findAllTest() {

        List<Movie> result = movieDao.findAll();

        assert (result.contains(titanic));
        assert (result.contains(suspiria));
        assert (result.contains(suspiriaRemake));
    }

    @Test
    public void findByIdTest() {

        Movie result = movieDao.findById(suspiria.getId());

        Assert.assertEquals(suspiria, result);
    }

    @Test
    public void findByTitleTest() {

        List<Movie> result = movieDao.findByTitle(titanic.getTitle());

        Assert.assertTrue(result.contains(titanic));
    }

    @Test
    public void findByTitleMultipleResultsTest() {

        List<Movie> result = movieDao.findByTitle("Suspiria");

        Assert.assertTrue(result.contains(suspiria));
        Assert.assertTrue(result.contains(suspiriaRemake));
    }

    @Test
    public void updateMovieTest() {

        Movie braveheart = new Movie("Braveheart", null, LocalDate.of(1995, 5, 19), genres, null);
        String bio = "It has Mel Gibson in it...";

        em.persist(braveheart);

        braveheart.setBio(bio);

        movieDao.update(braveheart);

        Movie result = em.find(Movie.class, braveheart.getId());

        Assert.assertEquals(bio, result.getBio());
    }

    @Test
    public void removeMovieTest() {

        movieDao.remove(suspiria);

        List<Movie> result = em.createQuery("select m from Movie m", Movie.class).getResultList();

        Assert.assertFalse(result.contains(suspiria));
        Assert.assertTrue(result.contains(titanic));
        Assert.assertTrue(result.contains(suspiriaRemake));
    }
}
