package cz.muni.fi.pa165;

import cz.muni.fi.pa165.dao.MovieDao;
import cz.muni.fi.pa165.dao.RatingDao;
import cz.muni.fi.pa165.entity.Genre;
import cz.muni.fi.pa165.entity.Movie;
import cz.muni.fi.pa165.entity.Rating;
import cz.muni.fi.pa165.entity.User;
import cz.muni.fi.pa165.exceptions.ServiceLayerException;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.Assert;

import javax.persistence.PersistenceException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

import static org.mockito.Mockito.when;

/**
 * Tests for ScoreComputationImpl class
 *
 * @author Jiri Papousek
 */
public class ScoreComputationServiceImplTest {

    private static final Set<Genre> GENRES = new HashSet<>(Arrays.asList(Genre.ACTION));

    @Mock
    private RatingDao ratingDao;

    @Mock
    private MovieDao movieDao;

    private ScoreComputationService scoreComputationService;

    private AutoCloseable closable;

    private Movie movie;
    private User user1;
    private User user2;
    private List<Rating> ratings;


    @BeforeMethod
    public void setup() {
        closable = MockitoAnnotations.openMocks(this);
        scoreComputationService = new ScoreComputationServiceImpl(ratingDao, movieDao);

        movie = new Movie("Titanic", null, LocalDate.of(1997, 2, 15), GENRES, null);
        user1 = new User("novak", "novak@mail.com");
        user2 = new User("svoboda", "svoboda@mail.com");

        Rating rating1 = new Rating(movie, user1, 1, 2, 3, 4, 5);
        rating1.setId(1L);
        Rating rating2 = new Rating(movie, user2, 2, 3, 2, 2, 1);
        rating1.setId(2L);

        ratings = new ArrayList<>();
        ratings.add(rating1);
        ratings.add(rating2);
    }

    @Test
    void getOriginalityScoreForMovie() {
        when(movieDao.findById(movie.getId())).thenReturn(movie);
        when(ratingDao.findByMovie(movie)).thenReturn(ratings);

        Assert.assertEquals(scoreComputationService.getOriginalityScoreForMovie(movie), new BigDecimal("1.5"));
    }

    @Test
    void getSoundtrackScoreForMovie() {
        when(movieDao.findById(movie.getId())).thenReturn(movie);
        when(ratingDao.findByMovie(movie)).thenReturn(ratings);

        Assert.assertEquals(scoreComputationService.getSoundtrackScoreForMovie(movie), new BigDecimal("2.5"));
    }

    @Test
    void getNarrativeScoreForMovie() {
        when(movieDao.findById(movie.getId())).thenReturn(movie);
        when(ratingDao.findByMovie(movie)).thenReturn(ratings);

        Assert.assertEquals(scoreComputationService.getNarrativeScoreForMovie(movie), new BigDecimal("2.5"));
    }

    @Test
    void getCinematographyScoreForMovie() {
        when(movieDao.findById(movie.getId())).thenReturn(movie);
        when(ratingDao.findByMovie(movie)).thenReturn(ratings);

        Assert.assertEquals(scoreComputationService.getCinematographyScoreForMovie(movie), new BigDecimal("3"));
    }

    @Test
    void getDepthScoreForMovie() {
        when(movieDao.findById(movie.getId())).thenReturn(movie);
        when(ratingDao.findByMovie(movie)).thenReturn(ratings);

        Assert.assertEquals(scoreComputationService.getDepthScoreForMovie(movie), new BigDecimal("3"));
    }

    @Test
    void getOverallScoreForMovie() {
        when(movieDao.findById(movie.getId())).thenReturn(movie);
        when(ratingDao.findByMovie(movie)).thenReturn(ratings);
        when(ratingDao.findById(ratings.get(0).getId())).thenReturn(ratings.get(0));
        when(ratingDao.findById(ratings.get(1).getId())).thenReturn(ratings.get(1));

        Assert.assertEquals(scoreComputationService.getOverallScoreForMovie(movie), new BigDecimal("2.5"));
    }

    @Test
    void getOverallScoreForRating() {
        when(movieDao.findById(movie.getId())).thenReturn(movie);
        when(ratingDao.findByMovie(movie)).thenReturn(ratings);
        when(ratingDao.findById(ratings.get(0).getId())).thenReturn(ratings.get(0));

        Assert.assertEquals(scoreComputationService.getOverallScoreForRating(ratings.get(0)), new BigDecimal("3"));
    }

    @Test
    void getOriginalityScoreForNullMovie() {
        Assert.assertThrows(IllegalArgumentException.class, () -> {
            scoreComputationService.getOriginalityScoreForMovie(null);
        });
    }

    @Test
    void getSoundtrackScoreForNullMovie() {
        Assert.assertThrows(IllegalArgumentException.class, () -> {
            scoreComputationService.getSoundtrackScoreForMovie(null);
        });
    }

    @Test
    void getNarrativeScoreForNullMovie() {
        Assert.assertThrows(IllegalArgumentException.class, () -> {
            scoreComputationService.getNarrativeScoreForMovie(null);
        });
    }

    @Test
    void getCinematographyScoreForNullMovie() {
        Assert.assertThrows(IllegalArgumentException.class, () -> {
            scoreComputationService.getCinematographyScoreForMovie(null);
        });
    }

    @Test
    void getDepthScoreForNullMovie() {
        Assert.assertThrows(IllegalArgumentException.class, () -> {
            scoreComputationService.getDepthScoreForMovie(null);
        });
    }

    @Test
    void getOverallScoreForNullMovie() {
        Assert.assertThrows(IllegalArgumentException.class, () -> {
            scoreComputationService.getOverallScoreForMovie(null);
        });
    }

    @Test
    void getOverallScoreForNullRating() {
        Assert.assertThrows(IllegalArgumentException.class, () -> {
            scoreComputationService.getOverallScoreForRating(null);
        });
    }

    @Test
    void getOriginalityScoreForNonexistentMovie() {
        when(movieDao.findById(movie.getId())).thenReturn(null);

        Assert.assertThrows(IllegalArgumentException.class, () -> {
            scoreComputationService.getOriginalityScoreForMovie(movie);
        });
    }

    @Test
    void getSoundtrackScoreForNonexistentMovie() {
        when(movieDao.findById(movie.getId())).thenReturn(null);

        Assert.assertThrows(IllegalArgumentException.class, () -> {
            scoreComputationService.getSoundtrackScoreForMovie(movie);
        });
    }

    @Test
    void getNarrativeScoreForNonexistentMovie() {
        when(movieDao.findById(movie.getId())).thenReturn(null);

        Assert.assertThrows(IllegalArgumentException.class, () -> {
            scoreComputationService.getNarrativeScoreForMovie(movie);
        });
    }

    @Test
    void getCinematographyScoreForNonexistentMovie() {
        when(movieDao.findById(movie.getId())).thenReturn(null);

        Assert.assertThrows(IllegalArgumentException.class, () -> {
            scoreComputationService.getCinematographyScoreForMovie(movie);
        });
    }

    @Test
    void getDepthScoreForNonexistentMovie() {
        when(movieDao.findById(movie.getId())).thenReturn(null);

        Assert.assertThrows(IllegalArgumentException.class, () -> {
            scoreComputationService.getDepthScoreForMovie(movie);
        });
    }

    @Test
    void getOverallScoreForNonexistentMovie() {
        when(movieDao.findById(movie.getId())).thenReturn(null);

        Assert.assertThrows(IllegalArgumentException.class, () -> {
            scoreComputationService.getOverallScoreForMovie(movie);
        });
    }

    @Test
    void getOverallScoreForNonexistentRating() {
        when(ratingDao.findById(ratings.get(0).getId())).thenReturn(null);

        Assert.assertThrows(IllegalArgumentException.class, () -> {
            scoreComputationService.getOverallScoreForRating(ratings.get(0));
        });
    }

    @Test
    void getOriginalityScorePersistenceLayerFailedInMovieDao() {
        when(movieDao.findById(movie.getId())).thenThrow(PersistenceException.class);

        Assert.assertThrows(ServiceLayerException.class, () -> {
            scoreComputationService.getOriginalityScoreForMovie(movie);
        });
    }

    @Test
    void getSoundtrackScorePersistenceLayerFailedInMovieDao() {
        when(movieDao.findById(movie.getId())).thenThrow(PersistenceException.class);

        Assert.assertThrows(ServiceLayerException.class, () -> {
            scoreComputationService.getSoundtrackScoreForMovie(movie);
        });
    }

    @Test
    void getNarrativeScorePersistenceLayerFailedInMovieDao() {
        when(movieDao.findById(movie.getId())).thenThrow(PersistenceException.class);

        Assert.assertThrows(ServiceLayerException.class, () -> {
            scoreComputationService.getNarrativeScoreForMovie(movie);
        });
    }

    @Test
    void getCinematographyScorePersistenceLayerFailedMovieDao() {
        when(movieDao.findById(movie.getId())).thenThrow(PersistenceException.class);

        Assert.assertThrows(ServiceLayerException.class, () -> {
            scoreComputationService.getCinematographyScoreForMovie(movie);
        });
    }

    @Test
    void getDepthScorePersistenceLayerFailedInMovieDao() {
        when(movieDao.findById(movie.getId())).thenThrow(PersistenceException.class);

        Assert.assertThrows(ServiceLayerException.class, () -> {
            scoreComputationService.getDepthScoreForMovie(movie);
        });
    }

    @Test
    void getOverallScoreForMoviePersistenceLayerFailedInMovieDao() {
        when(movieDao.findById(movie.getId())).thenThrow(PersistenceException.class);

        Assert.assertThrows(ServiceLayerException.class, () -> {
            scoreComputationService.getOverallScoreForMovie(movie);
        });
    }

    @Test
    void getOriginalityScorePersistenceLayerFailedInRatingDao() {
        when(movieDao.findById(movie.getId())).thenReturn(movie);
        when(ratingDao.findByMovie(movie)).thenThrow(PersistenceException.class);

        Assert.assertThrows(ServiceLayerException.class, () -> {
            scoreComputationService.getOriginalityScoreForMovie(movie);
        });
    }

    @Test
    void getSoundtrackScorePersistenceLayerFailedInRatingDao() {
        when(movieDao.findById(movie.getId())).thenReturn(movie);
        when(ratingDao.findByMovie(movie)).thenThrow(PersistenceException.class);

        Assert.assertThrows(ServiceLayerException.class, () -> {
            scoreComputationService.getSoundtrackScoreForMovie(movie);
        });
    }

    @Test
    void getNarrativeScoreFindByIdPersistenceLayerFailedInRatingDao() {
        when(movieDao.findById(movie.getId())).thenReturn(movie);
        when(ratingDao.findByMovie(movie)).thenThrow(PersistenceException.class);

        Assert.assertThrows(ServiceLayerException.class, () -> {
            scoreComputationService.getNarrativeScoreForMovie(movie);
        });
    }

    @Test
    void getCinematographyScorePersistenceLayerFailedInRatingDao() {
        when(movieDao.findById(movie.getId())).thenReturn(movie);
        when(ratingDao.findByMovie(movie)).thenThrow(PersistenceException.class);

        Assert.assertThrows(ServiceLayerException.class, () -> {
            scoreComputationService.getCinematographyScoreForMovie(movie);
        });
    }

    @Test
    void getDepthScorePersistenceLayerFailedInRatingDao() {
        when(movieDao.findById(movie.getId())).thenReturn(movie);
        when(ratingDao.findByMovie(movie)).thenThrow(PersistenceException.class);

        Assert.assertThrows(ServiceLayerException.class, () -> {
            scoreComputationService.getDepthScoreForMovie(movie);
        });
    }

    @Test
    void getOverallScoreForMoviePersistenceLayerFailedInRatingDao() {
        when(movieDao.findById(movie.getId())).thenReturn(movie);
        when(ratingDao.findByMovie(movie)).thenThrow(PersistenceException.class);

        Assert.assertThrows(ServiceLayerException.class, () -> {
            scoreComputationService.getOverallScoreForMovie(movie);
        });
    }

    @Test
    void getOverallScoreForRatingPersistenceLayerFailed() {
        when(ratingDao.findById(ratings.get(0).getId())).thenThrow(PersistenceException.class);

        Assert.assertThrows(ServiceLayerException.class, () -> {
            scoreComputationService.getOverallScoreForRating(ratings.get(0));
        });
    }
}
