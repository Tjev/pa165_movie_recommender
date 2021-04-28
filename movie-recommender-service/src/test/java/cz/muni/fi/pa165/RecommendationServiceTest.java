package cz.muni.fi.pa165;

import cz.muni.fi.pa165.dao.MovieDao;
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
import java.util.ArrayList;
import java.util.Arrays;

import static org.mockito.Mockito.when;

public class RecommendationServiceTest {

    @Mock
    private MovieDao movieDao;

    @Mock
    private RatingDao ratingDao;

    private RecommendationService recommendationService;

    private AutoCloseable closeable;

    Movie m1 = setupMovie(1L, "1");
    Movie m2 = setupMovie(2L, "2");
    Movie m3 = setupMovie(3L, "3");
    Movie m4 = setupMovie(4L, "4");
    Movie m5 = setupMovie(5L, "5");

    User u1 = setupUser("u1");
    User u2 = setupUser("u2");
    User u3 = setupUser("u3");

    Rating m1u1Rating = setupRating(m1, u1);
    Rating m1u2Rating = setupRating(m1, u2);
    Rating m1u3Rating = setupRating(m1, u3);

    Rating m2u2Rating = setupRating(m2, u2);
    Rating m2u1Rating = setupRating(m2, u1);

    Rating m3u2Rating = setupRating(m3, u2);
    Rating m3u3Rating = setupRating(m3, u3);
    Rating m3u1Rating = setupRating(m3, u1);

    Rating m2u3Rating = setupRating(m2, u3);
    Rating m4u2Rating = setupRating(m4, u2);
    Rating m5u2Rating = setupRating(m5, u2);


    @BeforeMethod
    public void setup() {
        closeable = MockitoAnnotations.openMocks(this);
        recommendationService = new RecommendationServiceImpl(movieDao, ratingDao);
    }

    @Test
    public void recommendationsBasedOnOneUser() {
        setupMovieDaoMocks(m1, m2, m3);
        setupRatingDaoMocksByUser(u1, m1u1Rating, m2u1Rating, m3u1Rating);
        setupRatingDaoMocksByMovie(m1, m1u1Rating);
        setupRatingDaoMocksByMovie(m2, m2u1Rating);
        setupRatingDaoMocksByMovie(m3, m3u1Rating);

        var recommends = recommendationService.getRecommendationsBasedOnUsers(m1);

        Assert.assertTrue(recommends.contains(m2));
        Assert.assertTrue(recommends.contains(m3));
    }

    @Test
    public void recommendationsBasedOnMultipleUsers() {
        setupMovieDaoMocks(m1, m2, m3);
        setupRatingDaoMocksByUser(u1, m1u1Rating, m2u1Rating);
        setupRatingDaoMocksByUser(u2, m1u2Rating, m3u2Rating);
        setupRatingDaoMocksByMovie(m1, m1u1Rating, m1u2Rating);
        setupRatingDaoMocksByMovie(m2, m2u1Rating);
        setupRatingDaoMocksByMovie(m3, m3u2Rating);

        var recommends = recommendationService.getRecommendationsBasedOnUsers(m1);

        Assert.assertTrue(recommends.contains(m2));
        Assert.assertTrue(recommends.contains(m3));
    }

    @Test
    public void recommendationsBasedOnUsersDoesntContainGivenMovie() {
        setupMovieDaoMocks(m1, m2, m3);
        setupRatingDaoMocksByUser(u1, m1u1Rating, m2u1Rating, m3u1Rating);
        setupRatingDaoMocksByUser(u2, m1u2Rating, m2u2Rating ,m3u2Rating);
        setupRatingDaoMocksByMovie(m1, m1u1Rating, m1u2Rating);
        setupRatingDaoMocksByMovie(m2, m2u1Rating, m2u2Rating);
        setupRatingDaoMocksByMovie(m3, m3u1Rating, m3u2Rating);

        var movies = new ArrayList<>(Arrays.asList(m1, m2, m3));

        for (var movie : movies) {
            var recommends = recommendationService.getRecommendationsBasedOnUsers(movie);

            Assert.assertFalse(recommends.contains(movie));
        }
    }

    @Test
    public void recommendationsBasedOnUsersDoesntContainDuplicates() {
        setupMovieDaoMocks(m1, m2, m3);
        setupRatingDaoMocksByUser(u1, m1u1Rating, m2u1Rating, m3u1Rating);
        setupRatingDaoMocksByUser(u2, m1u2Rating, m2u2Rating ,m3u2Rating);
        setupRatingDaoMocksByUser(u3, m1u3Rating, m2u3Rating ,m3u3Rating);
        setupRatingDaoMocksByMovie(m1, m1u1Rating, m1u2Rating, m1u3Rating);
        setupRatingDaoMocksByMovie(m2, m2u1Rating, m2u2Rating, m2u3Rating);
        setupRatingDaoMocksByMovie(m3, m3u1Rating, m3u2Rating, m3u3Rating);

        var recommends = recommendationService.getRecommendationsBasedOnUsers(m1);

        Assert.assertTrue(recommends.contains(m2));
        Assert.assertTrue(recommends.contains(m3));
        Assert.assertEquals(recommends.size(), 2);
    }

    @Test
    public void recommendationsBasedOnUsersIfGivenMovieHasNoReviews() {
        setupMovieDaoMocks(m1, m2);
        setupRatingDaoMocksByUser(u1, m2u1Rating);
        setupRatingDaoMocksByMovie(m1);
        setupRatingDaoMocksByMovie(m2, m2u1Rating);

        var recommends = recommendationService.getRecommendationsBasedOnUsers(m1);

        Assert.assertEquals(recommends.size(), 0);
    }

    @Test
    public void recommendationsBasedOnUsersWhoDidntRateOtherMovies() {
        setupMovieDaoMocks(m1, m2, m3);
        setupRatingDaoMocksByUser(u1, m1u1Rating);
        setupRatingDaoMocksByUser(u2, m1u2Rating);
        setupRatingDaoMocksByUser(u3, m1u3Rating);
        setupRatingDaoMocksByMovie(m1, m1u1Rating, m1u2Rating, m1u3Rating);
        setupRatingDaoMocksByMovie(m2);
        setupRatingDaoMocksByMovie(m3);

        var recommends = recommendationService.getRecommendationsBasedOnUsers(m1);

        Assert.assertEquals(recommends.size(), 0);
    }

    @Test
    public void recommendationsBasedOnUsersExcludeMoviesWithNoMutualViewers() {
        setupMovieDaoMocks(m1, m2, m3, m4, m5);
        // Group 1
        setupRatingDaoMocksByUser(u1, m1u1Rating, m2u2Rating, m3u2Rating);
        setupRatingDaoMocksByMovie(m1, m1u1Rating);
        setupRatingDaoMocksByMovie(m2, m2u1Rating);
        setupRatingDaoMocksByMovie(m3, m3u1Rating);
        // Group 2
        setupRatingDaoMocksByUser(u2, m4u2Rating, m5u2Rating);
        setupRatingDaoMocksByMovie(m4, m4u2Rating);
        setupRatingDaoMocksByMovie(m5, m5u2Rating);

        var recommendsForM1 = recommendationService.getRecommendationsBasedOnUsers(m1);
        Assert.assertEquals(recommendsForM1.size(), 2);
        Assert.assertTrue(recommendsForM1.contains(m2));
        Assert.assertTrue(recommendsForM1.contains(m3));

        var recommendsForM4 = recommendationService.getRecommendationsBasedOnUsers(m4);
        Assert.assertEquals(recommendsForM4.size(), 1);
        Assert.assertTrue(recommendsForM4.contains(m5));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void recommendationsBasedOnUsersWhenMovieIsNull() {
        recommendationService.getRecommendationsBasedOnUsers(null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void recommendationsBasedOnUsersWhenMovieDoesntExist() {
        Movie nonExistingMovie = setupMovie(19L, "Impostor");
        when(movieDao.findById(nonExistingMovie.getId())).thenReturn(null);

        recommendationService.getRecommendationsBasedOnUsers(nonExistingMovie);
    }

    @Test(expectedExceptions = ServiceLayerException.class)
    public void recommendationsBasedOnUsersWithPersistenceException() {
        Movie movie = setupMovie(1L, "Exception");
        when(movieDao.findById(movie.getId())).thenThrow(PersistenceException.class);

        recommendationService.getRecommendationsBasedOnUsers(movie);
    }

    @AfterMethod
    public void releaseMocks() throws Exception {
        closeable.close();
    }

    private Rating setupRating(Movie movie, User user) {
        Rating rating = new Rating();

        rating.setMovie(movie);
        rating.setUser(user);

        return rating;
    }

    private Movie setupMovie(Long id, String title) {
        Movie movie = new Movie();

        movie.setId(id);
        movie.setTitle(title);

        return movie;
    }

    private User setupUser(String username) {
        return new User(username, username + "@mail.com");
    }

    private void setupMovieDaoMocks(Movie... movies) {
        for (var movie : movies) {
            when(movieDao.findById(movie.getId())).thenReturn(movie);
        }
    }

    private void setupRatingDaoMocksByUser(User user, Rating... ratings) {
        when(ratingDao.findByUser(user))
                .thenReturn(new ArrayList<>(Arrays.asList(ratings)));
    }

    private void setupRatingDaoMocksByMovie(Movie movie, Rating... ratings) {
        when(ratingDao.findByMovie(movie))
                .thenReturn(new ArrayList<>(Arrays.asList(ratings)));
    }
}
