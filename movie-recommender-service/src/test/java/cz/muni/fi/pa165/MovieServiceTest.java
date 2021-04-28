package cz.muni.fi.pa165;

import cz.muni.fi.pa165.dao.MovieDao;
import cz.muni.fi.pa165.entity.Movie;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

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

    @AfterClass
    public void releaseMocks() throws Exception {
        closable.close();
    }
}
