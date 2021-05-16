package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entity.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * User DAO implementation working with in-memory data source.
 *
 * @author Tomas Jevocin
 */
@Repository
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public User create(User user) {
        em.persist(user);
        return user;
    }

    @Override
    public List<User> findAll() {
        return em.createQuery("SELECT u FROM User u", User.class).getResultList();
    }

    @Override
    public User findById(Long id) {
        return em.find(User.class, id);
    }

    @Override
    public User findByUsername(String username) {
        return em.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class)
                .setParameter("username", username)
                .getSingleResult();
    }

    @Override
    public User findByEmailAddress(String emailAddress) {
        return em.createQuery("SELECT u FROM User u WHERE u.emailAddress = :emailAddress", User.class)
                .setParameter("emailAddress", emailAddress)
                .getSingleResult();
    }

    @Override
    public User update(User user) {
        em.merge(user);
        return user;
    }
}
