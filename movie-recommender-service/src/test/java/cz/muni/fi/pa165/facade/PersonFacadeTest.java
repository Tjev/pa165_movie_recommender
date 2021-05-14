package cz.muni.fi.pa165.facade;

import cz.muni.fi.pa165.dto.movie.MovieDTO;
import cz.muni.fi.pa165.dto.person.PersonCreateDTO;
import cz.muni.fi.pa165.dto.person.PersonDTO;
import cz.muni.fi.pa165.dto.person.PersonDetailedDTO;
import cz.muni.fi.pa165.service.PersonService;
import cz.muni.fi.pa165.entity.Genre;
import cz.muni.fi.pa165.entity.Movie;
import cz.muni.fi.pa165.entity.Person;
import cz.muni.fi.pa165.mapper.MovieMapper;
import cz.muni.fi.pa165.mapper.PersonMapper;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.util.*;

import static org.mockito.Mockito.*;

/**
 * Tests for PersonFacadeImpl.
 *
 * @author Kristian Tkacik, Jiri Papousek
 */
public class PersonFacadeTest {

    @Mock
    private PersonService personService;

    @Mock
    private PersonMapper personMapper;

    @Mock
    private MovieMapper movieMapper;

    private PersonFacade personFacade;

    private AutoCloseable closable;

    private Person person;
    private PersonDTO personDTO;
    private PersonDetailedDTO personDetailedDTO;
    private PersonCreateDTO personCreateDTO;

    private Movie movie;
    private MovieDTO movieDTO;

    @BeforeMethod
    public void init() {
        closable = MockitoAnnotations.openMocks(this);
        personFacade = new PersonFacadeImpl(personMapper, movieMapper, personService);

        person = new Person();
        person.setId(1L);
        person.setName("Brad");
        person.setDateOfBirth(LocalDate.of(1963, 12, 18));
        person.setBio("Born in Oklahoma");

        personDTO = new PersonDTO();
        personDTO.setId(1L);
        personDTO.setName("Brad");
        personDTO.setDateOfBirth(LocalDate.of(1963, 12, 18));
        personDTO.setBio("Born in Oklahoma");

        personDetailedDTO = new PersonDetailedDTO();
        personDetailedDTO.setId(1L);
        personDetailedDTO.setName("Brad");
        personDetailedDTO.setDateOfBirth(LocalDate.of(1963, 12, 18));
        personDetailedDTO.setBio("Born in Oklahoma");

        personCreateDTO = new PersonCreateDTO();
        personCreateDTO.setName("Brad");
        personCreateDTO.setDateOfBirth(LocalDate.of(1963, 12, 18));
        personCreateDTO.setBio("Born in Oklahoma");

        movie = new Movie("Matrix", null, LocalDate.of(1999, 3, 31),
                new HashSet<>(Arrays.asList(Genre.ACTION)), null);
        movie.setId(1L);

        movieDTO = new MovieDTO();
        movieDTO.setId(1L);
        movieDTO.setTitle("Matrix");
        movieDTO.setReleaseYear(LocalDate.of(1999, 3, 31));
        movieDTO.setGenres(new HashSet<>(Arrays.asList(Genre.ACTION)));
    }

    @Test
    public void create() {
        when(personMapper.personCreateDTOToPerson(any(PersonCreateDTO.class))).thenReturn(person);
        personFacade.create(personCreateDTO);

        verify(personService, times(1)).create(any(Person.class));
        verifyNoMoreInteractions(personService);
    }

    @Test
    public void findById() {
        when(personMapper.personToPersonDetailedDTO(any(Person.class))).thenReturn(personDetailedDTO);
        when(personService.findById(any(Long.class))).thenReturn(person);
        Optional<PersonDetailedDTO> personDetailedDTO = personFacade.findById(1L);

        Assert.assertTrue(personDetailedDTO.isPresent());
        Assert.assertEquals(personDetailedDTO.get().getId(), person.getId());
        verify(personService, times(1)).findById(any(Long.class));
        verifyNoMoreInteractions(personService);
    }

    @Test
    public void findByName() {
        when(personMapper.personToPersonDetailedDTO(any(Person.class))).thenReturn(personDetailedDTO);
        List<Person> expected = new ArrayList<>(Arrays.asList(person));

        when(personService.findByName(any(String.class))).thenReturn(expected);
        Optional<List<PersonDetailedDTO>> result = personFacade.findByName("Brad");

        Assert.assertTrue(result.isPresent());
        Assert.assertEquals(result.get().get(0).getName(), person.getName());
        verify(personService, times(1)).findByName(any(String.class));
        verifyNoMoreInteractions(personService);
    }

    @Test
    public void update() {
        when(personMapper.personDetailedDTOToPerson(any(PersonDetailedDTO.class))).thenReturn(person);
        personFacade.update(personDetailedDTO);

        verify(personService, times(1)).update(any(Person.class));
        verifyNoMoreInteractions(personService);
    }

    @Test
    public void addDirectedMovie() {
        when(personMapper.personDTOToPerson(any(PersonDTO.class))).thenReturn(person);
        when(movieMapper.movieDTOToMovie(any(MovieDTO.class))).thenReturn(movie);

        Person updatedPerson = person;
        updatedPerson.addDirectedMovie(movie);

        PersonDetailedDTO personDetailedDTO = new PersonDetailedDTO();
        personDetailedDTO.setName("Jimmy");

        when(personService.update(any(Person.class))).thenReturn(updatedPerson);
        when(personMapper.personToPersonDetailedDTO(updatedPerson)).thenReturn(personDetailedDTO);

        Optional<PersonDetailedDTO> result = personFacade.addDirectedMovie(personDTO, movieDTO);

        Assert.assertTrue(result.isPresent());
        Assert.assertEquals(result.get(), personDetailedDTO);
    }

    @Test
    public void addActsInMovie() {
        when(personMapper.personDTOToPerson(any(PersonDTO.class))).thenReturn(person);
        when(movieMapper.movieDTOToMovie(any(MovieDTO.class))).thenReturn(movie);

        Person updatedPerson = person;
        updatedPerson.addActsInMovie(movie);

        PersonDetailedDTO personDetailedDTO = new PersonDetailedDTO();
        personDetailedDTO.setName("Jimmy");

        when(personService.update(any(Person.class))).thenReturn(updatedPerson);
        when(personMapper.personToPersonDetailedDTO(updatedPerson)).thenReturn(personDetailedDTO);

        Optional<PersonDetailedDTO> result = personFacade.addActsInMovie(personDTO, movieDTO);

        Assert.assertTrue(result.isPresent());
        Assert.assertEquals(result.get(), personDetailedDTO);
    }

    @Test
    public void remove() {
        when(personMapper.personDTOToPerson(any(PersonDTO.class))).thenReturn(person);
        personFacade.remove(personDTO);

        verify(personService, times(1)).remove(any(Person.class));
        verifyNoMoreInteractions(personService);
    }

    @AfterClass
    public void releaseMocks() throws Exception {
        closable.close();
    }
}
