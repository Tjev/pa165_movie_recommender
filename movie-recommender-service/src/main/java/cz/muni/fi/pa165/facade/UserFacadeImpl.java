package cz.muni.fi.pa165.facade;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import cz.muni.fi.pa165.dto.user.UserAuthenticateDTO;
import cz.muni.fi.pa165.dto.user.UserDTO;
import cz.muni.fi.pa165.dto.user.UserDetailedDTO;
import cz.muni.fi.pa165.dto.user.UserRegisterDTO;
import cz.muni.fi.pa165.exception.FacadeLayerException;
import cz.muni.fi.pa165.service.UserService;
import cz.muni.fi.pa165.entity.User;
import cz.muni.fi.pa165.exception.ServiceLayerException;
import cz.muni.fi.pa165.mapper.UserMapper;
import javax.inject.Inject;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.UnsupportedEncodingException;
import java.util.Optional;

/**
 * Implementation of {@link UserFacade} interface.
 *
 * @author Tomas Jevocin
 */
@Service
@Transactional
public class UserFacadeImpl implements UserFacade {

    private final UserService userService;
    private final UserMapper userMapper;

    @Inject
    public UserFacadeImpl(UserMapper userMapper, UserService userService) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @Override
    public UserDetailedDTO register(UserRegisterDTO userRegisterDTO) {
        User user = new User(userRegisterDTO.getUsername(), userRegisterDTO.getEmailAddress());

        User registeredUser;
        try {
            registeredUser = userService.register(user, userRegisterDTO.getPassword());
        } catch (ServiceLayerException e) {
            throw new FacadeLayerException("Error at service layer occurred", e);
        }

        return userMapper.userToUserDetailedDTO(registeredUser);
    }

    @Override
    public Optional<UserDetailedDTO> findById(Long id) {
        User user;
        try {
            user = userService.findById(id);
        } catch (ServiceLayerException e) {
            throw new FacadeLayerException("Error at service layer occurred", e);
        }

        return Optional.of(userMapper.userToUserDetailedDTO(user));
    }

    @Override
    public Optional<UserDetailedDTO> findByEmailAddress(String emailAddress) {
        User user;
        try {
            user = userService.findByEmailAddress(emailAddress);
        } catch (ServiceLayerException e) {
            throw new FacadeLayerException("Error at service layer occurred", e);
        }
        return Optional.of(userMapper.userToUserDetailedDTO(user));
    }

    @Override
    public Optional<UserDetailedDTO> findByUsername(String username) {
        User user;
        try {
            user = userService.findByUsername(username);
        } catch (ServiceLayerException e) {
            throw new FacadeLayerException("Error at service layer occurred", e);
        }

        return Optional.of(userMapper.userToUserDetailedDTO(user));
    }

    @Override
    public Optional<String> authenticate(UserAuthenticateDTO userAuthenticateDTO) throws UnsupportedEncodingException {
        Boolean authenticated;
        User user;
        try {
            user = userService.findByEmailAddress(userAuthenticateDTO.getEmailAddress());
            authenticated = userService.authenticate(user, userAuthenticateDTO.getPassword());
        } catch (ServiceLayerException e) {
            throw new FacadeLayerException("Error at service layer occurred", e);
        }

        if (!authenticated) {
            return Optional.empty();
        }

        return Optional.of(getJWTToken(user));
    }

    @Override
    public Boolean isAdmin(UserDTO userDTO) {
        User user = userMapper.userDTOToUser(userDTO);

        boolean isAdmin;
        try {
            isAdmin = userService.isAdmin(user);
        } catch (ServiceLayerException e) {
            throw new FacadeLayerException("Error at service layer occurred", e);
        }

        return isAdmin;
    }

    @Override
    public Boolean isDisabled(UserDTO userDTO) {
        User user = userMapper.userDTOToUser(userDTO);

        boolean isDisabled;
        try {
            isDisabled = userService.isDisabled(user);
        } catch (ServiceLayerException e) {
            throw new FacadeLayerException("Error at service layer occurred", e);
        }

        return isDisabled;
    }

    @Override
    public void disable(UserDTO userDTO) {
        User user = userMapper.userDTOToUser(userDTO);

        try {
            userService.disable(user);
        } catch (ServiceLayerException e) {
            throw new FacadeLayerException("Error at service layer occurred", e);
        }
    }

    @Override
    public UserDetailedDTO update(UserDetailedDTO userDetailedDTO) {
        User user = userMapper.userDetailedDTOToUser(userDetailedDTO);

        User updatedUser;
        try {
            updatedUser = userService.update(user);
        } catch (ServiceLayerException e) {
            throw new FacadeLayerException("Error at service layer occurred", e);
        }
        return userMapper.userToUserDetailedDTO(updatedUser);
    }

    private String getJWTToken(User user) throws UnsupportedEncodingException {
        Algorithm algorithm = Algorithm.HMAC256("secret");
        return JWT.create()
                .withClaim("id", 1L)
                .withClaim("email", user.getEmailAddress())
                .withClaim("username", user.getUsername())
                .withClaim("admin", user.isAdmin())
                .sign(algorithm);
    }
}
