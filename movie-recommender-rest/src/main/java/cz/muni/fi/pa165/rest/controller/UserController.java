package cz.muni.fi.pa165.rest.controller;

import cz.muni.fi.pa165.dto.user.UserAuthenticateDTO;
import cz.muni.fi.pa165.dto.user.UserDTO;
import cz.muni.fi.pa165.dto.user.UserDetailedDTO;
import cz.muni.fi.pa165.facade.UserFacade;
import org.slf4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;

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
     * curl -X POST -i -H "Content-Type: application/json" --data '{"username": "TomWiseau1", "emailAddress": "tom.wiseau@gmail.com"}' http://localhost:8080/pa165/rest/users/register?password=OhHiMark
     *
     * @param userDTO user to be registered
     * @return detailed DTO of the user after its creation
     */
    @RequestMapping(value = "/register", method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final UserDetailedDTO register(@RequestBody UserDTO userDTO, String password) {
        logger.debug("rest register({})", userDTO);

        return userFacade.register(userDTO, password).get();
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

        return userFacade.findById(id).get();
    }

    /**
     * Find user by its username.
     *
     * curl -X GET -i http://localhost:8080/pa165/rest/users/find-by-username?name=TomWiseau1
     *
     * @param username username of the user to be found
     * @return detailed DTO of the found user
     */
    @RequestMapping(value = "/find-by-username", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final UserDetailedDTO findByUsername(@RequestBody String username) {
        logger.debug("rest findByUsername({})", username);

        return userFacade.findByUsername(username).get();
    }

    /**
     * Find user by its email address.
     *
     * curl -X GET -i --data '{"emailAddress}' http://localhost:8080/pa165/rest/users/find-by-username?emailAddress=tom%2ewiseau%40gmail%2ecom
     *
     * @param emailAddress email address of the user to be found
     * @return detailed DTO of the found user
     */
    @RequestMapping(value = "/find-by-mail", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final UserDetailedDTO findByEmailAddress(@RequestBody String emailAddress) {
        logger.debug("rest findByEmailAddress({})", emailAddress);

        return userFacade.findByEmailAddress(emailAddress).get();
    }

    /**
     * Authenticate user against his stored credentials.
     *
     * curl -X POST -i -H "Content-Type: application/json" --data '{"id": "1", "password": "Ididnothither!"}' http://localhost:8080/pa165/rest/users/auth
     *
     * @param userAuthenticateDTO user to be authenticated
     * @return true if the authentication was successful, false otherwise
     */
    @RequestMapping(value = "/auth", method = RequestMethod.POST,
                    consumes = MediaType.APPLICATION_JSON_VALUE,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    public final Boolean authenticate(@RequestBody UserAuthenticateDTO userAuthenticateDTO) {
        logger.debug("rest authenticate user ({})", userAuthenticateDTO.getUserId());

        return userFacade.authenticate(userAuthenticateDTO).get();
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

        return userFacade.isAdmin(userDTO).get();
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

        return userFacade.isDisabled(userDTO).get();
    }

    /**
     * Disable the given user in the system.
     *
     * curl -X POST -i -H "Content-Type: application/json" --data '{"id": "1"}' http://localhost:8080/pa165/rest/users/disable
     *
     * @param userDTO user to be disabled
     */
    @RequestMapping(value = "/disable", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final Boolean disable(@RequestBody UserDTO userDTO) {
        logger.debug("rest disable({})", userDTO);

        return userFacade.disable(userDTO);
    }

    /**
     * Update the given user.
     *
     * curl -X PUT -i -H "Content-Type: application/json" --data '{"id": "1", "username": "TomWiseau1, "emailAddress": "hi.doggy@gmail.com"}' http://localhost:8080/pa165/rest/users/update
     *
     * @param userDetailedDTO DTO of the user to be updated
     * @return detailed DTO of the user after update operation
     */
    @RequestMapping(value = "/update", method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final UserDetailedDTO update(@RequestBody UserDetailedDTO userDetailedDTO) {
        logger.debug("rest update({})", userDetailedDTO);

        return userFacade.update(userDetailedDTO).get();
    }
}