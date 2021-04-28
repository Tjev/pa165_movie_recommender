package cz.muni.fi.pa165;

import cz.muni.fi.pa165.dao.MovieDao;
import cz.muni.fi.pa165.entity.Genre;
import cz.muni.fi.pa165.entity.Movie;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.mockito.Mockito.*;

public class MovieServiceTest {

    @Mock
    private MovieDao movieDao;

    private MovieService movieService;

    private AutoCloseable closable;

    private Movie movie1;
    private Movie movie2;
    private List<Movie> movies;

    @BeforeClass
    public void setup() {
        closable = MockitoAnnotations.openMocks(this);
        movieService = new MovieServiceImpl(movieDao);
    }

    @BeforeMethod
    public void init() {
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
    public void createMovieTest() {
        movieService.create(movie1);
        verify(movieDao, times(1)).create(movie1);
    }

    @Test
    public void findByIdTest() {
        when(movieDao.findById(movie1.getId())).thenReturn(movie1);
        Assert.assertEquals(movie1, movieService.findById(movie1.getId()));
    }

    @Test
    public void findByTitleTest() {
        when(movieDao.findByTitle(movie1.getTitle())).thenReturn(movies);
        Assert.assertEquals(movies, movieService.findByTitle(movie1.getTitle()));
    }

    @Test
    public void findAllEmptyTest() {
        List<Movie> movies = new ArrayList<>();
        when(movieDao.findAll()).thenReturn(movies);

        Assert.assertEquals(movieService.findAll(), movies);
        Assert.assertEquals(movieService.findAll().size(), 0);
    }

    @Test
    public void findAllTest() {
        when(movieDao.findAll()).thenReturn(movies);
        Assert.assertEquals(movieService.findAll(), movies);
    }

    @Test
    public void updateTest() {
        when(movieDao.findById(movie1.getId())).thenReturn(movie1);
        movie1.setTitle("New Title");
        movieService.update(movie1);

        verify(movieDao, times(1)).update(movie1);
    }

    @Test
    public void removeTest() {
        when(movieDao.findById(movie2.getId())).thenReturn(movie2);
        movieService.remove(movie2);

        verify(movieDao, times(1)).remove(movie2);
    }

    @AfterClass
    public void releaseMocks() throws Exception {
        closable.close();
    }
}
