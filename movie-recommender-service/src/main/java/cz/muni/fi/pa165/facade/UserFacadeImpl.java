package cz.muni.fi.pa165.facade;

import cz.fi.muni.pa165.dto.UserAuthenticateDTO;
import cz.fi.muni.pa165.dto.UserDTO;
import cz.fi.muni.pa165.dto.UserDetailedDTO;
import cz.fi.muni.pa165.facade.UserFacade;
import cz.muni.fi.pa165.UserService;
import cz.muni.fi.pa165.entity.User;
import cz.muni.fi.pa165.exceptions.ServiceLayerException;
import cz.muni.fi.pa165.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class UserFacadeImpl implements UserFacade {

    private final UserService userService;
    private final UserMapper userMapper;

    @Autowired
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
    public Boolean authenticate(UserAuthenticateDTO userAuthenticateDTO) {
        User user;
        try {
            user = userService.findById(userAuthenticateDTO.getUserId());
        } catch (ServiceLayerException e) {
            return false;
        }

        return userService.authenticate(user, userAuthenticateDTO.getPassword());
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
