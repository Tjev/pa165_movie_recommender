package cz.muni.fi.pa165.facade;

import cz.muni.fi.pa165.dto.UserAuthenticateDTO;
import cz.muni.fi.pa165.dto.UserDTO;
import cz.muni.fi.pa165.dto.UserDetailedDTO;

import java.util.Optional;

/**
 * Facade for interaction with the User service.
 *
 * @author Tomas Jevocin.
 */
public interface UserFacade {

    /**
     * Registers the user into the system.
     *
     * @param userDTO - user to be registered
     * @param password - password to be hashed and stored for the user
     * @return - DTO representation of the registered user
     */
    Optional<UserDetailedDTO> register(UserDTO userDTO, String password);

    /**
     * Retrieves a user of the system with the matching id.
     *
     * @param id - id to be used as the key for retrieval
     * @return - user DTO matching the id
     */
    Optional<UserDetailedDTO> findById(Long id);

    /**
     * Retrieves a user of the system with the matching email address.
     *
     * @param emailAddress - email address to be used as the key for retrieval
     * @return - user DTO matching the email address
     */
    Optional<UserDetailedDTO> findByEmailAddress(String emailAddress);

    /**
     * Retrieves a user of the system with the matching username.
     *
     * @param username - username to be used as the key for retrieval
     * @return - user DTO matching the username
     */
    Optional<UserDetailedDTO> findByUsername(String username);

    /**
     * Attempts to authenticate a user by hashing and comparing the given password with the one stored for the given user.
     *
     * @param userAuthenticateDTO - user whose password is to be checked
     * @return true if the password hashes match
     */
    Optional<Boolean> authenticate(UserAuthenticateDTO userAuthenticateDTO);

    /**
     * Check if the given user is admin.
     *
     * @param userDTO - user to be checked for admin privileges
     * @return true if user is admin
     */
    Optional<Boolean> isAdmin(UserDTO userDTO);

    Boolean disable(UserDTO userDTO);

    /**
     * Updates the given user.
     *
     * @param userDetailedDTO - user DTO with data for update in attributes
     * @return DTO with user data after the update operation
     */
    Optional<UserDetailedDTO> update(UserDetailedDTO userDetailedDTO);
}
