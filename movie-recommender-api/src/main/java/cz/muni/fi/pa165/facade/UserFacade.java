package cz.muni.fi.pa165.facade;

import cz.muni.fi.pa165.dto.user.UserAuthenticateDTO;
import cz.muni.fi.pa165.dto.user.UserDTO;
import cz.muni.fi.pa165.dto.user.UserDetailedDTO;

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
    UserDetailedDTO register(UserDTO userDTO, String password);

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
    Boolean authenticate(UserAuthenticateDTO userAuthenticateDTO);

    /**
     * Check if the given user is admin.
     *
     * @param userDTO - user to be checked for admin privileges
     * @return true if user is admin
     */
    Boolean isAdmin(UserDTO userDTO);

    /**
     * Check if the given user is disabled.
     *
     * @param userDTO - user to be checked
     * @return true if user is disabled
     */
    Boolean isDisabled(UserDTO userDTO);

    /**
     * Disable an account of the given user
     *
     * @param userDTO - user to be disabled
     */
    void disable(UserDTO userDTO);

    /**
     * Updates the given user.
     *
     * @param userDetailedDTO - user DTO with data for update in attributes
     * @return DTO with user data after the update operation
     */
    UserDetailedDTO update(UserDetailedDTO userDetailedDTO);
}
