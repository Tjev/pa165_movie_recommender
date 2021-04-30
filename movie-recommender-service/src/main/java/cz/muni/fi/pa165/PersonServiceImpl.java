package cz.muni.fi.pa165;

import cz.muni.fi.pa165.dao.PersonDao;
import cz.muni.fi.pa165.entity.Person;
import cz.muni.fi.pa165.exceptions.ServiceLayerException;
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
    public Person create(Person person) {
        if (person == null) {
            throw new IllegalArgumentException("Person parameter is null.");
        }

        try {
            return personDao.create(person);
        } catch (Exception e) {
            throw new ServiceLayerException("Error occurred while creating Person.", e);
        }
    }

    @Override
    public List<Person> findAll() {
        try {
            return personDao.findAll();
        } catch (Exception e) {
            throw new ServiceLayerException("Error occurred while finding Persons.", e);
        }
    }

    @Override
    public Person findById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Id parameter is null.");
        }

        try {
            return personDao.findById(id);
        } catch (Exception e) {
            throw new ServiceLayerException("Error occurred while finding Person by its id.", e);
        }
    }

    @Override
    public List<Person> findByName(String name) {
        if (name == null) {
            throw new IllegalArgumentException("Person parameter is null.");
        }

        if (name.isEmpty()) {
            throw new IllegalArgumentException("Person parameter is empty.");
        }

        try {
            return personDao.findByName(name);
        } catch (Exception e) {
            throw new ServiceLayerException("Error occurred while finding Person by its name.", e);
        }
    }

    @Override
    public Person update(Person person) {
        if (person == null) {
            throw new IllegalArgumentException("Person parameter is null.");
        }

        Person found = findById(person.getId());

        if (found == null) {
            throw new IllegalArgumentException("Person is not in the database.");
        }

        try {
            return personDao.update(person);
        } catch (Exception e) {
            throw new ServiceLayerException("Error occurred while updating Person.", e);
        }
    }

    @Override
    public void remove(Person person) {
        if (person == null) {
            throw new IllegalArgumentException("Person parameter is null.");
        }

        Person found = findById(person.getId());

        if (found == null) {
            throw new IllegalArgumentException("Person is not in the database.");
        }

        try {
            personDao.remove(person);
        } catch (Exception e) {
            throw new ServiceLayerException("Error occurred while removing Person.", e);
        }
    }
}
