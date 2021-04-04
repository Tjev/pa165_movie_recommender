package cz.muni.fi.pa165.movie_recommender.entity.dao;

import cz.muni.fi.pa165.movie_recommender.entity.Person;

import java.util.List;

/**
 * DAO for manipulation with Person entities
 *
 * @author Jiri Papousek
 */
public interface PersonDao {

    /**
     * Saves person into the data source.
     *
     * @param person to be saved
     */
    void create(Person person);

    /**
     * Retrieves a list of all persons from the data source.
     *
     * @return list of persons
     */
    List<Person> findAll();

    /**
     * Retrieves a person by its ID from the data source.
     *
     * @param id of the person
     * @return person matching the id
     */
    Person findById(Long id);

    /**
     * Retrieves all persons from the data source who match the given name
     *
     * @param name to be matched
     * @return list of all persons matching the name
     */
    List<Person> findByName(String name);

    /**
     * Merges the person by its ID with its current representation in the data source.
     *
     * @param person to be merged
     */
    void update(Person person);

    /**
     * Removes the person from the data source.
     *
     * @param person to be removed
     */
    void remove(Person person);
}
