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
import javax.persistence.PersistenceUnit;

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
        u1.setMailAddress("john@newmail.com");
        u2.setMailAddress("maria@newmail.com");

        userDao.update(u1);
        userDao.update(u2);

        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();
            User foundUser1 = em.find(User.class, u1.getId());
            User foundUser2 = em.find(User.class, u2.getId());
            em.getTransaction().commit();

            Assert.assertEquals(foundUser1.getMailAddress(), u1.getMailAddress());
            Assert.assertEquals(foundUser2.getMailAddress(), u2.getMailAddress());
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
