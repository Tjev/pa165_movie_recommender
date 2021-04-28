package cz.muni.fi.pa165;

import cz.muni.fi.pa165.dao.MovieDao;
import cz.muni.fi.pa165.entity.Movie;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

public class MovieServiceTest {

    @Mock
    private MovieDao movieDao;

    private MovieService movieService;

    private AutoCloseable closable;

    @BeforeClass
    public void setup() {
        closable = MockitoAnnotations.openMocks(this);
        movieService = new MovieServiceImpl(movieDao);
    }

    @Test
    public void createMovieTest() {
        Movie movie = new Movie();
        movie.setTitle("Dune");

        movieService.create(movie);

        verify(movieDao, times(1)).create(movie);
    }

    @Test
    public void findByIdTest() {
        Movie movie = new Movie();
        movie.setId(1L);

        when(movieDao.findById(movie.getId())).thenReturn(movie);
        Assert.assertEquals(movie, movieService.findById(movie.getId()));
    }

    @Test
    public void findByTitleTest() {
        List<Movie> movies = new ArrayList<>();
        Movie movie = new Movie();
        movie.setTitle("Tenet");
        movies.add(movie);

        when(movieDao.findByTitle(movie.getTitle())).thenReturn(movies);
        Assert.assertEquals(movies, movieService.findByTitle(movie.getTitle()));
    }

    @Test
    public void findAllEmptyTest() {
        List<Movie> movies = new ArrayList<>();
        when(movieDao.findAll()).thenReturn(movies);

        Assert.assertEquals(movieService.findAll(), movies);
    }

    @Test
    public void findAllTest() {
        List<Movie> movies = new ArrayList<>();

        Movie movie1 = new Movie();
        movie1.setTitle("Matrix");
        Movie movie2 = new Movie();
        movie2.setTitle("Dune");

        movies.add(movie1);
        movies.add(movie2);
        when(movieDao.findAll()).thenReturn(movies);

        Assert.assertEquals(movieService.findAll(), movies);
    }

    @AfterClass
    public void releaseMocks() throws Exception {
        closable.close();
    }
}
