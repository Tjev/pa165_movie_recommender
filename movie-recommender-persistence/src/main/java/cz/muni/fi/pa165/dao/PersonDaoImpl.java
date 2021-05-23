package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entity.Person;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Person DAO implementation working with in-memory data source.
 *
 * @author Jiri Papousek
 */
@Repository
public class PersonDaoImpl implements PersonDao {

    @PersistenceContext
    EntityManager em;

    @Override
    public Person create(Person person) {
        em.persist(person);
        return person;
    }

    @Override
    public List<Person> findAll() {
        return em.createQuery("SELECT p FROM Person p", Person.class).getResultList();
    }

    @Override
    public Person findById(Long id) {
        return em.find(Person.class, id);
    }

    @Override
    public List<Person> findByName(String name) {
        return em.createQuery("SELECT p FROM Person p WHERE LOWER(p.name) LIKE :n", Person.class)
                .setParameter("n", "%" + name.toLowerCase() + "%")
                .getResultList();
    }

    @Override
    public Person update(Person person) {
        em.merge(person);
        return person;
    }

    @Override
    public void remove(Person person) {
        if (!em.contains(person)) {
            person = em.merge(person);
        }
        em.remove(person);
    }
}
