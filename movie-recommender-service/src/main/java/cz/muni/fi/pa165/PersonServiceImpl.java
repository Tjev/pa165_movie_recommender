package cz.muni.fi.pa165;

import cz.muni.fi.pa165.dao.PersonDao;
import cz.muni.fi.pa165.dao.PersonDaoImpl;
import cz.muni.fi.pa165.entity.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of PersonService interface
 *
 * @author Jiri Papousek
 */
@Service
public class PersonServiceImpl implements PersonService {

    private final PersonDao personDao;

    @Autowired
    public PersonServiceImpl(PersonDao personDao) {
        this.personDao = personDao;
    }

    @Override
    public void create(Person person) {
        personDao.create(person);
    }

    @Override
    public List<Person> findAll() {
        return personDao.findAll();
    }

    @Override
    public Person findById(Long id) {
        return personDao.findById();
    }

    @Override
    public List<Person> findByName(String name) {
        return personDao.findByName(name);
    }

    @Override
    public void update(Person person) {
        personDao.update(person);
    }

    @Override
    public void remove(Person person) {
        personDao.remove(person);
    }
}
