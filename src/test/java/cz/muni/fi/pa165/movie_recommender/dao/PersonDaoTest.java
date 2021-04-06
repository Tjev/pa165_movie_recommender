package cz.muni.fi.pa165.movie_recommender.dao;

import cz.muni.fi.pa165.movie_recommender.PersistenceApplicationContext;
import cz.muni.fi.pa165.movie_recommender.entity.Movie;
import cz.muni.fi.pa165.movie_recommender.entity.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.transaction.annotation.Transactional;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.validation.ConstraintViolationException;
import java.time.LocalDate;
import java.util.List;

@ContextConfiguration(classes = PersistenceApplicationContext.class)
public class PersonDaoTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private PersonDao personDao;

    @PersistenceUnit
    EntityManagerFactory emf;

    @Test
    public void findByIdTest() {
        Person p = new Person();
        p.setName("name");
        persistToDB(p);

        Person found = personDao.findById(p.getId());
        Assert.assertNotNull(found);
        Assert.assertEquals(found, p);
    }

    @Test
    public void createTest() {
        Person p = new Person();
        p.setName("Some name");
        p.setBio("Some bio");
        p.setDateOfBirth(LocalDate.of(1990, 4, 22));

        personDao.create(p);

        Person found = getFromDB(p.getId());

        Assert.assertNotNull(found);
        Assert.assertEquals(found.getName(), p.getName());
        Assert.assertEquals(found.getBio(), p.getBio());
        Assert.assertEquals(found.getDateOfBirth(), p.getDateOfBirth());
        Assert.assertEquals(found, p);
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

        Assert.assertNotNull(found);
        Assert.assertEquals(found.size(), 3);
        Assert.assertTrue(found.contains(firstP));
        Assert.assertTrue(found.contains(secondP));
        Assert.assertTrue(found.contains(thirdP));
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

        Assert.assertNotNull(found);
        Assert.assertEquals(found.size(), 2);
        Assert.assertTrue(found.contains(firstP));
        Assert.assertTrue(found.contains(secondP));
        Assert.assertFalse(found.contains(thirdP));
    }

    @Test
    public void removeTest() {
        Person p = new Person();
        p.setName("name");

        persistToDB(p);
        Assert.assertNotNull(getFromDB(p.getId()));

        personDao.remove(p);
        Assert.assertNull(getFromDB(p.getId()));
    }

    @Test
    public void updateTest() {
        Person p = new Person();
        p.setName("Some name");
        p.setBio("Some bio");
        p.setDateOfBirth(LocalDate.of(1990, 4, 22));
        persistToDB(p);

        p.setName("new name");
        p.setBio("new bio");
        p.setDateOfBirth(LocalDate.of(2000, 1, 1));
        personDao.update(p);

        Person found = getFromDB(p.getId());
        Assert.assertEquals(found.getName(), "new name");
        Assert.assertEquals(found.getBio(), "new bio");
        Assert.assertEquals(found.getDateOfBirth(), LocalDate.of(2000, 1, 1));
    }

    @Test(expectedExceptions = ConstraintViolationException.class)
    public void nullNameTest() {
        Person p = new Person();
        personDao.create(p);
    }



    private void persistToDB(Object object) {
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();
            em.persist(object);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    private Person getFromDB(long id) {
        Person p;
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();
            p = em.find(Person.class, id);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return p;
    }

    @AfterMethod
    public void afterTest() {
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();
            em.createQuery("delete from Person").executeUpdate();
            em.createQuery("delete from Movie").executeUpdate();
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }


}