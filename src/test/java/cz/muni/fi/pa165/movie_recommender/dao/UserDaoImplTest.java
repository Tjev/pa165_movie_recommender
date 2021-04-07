package cz.muni.fi.pa165.movie_recommender.dao;

import cz.muni.fi.pa165.movie_recommender.PersistenceApplicationContext;
import cz.muni.fi.pa165.movie_recommender.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceException;
import javax.persistence.PersistenceUnit;
import javax.validation.ConstraintViolationException;

/**
 * Test class for UserDaoImpl.
 *
 * @author Kristian Tkacik
 */
@ContextConfiguration(classes = PersistenceApplicationContext.class)
public class UserDaoImplTest extends AbstractTestNGSpringContextTests {

    @PersistenceUnit
    private EntityManagerFactory emf;

    @Autowired
    private UserDao userDao;

    private User u1;
    private User u2;

    @BeforeMethod
    public void setup() {
        u1 = new User("John", "john@mail.com");
        u2 = new User("Maria", "maria@mail.com");

        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();
            em.persist(u1);
            em.persist(u2);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Test
    public void createTest() {
        User u = new User("Cameron", "diaz@mail.com");
        userDao.create(u);

        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();
            User foundUser = em.find(User.class, u.getId());
            em.getTransaction().commit();

            Assert.assertEquals(foundUser, u);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Test(expectedExceptions = PersistenceException.class)
    public void createDuplicateTest() {
        User u = new User("Cameron", "diaz@mail.com");
        userDao.create(u);
        userDao.create(u);
    }

    @Test(expectedExceptions = PersistenceException.class)
    public void createWithNonUniqueUsernameTest() {
        User u = new User("John", "diaz@mail.com");
        userDao.create(u);
    }

    @Test(expectedExceptions = PersistenceException.class)
    public void createWithNonUniqueMailAddressTest() {
        User u = new User("Cameron", "john@mail.com");
        userDao.create(u);
    }

    @Test(expectedExceptions = ConstraintViolationException.class)
    public void createWithNullUsernameTest() {
        User u = new User(null, "diaz@mail.com");
        userDao.create(u);
    }

    @Test(expectedExceptions = ConstraintViolationException.class)
    public void createWithNullMailAddressTest() {
        User u = new User("Cameron", null);
        userDao.create(u);
    }

    @Test(expectedExceptions = ConstraintViolationException.class)
    public void createWithInvalidEmailTest() {
        User u = new User("Cameron", "mail");
        userDao.create(u);
    }

    @Test(expectedExceptions = ConstraintViolationException.class)
    public void createWithEmptyUsernameTest() {
        User u = new User("", "diaz@mail.com");
        userDao.create(u);
    }

    @Test(expectedExceptions = ConstraintViolationException.class)
    public void createWithEmptyMailAddressTest() {
        User u = new User("Cameron", "");
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
        Assert.assertTrue(userDao.findByUsername("John").contains(u1));
        Assert.assertTrue(userDao.findByUsername("Maria").contains(u2));
        Assert.assertEquals(userDao.findByUsername("John").size(), 1);
        Assert.assertEquals(userDao.findByUsername("Maria").size(), 1);
    }

    @Test
    public void updateTest() {
        u1.setMailAddress("john@newmail.com");
        u2.setMailAddress("maria@newemail.com");

        userDao.update(u1);
        userDao.update(u2);

        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();
            User foundUser1 = em.find(User.class, u1.getId());
            User foundUser2 = em.find(User.class, u2.getId());
            em.getTransaction().commit();

            Assert.assertEquals(foundUser1, u1);
            Assert.assertEquals(foundUser2, u2);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Test
    public void removeTest() {
        userDao.remove(u1);
        userDao.remove(u2);

        EntityManager em = null;
        try {
            em = emf.createEntityManager();

            em.getTransaction().begin();
            User foundUser1 = em.find(User.class, u1.getId());
            User foundUser2 = em.find(User.class, u2.getId());
            em.getTransaction().commit();

            Assert.assertNull(foundUser1);
            Assert.assertNull(foundUser2);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @AfterMethod
    public void cleanup() {
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();
            em.createQuery("delete from User").executeUpdate();
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
}
