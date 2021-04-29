package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.PersistenceApplicationContext;
import cz.muni.fi.pa165.entity.Genre;
import cz.muni.fi.pa165.entity.Movie;
import cz.muni.fi.pa165.entity.Rating;
import cz.muni.fi.pa165.entity.User;

import javax.persistence.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.testng.Assert;
import org.testng.annotations.*;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Tests for RatingDaoImpl
 *
 * @author Jiri Papousek
 */
@ContextConfiguration(classes = PersistenceApplicationContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class RatingDaoTest extends AbstractTestNGSpringContextTests {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private RatingDao dao;

    private Movie movie1;
    private Movie movie2;
    private User user1;
    private User user2;

    @BeforeMethod
    public void beforeTest() {
        Set<Genre> genres = new HashSet<>(Arrays.asList(Genre.ACTION));

        movie1 = new Movie("Shrek", null, LocalDate.of(2000, 1, 1), genres, null);
        movie2 = new Movie("James Bond", null, LocalDate.of(2000, 11, 11), genres, null);

        user1 = new User("novak", "novak@mail.com");
        user2 = new User("svoboda", "svoboda@mail.com");

        user1.setPasswordHash("password1");
        user2.setPasswordHash("password2");

        em.persist(movie1);
        em.persist(movie2);
        em.persist(user1);
        em.persist(user2);
    }

    @Test
    public void createTest() {
        Rating rating = new Rating(movie1, user1, 1, 2, 3, 4, 5);

        dao.create(rating);

        Assert.assertEquals(em.find(Rating.class, rating.getId()), rating);
    }

    @Test(expectedExceptions = ConstraintViolationException.class)
    public void createWithTooHighOriginalityTest() {
        Rating rating = new Rating(movie1, user1, 6, 2, 3, 4, 5);

        dao.create(rating);
    }

    @Test(expectedExceptions = {ConstraintViolationException.class})
    public void createWithTooLowOriginalityTest() {
        Rating rating = new Rating(movie1, user1, 0, 2, 3, 4, 5);

        dao.create(rating);
    }

    @Test(expectedExceptions = {ConstraintViolationException.class})
    public void createRatingWithoutMovie() {
        Rating rating = new Rating(null, user1, 1, 2, 3, 4, 5);

        dao.create(rating);
    }

    @Test(expectedExceptions = {ConstraintViolationException.class})
    public void createRatingWithoutUser() {
        Rating rating = new Rating(movie1, null, 1, 2, 3, 4, 5);

        dao.create(rating);
    }

    @Test
    void findAllTest() {
        Rating rating1 = new Rating(movie1, user1, 1, 2, 3, 4, 5);
        Rating rating2 = new Rating(movie1, user2, 2, 3, 2, 3, 2);
        Rating rating3 = new Rating(movie2, user2, 5, 3, 4, 2, 1);

        em.persist(rating1);
        em.persist(rating2);
        em.persist(rating3);

        List<Rating> ratings = dao.findAll();

        Assert.assertTrue(ratings.contains(rating1));
        Assert.assertTrue(ratings.contains(rating2));
        Assert.assertTrue(ratings.contains(rating3));
        Assert.assertEquals(ratings.size(), 3);
    }

    @Test
    void findByIdTest() {
        Rating rating = new Rating(movie1, user1, 1, 2, 3, 4, 5);

        em.persist(rating);

        Assert.assertEquals(dao.findById(rating.getId()), rating);
    }

    @Test
    public void findByMovieTest() {
        Rating rating1 = new Rating(movie1, user1, 1, 2, 3, 4, 5);
        Rating rating2 = new Rating(movie1, user2, 2, 3, 2, 3, 2);
        Rating rating3 = new Rating(movie2, user2, 5, 3, 4, 2, 1);

        em.persist(rating1);
        em.persist(rating2);
        em.persist(rating3);

        List<Rating> ratings = dao.findByMovie(movie1);

        Assert.assertTrue(ratings.contains(rating1));
        Assert.assertTrue(ratings.contains(rating2));
        Assert.assertEquals(ratings.size(), 2);
    }

    @Test
    public void findByUserTest() {
        Rating rating1 = new Rating(movie1, user1, 1, 2, 3, 4, 5);
        Rating rating2 = new Rating(movie1, user2, 2, 3, 2, 3, 2);
        Rating rating3 = new Rating(movie2, user2, 5, 3, 4, 2, 1);

        em.persist(rating1);
        em.persist(rating2);
        em.persist(rating3);

        List<Rating> ratings = dao.findByUser(user2);

        Assert.assertTrue(ratings.contains(rating2));
        Assert.assertTrue(ratings.contains(rating3));
        Assert.assertEquals(ratings.size(), 2);
    }

    @Test
    public void updateTest() {
        Rating rating = new Rating(movie1, user1, 1, 2, 3, 4, 5);

        em.persist(rating);
        rating.setMovie(movie2);
        rating.setUser(user2);
        rating.setOriginality(4);
        dao.update(rating);

        Assert.assertEquals(em.find(Rating.class, rating.getId()), rating);
    }

    @Test
    public void removeTest() {
        Rating rating1 = new Rating(movie1, user1, 1, 2, 3, 4, 5);
        Rating rating2 = new Rating(movie1, user2, 2, 3, 2, 3, 2);
        Rating rating3 = new Rating(movie2, user2, 5, 3, 4, 2, 1);

        em.persist(rating1);
        em.persist(rating2);
        em.persist(rating3);

        dao.remove(rating1);

        Assert.assertNull(em.find(Rating.class, rating1.getId()));
        Assert.assertEquals(em.find(Rating.class, rating2.getId()), rating2);
        Assert.assertEquals(em.find(Rating.class, rating3.getId()), rating3);
    }

}
