package cz.fi.muni.pa165.facade;

import cz.fi.muni.pa165.dto.PersonCreateDto;
import cz.fi.muni.pa165.dto.PersonDto;

/**
 * Facade for interaction with person data (actors and directors).
 *
 * @author Jiri Papousek
 */
public interface PersonFacade {

    /**
     * Creates a new person.
     *
     * @param personCreateDto DTO with person data
     * @return DTO after creation operation
     */
    PersonDto create(PersonCreateDto personCreateDto);

    /**
     * Finds a person by id.
     *
     * @param id of the person in the data source
     * @return DTO with person data
     */
    PersonDto findById(Long id);

    /**
     * Finds a person by its name.
     *
     * @param name of the person in the data source
     * @return DTO with person data
     */
    PersonDto findByName(String name);

    /**
     * Updates the given person.
     *
     * @param personDto DTO with data for update in attributes
     * @return DTO with person data after update operation
     */
    PersonDto update(PersonDto personDto);

    /**
     * Adds a movie to person which he/she has directed.
     *
     * @param personDto DTO of person that has directed the movie
     * @param movie that has been directed
     * @return DTO with person data after update operation
     */
    PersonDto addDirectedMovie(PersonDto personDto, MovieDto movie);

    /**
     * Adds a movie to person in which he/she has acted.
     *
     * @param personDto DTO of person that has acted in the movie
     * @param movie the person acted in
     * @return DTO with person data after update operation
     */
    PersonDto addActsInMovie(PersonDto personDto, MovieDto movie);

    /**
     * Removes the given person
     *
     * @param personDto DTO of the person to be removed
     */
    void remove(PersonDto personDto);
}
