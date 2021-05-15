package cz.muni.fi.pa165.mapper;

import cz.muni.fi.pa165.dto.person.PersonCreateDTO;
import cz.muni.fi.pa165.dto.person.PersonDTO;
import cz.muni.fi.pa165.dto.person.PersonDetailedDTO;
import cz.muni.fi.pa165.entity.Person;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;

/**
 * Mapper for Person entity and its DTOs.
 *
 * @author Jiri Papousek
 */
@Mapper(collectionMappingStrategy = CollectionMappingStrategy.TARGET_IMMUTABLE, componentModel = "spring")
public interface PersonMapper {

    PersonDTO personToPersonDTO(Person person);
    Person personDTOToPerson(PersonDTO personDTO);

    Person personDetailedDTOToPerson(PersonDetailedDTO personDetailedDTO);
    PersonDetailedDTO personToPersonDetailedDTO(Person person);

    PersonCreateDTO personToPersonCreateDTO(Person person);
    Person personCreateDTOToPerson(PersonCreateDTO personCreateDTO);
}
