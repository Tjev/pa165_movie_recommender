package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.dao.MovieDao;
import cz.muni.fi.pa165.dao.RatingDao;
import cz.muni.fi.pa165.entity.Genre;
import cz.muni.fi.pa165.entity.Movie;
import cz.muni.fi.pa165.entity.Rating;
import cz.muni.fi.pa165.entity.User;
import cz.muni.fi.pa165.exception.ServiceLayerException;
import cz.muni.fi.pa165.service.RecommendationService;
import cz.muni.fi.pa165.service.RecommendationServiceImpl;
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

/**
 * Tests for {@link RecommendationServiceImpl} methods
 *
 * @author Radoslav Chudovsky
 */
public class RecommendationServiceTest {

    @Mock
    private MovieDao movieDao;

    @Mock
    private RatingDao ratingDao;

    private RecommendationService recommendationService;

    private AutoCloseable closeable;

    // Movies
    private final Movie m1 = setupMovie(1L, "Goldfinger", Genre.ACTION); // best Bond movie
    private final Movie m2 = setupMovie(2L, "Pulp Fiction", Genre.ACTION, Genre.NOIR, Genre.COMEDY);
    private final Movie m3 = setupMovie(3L, "Blade Runner", Genre.ACTION, Genre.SCIFI, Genre.NOIR);
    private final Movie m4 = setupMovie(4L, "Paddington 2", Genre.FAMILY, Genre.COMEDY);
    private final Movie m5 = setupMovie(5L, "Tiger King", Genre.DOCUMENTARY);

    // Users
    private final User u1 = setupUser("u1");
    private final User u2 = setupUser("u2");
    private final User u3 = setupUser("u3");

    // Ratings
    private final Rating m1u1Rating = setupRating(m1, u1);
    private final Rating m1u2Rating = setupRating(m1, u2);
    private final Rating m1u3Rating = setupRating(m1, u3);

    private final Rating m2u1Rating = setupRating(m2, u1);
    private final Rating m2u2Rating = setupRating(m2, u2);
    private final Rating m2u3Rating = setupRating(m2, u3);

    private final Rating m3u1Rating = setupRating(m3, u1);
    private final Rating m3u2Rating = setupRating(m3, u2);
    private final Rating m3u3Rating = setupRating(m3, u3);

    private final Rating m4u2Rating = setupRating(m4, u2);

    private final Rating m5u2Rating = setupRating(m5, u2);

    @BeforeMethod
    public void setup() {
        closeable = MockitoAnnotations.openMocks(this);
        recommendationService = new RecommendationServiceImpl(movieDao, ratingDao);
    }

    @Test
    public void recommendationsBasedOnOneUser() {
        setupMovieDaoMockFindById(m1, m2, m3);
        setupRatingDaoMocksFindByUser(u1, m1u1Rating, m2u1Rating, m3u1Rating);
        setupRatingDaoMocksFindByMovie(m1, m1u1Rating);
        setupRatingDaoMocksFindByMovie(m2, m2u1Rating);
        setupRatingDaoMocksFindByMovie(m3, m3u1Rating);

        var recommends = recommendationService.getRecommendationsBasedOnUsers(m1);

        Assert.assertTrue(recommends.contains(m2));
        Assert.assertTrue(recommends.contains(m3));
    }

    @Test
    public void recommendationsBasedOnMultipleUsers() {
        setupMovieDaoMockFindById(m1, m2, m3);
        setupRatingDaoMocksFindByUser(u1, m1u1Rating, m2u1Rating);
        setupRatingDaoMocksFindByUser(u2, m1u2Rating, m3u2Rating);
        setupRatingDaoMocksFindByMovie(m1, m1u1Rating, m1u2Rating);
        setupRatingDaoMocksFindByMovie(m2, m2u1Rating);
        setupRatingDaoMocksFindByMovie(m3, m3u2Rating);

        var recommends = recommendationService.getRecommendationsBasedOnUsers(m1);

        Assert.assertTrue(recommends.contains(m2));
        Assert.assertTrue(recommends.contains(m3));
    }

    @Test
    public void recommendationsBasedOnUsersDontContainGivenMovie() {
        setupMovieDaoMockFindById(m1, m2, m3);
        setupRatingDaoMocksFindByUser(u1, m1u1Rating, m2u1Rating, m3u1Rating);
        setupRatingDaoMocksFindByUser(u2, m1u2Rating, m2u2Rating ,m3u2Rating);
        setupRatingDaoMocksFindByMovie(m1, m1u1Rating, m1u2Rating);
        setupRatingDaoMocksFindByMovie(m2, m2u1Rating, m2u2Rating);
        setupRatingDaoMocksFindByMovie(m3, m3u1Rating, m3u2Rating);

        var movies = new ArrayList<>(Arrays.asList(m1, m2, m3));

        for (var movie : movies) {
            var recommends = recommendationService.getRecommendationsBasedOnUsers(movie);

            Assert.assertFalse(recommends.contains(movie));
        }
    }

    @Test
    public void recommendationsBasedOnUsersDontContainDuplicates() {
        setupMovieDaoMockFindById(m1, m2, m3);
        setupRatingDaoMocksFindByUser(u1, m1u1Rating, m2u1Rating, m3u1Rating);
        setupRatingDaoMocksFindByUser(u2, m1u2Rating, m2u2Rating ,m3u2Rating);
        setupRatingDaoMocksFindByUser(u3, m1u3Rating, m2u3Rating ,m3u3Rating);
        setupRatingDaoMocksFindByMovie(m1, m1u1Rating, m1u2Rating, m1u3Rating);
        setupRatingDaoMocksFindByMovie(m2, m2u1Rating, m2u2Rating, m2u3Rating);
        setupRatingDaoMocksFindByMovie(m3, m3u1Rating, m3u2Rating, m3u3Rating);

        var recommends = recommendationService.getRecommendationsBasedOnUsers(m1);

        Assert.assertTrue(recommends.contains(m2));
        Assert.assertTrue(recommends.contains(m3));
        Assert.assertEquals(recommends.size(), 2);
    }

    @Test
    public void recommendationsBasedOnUsersIfGivenMovieHasNoReviews() {
        setupMovieDaoMockFindById(m1, m2);
        setupRatingDaoMocksFindByUser(u1, m2u1Rating);
        setupRatingDaoMocksFindByMovie(m1);
        setupRatingDaoMocksFindByMovie(m2, m2u1Rating);

        var recommends = recommendationService.getRecommendationsBasedOnUsers(m1);

        Assert.assertEquals(recommends.size(), 0);
    }

    @Test
    public void recommendationsBasedOnUsersWhoDidntRateOtherMovies() {
        setupMovieDaoMockFindById(m1, m2, m3);
        setupRatingDaoMocksFindByUser(u1, m1u1Rating);
        setupRatingDaoMocksFindByUser(u2, m1u2Rating);
        setupRatingDaoMocksFindByUser(u3, m1u3Rating);
        setupRatingDaoMocksFindByMovie(m1, m1u1Rating, m1u2Rating, m1u3Rating);
        setupRatingDaoMocksFindByMovie(m2);
        setupRatingDaoMocksFindByMovie(m3);

        var recommends = recommendationService.getRecommendationsBasedOnUsers(m1);

        Assert.assertEquals(recommends.size(), 0);
    }

    @Test
    public void recommendationsBasedOnUsersCanHaveDifferentGenres() {
        setupMovieDaoMockFindById(m4, m5);
        setupRatingDaoMocksFindByUser(u2, m4u2Rating, m5u2Rating);
        setupRatingDaoMocksFindByMovie(m4, m4u2Rating);
        setupRatingDaoMocksFindByMovie(m5, m5u2Rating);

        var recommends = recommendationService.getRecommendationsBasedOnUsers(m4);

        Assert.assertTrue(recommends.contains(m5));
    }

    @Test
    public void recommendationsBasedOnUsersExcludeMoviesWithNoMutualViewers() {
        setupMovieDaoMockFindById(m1, m2, m3, m4, m5);

        // Isolated group of viewers 1
        setupRatingDaoMocksFindByUser(u1, m1u1Rating, m2u2Rating, m3u2Rating);
        setupRatingDaoMocksFindByMovie(m1, m1u1Rating);
        setupRatingDaoMocksFindByMovie(m2, m2u1Rating);
        setupRatingDaoMocksFindByMovie(m3, m3u1Rating);

        // Isolated group of viewers 2
        setupRatingDaoMocksFindByUser(u2, m4u2Rating, m5u2Rating);
        setupRatingDaoMocksFindByMovie(m4, m4u2Rating);
        setupRatingDaoMocksFindByMovie(m5, m5u2Rating);

        var recommendsForM1 = recommendationService.getRecommendationsBasedOnUsers(m1);
        Assert.assertEquals(recommendsForM1.size(), 2);
        Assert.assertTrue(recommendsForM1.contains(m2));
        Assert.assertTrue(recommendsForM1.contains(m3));

        var recommendsForM4 = recommendationService.getRecommendationsBasedOnUsers(m4);
        Assert.assertEquals(recommendsForM4.size(), 1);
        Assert.assertTrue(recommendsForM4.contains(m5));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void recommendationsBasedOnUsersWhenGivenMovieIsNull() {
        recommendationService.getRecommendationsBasedOnUsers(null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void recommendationsBasedOnUsersWhenGivenMovieDoesntExist() {
        Movie nonExistingMovie = setupMovie(19L, "Impostor", Genre.MYSTERY);
        when(movieDao.findById(nonExistingMovie.getId()))
                .thenReturn(null);

        recommendationService.getRecommendationsBasedOnUsers(nonExistingMovie);
    }

    @Test(expectedExceptions = ServiceLayerException.class)
    public void recommendationsBasedOnUsersWithPersistenceException() {
        Movie movie = setupMovie(1L, "Exception", Genre.DRAMA);
        when(movieDao.findById(movie.getId()))
                .thenThrow(PersistenceException.class);

        recommendationService.getRecommendationsBasedOnUsers(movie);
    }

    @Test
    public void recommendationsBasedOnOneGenre() {
        setupMovieDaoMockFindById(m1);
        setupMovieDaoMockFindAll(m1, m2, m3);

        var recommends = recommendationService.getRecommendationsBasedOnGenres(m1);

        Assert.assertTrue(recommends.contains(m2));
        Assert.assertTrue(recommends.contains(m3));
    }

    @Test
    public void recommendationsBasedOnMultipleGenres() {
        setupMovieDaoMockFindById(m2);
        setupMovieDaoMockFindAll(m1, m2, m3, m4);

        var recommends = recommendationService.getRecommendationsBasedOnGenres(m2);

        Assert.assertTrue(recommends.contains(m1));
        Assert.assertTrue(recommends.contains(m3));
        Assert.assertTrue(recommends.contains(m4));
    }

    @Test
    public void recommendationsBasedOnGenresDontContainGivenMovie() {
        setupMovieDaoMockFindById(m1, m2, m3);
        setupMovieDaoMockFindAll(m1, m2, m3);

        var movies = new ArrayList<>(Arrays.asList(m1, m2, m3));

        for (var movie : movies) {
            var recommends = recommendationService.getRecommendationsBasedOnGenres(movie);

            Assert.assertFalse(recommends.contains(movie));
        }
    }

    @Test
    public void recommendationsBasedOnOneGenreDontContainDuplicates() {
        setupMovieDaoMockFindById(m1);
        setupMovieDaoMockFindAll(m1, m2, m3);

        var recommends = recommendationService.getRecommendationsBasedOnGenres(m1);

        Assert.assertTrue(recommends.contains(m2));
        Assert.assertTrue(recommends.contains(m3));
        Assert.assertEquals(recommends.size(), 2);
    }

    @Test
    public void recommendationsBasedOnGenresDontContainOtherGenres() {
        setupMovieDaoMockFindById(m1);
        setupMovieDaoMockFindAll(m1, m2, m3, m4, m5);

        var recommends = recommendationService.getRecommendationsBasedOnGenres(m1);

        Assert.assertTrue(recommends.contains(m2));
        Assert.assertTrue(recommends.contains(m3));
        Assert.assertFalse(recommends.contains(m4));
        Assert.assertFalse(recommends.contains(m5));
    }

    @Test
    public void recommendationsBasedOnGenresWhenGivenMovieIsTheOnlyOneInTheGenre() {
        setupMovieDaoMockFindById(m5);
        setupMovieDaoMockFindAll(m1, m2, m3, m4, m5);

        var recommends = recommendationService.getRecommendationsBasedOnGenres(m5);

        Assert.assertEquals(recommends.size(), 0);
    }

    @Test
    public void recommendationsBasedOnGenresDontDependOnWhetherViewersAreMutual() {
        setupMovieDaoMockFindById(m1);
        setupMovieDaoMockFindAll(m1, m2, m3);

        // mutual viewer
        setupRatingDaoMocksFindByUser(u1, m1u1Rating, m2u1Rating);
        setupRatingDaoMocksFindByMovie(m1, m1u1Rating);
        setupRatingDaoMocksFindByMovie(m2, m2u1Rating);

        //not a mutual viewer
        setupRatingDaoMocksFindByUser(u2, m3u2Rating);
        setupRatingDaoMocksFindByMovie(m3, m3u2Rating);

        var recommends = recommendationService.getRecommendationsBasedOnGenres(m1);

        Assert.assertTrue(recommends.contains(m2));
        Assert.assertTrue(recommends.contains(m3));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void recommendationsBasedOnGenresWhenGivenMovieIsNull() {
        recommendationService.getRecommendationsBasedOnGenres(null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void recommendationsBasedOnGenresWhenGivenMovieDoesntExist() {
        Movie nonExistingMovie = setupMovie(19L, "Impostor", Genre.MYSTERY);
        when(movieDao.findById(nonExistingMovie.getId()))
                .thenReturn(null);

        recommendationService.getRecommendationsBasedOnGenres(nonExistingMovie);
    }

    @Test(expectedExceptions = ServiceLayerException.class)
    public void recommendationsBasedOnGenresWithPersistenceException() {
        Movie movie = setupMovie(1L, "Exception", Genre.DRAMA);
        when(movieDao.findById(movie.getId()))
                .thenThrow(PersistenceException.class);

        recommendationService.getRecommendationsBasedOnGenres(movie);
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

    private void setupMovieDaoMockFindById(Movie... movies) {
        for (var movie : movies) {
            when(movieDao.findById(movie.getId()))
                    .thenReturn(movie);
        }
    }

    private void setupMovieDaoMockFindAll(Movie... movies) {
        when(movieDao.findAll())
                .thenReturn(new ArrayList<>(Arrays.asList(movies)));
    }

    private void setupRatingDaoMocksFindByUser(User user, Rating... ratings) {
        when(ratingDao.findByUser(user))
                .thenReturn(new ArrayList<>(Arrays.asList(ratings)));
    }

    private void setupRatingDaoMocksFindByMovie(Movie movie, Rating... ratings) {
        when(ratingDao.findByMovie(movie))
                .thenReturn(new ArrayList<>(Arrays.asList(ratings)));
    }
}
