package cz.muni.fi.pa165.movie_recommender.dao;

import cz.muni.fi.pa165.movie_recommender.dao.PersonDao;
import cz.muni.fi.pa165.movie_recommender.entity.Person;
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
    public void create(Person person) {
        em.persist(person);
    }

    @Override
    public List<Person> findAll() {
        return em.createQuery("select p from Person p").getResultList();
    }

    @Override
    public Person findById(Long id) {
        return em.find(Person.class, id);
    }

    @Override
    public List<Person> findByName(String name) {
        return em.createQuery("select p from Person p where p.name = :name")
                .setParameter("name", name)
                .getResultList();
    }

    @Override
    public void update(Person person) {
        em.merge(person);
    }

    @Override
    public void remove(Person person) {
        em.remove(person);
    }

}
