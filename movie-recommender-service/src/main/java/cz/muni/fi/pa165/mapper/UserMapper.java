package cz.muni.fi.pa165.mapper;

import cz.muni.fi.pa165.dto.user.UserAuthenticateDTO;
import cz.muni.fi.pa165.dto.user.UserDTO;
import cz.muni.fi.pa165.dto.user.UserDetailedDTO;
import cz.muni.fi.pa165.entity.User;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * Maps User entity related DTOs to their Entity counterparts.
 *
 * @author Tomas Jevocin
 */
@Mapper(collectionMappingStrategy = CollectionMappingStrategy.TARGET_IMMUTABLE, componentModel = "spring")
public interface UserMapper {

    UserDTO userToUserDTO(User user);
    User userDTOToUser(UserDTO userDTO);

    UserDetailedDTO userToUserDetailedDTO(User user);
    User userDetailedDTOToUser(UserDetailedDTO userDetailedDTO);

    UserAuthenticateDTO userToUserAuthenticateDTO(User user);
    User userAuthenticateDTOToUser(UserAuthenticateDTO userAuthenticateDTO);

    List<UserDTO> usersToUserDTOs(List<User> users);
}
