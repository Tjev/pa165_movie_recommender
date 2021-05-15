package cz.muni.fi.pa165.rest.controller;

import javax.inject.Inject;

import cz.muni.fi.pa165.dto.person.PersonCreateDTO;
import cz.muni.fi.pa165.dto.person.PersonDTO;
import cz.muni.fi.pa165.dto.person.PersonDetailedDTO;
import cz.muni.fi.pa165.facade.PersonFacade;
import org.slf4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public final PersonDetailedDTO create(@RequestBody PersonCreateDTO personCreateDTO) {
        logger.debug("rest create({})", personCreateDTO);

        return personFacade.create(personCreateDTO).get();
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

        return personFacade.findById(id).get();
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

        return personFacade.findByName(name).get();
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
    public final PersonDetailedDTO update(@RequestBody PersonDetailedDTO personDetailedDTO) {
        logger.debug("rest update({})", personDetailedDTO);

        return personFacade.update(personDetailedDTO).get();
    }

    /**
     * Remove the given person from the data source.
     *
     * curl -X DELETE -i -H "Content-Type: application/json" --data '{"id": "1"}' http://localhost:8080/pa165/rest/persons/remove
     *
     * @param personDTO person to be removed
     */
    @RequestMapping(value = "/remove", method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public final void remove(@RequestBody PersonDTO personDTO) {
        logger.debug("rest remove({})", personDTO);

        personFacade.remove(personDTO);
    }
}
