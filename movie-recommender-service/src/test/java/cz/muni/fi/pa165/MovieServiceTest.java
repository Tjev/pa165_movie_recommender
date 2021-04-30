package cz.muni.fi.pa165;

import cz.muni.fi.pa165.dao.MovieDao;
import cz.muni.fi.pa165.entity.Genre;
import cz.muni.fi.pa165.entity.Movie;
import cz.muni.fi.pa165.exception.ServiceLayerException;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.persistence.PersistenceException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.mockito.Mockito.*;

/**
 * Tests for MovieService.
 *
 * @author Kristian Tkacik
 */
public class MovieServiceTest {

    @Mock
    private MovieDao movieDao;

    private MovieService movieService;

    private AutoCloseable closable;

    private Movie movie1;
    private Movie movie2;
    private List<Movie> movies;

    @BeforeMethod
    public void init() {
        closable = MockitoAnnotations.openMocks(this);
        movieService = new MovieServiceImpl(movieDao);

        movie1 = new Movie("Matrix", null, LocalDate.of(1999, 3, 31),
                new HashSet<Genre>(Arrays.asList(Genre.ACTION)), null);
        movie2 = new Movie("Dune", null, LocalDate.of(2021, 10, 1),
                new HashSet<Genre>(Arrays.asList(Genre.SCIFI)), null);

        movie1.setId(1L);
        movie2.setId(2L);

        movies = new ArrayList<>();
        movies.add(movie1);
        movies.add(movie2);
    }

    @Test
    public void createMovie() {
        movieService.create(movie1);
        verify(movieDao, times(1)).create(movie1);
        verifyNoMoreInteractions(movieDao);
    }

    @Test
    void createNullMovie() {
        Assert.assertThrows(IllegalArgumentException.class, () -> {
            movieService.create(null);
        });
    }

    @Test
    void createThrowsServiceLayerException() {
        doThrow(PersistenceException.class).when(movieDao).create(movie1);

        Assert.assertThrows(ServiceLayerException.class, () -> {
            movieService.create(movie1);
        });
    }

    @Test
    public void findAll() {
        when(movieDao.findAll()).thenReturn(movies);
        Assert.assertEquals(movieService.findAll(), movies);
    }

    @Test
    public void findAllEmpty() {
        List<Movie> movies = new ArrayList<>();
        when(movieDao.findAll()).thenReturn(movies);

        Assert.assertEquals(movieService.findAll(), movies);
        Assert.assertEquals(movieService.findAll().size(), 0);
    }

    @Test
    public void findAllThrowsServiceLayerException() {
        when(movieDao.findAll()).thenThrow(PersistenceException.class);
        Assert.assertThrows(ServiceLayerException.class, () -> {
            movieService.findAll();
        });
    }

    @Test
    public void findById() {
        when(movieDao.findById(movie1.getId())).thenReturn(movie1);
        Assert.assertEquals(movie1, movieService.findById(movie1.getId()));
    }

    @Test
    void findByIdNull() {
        Assert.assertThrows(IllegalArgumentException.class, () -> {
            movieService.findById(null);
        });
    }

    @Test
    void findByIdThrowsServiceLayerException() {
        when(movieDao.findById(movie1.getId())).thenThrow(PersistenceException.class);

        Assert.assertThrows(ServiceLayerException.class, () -> {
            movieService.findById(movie1.getId());
        });
    }

    @Test
    public void findByTitle() {
        when(movieDao.findByTitle(movie1.getTitle())).thenReturn(movies);
        Assert.assertEquals(movies, movieService.findByTitle(movie1.getTitle()));
    }

    @Test
    public void findByTitleNull() {
        Assert.assertThrows(IllegalArgumentException.class, () -> {
            movieService.findByTitle(null);
        });
    }

    @Test
    public void findByTitleEmpty() {
        Assert.assertThrows(IllegalArgumentException.class, () -> {
            movieService.findByTitle("");
        });
    }

    @Test
    public void findByTitleThrowsServiceLayerException() {
        when(movieDao.findByTitle(movie1.getTitle())).thenThrow(PersistenceException.class);
        Assert.assertThrows(IllegalArgumentException.class, () -> {
            movieService.findByTitle("");
        });
    }

    @Test
    public void update() {
        when(movieDao.findById(movie1.getId())).thenReturn(movie1);
        movie1.setTitle("New Title");
        movieService.update(movie1);

        verify(movieDao, times(1)).update(movie1);
        verify(movieDao, times(1)).findById(movie1.getId());
        verifyNoMoreInteractions(movieDao);
    }

    @Test
    public void updateNullMovie() {
        Assert.assertThrows(IllegalArgumentException.class, () -> {
            movieService.update(null);
        });
    }

    @Test
    public void updateMovieNotInDatabase() {
        Movie movie = new Movie();

        Assert.assertThrows(IllegalArgumentException.class, () -> {
            movieService.update(movie);
        });
    }

    @Test
    public void updateThrowsServiceLayerException() {
        doThrow(PersistenceException.class).when(movieDao).update(movie1);
        Assert.assertThrows(IllegalArgumentException.class, () -> {
            movieService.update(movie1);
        });
    }

    @Test
    public void remove() {
        when(movieDao.findById(movie2.getId())).thenReturn(movie2);
        movieService.remove(movie2);

        verify(movieDao, times(1)).remove(movie2);
    }

    @Test
    public void removeNullMovie() {
        Assert.assertThrows(IllegalArgumentException.class, () -> {
            movieService.remove(null);
        });
    }

    @Test
    public void removeMovieNotInDatabase() {
        Movie movie = new Movie();

        Assert.assertThrows(IllegalArgumentException.class, () -> {
            movieService.remove(movie);
        });
    }

    @Test
    public void removeThrowsServiceLayerException() {
        doThrow(PersistenceException.class).when(movieDao).remove(movie1);
        Assert.assertThrows(IllegalArgumentException.class, () -> {
            movieService.remove(movie1);
        });
    }

    @AfterClass
    public void releaseMocks() throws Exception {
        closable.close();
    }
}
