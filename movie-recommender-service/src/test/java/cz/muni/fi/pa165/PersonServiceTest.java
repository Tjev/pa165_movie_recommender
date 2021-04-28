package cz.muni.fi.pa165;

import cz.muni.fi.pa165.dao.PersonDao;
import cz.muni.fi.pa165.entity.Person;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.testng.Assert.*;

public class PersonServiceTest {

    @Mock
    private PersonDao personDao;
    private PersonService personService;
    private AutoCloseable closable;

    private Person p1;
    private Person p2;
    private Person p3;

    @BeforeMethod
    public void setUp() {
        closable = MockitoAnnotations.openMocks(this);
        personService = new PersonServiceImpl(personDao);

        p1 = new Person();
        p2 = new Person();
        p3 = new Person();

        p1.setId(1L);
    }

    @Test
    public void testCreate() {
        personService.create(p1);

        verify(personDao, times(1)).create(p1);
        verifyNoMoreInteractions(personDao);
    }

    @Test
    public void testFindAll() {
        List<Person> people = new ArrayList<>(Arrays.asList(p1, p2, p3));
        when(personDao.findAll()).thenReturn(people);

        List<Person> result = personService.findAll();

        verify(personDao, times(1)).findAll();
        assertEquals(result, people);
        verifyNoMoreInteractions(personDao);
    }

    @Test
    public void testFindById() {
        when(personDao.findById(1L)).thenReturn(p1);

        Person result = personService.findById(1L);

        verify(personDao, times(1)).findById(1L);
        assertEquals(result, p1);
        verifyNoMoreInteractions(personDao);
    }

    @Test
    public void testFindByName() {
        String name = "Jergus";
        p1.setName(name);
        List<Person> people = new ArrayList<>(Arrays.asList(p1));
        when(personDao.findByName(name)).thenReturn(people);

        List<Person> result = personService.findByName(name);

        verify(personDao, times(1)).findByName(name);
        assertEquals(result, people);
        verifyNoMoreInteractions(personDao);
    }

    @Test
    public void testUpdate() {
        when(personDao.findById(1L)).thenReturn(p1);

        personService.update(p1);

        verify(personDao, times(1)).update(p1);
        verifyNoMoreInteractions(personDao);
    }

    @Test
    public void testRemove() {
        when(personDao.findById(1L)).thenReturn(p1);

        personService.remove(p1);

        verify(personDao, times(1)).remove(p1);
        verifyNoMoreInteractions(personDao);
    }

    @AfterMethod
    public void tearDown() throws Exception {
        closable.close();
    }
}