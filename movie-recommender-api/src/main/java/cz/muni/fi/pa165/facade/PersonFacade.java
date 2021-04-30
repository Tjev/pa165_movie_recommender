package cz.muni.fi.pa165.facade;

import cz.muni.fi.pa165.dto.MovieDTO;
import cz.muni.fi.pa165.dto.PersonCreateDTO;
import cz.muni.fi.pa165.dto.PersonDetailedDTO;
import cz.muni.fi.pa165.dto.PersonDTO;

import java.util.List;
import java.util.Optional;

/**
 * Facade for interaction with person data (actors and directors).
 *
 * @author Jiri Papousek
 */
public interface PersonFacade {

    /**
     * Creates a new person.
     *
     * @param personCreateDTO DTO with person data
     * @return optional DTO after creation operation
     */
    Optional<PersonDetailedDTO> create(PersonCreateDTO personCreateDTO);

    /**
     * Finds a person by id.
     *
     * @param id of the person in the data source
     * @return optional DTO with person data
     */
    Optional<PersonDetailedDTO> findById(Long id);

    /**
     * Finds a person by its name.
     *
     * @param name of the person in the data source
     * @return optional list of DTOs with person data
     */
    Optional<List<PersonDetailedDTO>> findByName(String name);

    /**
     * Updates the given person.
     *
     * @param personDetailedDTO DTO with data for update in attributes
     * @return optional DTO with person data after update operation
     */
    Optional<PersonDetailedDTO> update(PersonDetailedDTO personDetailedDTO);

    /**
     * Adds a movie to person which he/she has directed.
     *
     * @param personDTO DTO of person that has directed the movie
     * @param movieDTO that has been directed
     * @return optional DTO with person data after update operation
     */
    Optional<PersonDetailedDTO> addDirectedMovie(PersonDTO personDTO, MovieDTO movieDTO);

    /**
     * Adds a movie to person in which he/she has acted.
     *
     * @param personDTO DTO of person that has acted in the movie
     * @param movieDTO the person acted in
     * @return optional DTO with person data after update operation
     */
    Optional<PersonDetailedDTO> addActsInMovie(PersonDTO personDTO, MovieDTO movieDTO);

    /**
     * Removes the given person.
     *
     * @param personDTO DTO of the person to be removed
     * @return true if removal was successful, false otherwise
     */
    Boolean remove(PersonDTO personDTO);
}
