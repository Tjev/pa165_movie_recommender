package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.dao.RatingDao;
import cz.muni.fi.pa165.entity.Genre;
import cz.muni.fi.pa165.entity.Movie;
import cz.muni.fi.pa165.entity.Rating;
import cz.muni.fi.pa165.entity.User;
import cz.muni.fi.pa165.exception.ServiceLayerException;
import cz.muni.fi.pa165.service.impl.RatingServiceImpl;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.persistence.PersistenceException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

/**
 * Tests for RatingServiceImpl methods
 *
 * @author Radoslav Chudovsky
 */
public class RatingServiceTest {

    @Mock
    private RatingDao ratingDao;

    private RatingService ratingService;

    private AutoCloseable closeable;

    private Movie movie1;
    private Movie movie2;
    private User user1;
    private User user2;

    @BeforeMethod
    public void setup() {
        closeable = MockitoAnnotations.openMocks(this);
        ratingService = new RatingServiceImpl(ratingDao);

        movie1 = setupMovie(1L, "Indiana Jones", Genre.ROMANCE);
        movie2 = setupMovie(2L, "Titanic", Genre.ACTION);
        user1 = setupUser("John");
        user2 = setupUser("George");
    }

    @Test
    public void createRating() {
        Rating rating = setupPredefinedRating();

        ratingService.create(rating);

        verify(ratingDao, times(1)).create(rating);
        verifyNoMoreInteractions(ratingDao);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void createRatingWithNullArgument() {
        ratingService.create(null);
    }

    @Test(expectedExceptions = ServiceLayerException.class)
    public void createRatingPersistenceException() {
        Rating rating = setupPredefinedRating();

        doThrow(PersistenceException.class)
                .when(ratingDao)
                .create(rating);

        ratingService.create(rating);
    }

    @Test
    public void findAllRatings() {
        Rating r1 = setupRating(1L,1, 1, 1, 1, 1, movie1, user1);
        Rating r2 = setupRating(2L,5, 5, 5, 5, 5, movie1, user1);

        List<Rating> ratings = new ArrayList<>(Arrays.asList(r1, r2));
        when(ratingDao.findAll()).thenReturn(ratings);

        var foundRatings = ratingService.findAll();

        verify(ratingDao, times(1)).findAll();
        verifyNoMoreInteractions(ratingDao);
        Assert.assertTrue(foundRatings.contains(r1));
        Assert.assertTrue(foundRatings.contains(r2));
        Assert.assertEquals(foundRatings.size(), 2);
    }

    @Test(expectedExceptions = ServiceLayerException.class)
    public void findAllRatingsPersistenceException() {
        when(ratingDao.findAll()).thenThrow(PersistenceException.class);

        ratingService.findAll();
    }

    @Test
    public void findRatingById() {
        Rating rating = setupPredefinedRating();
        when(ratingDao.findById(rating.getId())).thenReturn(rating);

        Rating foundRating = ratingService.findById(rating.getId());

        verify(ratingDao, times(1)).findById(anyLong());
        verifyNoMoreInteractions(ratingDao);
        Assert.assertEquals(rating, foundRating);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void findRatingByIdNullWithNullArgument() {
        ratingService.findById(null);
    }

    @Test(expectedExceptions = ServiceLayerException.class)
    public void findRatingByIdPersistenceException() {
        Rating rating = setupPredefinedRating();
        when(ratingDao.findById(rating.getId())).thenThrow(PersistenceException.class);

        ratingService.findById(rating.getId());
    }

    @Test
    public void findByUser() {
        Rating r1 = setupRating(1L,1, 1, 1, 1, 1, movie1, user1);
        Rating r2 = setupRating(2L,5, 5, 5, 5, 5, movie2, user1);

        List<Rating> ratings = new ArrayList<>(Arrays.asList(r1, r2));
        when(ratingDao.findByUser(user1)).thenReturn(ratings);

        var foundRatings = ratingService.findByUser(user1);

        verify(ratingDao, times(1)).findByUser(user1);
        verifyNoMoreInteractions(ratingDao);
        Assert.assertTrue(foundRatings.contains(r1));
        Assert.assertTrue(foundRatings.contains(r2));
        Assert.assertEquals(foundRatings.size(), 2);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void findByNullUser() {
        ratingService.findByUser(null);
    }

    @Test(expectedExceptions = ServiceLayerException.class)
    public void findByUserPersistenceException() {
        when(ratingService.findByUser(user1)).thenThrow(PersistenceException.class);

        ratingService.findByUser(user1);
    }

    @Test
    public void findByMovie() {
        Rating r1 = setupRating(1L,1, 1, 1, 1, 1, movie1, user1);
        Rating r2 = setupRating(2L,5, 5, 5, 5, 5, movie1, user2);

        List<Rating> ratings = new ArrayList<>(Arrays.asList(r1, r2));
        when(ratingDao.findByMovie(movie1)).thenReturn(ratings);

        var foundRatings = ratingService.findByMovie(movie1);

        verify(ratingDao, times(1)).findByMovie(movie1);
        verifyNoMoreInteractions(ratingDao);
        Assert.assertTrue(foundRatings.contains(r1));
        Assert.assertTrue(foundRatings.contains(r2));
        Assert.assertEquals(foundRatings.size(), 2);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void findByNullMovie() {
        ratingService.findByMovie(null);
    }

    @Test(expectedExceptions = ServiceLayerException.class)
    public void findByMoviePersistenceException() {
        when(ratingService.findByMovie(movie1)).thenThrow(PersistenceException.class);

        ratingService.findByMovie(movie1);
    }

    @Test
    public void updateRating() {
        Rating rating = setupPredefinedRating();
        when(ratingDao.findById(rating.getId())).thenReturn(rating);

        ratingService.update(rating);

        verify(ratingDao, times(1)).findById(anyLong());
        verify(ratingDao, times(1)).update(any());
        verifyNoMoreInteractions(ratingDao);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void updateRatingWithNullArgument() {
        ratingService.update(null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void updateNonExistingRating() {
        Rating rating = setupPredefinedRating();
        when(ratingDao.findById(rating.getId())).thenReturn(null);

        ratingService.update(rating);
    }

    @Test(expectedExceptions = ServiceLayerException.class)
    public void updateRatingPersistenceException() {
        Rating rating = setupPredefinedRating();

        when(ratingDao.findById(rating.getId())).thenReturn(rating);
        doThrow(PersistenceException.class)
                .when(ratingDao)
                .update(rating);

        ratingService.update(rating);
    }

    @Test
    public void removeTest() {
        Rating rating = setupPredefinedRating();
        when(ratingDao.findById(rating.getId())).thenReturn(rating);

        ratingService.remove(rating);

        verify(ratingDao, times(1)).findById(anyLong());
        verify(ratingDao, times(1)).remove(any());
        verifyNoMoreInteractions(ratingDao);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void removeRatingWithNullArgument() {
        ratingService.remove(null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void removeNonExistingRating() {
        Rating rating = setupPredefinedRating();
        when(ratingDao.findById(rating.getId())).thenReturn(null);

        ratingService.remove(rating);
    }

    @Test(expectedExceptions = ServiceLayerException.class)
    public void removeRatingPersistenceException() {
        Rating rating = setupPredefinedRating();

        when(ratingDao.findById(rating.getId())).thenReturn(rating);
        doThrow(PersistenceException.class)
                .when(ratingDao)
                .remove(rating);

        ratingService.remove(rating);
    }

    @AfterMethod
    public void releaseMocks() throws Exception {
        closeable.close();
    }

    private Movie setupMovie(Long id, String title, Genre... genres) {
        Movie movie = new Movie();

        movie.setId(id);
        movie.setTitle(title);

        for (var genre : genres) {
            movie.addGenre(genre);
        }

        return movie;
    }

    private User setupUser(String username) {
        return new User(username, username + "@mail.com");
    }

    private Rating setupRating(Long id, int originality, int soundtrack, int narrative,
                               int cinematography, int depth, Movie movie, User user) {
        Rating rating = new Rating(movie, user, originality, soundtrack, narrative, cinematography, depth);
        rating.setId(id);

        return rating;
    }

    private Rating setupPredefinedRating() {
        return setupRating(1L, 1, 2, 3, 4, 5, movie1, user1);
    }
}
