package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.dao.PersonDao;
import cz.muni.fi.pa165.entity.Person;
import cz.muni.fi.pa165.exception.ServiceLayerException;
import javax.inject.Inject;
import javax.persistence.PersistenceException;
import javax.validation.ConstraintViolationException;

import org.springframework.dao.DataAccessException;
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

    @Inject
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
        } catch (PersistenceException
                | ConstraintViolationException
                | DataAccessException e) {
            throw new ServiceLayerException("Error occurred while creating Person.", e);
        }
    }

    @Override
    public List<Person> findAll() {
        try {
            return personDao.findAll();
        } catch (PersistenceException | DataAccessException e) {
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
        } catch (PersistenceException | DataAccessException e) {
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
        } catch (PersistenceException | DataAccessException e) {
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
        } catch (PersistenceException
                | ConstraintViolationException
                | DataAccessException e) {
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
        } catch (PersistenceException | DataAccessException e) {
            throw new ServiceLayerException("Error occurred while removing Person.", e);
        }
    }
}
