package cz.fi.muni.pa165.facade;

import cz.fi.muni.pa165.dto.UserAuthenticateDTO;
import cz.fi.muni.pa165.dto.UserDTO;

import java.util.List;

/**
 * Facade for interaction with the User service.
 *
 * @author Tomas Jevocin.
 */
public interface UserFacade {

    /**
     * Registers the user into the system.
     *
     * @param user - user to be registered
     * @param password - password to be hashed and stored for the user
     * @return - DTO representation of the registered user
     */
    UserDTO register(UserDTO user, String password);

    /**
     * Retrieves all registered system users.
     *
     * @return list of DTOs representing registered users
     */
    List<UserDTO> findAll();

    /**
     * Retrieves a user of the system with the matching id.
     *
     * @param id - id to be used as the key for retrieval
     * @return - user DTO matching the id
     */
    UserDTO findById(Long id);

    /**
     * Retrieves a user of the system with the matching email address.
     *
     * @param emailAddress - email address to be used as the key for retrieval
     * @return - user DTO matching the email address
     */
    UserDTO findByEmailAddress(String emailAddress);

    /**
     * Retrieves a user of the system with the matching username.
     *
     * @param username - username to be used as the key for retrieval
     * @return - user DTO matching the username
     */
    UserDTO findByUsername(String username);

    /**
     * Attempts to authenticate a user by hashing and comparing the given password with the one stored for the given user.
     *
     * @param user - user whose password is to be checked
     * @return true if the password hashes match
     */
    boolean authenticate(UserAuthenticateDTO user);

    /**
     * Check if the given user is admin.
     *
     * @param user - user to be checked for admin privileges
     * @return true if user is admin
     */
    boolean isAdmin(UserDTO user);
}
