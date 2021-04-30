package cz.muni.fi.pa165.facade;

import cz.fi.muni.pa165.dto.MovieDTO;
import cz.fi.muni.pa165.dto.PersonCreateDTO;
import cz.fi.muni.pa165.dto.PersonDTO;
import cz.fi.muni.pa165.dto.PersonDetailedDTO;
import cz.fi.muni.pa165.facade.PersonFacade;
import cz.muni.fi.pa165.PersonService;
import cz.muni.fi.pa165.entity.Movie;
import cz.muni.fi.pa165.entity.Person;
import cz.muni.fi.pa165.exceptions.ServiceLayerException;
import cz.muni.fi.pa165.mapper.MovieMapper;
import cz.muni.fi.pa165.mapper.PersonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PersonFacadeImpl implements PersonFacade {

    private final PersonMapper personMapper;

    private final MovieMapper movieMapper;

    private final PersonService personService;

    @Autowired
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
            return null;
        }
    }

    @Override
    public PersonDetailedDTO findById(Long id) {
        try {
            Person person = personService.findById(id);
            return personMapper.personToPersonDetailedDTO(person);
        } catch (ServiceLayerException e) {
            return null;
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
            return Collections.EMPTY_LIST;
        }

    }

    @Override
    public PersonDetailedDTO update(PersonDetailedDTO personDetailedDTO) {
        try {
            Person person = personMapper.personDetailedDTOToPerson(personDetailedDTO);
            person = personService.update(person);
            return personMapper.personToPersonDetailedDTO(person);
        }  catch (ServiceLayerException e) {
            return null;
        }
    }

    @Override
    public PersonDetailedDTO addDirectedMovie(PersonDTO personDTO, MovieDTO movieDTO) {
        try {
            Person person = personMapper.personDTOToPerson(personDTO);
            Movie movie = movieMapper.movieDTOToMovie(movieDTO);
            person.addDirectedMovie(movie);
            person = personService.update(person);
            return personMapper.personToPersonDetailedDTO(person);
        } catch (ServiceLayerException e) {
            return null;
        }

    }

    @Override
    public PersonDetailedDTO addActsInMovie(PersonDTO personDTO, MovieDTO movieDTO) {
        try {
            Person person = personMapper.personDTOToPerson(personDTO);
            Movie movie = movieMapper.movieDTOToMovie(movieDTO);
            person.addActsInMovie(movie);
            person = personService.update(person);
            return personMapper.personToPersonDetailedDTO(person);
        } catch (ServiceLayerException e) {
            return null;
        }

    }

    @Override
    public boolean remove(PersonDTO personDTO) {
        try {
            Person person = personMapper.personDTOToPerson(personDTO);
            personService.remove(person);
            return true;
        } catch (ServiceLayerException e) {
            return false;
        }
    }
}
