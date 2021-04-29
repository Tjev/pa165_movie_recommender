package cz.muni.fi.pa165;

import cz.muni.fi.pa165.dao.PersonDao;
import cz.muni.fi.pa165.entity.Person;
import cz.muni.fi.pa165.exceptions.ServiceLayerException;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.persistence.PersistenceException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.testng.Assert.*;

/**
 * Tests for {@link PersonServiceImpl} class
 *
 * @author Tomas Jevocin
 */
public class PersonServiceTest {

    @Mock
    private PersonDao personDao;
    private PersonService personService;
    private AutoCloseable closable;

    private Person person1;
    private Person person2;

    @BeforeMethod
    public void setUp() {
        closable = MockitoAnnotations.openMocks(this);
        personService = new PersonServiceImpl(personDao);

        person1 = new Person();
        person2 = new Person();

        person1.setId(1L);
    }

    @Test
    public void createPerson() {
        personService.create(person1);

        verify(personDao, times(1)).create(person1);
        verifyNoMoreInteractions(personDao);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void createNullPerson() {
        personService.create(null);
    }

    @Test(expectedExceptions = ServiceLayerException.class)
    public void createWithFailureOnPersistenceLayer() {
        doThrow(PersistenceException.class)
                .when(personDao)
                .create(person1);

        personService.create(person1);
    }

    @Test
    public void findAllPersons() {
        List<Person> people = new ArrayList<>(Arrays.asList(person1, person2));
        when(personDao.findAll()).thenReturn(people);

        List<Person> result = personService.findAll();

        verify(personDao, times(1)).findAll();
        assertEquals(result, people);
        verifyNoMoreInteractions(personDao);
    }

    @Test(expectedExceptions = ServiceLayerException.class)
    public void findAllPersonsWithFailureOnPersistenceLayer() {
        doThrow(PersistenceException.class)
                .when(personDao)
                .findAll();

        personService.findAll();
    }

    @Test
    public void findById() {
        when(personDao.findById(1L)).thenReturn(person1);

        Person result = personService.findById(1L);

        verify(personDao, times(1)).findById(1L);
        assertEquals(result, person1);
        verifyNoMoreInteractions(personDao);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void findByIdWithNull() {
        personService.findById(null);
    }

    @Test(expectedExceptions = ServiceLayerException.class)
    public void findByIdWithFailureOnPersistenceLayer() {
        doThrow(PersistenceException.class)
                .when(personDao)
                .findById(1L);

        personService.findById(1L);
    }

    @Test
    public void findByName() {
        String name = "Jergus";
        person1.setName(name);
        List<Person> people = new ArrayList<>(Arrays.asList(person1));
        when(personDao.findByName(name)).thenReturn(people);

        List<Person> result = personService.findByName(name);

        verify(personDao, times(1)).findByName(name);
        assertEquals(result, people);
        verifyNoMoreInteractions(personDao);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void findByNameWithNull() {
        personService.findByName(null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void findByNameWithEmptyName() {
        personService.findByName("");
    }

    @Test(expectedExceptions = ServiceLayerException.class)
    public void findByNameWithFailureOnPersistenceLayer() {
        doThrow(PersistenceException.class)
                .when(personDao)
                .findByName("Jergus");

        personService.findByName("Jergus");
    }

    @Test
    public void updatePerson() {
        when(personDao.findById(1L)).thenReturn(person1);

        personService.update(person1);

        verify(personDao, times(1)).findById(1L);
        verify(personDao, times(1)).update(person1);
        verifyNoMoreInteractions(personDao);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void updateWithNull() {
        personService.update(null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void updateWithInvalidId() {
        person1.setId(1L);
        when(personDao.findById(1L)).thenReturn(null);

        personService.update(person1);
    }

    @Test(expectedExceptions = ServiceLayerException.class)
    public void updateWithFailureOnPersistenceLayer() {
        when(personDao.findById(1L)).thenReturn(person1);
        doThrow(PersistenceException.class)
                .when(personDao)
                .update(person1);

        personService.update(person1);
    }

    @Test
    public void removePerson() {
        when(personDao.findById(1L)).thenReturn(person1);

        personService.remove(person1);

        verify(personDao, times(1)).findById(1L);
        verify(personDao, times(1)).remove(person1);
        verifyNoMoreInteractions(personDao);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void removeWithNull() {
        personService.remove(null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void removeWithInvalidId() {
        person1.setId(1L);
        when(personDao.findById(1L)).thenReturn(null);

        personService.remove(person1);
    }

    @Test(expectedExceptions = ServiceLayerException.class)
    public void removeWithFailureOnPersistenceLayer() {
        when(personDao.findById(1L)).thenReturn(person1);
        doThrow(PersistenceException.class)
                .when(personDao)
                .remove(person1);

        personService.remove(person1);
    }

    @AfterMethod
    public void tearDown() throws Exception {
        closable.close();
    }
}