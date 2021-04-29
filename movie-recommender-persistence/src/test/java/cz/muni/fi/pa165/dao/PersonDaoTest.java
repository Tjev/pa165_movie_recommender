package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.PersistenceApplicationContext;
import cz.muni.fi.pa165.entity.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;
import java.time.LocalDate;
import java.util.List;

/**
 * Tests for PersonDaoImpl methods
 *
 * @author Radoslav Chudovsky
 */
@ContextConfiguration(classes = PersistenceApplicationContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class PersonDaoTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private PersonDao personDao;

    @PersistenceContext
    private EntityManager em;

    @Test
    public void createTest() {
        Person p = new Person();
        p.setName("name");
        p.setBio("bio");
        p.setDateOfBirth(LocalDate.of(1990, 4, 22));

        personDao.create(p);

        Person found = getFromDB(p.getId());

        Assert.assertEquals(found, p);
    }

    @Test(expectedExceptions = ConstraintViolationException.class)
    public void nullNameTest() {
        Person p = new Person();

        personDao.create(p);
    }

    @Test(expectedExceptions = ConstraintViolationException.class)
    public void emptyNameTest() {
        Person p = new Person();
        p.setName("");

        personDao.create(p);
    }

    @Test
    public void findAllTest() {
        Person firstP = new Person();
        firstP.setName("first person");
        persistToDB(firstP);

        Person secondP = new Person();
        secondP.setName("second person");
        persistToDB(secondP);

        Person thirdP = new Person();
        thirdP.setName("third person");
        persistToDB(thirdP);

        List<Person> found = personDao.findAll();

        Assert.assertEquals(found.size(), 3);
        Assert.assertTrue(found.contains(firstP));
        Assert.assertTrue(found.contains(secondP));
        Assert.assertTrue(found.contains(thirdP));
    }

    @Test
    public void findByIdTest() {
        Person p = new Person();
        p.setName("name");
        persistToDB(p);

        Person found = personDao.findById(p.getId());

        Assert.assertEquals(found, p);
    }

    @Test
    public void findByNameTest() {
        String name = "same";
        Person firstP = new Person();
        firstP.setName(name);
        persistToDB(firstP);

        Person secondP = new Person();
        secondP.setName(name);
        persistToDB(secondP);

        Person thirdP = new Person();
        thirdP.setName("different");
        persistToDB(thirdP);

        List<Person> found = personDao.findByName(name);

        Assert.assertEquals(found.size(), 2);
        Assert.assertTrue(found.contains(firstP));
        Assert.assertTrue(found.contains(secondP));
        Assert.assertFalse(found.contains(thirdP));
    }

    @Test
    public void updateTest() {
        Person p = new Person();
        p.setName("name");
        p.setBio("bio");
        p.setDateOfBirth(LocalDate.of(1990, 4, 22));
        persistToDB(p);

        p.setName("new name");
        p.setBio("new bio");
        p.setDateOfBirth(LocalDate.of(2000, 1, 1));

        personDao.update(p);

        Person found = getFromDB(p.getId());

        Assert.assertEquals(found, p);
    }

    @Test
    public void removeTest() {
        Person p = new Person();
        p.setName("name");
        persistToDB(p);

        personDao.remove(p);

        Assert.assertNull(getFromDB(p.getId()));
    }

    public void persistToDB(Object object) {
        em.persist(object);
    }

    private Person getFromDB(long id) {
        Person p = em.find(Person.class, id);
        return p;
    }
}
