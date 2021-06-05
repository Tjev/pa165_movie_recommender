package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.PersistenceApplicationContext;
import cz.muni.fi.pa165.entity.User;
import javax.inject.Inject;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.testng.Assert;
import org.testng.annotations.*;

import javax.persistence.*;
import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

/**
 * Test class for UserDaoImpl.
 *
 * @author Kristian Tkacik
 */
@ContextConfiguration(classes = PersistenceApplicationContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class UserDaoTest extends AbstractTestNGSpringContextTests {

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserDao userDao;

    private User u1;
    private User u2;

    @BeforeMethod
    public void setUp() {
        u1 = new User("John", "john@mail.com");
        u2 = new User("Maria", "maria@mail.com");

        u1.setPasswordHash("password1");
        u2.setPasswordHash("password2");

        em.persist(u1);
        em.persist(u2);
    }

    @Test
    public void createTest() {
        User u = new User("Cameron", "diaz@mail.com");
        u.setAdmin(false);
        u.setPasswordHash("password");

        userDao.create(u);

        User foundUser = em.find(User.class, u.getId());

        Assert.assertEquals(foundUser, u);
    }

    @Test(expectedExceptions = PersistenceException.class)
    public void createWithNonUniqueUsernameTest() {
        User u = new User("John", "diaz@mail.com");
        u.setAdmin(false);
        u.setPasswordHash("password");

        userDao.create(u);
    }

    @Test(expectedExceptions = PersistenceException.class)
    public void createWithNonUniqueMailAddressTest() {
        User u = new User("Cameron", "john@mail.com");
        u.setAdmin(false);
        u.setPasswordHash("password");

        userDao.create(u);
    }

    @Test(expectedExceptions = ConstraintViolationException.class)
    public void createWithNullUsernameTest() {
        User u = new User(null, "diaz@mail.com");
        u.setAdmin(false);
        u.setPasswordHash("password");

        userDao.create(u);
    }

    @Test(expectedExceptions = ConstraintViolationException.class)
    public void createWithNullMailAddressTest() {
        User u = new User("Cameron", null);
        u.setAdmin(false);
        u.setPasswordHash("password");

        userDao.create(u);
    }

    @Test(expectedExceptions = ConstraintViolationException.class)
    public void createWithInvalidEmailTest() {
        User u = new User("Cameron", "mail");
        u.setAdmin(false);
        u.setPasswordHash("password");

        userDao.create(u);
    }

    @Test(expectedExceptions = ConstraintViolationException.class)
    public void createWithEmptyUsernameTest() {
        User u = new User("", "diaz@mail.com");
        u.setAdmin(false);
        u.setPasswordHash("password");

        userDao.create(u);
    }

    @Test(expectedExceptions = ConstraintViolationException.class)
    public void createWithEmptyMailAddressTest() {
        User u = new User("Cameron", "");
        u.setAdmin(false);
        u.setPasswordHash("password");

        userDao.create(u);
    }

    @Test
    public void findAllTest() {
        Assert.assertTrue(userDao.findAll().contains(u1));
        Assert.assertTrue(userDao.findAll().contains(u2));
        Assert.assertEquals(userDao.findAll().size(), 2);
    }
    
    @Test
    public void findByIdTest() {
        Assert.assertEquals(userDao.findById(u1.getId()), u1);
        Assert.assertEquals(userDao.findById(u2.getId()), u2);
    }

    @Test
    public void findByUsernameTest() {
        Assert.assertEquals(userDao.findByUsername("John"), u1);
        Assert.assertEquals(userDao.findByUsername("Maria"), u2);
    }

    @Test
    public void updateTest() {
        u1.setEmailAddress("john@newmail.com");
        u2.setEmailAddress("maria@newemail.com");

        userDao.update(u1);
        userDao.update(u2);

        User foundUser1 = em.find(User.class, u1.getId());
        User foundUser2 = em.find(User.class, u2.getId());

        Assert.assertEquals(foundUser1, u1);
        Assert.assertEquals(foundUser2, u2);
    }
}
