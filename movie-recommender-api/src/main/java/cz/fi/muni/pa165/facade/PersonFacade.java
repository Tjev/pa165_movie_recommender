package cz.fi.muni.pa165.facade;

import cz.fi.muni.pa165.dto.MovieDTO;
import cz.fi.muni.pa165.dto.PersonCreateDTO;
import cz.fi.muni.pa165.dto.PersonDetailedDTO;
import cz.fi.muni.pa165.dto.PersonDTO;

import java.util.List;

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
    PersonDetailedDTO create(PersonCreateDTO personCreateDto);

    /**
     * Finds a person by id.
     *
     * @param id of the person in the data source
     * @return DTO with person data
     */
    PersonDetailedDTO findById(Long id);

    /**
     * Finds a person by its name.
     *
     * @param name of the person in the data source
     * @return list of DTOs with person data
     */
    List<PersonDetailedDTO> findByName(String name);

    /**
     * Updates the given person.
     *
     * @param personDetailedDto DTO with data for update in attributes
     * @return DTO with person data after update operation
     */
    PersonDetailedDTO update(PersonDetailedDTO personDetailedDto);

    /**
     * Adds a movie to person which he/she has directed.
     *
     * @param personDto DTO of person that has directed the movie
     * @param movieDto that has been directed
     * @return DTO with person data after update operation
     */
    PersonDetailedDTO addDirectedMovie(PersonDTO personDto, MovieDTO movieDto);

    /**
     * Adds a movie to person in which he/she has acted.
     *
     * @param personDto DTO of person that has acted in the movie
     * @param movieDto the person acted in
     * @return DTO with person data after update operation
     */
    PersonDetailedDTO addActsInMovie(PersonDTO personDto, MovieDTO movieDto);

    /**
     * Removes the given person
     *
     * @param personDto DTO of the person to be removed
     */
    void remove(PersonDTO personDto);
}
