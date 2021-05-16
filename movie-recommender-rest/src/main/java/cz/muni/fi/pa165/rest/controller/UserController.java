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

    @RequestMapping(value = "/register", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final UserDetailedDTO register(@RequestBody UserDTO userDTO, String password) {
        logger.debug("rest register({})", userDTO);

        return userFacade.register(userDTO, password).get();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final UserDetailedDTO findById(@PathVariable Long id) {
        logger.debug("rest findById({})", id);

        return userFacade.findById(id).get();
    }

    @RequestMapping(value = "/find-by-username", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final UserDetailedDTO findByUsername(@RequestBody String username) {
        logger.debug("rest findByUsername({})", username);

        return userFacade.findByUsername(username).get();
    }

    @RequestMapping(value = "/find-by-mail", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final UserDetailedDTO findByEmailAddress(@RequestBody String emailAddress) {
        logger.debug("rest findByEmailAddress({})", emailAddress);

        return userFacade.findByEmailAddress(emailAddress).get();
    }

    @RequestMapping(value = "/auth", method = RequestMethod.POST,
                    consumes = MediaType.APPLICATION_JSON_VALUE,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    public final Boolean authenticate(@RequestBody UserAuthenticateDTO userAuthenticateDTO) {
        logger.debug("rest authenticate user ({})", userAuthenticateDTO.getUserId());

        return userFacade.authenticate(userAuthenticateDTO).get();
    }

    @RequestMapping(value = "/is-admin", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE)
    public final Boolean isAdmin(@RequestBody UserDTO userDTO) {
        logger.debug("rest isAdmin({})", userDTO);

        return userFacade.isAdmin(userDTO).get();
    }

    @RequestMapping(value = "/is-disabled", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE)
    public final Boolean isDisabled(@RequestBody UserDTO userDTO) {
        logger.debug("rest isDisabled({})", userDTO);

        return userFacade.isDisabled(userDTO).get();
    }

    @RequestMapping(value = "/disable", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final Boolean disable(@RequestBody UserDTO userDTO) {
        logger.debug("rest disable({})", userDTO);

        return userFacade.disable(userDTO);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final UserDetailedDTO update(@RequestBody UserDetailedDTO userDetailedDTO) {
        logger.debug("rest update({})", userDetailedDTO);

        return userFacade.update(userDetailedDTO).get();
    }
}
