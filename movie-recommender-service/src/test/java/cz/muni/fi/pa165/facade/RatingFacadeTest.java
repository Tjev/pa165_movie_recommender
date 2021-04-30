package cz.muni.fi.pa165.facade;

import cz.fi.muni.pa165.dto.MovieDTO;
import cz.fi.muni.pa165.dto.RatingCreateDTO;
import cz.fi.muni.pa165.dto.RatingDTO;
import cz.fi.muni.pa165.dto.UserDTO;
import cz.fi.muni.pa165.facade.RatingFacade;
import cz.muni.fi.pa165.MovieService;
import cz.muni.fi.pa165.RatingService;
import cz.muni.fi.pa165.ScoreComputationService;
import cz.muni.fi.pa165.UserService;
import cz.muni.fi.pa165.entity.Genre;
import cz.muni.fi.pa165.entity.Movie;
import cz.muni.fi.pa165.entity.Rating;
import cz.muni.fi.pa165.entity.User;
import cz.muni.fi.pa165.mapper.RatingMapper;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@Transactional
public class RatingFacadeTest {

    @Mock
    private RatingService ratingService;

    @Mock
    private UserService userService;

    @Mock
    private MovieService movieService;

    @Mock
    private ScoreComputationService scoreComputationService;

    @Mock
    private RatingMapper ratingMapper;

    private RatingFacade ratingFacade;

    private AutoCloseable closeable;

    private User user;
    private Movie movie;

    @BeforeMethod
    public void setup() {
        closeable = MockitoAnnotations.openMocks(this);
        ratingFacade = new RatingFacadeImpl(ratingService, userService, movieService,
                scoreComputationService, ratingMapper);

        setupUser();
        setupMovie();

        when(ratingMapper.ratingCreateDTOToRating(any(RatingCreateDTO.class)))
                .thenReturn(new Rating());
        when(ratingMapper.ratingDTOToRating(any(RatingDTO.class)))
                .thenReturn(new Rating());
        when(ratingMapper.ratingToRatingDTO(any(Rating.class)))
                .thenReturn(new RatingDTO());
    }

    private void setupUser() {
        user = new User("user", "user@mail.com");
    }

    private void setupMovie() {
        movie = new Movie();
        movie.setTitle("title");
        movie.addGenre(Genre.THRILLER);
        movie.setYear(LocalDate.of(2000, 1, 1));
    }

    @AfterMethod
    public void releaseMocks() throws Exception {
        closeable.close();
    }

    @Test
    public void create() {
        RatingCreateDTO ratingCreateDTO = new RatingCreateDTO();
        ratingCreateDTO.setCinematography(3);
        Rating createdRating = new Rating();
        createdRating.setCinematography(3);

        when(ratingService.create(any(Rating.class))).thenReturn(new Rating());

        ratingFacade.create(ratingCreateDTO);

        verify(ratingService, times(1)).create(any(Rating.class));
        verify(ratingMapper, times(1)).ratingCreateDTOToRating(any(RatingCreateDTO.class));
        verify(ratingMapper, times(1)).ratingToRatingDTO(any(Rating.class));
    }

    @Test
    public void update() {
        RatingDTO ratingDTO = new RatingDTO();
        when(ratingService.update(any(Rating.class))).thenReturn(new Rating());

        ratingFacade.update(ratingDTO);

        verify(ratingService, times(1)).update(any(Rating.class));
        verify(ratingMapper, times(1)).ratingDTOToRating(any(RatingDTO.class));
        verify(ratingMapper, times(1)).ratingToRatingDTO(any(Rating.class));
    }

    @Test
    public void remove() {
        RatingDTO ratingDTO = new RatingDTO();
        ratingFacade.remove(ratingDTO);

        verify(ratingService, times(1)).remove(any(Rating.class));
        verify(ratingMapper, times(1)).ratingDTOToRating(any(RatingDTO.class));
    }

    @Test
    public void findById() {
        when(ratingService.findById(anyLong())).thenReturn(new Rating());

        ratingFacade.findById(1L);

        verify(ratingService, times(1)).findById(anyLong());
        verify(ratingMapper, times(1)).ratingToRatingDTO(any(Rating.class));
    }

    @Test
    public void findByMovie() {
        MovieDTO movieDTO = new MovieDTO();
        movieDTO.setId(1L);
        when(movieService.findById(anyLong())).thenReturn(new Movie());
        when(ratingService.findByMovie(any(Movie.class))).thenReturn(new ArrayList<>());

        ratingFacade.findByMovie(movieDTO);

        verify(ratingService, times(1)).findByMovie(any(Movie.class));
        verify(movieService, times(1)).findById(anyLong());
        verify(ratingMapper, times(1)).mapRatingsToRatingDTOs(any(ArrayList.class));
    }

    @Test
    public void findByUser() {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(1L);
        when(userService.findById(anyLong())).thenReturn(new User());
        when(ratingService.findByUser(any(User.class))).thenReturn(new ArrayList<>());

        ratingFacade.findByUser(userDTO);

        verify(ratingService, times(1)).findByUser(any(User.class));
        verify(userService, times(1)).findById(anyLong());
        verify(ratingMapper, times(1)).mapRatingsToRatingDTOs(any(ArrayList.class));
    }

    @Test
    public void getOverallScore() {
        RatingDTO ratingDTO = new RatingDTO();
        when(scoreComputationService.getOverallScoreForRating(any(Rating.class))).thenReturn(new BigDecimal('1'));

        ratingFacade.getOverallScore(ratingDTO);

        verify(scoreComputationService, times(1))
                .getOverallScoreForRating(any(Rating.class));
        verify(ratingMapper, times(1)).ratingDTOToRating(any(RatingDTO.class));

    }
}
