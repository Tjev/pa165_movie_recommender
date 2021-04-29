package cz.muni.fi.pa165;

import cz.muni.fi.pa165.dao.RatingDao;
import cz.muni.fi.pa165.entity.Movie;
import cz.muni.fi.pa165.entity.Rating;
import cz.muni.fi.pa165.entity.User;
import cz.muni.fi.pa165.exceptions.ServiceLayerException;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.persistence.PersistenceException;
import javax.validation.ConstraintViolationException;
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

    private Movie movie;
    private User user;

    @BeforeMethod
    public void setup() {
        closeable = MockitoAnnotations.openMocks(this);
        ratingService = new RatingServiceImpl(ratingDao);

        movie = new Movie();
        user = new User();
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
        Rating r1 = setupRating(1L,1, 1, 1, 1, 1, movie, user);
        Rating r2 = setupRating(2L,5, 5, 5, 5, 5, movie, user);

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

    private Rating setupRating(Long id, int originality, int soundtrack, int narrative,
                               int cinematography, int depth, Movie movie, User user) {
        Rating rating = new Rating(movie, user, originality, soundtrack, narrative, cinematography, depth);
        rating.setId(id);

        return rating;
    }

    private Rating setupPredefinedRating() {
        return setupRating(1L, 1, 2, 3, 4, 5, movie, user);
    }
}