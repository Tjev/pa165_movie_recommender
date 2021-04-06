package cz.muni.fi.pa165.movie_recommender.dao;

import cz.muni.fi.pa165.movie_recommender.PersistenceApplicationContext;
import cz.muni.fi.pa165.movie_recommender.entity.Genre;
import cz.muni.fi.pa165.movie_recommender.entity.Movie;
import cz.muni.fi.pa165.movie_recommender.entity.Rating;
import cz.muni.fi.pa165.movie_recommender.entity.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

        movie1 = new Movie("Shrek", null, null, genres, null);
        movie2 = new Movie("James Bond", null, null, genres, null);

        user1 = new User("novak", "novak@mail.com");
        user2 = new User("svoboda", "svoboda@mail.com");

        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        em.persist(movie1);
        em.persist(user1);
        em.persist(movie2);
        em.persist(user2);

        em.getTransaction().commit();
        em.close();
    }

    @Test
    public void createTest() {
        EntityManager em = emf.createEntityManager();

        Rating rating = new Rating(movie1, user1, 1, 2, 3, 4, 5);
        dao.create(rating);

        em.getTransaction().begin();
        Rating result = em.find(Rating.class, rating.getId());
        em.getTransaction().commit();
        em.close();

        Assert.assertEquals(result, rating);
    }

    @Test
    void findAllTest() {
        EntityManager em = emf.createEntityManager();

        Rating rating1 = new Rating(movie1, user1, 1, 2, 3, 4, 5);
        Rating rating2 = new Rating(movie1, user2, 2, 3, 2, 3, 2);
        Rating rating3 = new Rating(movie2, user2, 5, 3, 4, 2, 1);

        em.getTransaction().begin();
        em.persist(rating1);
        em.persist(rating2);
        em.persist(rating3);
        em.getTransaction().commit();
        em.close();

        List<Rating> ratings = dao.findAll();

        Assert.assertTrue(ratings.contains(rating1));
        Assert.assertTrue(ratings.contains(rating2));
        Assert.assertTrue(ratings.contains(rating3));
        Assert.assertEquals(ratings.size(), 3);
    }

    @Test
    void findByIdTest() {
        EntityManager em = emf.createEntityManager();

        Rating rating = new Rating(movie1, user1, 1, 2, 3, 4, 5);

        em.getTransaction().begin();
        em.persist(rating);
        em.getTransaction().commit();
        em.close();

        Rating result = dao.findById(rating.getId());

        Assert.assertEquals(result, rating);
    }

    @Test
    public void updateTest() {
        EntityManager em = emf.createEntityManager();

        Rating rating = new Rating(movie1, user1, 1, 2, 3, 4, 5);

        em.getTransaction().begin();
        em.persist(rating);
        em.getTransaction().commit();
        em.close();

        rating.setOriginality(4);

        dao.update(rating);

        em = emf.createEntityManager();
        em.getTransaction().begin();
        Rating result = em.find(Rating.class, rating.getId());
        em.getTransaction().commit();
        em.close();

        Assert.assertEquals(result.getOriginality(), 4);
    }

    @Test
    public void removeTest() {
        EntityManager em = emf.createEntityManager();

        Rating rating1 = new Rating(movie1, user1, 1, 2, 3, 4, 5);
        Rating rating2 = new Rating(movie1, user2, 2, 3, 2, 3, 2);
        Rating rating3 = new Rating(movie2, user2, 5, 3, 4, 2, 1);

        em.getTransaction().begin();
        em.persist(rating1);
        em.persist(rating2);
        em.persist(rating3);
        em.getTransaction().commit();
        em.close();

        dao.remove(rating1);

        em = emf.createEntityManager();
        em.getTransaction().begin();
        Rating result1 = em.find(Rating.class, rating1.getId());
        Rating result2 = em.find(Rating.class, rating2.getId());
        Rating result3 = em.find(Rating.class, rating3.getId());
        em.getTransaction().commit();
        em.close();

        Assert.assertNull(result1);
        Assert.assertEquals(result2, rating2);
        Assert.assertEquals(result3, rating3);
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
