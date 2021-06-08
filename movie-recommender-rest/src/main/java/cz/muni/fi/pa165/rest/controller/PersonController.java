package cz.muni.fi.pa165.rest.controller;

import javax.inject.Inject;
import javax.validation.Valid;

import cz.muni.fi.pa165.dto.person.PersonCreateDTO;
import cz.muni.fi.pa165.dto.person.PersonDTO;
import cz.muni.fi.pa165.dto.person.PersonDetailedDTO;
import cz.muni.fi.pa165.dto.user.UserDetailedDTO;
import cz.muni.fi.pa165.exception.FacadeLayerException;
import cz.muni.fi.pa165.facade.PersonFacade;
import cz.muni.fi.pa165.rest.exception.DataSourceException;
import cz.muni.fi.pa165.rest.exception.InvalidParameterException;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.slf4j.LoggerFactory.*;

/**
 * Controller for managing actors and directors.
 *
 * @author Jiri Papousek
 */
@RestController
@RequestMapping(value = "/persons")
public class PersonController {

    final static Logger logger = getLogger(PersonController.class);

    private final PersonFacade personFacade;

    @Inject
    public PersonController(PersonFacade personFacade) { this.personFacade = personFacade; }

    /**
     * Create a given person.
     *
     * curl -X POST -i -H "Content-Type: application/json" --data '{"name": "Mads Mikkelsen", "bio": "He is cool.", "dateOfBirth": "1999-01-01"}' http://localhost:8080/pa165/rest/persons/create
     *
     * @param personCreateDTO person to be created
     * @return detailed DTO of the person after create operation
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public final PersonDetailedDTO create(@Valid @RequestBody PersonCreateDTO personCreateDTO) {
        logger.debug("rest create({})", personCreateDTO);

        try {
            return personFacade.create(personCreateDTO);
        } catch (FacadeLayerException e) {
            throw new DataSourceException("The instance already exists.", e);
        } catch (IllegalArgumentException e) {
            throw new InvalidParameterException("Given parameters were invalid.", e);
        }
    }

    /**
     * Find a person by its id.
     *
     * curl -X GET -i http://localhost:8080/pa165/rest/persons/1
     *
     * @param id id of the person to be found
     * @return detailed DTO of the found person
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final PersonDetailedDTO findById(@PathVariable("id") long id) {
        logger.debug("rest findById({})", id);

        Optional<PersonDetailedDTO> result;
        try {
            result = personFacade.findById(id);
        } catch (FacadeLayerException e) {
            throw new DataSourceException("Problem with the data source occurred.", e);
        } catch (IllegalArgumentException e) {
            throw new InvalidParameterException("Given parameters were invalid.", e);
        }

        if (result.isEmpty()) {
            throw new InvalidParameterException("Person with given id has not been found.");
        }

        return result.get();
    }

    /**
     * Find persons by their name.
     *
     * curl -X GET -i http://localhost:8080/pa165/rest/persons/find-by-name?name=Mads%20Mikkelsen
     *
     * @param name name of the person to be found
     * @return list of detailed DTOs of the found persons
     */
    @RequestMapping(value = "/find-by-name", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final List<PersonDetailedDTO> findByName(@RequestParam String name) {
        logger.debug("rest findByName({})", name);

        List<PersonDetailedDTO> result;
        try {
            result = personFacade.findByName(name);
        } catch (FacadeLayerException e) {
            throw new DataSourceException("Problem with the data source occurred.", e);
        } catch (IllegalArgumentException e) {
            throw new InvalidParameterException("Given parameters were invalid.", e);
        }

        return result;
    }

    /**
     * Update the given person.
     *
     * curl -X PUT -i -H "Content-Type: application/json" --data '{"id": "1", "name": "Mads Mikkelsen", "bio": "He is not that cool.", "dateOfBirth": "1999-01-01"}' http://localhost:8080/pa165/rest/persons/update
     *
     * @param personDetailedDTO DTO of the person to be updated
     * @return detailed DTO of the person after update operation
     */
    @RequestMapping(value = "/update", method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final PersonDetailedDTO update(@Valid @RequestBody PersonDetailedDTO personDetailedDTO) {
        logger.debug("rest update({})", personDetailedDTO);

        try {
            return personFacade.update(personDetailedDTO);
        } catch (FacadeLayerException e) {
            throw new DataSourceException("Problem with the data source occurred.", e);
        } catch (IllegalArgumentException e) {
            throw new InvalidParameterException("Given parameters were invalid.", e);
        }
    }

    /**
     * Remove the given person from the data source.
     *
     * curl -X DELETE -i -H "Content-Type: application/json" --data '{"id": "1"}' http://localhost:8080/pa165/rest/persons/remove
     *
     * @param personDTO person to be removed
     */
    @RequestMapping(value = "/remove", method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public final void remove(@Valid @RequestBody PersonDTO personDTO) {
        logger.debug("rest remove({})", personDTO);

        try {
            personFacade.remove(personDTO);
        } catch (FacadeLayerException e) {
            throw new DataSourceException("Problem with the data source occurred.", e);
        } catch (IllegalArgumentException e) {
            throw new InvalidParameterException("Given parameters were invalid.", e);
        }
    }
}
