package cz.muni.fi.pa165.rest.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import cz.muni.fi.pa165.dto.user.UserAuthenticateDTO;
import cz.muni.fi.pa165.dto.user.UserDTO;
import cz.muni.fi.pa165.dto.user.UserDetailedDTO;
import cz.muni.fi.pa165.dto.user.UserRegisterDTO;
import cz.muni.fi.pa165.exception.FacadeLayerException;
import cz.muni.fi.pa165.facade.UserFacade;
import cz.muni.fi.pa165.rest.exception.DataSourceException;
import cz.muni.fi.pa165.rest.exception.InvalidParameterException;
import cz.muni.fi.pa165.rest.exception.UnauthorizedException;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Optional;

import static org.slf4j.LoggerFactory.*;

@RestController
@RequestMapping(value = "/users")
public class UserController {

    final static Logger logger = getLogger(UserController.class);

    private final UserFacade userFacade;

    @Inject
    public UserController(UserFacade userFacade) {
        this.userFacade = userFacade;
    }

    /**
     * Register a user into the system.
     *
     * curl -X PUT -i -H "Content-Type: application/json" --data '{"username": "TomWiseau1", "emailAddress": "tom.wiseau@gmail.com", "password": "OhHiMark"}' http://localhost:8080/pa165/rest/users/register
     *
     * @param userRegisterDTO user to be registered
     * @return detailed DTO of the user after its creation
     */
    @RequestMapping(value = "/register", method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public final UserDetailedDTO register(@RequestBody UserRegisterDTO userRegisterDTO) {
        logger.debug("rest register({})", userRegisterDTO);

        try {
            return userFacade.register(userRegisterDTO);
        } catch (FacadeLayerException e) {
            throw new DataSourceException("The instance already exists.", e);
        } catch (IllegalArgumentException e) {
            throw new InvalidParameterException("Given parameters were invalid.", e);
        }
    }

    /**
     * Retrieve all system users.
     *
     * curl -X GET -i http://localhost:8080/pa165/rest/users
     *
     * @return list of User DTO objects
     */
    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final List<UserDTO> findAll() {
        logger.debug("rest findAll()");

        List<UserDTO> result;
        try {
            result = userFacade.findAll();
        } catch (FacadeLayerException e) {
            throw new DataSourceException("Problem with the data source occurred.", e);
        }

        return result;
    }

    /**
     * Find a user by its id.
     *
     * curl -X GET -i http://localhost:8080/pa165/rest/users/1
     *
     * @param id id of the user to be found
     * @return detailed DTO of the found user
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final UserDetailedDTO findById(@PathVariable Long id) {
        logger.debug("rest findById({})", id);

        Optional<UserDetailedDTO> result;
        try {
            result = userFacade.findById(id);
        } catch (FacadeLayerException e) {
            throw new DataSourceException("Problem with the data source occurred.", e);
        } catch (IllegalArgumentException e) {
            throw new InvalidParameterException("Given parameters were invalid.", e);
        }

        if (result.isEmpty()) {
            throw new InvalidParameterException("User with given id has not been found.");
        }

        return result.get();
    }

    /**
     * Find user by its username.
     *
     * curl -X GET -i http://localhost:8080/pa165/rest/users/find-by-username?username=TomWiseau1
     *
     * @param username username of the user to be found
     * @return detailed DTO of the found user
     */
    @RequestMapping(value = "/find-by-username", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final UserDetailedDTO findByUsername(@RequestParam String username) {
        logger.debug("rest findByUsername({})", username);

        Optional<UserDetailedDTO> result;
        try {
            result = userFacade.findByUsername(username);
        } catch (FacadeLayerException e) {
            throw new DataSourceException("Problem with the data source occurred.", e);
        } catch (IllegalArgumentException e) {
            throw new InvalidParameterException("Given parameters were invalid.", e);
        }

        if (result.isEmpty()) {
            throw new InvalidParameterException("User with given username has not been found.");
        }

        return result.get();
    }

    /**
     * Find user by its email address.
     *
     * curl -X GET -i http://localhost:8080/pa165/rest/users/find-by-mail?emailAddress=tom.wiseau%40gmail.com
     *
     * @param emailAddress email address of the user to be found
     * @return detailed DTO of the found user
     */
    @RequestMapping(value = "/find-by-mail", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final UserDetailedDTO findByEmailAddress(@RequestParam String emailAddress) {
        logger.debug("rest findByEmailAddress({})", emailAddress);

        Optional<UserDetailedDTO> result;
        try {
            result = userFacade.findByEmailAddress(emailAddress);
        } catch (FacadeLayerException e) {
            throw new DataSourceException("Problem with the data source occurred.", e);
        } catch (IllegalArgumentException e) {
            throw new InvalidParameterException("Given parameters were invalid.", e);
        }

        if (result.isEmpty()) {
            throw new InvalidParameterException("User with given email address has not been found.");
        }

        return result.get();
    }

    /**
     * Authenticate user against his stored credentials.
     *
     * curl -X POST -i -H "Content-Type: application/json" --data '{"emailAddress": "tom.wiseau@gmail.com", "password": "OhHiMark"}' http://localhost:8080/pa165/rest/users/auth
     *
     * @param userAuthenticateDTO user to be authenticated
     * @return true if the authentication was successful, false otherwise
     */
    @RequestMapping(value = "/auth", method = RequestMethod.POST,
                    consumes = MediaType.APPLICATION_JSON_VALUE,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    public final String authenticate(@RequestBody UserAuthenticateDTO userAuthenticateDTO) {
        logger.debug("rest authenticate user ({})", userAuthenticateDTO.getEmailAddress());

        Optional<String> token;
        try {
            token = userFacade.authenticate(userAuthenticateDTO);
        } catch (FacadeLayerException e) {
            throw new DataSourceException("Problem with the data source occurred.", e);
        } catch (IllegalArgumentException | UnsupportedEncodingException e) {
            throw new InvalidParameterException("Given parameters were invalid.", e);
        }

        if (token.isEmpty()) {
            throw new UnauthorizedException("Username or password is not valid.");
        }

        return token.get();
    }

    /**
     * Check if the given user account has admin privileges.
     *
     * curl -X GET -i -H "Content-Type: application/json" --data '{"id": "1"}' http://localhost:8080/pa165/rest/users/is-admin
     *
     * @param userDTO user to be checked
     * @return true if the user is admin, false otherwise
     */
    @RequestMapping(value = "/is-admin", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE)
    public final Boolean isAdmin(@RequestBody UserDTO userDTO) {
        logger.debug("rest isAdmin({})", userDTO);

        try {
            return userFacade.isAdmin(userDTO);
        } catch (FacadeLayerException e) {
            throw new DataSourceException("Problem with the data source occurred.", e);
        } catch (IllegalArgumentException e) {
            throw new InvalidParameterException("Given parameters were invalid.", e);
        }
    }

    /**
     * Check if the given user account is disabled.
     *
     * curl -X GET -i -H "Content-Type: application/json" --data '{"id": "1"}' http://localhost:8080/pa165/rest/users/is-disabled
     *
     * @param userDTO user to be checked
     * @return true if the user is disabled within the system, false otherwise
     */
    @RequestMapping(value = "/is-disabled", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE)
    public final Boolean isDisabled(@RequestBody UserDTO userDTO) {
        logger.debug("rest isDisabled({})", userDTO);

        try {
            return userFacade.isDisabled(userDTO);
        } catch (FacadeLayerException e) {
            throw new DataSourceException("Problem with the data source occurred.", e);
        } catch (IllegalArgumentException e) {
            throw new InvalidParameterException("Given parameters were invalid.", e);
        }
    }

    /**
     * Disable the given user in the system.
     *
     * curl -X POST -i -H "Content-Type: application/json" http://localhost:8080/pa165/rest/users/disable?id=1
     *
     * @param id id of the user to be disabled
     */
    @RequestMapping(value = "/disable", method = RequestMethod.POST)
    public final void disable(@RequestParam Long id) {
        logger.debug("rest disable({})", id);

        try {
            userFacade.disable(id);
        } catch (FacadeLayerException e) {
            throw new DataSourceException("Problem with the data source occurred.", e);
        } catch (IllegalArgumentException e) {
            throw new InvalidParameterException("Given parameters were invalid.", e);
        }
    }

    /**
     * Update the given user.
     *
     * curl -X PUT -i -H "Content-Type: application/json" --data '{"id": "1", "username": "TomWiseau1", "emailAddress": "hi.doggy@gmail.com"}' http://localhost:8080/pa165/rest/users/update
     *
     * @param userDetailedDTO DTO of the user to be updated
     * @return detailed DTO of the user after update operation
     */
    @RequestMapping(value = "/update", method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final UserDetailedDTO update(@RequestBody UserDetailedDTO userDetailedDTO) {
        logger.debug("rest update({})", userDetailedDTO);

        try {
            return userFacade.update(userDetailedDTO);
        } catch (FacadeLayerException e) {
            throw new DataSourceException("Problem with the data source occurred.", e);
        } catch (IllegalArgumentException e) {
            throw new InvalidParameterException("Given parameters were invalid.", e);
        }
    }
}
