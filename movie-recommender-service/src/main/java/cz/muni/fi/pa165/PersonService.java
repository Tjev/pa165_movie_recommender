package cz.muni.fi.pa165;

import cz.muni.fi.pa165.entity.Person;

import java.util.List;

/**
 * Interface for service manipulating with Person entity.
 *
 * @author Jiri Papousek
 */
public interface PersonService {

    /**
     * Saves person into the persistence layer.
     *
     * @param person to be saved
     */
    void create(Person person);

    /**
     * Retrieves a list of all persons from the persistence layer.
     *
     * @return list of persons
     */
    List<Person> findAll();

    /**
     * Retrieves a person by its ID from the persistence layer.
     *
     * @param id of the person
     * @return person matching the id
     */
    Person findById(Long id);

    /**
     * Retrieves all persons from the persistence layer who match the given name
     *
     * @param name to be matched
     * @return list of all persons matching the name
     */
    List<Person> findByName(String name);

    /**
     * Merges the person by its ID with its current representation in the persistence layer.
     *
     * @param person to be merged
     */
    void update(Person person);

    /**
     * Removes the person from the persistence layer.
     *
     * @param person to be removed
     */
    void remove(Person person);
}
