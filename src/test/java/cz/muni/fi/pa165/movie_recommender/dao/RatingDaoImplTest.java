package cz.muni.fi.pa165.movie_recommender.dao;

import cz.muni.fi.pa165.movie_recommender.entity.Genre;
import cz.muni.fi.pa165.movie_recommender.entity.Movie;
import cz.muni.fi.pa165.movie_recommender.entity.Rating;
import cz.muni.fi.pa165.movie_recommender.entity.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
@Transactional
public class RatingDaoImplTest extends AbstractTestNGSpringContextTests {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private RatingDao dao;

    private Movie movie1;
    private Movie movie2;
    private User user1;
    private User user2;

    @BeforeEach
    public void setUp() {
        Set<Genre> genres = new HashSet<>();
        genres.add(Genre.ACTION);
        movie1 = new Movie("Shrek", null, null, genres, null);
        movie2 = new Movie("James Bond", null, null, genres, null);

        user1 = new User("novak", "novak@mail.com");
        user2 = new User("svoboda", "svoboda@mail.com");

        em.getTransaction().begin();
        em.persist(movie1);
        em.persist(user1);
        em.persist(movie2);
        em.persist(user2);
        em.getTransaction().commit();
        em.close();
    }

    @AfterEach
    public void afterEach() {
        em.getTransaction().begin();
        em.createQuery("delete from Movie").executeUpdate();
        em.createQuery("delete from User").executeUpdate();
        em.createQuery("delete from Rating").executeUpdate();
        em.getTransaction().commit();
        em.close();
    }

    @Test
    public void createTest() {
        Rating rating = new Rating(movie1, user1, 1, 2, 3, 4, 5);
        dao.create(rating);

        em.getTransaction().begin();
        Rating result = em.find(Rating.class, rating.getId());
        em.getTransaction().commit();
        em.close();

        assertThat(result).isEqualTo(rating);
    }

    @Test
    void findAllTest() {
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

        assertThat(ratings.contains(rating1)).isTrue();
        assertThat(ratings.contains(rating2)).isTrue();
        assertThat(ratings.contains(rating3)).isTrue();
        assertThat(ratings.size()).isEqualTo(3);
    }

    @Test
    void findByIdTest() {
        Rating rating = new Rating(movie1, user1, 1, 2, 3, 4, 5);

        em.getTransaction().begin();
        em.persist(rating);
        em.getTransaction().commit();
        em.close();

        Rating result = dao.findById(rating.getId());

        assertThat(result).isEqualTo(rating);
    }

    @Test
    public void updateTest() {
        Rating rating = new Rating(movie1, user1, 1, 2, 3, 4, 5);

        em.getTransaction().begin();
        em.persist(rating);
        em.getTransaction().commit();
        em.close();

        rating.setOriginality(4);

        dao.update(rating);

        em.getTransaction().begin();
        Rating result = em.find(Rating.class, rating.getId());
        em.getTransaction().commit();
        em.close();

        assertThat(result.getOriginality()).isEqualTo(4);
    }

    @Test
    public void removeTest() {
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

        em.getTransaction().begin();
        Rating result1 = em.find(Rating.class, rating1.getId());
        Rating result2 = em.find(Rating.class, rating2.getId());
        Rating result3 = em.find(Rating.class, rating3.getId());
        em.getTransaction().commit();
        em.close();

        assertThat(result1).isEqualTo(null);
        assertThat(result2).isEqualTo(rating2);
        assertThat(result3).isEqualTo(rating3);
    }

}
