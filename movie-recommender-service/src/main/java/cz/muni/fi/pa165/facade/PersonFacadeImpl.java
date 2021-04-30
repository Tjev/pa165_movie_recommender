package cz.muni.fi.pa165.facade;

import cz.fi.muni.pa165.dto.MovieDTO;
import cz.fi.muni.pa165.dto.PersonCreateDTO;
import cz.fi.muni.pa165.dto.PersonDTO;
import cz.fi.muni.pa165.dto.PersonDetailedDTO;
import cz.fi.muni.pa165.facade.PersonFacade;
import cz.muni.fi.pa165.PersonService;
import cz.muni.fi.pa165.entity.Movie;
import cz.muni.fi.pa165.entity.Person;
import cz.muni.fi.pa165.mapper.MovieMapper;
import cz.muni.fi.pa165.mapper.PersonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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
    public PersonDetailedDTO create(PersonCreateDTO personCreateDto) {
        Person person = personMapper.personCreateDTOToPerson(personCreateDto);
        person = personService.create(person);
        return personMapper.personToPersonDetailedDTO(person);
    }

    @Override
    public PersonDetailedDTO findById(Long id) {
        Person person = personService.findById(id);
        return personMapper.personToPersonDetailedDTO(person);
    }

    @Override
    public List<PersonDetailedDTO> findByName(String name) {
        List<Person> persons = personService.findByName(name);
        return persons.stream()
                .map(personMapper::personToPersonDetailedDTO)
                .collect(Collectors.toList());
    }

    @Override
    public PersonDetailedDTO update(PersonDetailedDTO personDetailedDto) {
        Person person = personMapper.personDetailedDTOToPerson(personDetailedDto);
        person = personService.update(person);
        return personMapper.personToPersonDetailedDTO(person);
    }

    @Override
    public PersonDetailedDTO addDirectedMovie(PersonDTO personDto, MovieDTO movieDto) {
        Person person = personMapper.personDTOToPerson(personDto);
        Movie movie = movieMapper.movieDTOToMovie(movieDto);
        person.addDirectedMovie(movie);
        person = personService.update(person);
        return personMapper.personToPersonDetailedDTO(person);
    }

    @Override
    public PersonDetailedDTO addActsInMovie(PersonDTO personDto, MovieDTO movieDto) {
        Person person = personMapper.personDTOToPerson(personDto);
        Movie movie = movieMapper.movieDTOToMovie(movieDto);
        person.addActsInMovie(movie);
        person = personService.update(person);
        return personMapper.personToPersonDetailedDTO(person);
    }

    @Override
    public void remove(PersonDTO personDto) {
        Person person = personMapper.personDTOToPerson(personDto);
        personService.remove(person);
    }
}
