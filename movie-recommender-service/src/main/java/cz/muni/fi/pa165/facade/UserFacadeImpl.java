package cz.muni.fi.pa165.facade;

import cz.muni.fi.pa165.dto.user.UserAuthenticateDTO;
import cz.muni.fi.pa165.dto.user.UserDTO;
import cz.muni.fi.pa165.dto.user.UserDetailedDTO;
import cz.muni.fi.pa165.service.UserService;
import cz.muni.fi.pa165.entity.User;
import cz.muni.fi.pa165.exception.ServiceLayerException;
import cz.muni.fi.pa165.mapper.UserMapper;
import javax.inject.Inject;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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
    public Optional<UserDetailedDTO> register(UserDTO userDTO, String password) {
        User user = userMapper.userDTOToUser(userDTO);

        User registeredUser;
        try {
            registeredUser = userService.register(user, password);
        } catch (ServiceLayerException e) {
            return Optional.empty();
        }

        return Optional.of(userMapper.userToUserDetailedDTO(registeredUser));
    }

    @Override
    public Optional<UserDetailedDTO> findById(Long id) {
        User user;
        try {
            user = userService.findById(id);
        } catch (ServiceLayerException e) {
            return Optional.empty();
        }

        return Optional.of(userMapper.userToUserDetailedDTO(user));
    }

    @Override
    public Optional<UserDetailedDTO> findByEmailAddress(String emailAddress) {
        User user;
        try {
            user = userService.findByEmailAddress(emailAddress);
        } catch (ServiceLayerException e) {
            return Optional.empty();
        }
        return Optional.of(userMapper.userToUserDetailedDTO(user));
    }

    @Override
    public Optional<UserDetailedDTO> findByUsername(String username) {
        User user;
        try {
            user = userService.findByUsername(username);
        } catch (ServiceLayerException e) {
            return Optional.empty();
        }

        return Optional.of(userMapper.userToUserDetailedDTO(user));
    }

    @Override
    public Optional<Boolean> authenticate(UserAuthenticateDTO userAuthenticateDTO) {
        Boolean authenticated;
        try {
            User user = userService.findById(userAuthenticateDTO.getUserId());
            authenticated = userService.authenticate(user, userAuthenticateDTO.getPassword());
        } catch (ServiceLayerException e) {
            return Optional.empty();
        }

        return Optional.of(authenticated);
    }

    @Override
    public Optional<Boolean> isAdmin(UserDTO userDTO) {
        User user = userMapper.userDTOToUser(userDTO);

        boolean isAdmin;
        try {
            isAdmin = userService.isAdmin(user);
        } catch (ServiceLayerException e) {
            return Optional.empty();
        }

        return Optional.of(isAdmin);
    }

    @Override
    public Optional<Boolean> isDisabled(UserDTO userDTO) {
        User user = userMapper.userDTOToUser(userDTO);

        boolean isDisabled;
        try {
            isDisabled = userService.isDisabled(user);
        } catch (ServiceLayerException e) {
            return Optional.empty();
        }

        return Optional.of(isDisabled);
    }

    @Override
    public Boolean disable(UserDTO userDTO) {
        User user = userMapper.userDTOToUser(userDTO);

        try {
            userService.disable(user);
        } catch (ServiceLayerException e) {
            return false;
        }

        return true;
    }

    @Override
    public Optional<UserDetailedDTO> update(UserDetailedDTO userDetailedDTO) {
        User user = userMapper.userDetailedDTOToUser(userDetailedDTO);

        User updatedUser;
        try {
            updatedUser = userService.update(user);
        } catch (ServiceLayerException e) {
            return Optional.empty();
        }
        return Optional.of(userMapper.userToUserDetailedDTO(updatedUser));
    }
}
