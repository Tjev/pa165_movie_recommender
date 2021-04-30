package cz.muni.fi.pa165.mapper;

import cz.fi.muni.pa165.dto.PersonCreateDTO;
import cz.fi.muni.pa165.dto.PersonDTO;
import cz.fi.muni.pa165.dto.PersonDetailedDTO;
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
    Person personDTOToPerson(PersonDTO personDto);

    Person personDetailedDTOToPerson(PersonDetailedDTO personDetailedDto);
    PersonDetailedDTO personToPersonDetailedDTO(Person person);

    PersonCreateDTO personToPersonCreateDTO(Person person);
    Person personCreateDTOToPerson(PersonCreateDTO personCreateDTO);
}
