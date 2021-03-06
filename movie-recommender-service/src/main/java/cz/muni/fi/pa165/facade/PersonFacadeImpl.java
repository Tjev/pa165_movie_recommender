package cz.muni.fi.pa165.facade;

import cz.muni.fi.pa165.dto.movie.MovieDTO;
import cz.muni.fi.pa165.dto.person.PersonCreateDTO;
import cz.muni.fi.pa165.dto.person.PersonDTO;
import cz.muni.fi.pa165.dto.person.PersonDetailedDTO;
import cz.muni.fi.pa165.exception.FacadeLayerException;
import cz.muni.fi.pa165.service.PersonService;
import cz.muni.fi.pa165.entity.Movie;
import cz.muni.fi.pa165.entity.Person;
import cz.muni.fi.pa165.exception.ServiceLayerException;
import cz.muni.fi.pa165.mapper.MovieMapper;
import cz.muni.fi.pa165.mapper.PersonMapper;
import javax.inject.Inject;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implementation of PersonFacade.
 *
 * @author Jiri Papousek
 */
@Service
@Transactional
public class PersonFacadeImpl implements PersonFacade {

    private final PersonMapper personMapper;

    private final MovieMapper movieMapper;

    private final PersonService personService;

    @Inject
    public PersonFacadeImpl(PersonMapper personMapper, MovieMapper movieMapper, PersonService personService) {
        this.personMapper = personMapper;
        this.movieMapper = movieMapper;
        this.personService = personService;
    }

    @Override
    public PersonDetailedDTO create(PersonCreateDTO personCreateDTO) {
        try {
            Person person = personMapper.personCreateDTOToPerson(personCreateDTO);
            person = personService.create(person);
            return personMapper.personToPersonDetailedDTO(person);
        } catch (ServiceLayerException e) {
            throw new FacadeLayerException("Error at service layer occurred", e);
        }
    }

    @Override
    public Optional<PersonDetailedDTO> findById(Long id) {
        try {
            Person person = personService.findById(id);
            return Optional.ofNullable(personMapper.personToPersonDetailedDTO(person));
        } catch (ServiceLayerException e) {
            throw new FacadeLayerException("Error at service layer occurred", e);
        }
    }

    @Override
    public List<PersonDetailedDTO> findByName(String name) {
        try {
            List<Person> persons = personService.findByName(name);
            return persons.stream()
                    .map(personMapper::personToPersonDetailedDTO)
                    .collect(Collectors.toList());
        } catch (ServiceLayerException e) {
            throw new FacadeLayerException("Error at service layer occurred", e);
        }

    }

    @Override
    public PersonDetailedDTO update(PersonDetailedDTO personDetailedDTO) {
        try {
            Person person = personMapper.personDetailedDTOToPerson(personDetailedDTO);
            person = personService.update(person);
            return personMapper.personToPersonDetailedDTO(person);
        }  catch (ServiceLayerException e) {
            throw new FacadeLayerException("Error at service layer occurred", e);
        }
    }

    @Override
    public PersonDetailedDTO addDirectedMovie(PersonDTO personDTO, MovieDTO movieDTO) {
        if (personDTO == null) {
            throw new IllegalArgumentException("PersonDTO is null.");
        }
        if (movieDTO == null) {
            throw new IllegalArgumentException("MovieDTO is null");
        }
        try {
            Person person = personMapper.personDTOToPerson(personDTO);
            Movie movie = movieMapper.movieDTOToMovie(movieDTO);
            person.addDirectedMovie(movie);
            person = personService.update(person);
            return personMapper.personToPersonDetailedDTO(person);
        } catch (ServiceLayerException e) {
            throw new FacadeLayerException("Error at service layer occurred", e);
        }

    }

    @Override
    public PersonDetailedDTO addActsInMovie(PersonDTO personDTO, MovieDTO movieDTO) {
        if (personDTO == null) {
            throw new IllegalArgumentException("PersonDTO is null.");
        }
        if (movieDTO == null) {
            throw new IllegalArgumentException("MovieDTO is null");
        }
        try {
            Person person = personMapper.personDTOToPerson(personDTO);
            Movie movie = movieMapper.movieDTOToMovie(movieDTO);
            person.addActsInMovie(movie);
            person = personService.update(person);
            return personMapper.personToPersonDetailedDTO(person);
        } catch (ServiceLayerException e) {
            throw new FacadeLayerException("Error at service layer occurred", e);
        }

    }

    @Override
    public void remove(PersonDTO personDTO) {
        try {
            Person person = personMapper.personDTOToPerson(personDTO);
            personService.remove(person);
        } catch (ServiceLayerException e) {
            throw new FacadeLayerException("Error at service layer occurred", e);
        }
    }
}
