package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.PersistenceApplicationContext;
import cz.muni.fi.pa165.entity.Genre;
import cz.muni.fi.pa165.entity.Movie;
import cz.muni.fi.pa165.entity.Rating;
import cz.muni.fi.pa165.entity.User;

import javax.persistence.PersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
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
public class RatingDaoImplTest extends AbstractTestNGSpringContextTests {

    @PersistenceUnit
    private EntityManagerFactory emf;

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

        persistToDB(movie1);
        persistToDB(movie2);
        persistToDB(user1);
        persistToDB(user2);
    }

    @Test
    public void createTest() {
        Rating rating = new Rating(movie1, user1, 1, 2, 3, 4, 5);

        dao.create(rating);

        Assert.assertEquals(getFromDB(rating.getId()), rating);
    }

    @Test(expectedExceptions = PersistenceException.class)
    public void createDuplicateTest() {
        Rating rating = new Rating(movie1, user1, 1, 2, 3, 4, 5);

        dao.create(rating);
        dao.create(rating);
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

        persistToDB(rating1);
        persistToDB(rating2);
        persistToDB(rating3);

        List<Rating> ratings = dao.findAll();

        Assert.assertTrue(ratings.contains(rating1));
        Assert.assertTrue(ratings.contains(rating2));
        Assert.assertTrue(ratings.contains(rating3));
        Assert.assertEquals(ratings.size(), 3);
    }

    @Test
    void findByIdTest() {
        Rating rating = new Rating(movie1, user1, 1, 2, 3, 4, 5);

        persistToDB(rating);

        Assert.assertEquals(dao.findById(rating.getId()), rating);
    }

    @Test
    public void updateTest() {
        Rating rating = new Rating(movie1, user1, 1, 2, 3, 4, 5);

        persistToDB(rating);
        rating.setMovie(movie2);
        rating.setUser(user2);
        rating.setOriginality(4);
        dao.update(rating);

        Assert.assertEquals(getFromDB(rating.getId()), rating);
    }

    @Test
    public void removeTest() {
        Rating rating1 = new Rating(movie1, user1, 1, 2, 3, 4, 5);
        Rating rating2 = new Rating(movie1, user2, 2, 3, 2, 3, 2);
        Rating rating3 = new Rating(movie2, user2, 5, 3, 4, 2, 1);

        persistToDB(rating1);
        persistToDB(rating2);
        persistToDB(rating3);

        dao.remove(rating1);

        Assert.assertNull(getFromDB(rating1.getId()));
        Assert.assertEquals(getFromDB(rating2.getId()), rating2);
        Assert.assertEquals(getFromDB(rating3.getId()), rating3);
    }

    private void persistToDB(Object object) {
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();
            em.persist(object);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    private Rating getFromDB(long id) {
        Rating p;
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();
            p = em.find(Rating.class, id);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return p;
    }

    @AfterMethod
    public void afterTest() {
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();

        em.createQuery("delete from Rating").executeUpdate();
        em.createQuery("delete from Movie").executeUpdate();
        em.createQuery("delete from User").executeUpdate();

        em.getTransaction().commit();
        em.close();
    }
}