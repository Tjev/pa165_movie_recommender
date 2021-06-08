package cz.muni.fi.pa165.facade;

import cz.muni.fi.pa165.dto.movie.MovieCreateDTO;
import cz.muni.fi.pa165.dto.movie.MovieDTO;
import cz.muni.fi.pa165.dto.movie.MovieDetailedDTO;
import cz.muni.fi.pa165.dto.person.PersonDTO;
import cz.muni.fi.pa165.service.MovieService;
import cz.muni.fi.pa165.service.RecommendationService;
import cz.muni.fi.pa165.service.ScoreComputationService;
import cz.muni.fi.pa165.entity.*;
import cz.muni.fi.pa165.mapper.MovieMapper;
import cz.muni.fi.pa165.mapper.PersonMapper;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

/**
 * Tests for MovieFacade implementation.
 *
 * @author Jiri Papousek
 */
public class MovieFacadeTest {

    private AutoCloseable closeable;

    @Mock
    private MovieService movieService;

    @Mock
    private RecommendationService recommendationService;

    @Mock
    private ScoreComputationService scoreService;

    @Mock
    private MovieMapper movieMapper;

    @Mock
    private PersonMapper personMapper;

    private MovieFacade movieFacade;

    private Movie m1;
    private Movie m2;
    private Movie m3;
    private Movie m4;
    private Movie m5;

    private Person p;

    @BeforeMethod
    public void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
        movieFacade = new MovieFacadeImpl(
                movieService,
                recommendationService,
                scoreService,
                movieMapper,
                personMapper);

        m1 = setupMovie(1L, "Golfinger", Genre.ACTION); // best Bond movie
        m2 = setupMovie(2L, "Pulp Fiction", Genre.ACTION, Genre.NOIR, Genre.COMEDY);
        m3 = setupMovie(3L, "Blade Runner", Genre.ACTION, Genre.SCIFI, Genre.NOIR);
        m4 = setupMovie(4L, "Paddington 2", Genre.FAMILY, Genre.COMEDY);
        m5 = setupMovie(5L, "Tiger King", Genre.DOCUMENTARY);

        p = new Person("Brad Pitt", LocalDate.now(), "He is cool");

        when(scoreService.getOverallScoreForMovie(m1)).thenReturn(new BigDecimal("5"));
        when(scoreService.getOverallScoreForMovie(m2)).thenReturn(new BigDecimal("4"));
        when(scoreService.getOverallScoreForMovie(m3)).thenReturn(new BigDecimal("3"));
        when(scoreService.getOverallScoreForMovie(m4)).thenReturn(new BigDecimal("2"));
        when(scoreService.getOverallScoreForMovie(m5)).thenReturn(new BigDecimal("1"));
    }

    @Test
    public void create() {
        MovieCreateDTO inputDTO = new MovieCreateDTO();
        inputDTO.setTitle(m1.getTitle());
        when(movieMapper.movieCreateDTOToMovie(inputDTO)).thenReturn(m1);

        MovieDetailedDTO outputDTO = new MovieDetailedDTO();
        outputDTO.setTitle(m1.getTitle());
        outputDTO.setId(1L);

        when(movieService.create(m1)).thenReturn(m1);
        when(movieMapper.movieToMovieDetailedDTO(m1)).thenReturn(outputDTO);

        MovieDetailedDTO result = movieFacade.create(inputDTO);

        Assert.assertEquals(result, outputDTO);
    }

    @Test
    public void findById() {
        MovieDetailedDTO outputDTO = new MovieDetailedDTO();
        outputDTO.setTitle(m1.getTitle());
        outputDTO.setId(1L);

        when(movieService.findById(1L)).thenReturn(m1);
        when(movieMapper.movieToMovieDetailedDTO(m1)).thenReturn(outputDTO);

        Optional<MovieDetailedDTO> result = movieFacade.findById(1L);

        Assert.assertTrue(result.isPresent());
        Assert.assertEquals(result.get(), outputDTO);
    }

    @Test
    public void findByTitle() {
        MovieDetailedDTO outputDTO = new MovieDetailedDTO();
        outputDTO.setTitle(m1.getTitle());
        outputDTO.setId(1L);

        when(movieService.findByTitle(m1.getTitle())).thenReturn(List.of(m1));
        when(movieMapper.movieListToMovieDetailedDTOList(List.of(m1))).thenReturn(List.of(outputDTO));

        List<MovieDetailedDTO> result = movieFacade.findByTitle(m1.getTitle());

        Assert.assertEquals(result, List.of(outputDTO));
    }

    @Test
    public void update() {
        MovieDetailedDTO DTO = new MovieDetailedDTO();
        DTO.setTitle(m1.getTitle());
        DTO.setId(1L);

        when(movieMapper.movieDetailedDTOToMovie(DTO)).thenReturn(m1);
        when(movieService.update(m1)).thenReturn(m1);
        when(movieMapper.movieToMovieDetailedDTO(m1)).thenReturn(DTO);

        MovieDetailedDTO result = movieFacade.update(DTO);

        Assert.assertEquals(result, DTO);
    }

    @Test
    public void addActor() {
        MovieDTO movieDTO = new MovieDTO();
        movieDTO.setTitle(m1.getTitle());
        movieDTO.setId(1L);

        PersonDTO personDTO = new PersonDTO();
        personDTO.setId(1L);

        MovieDetailedDTO updatedMovieDTO = new MovieDetailedDTO();
        movieDTO.setTitle(m1.getTitle());
        movieDTO.setId(1L);

        Movie m1updated = m1;
        m1updated.addActor(p);

        when(movieMapper.movieDTOToMovie(movieDTO)).thenReturn(m1);
        when(personMapper.personDTOToPerson(personDTO)).thenReturn(p);
        when(movieService.update(m1updated)).thenReturn(m1updated);
        when(movieMapper.movieToMovieDetailedDTO(m1updated)).thenReturn(updatedMovieDTO);

        MovieDetailedDTO result = movieFacade.addActor(movieDTO, personDTO);

        Assert.assertEquals(result, updatedMovieDTO);
    }

    @Test
    public void addDirector() {
        MovieDTO movieDTO = new MovieDTO();
        movieDTO.setTitle(m1.getTitle());
        movieDTO.setId(1L);

        PersonDTO personDTO = new PersonDTO();
        personDTO.setId(1L);

        MovieDetailedDTO updatedMovieDTO = new MovieDetailedDTO();
        movieDTO.setTitle(m1.getTitle());
        movieDTO.setId(1L);

        Movie m1updated = m1;
        m1updated.addDirector(p);

        when(movieMapper.movieDTOToMovie(movieDTO)).thenReturn(m1);
        when(personMapper.personDTOToPerson(personDTO)).thenReturn(p);
        when(movieService.update(m1updated)).thenReturn(m1updated);
        when(movieMapper.movieToMovieDetailedDTO(m1updated)).thenReturn(updatedMovieDTO);

        MovieDetailedDTO result = movieFacade.addDirector(movieDTO, personDTO);

        Assert.assertEquals(result, updatedMovieDTO);
    }

    @Test
    public void remove() {
        MovieDTO DTO = new MovieDTO();
        DTO.setTitle(m1.getTitle());
        DTO.setId(1L);

        when(movieMapper.movieDTOToMovie(DTO)).thenReturn(m1);

        movieFacade.remove(DTO);
        verify(movieService, times(1)).remove(m1);
        verifyNoMoreInteractions(movieService);
    }

    @Test
    public void getOverallScore() {
        MovieDTO DTO = new MovieDTO();
        DTO.setTitle(m1.getTitle());
        DTO.setId(1L);

        when(movieMapper.movieDTOToMovie(DTO)).thenReturn(m1);

        when(scoreService.getOverallScoreForMovie(m1)).thenReturn(BigDecimal.ONE);

        BigDecimal result = movieFacade.getOverallScore(DTO);

        Assert.assertEquals(result, BigDecimal.ONE);
    }

    @Test
    public void getOriginalityScore() {
        MovieDTO DTO = new MovieDTO();
        DTO.setTitle(m1.getTitle());
        DTO.setId(1L);

        when(movieMapper.movieDTOToMovie(DTO)).thenReturn(m1);

        when(scoreService.getOriginalityScoreForMovie(m1)).thenReturn(BigDecimal.ONE);

        BigDecimal result = movieFacade.getOriginalityScore(DTO);

        Assert.assertEquals(result, BigDecimal.ONE);
    }

    @Test
    public void getSoundtrackScore() {
        MovieDTO DTO = new MovieDTO();
        DTO.setTitle(m1.getTitle());
        DTO.setId(1L);

        when(movieMapper.movieDTOToMovie(DTO)).thenReturn(m1);

        when(scoreService.getSoundtrackScoreForMovie(m1)).thenReturn(BigDecimal.ONE);

        BigDecimal result = movieFacade.getSoundtrackScore(DTO);

        Assert.assertEquals(result, BigDecimal.ONE);
    }

    @Test
    public void getNarrativeScore() {
        MovieDTO DTO = new MovieDTO();
        DTO.setTitle(m1.getTitle());
        DTO.setId(1L);

        when(movieMapper.movieDTOToMovie(DTO)).thenReturn(m1);

        when(scoreService.getNarrativeScoreForMovie(m1)).thenReturn(BigDecimal.ONE);

        BigDecimal result = movieFacade.getNarrativeScore(DTO);

        Assert.assertEquals(result, BigDecimal.ONE);
    }

    @Test
    public void getCinematographyScore() {
        MovieDTO DTO = new MovieDTO();
        DTO.setTitle(m1.getTitle());
        DTO.setId(1L);

        when(movieMapper.movieDTOToMovie(DTO)).thenReturn(m1);

        when(scoreService.getCinematographyScoreForMovie(m1)).thenReturn(BigDecimal.ONE);

        BigDecimal result = movieFacade.getCinematographyScore(DTO);

        Assert.assertEquals(result, BigDecimal.ONE);
    }

    @Test
    public void getDepthScore() {
        MovieDTO DTO = new MovieDTO();
        DTO.setTitle(m1.getTitle());
        DTO.setId(1L);

        when(movieMapper.movieDTOToMovie(DTO)).thenReturn(m1);

        when(scoreService.getDepthScoreForMovie(m1)).thenReturn(BigDecimal.ONE);

        BigDecimal result = movieFacade.getDepthScore(DTO);

        Assert.assertEquals(result, BigDecimal.ONE);
    }


    @Test
    public void getRecommendations() {
        when(recommendationService.getRecommendationsBasedOnGenres(m2)).thenReturn(List.of(m1, m3, m4));
        when(recommendationService.getRecommendationsBasedOnUsers(m2)).thenReturn(List.of(m1, m4, m5));

        // Setup input

        MovieDTO inputDTO = new MovieDTO();
        when(movieMapper.movieDTOToMovie(inputDTO)).thenReturn(m2);

        // Setup output

        MovieDetailedDTO m1DTO = new MovieDetailedDTO();
        m1DTO.setTitle(m1.getTitle());

        MovieDetailedDTO m4DTO = new MovieDetailedDTO();
        m4DTO.setTitle(m4.getTitle());

        MovieDetailedDTO m3DTO = new MovieDetailedDTO();
        m3DTO.setTitle(m3.getTitle());


        List<MovieDetailedDTO> expected = List.of(m1DTO, m4DTO, m3DTO);

        when(movieMapper.movieListToMovieDetailedDTOList(List.of(m1, m4, m3))).thenReturn(expected);

        List<MovieDetailedDTO> result = movieFacade.getRecommendations(inputDTO, 3);

        Assert.assertEquals(result, expected);
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
}
