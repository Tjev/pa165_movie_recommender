package cz.muni.fi.pa165.facade;

import cz.fi.muni.pa165.dto.UserAuthenticateDTO;
import cz.fi.muni.pa165.dto.UserDTO;
import cz.fi.muni.pa165.dto.UserDetailedDTO;
import cz.fi.muni.pa165.facade.UserFacade;
import cz.muni.fi.pa165.UserService;
import cz.muni.fi.pa165.entity.User;
import cz.muni.fi.pa165.mapper.UserMapper;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Optional;

import static org.mockito.Mockito.*;

/**
 * Tests for UserFacadeImpl.
 *
 * @author Kristian Tkacik
 */
public class UserFacadeTest {

    @Mock
    private UserService userService;

    @Mock
    private UserMapper userMapper;

    private UserFacade userFacade;

    private AutoCloseable closable;

    private User user;
    private UserDTO userDTO;
    private UserDetailedDTO userDetailedDTO;
    private UserAuthenticateDTO userAuthenticateDTO;

    @BeforeMethod
    public void init() {
        closable = MockitoAnnotations.openMocks(this);
        userFacade = new UserFacadeImpl(userMapper, userService);

        PasswordEncoder encoder = new Argon2PasswordEncoder();

        user = new User("John", "john@email.com");
        user.setId(1L);
        user.setAdmin(true);
        user.setPasswordHash(encoder.encode("password1"));

        userDTO = new UserDTO();
        userDTO.setId(1L);
        userDTO.setUsername("John");
        userDTO.setEmailAddress("john@email.com");
        userDTO.setAdmin(false);
        userDTO.setPasswordHash(encoder.encode("password1"));

        userDetailedDTO = new UserDetailedDTO();
        userDetailedDTO.setId(1L);
        userDetailedDTO.setUsername("John");
        userDetailedDTO.setEmailAddress("john@email.com");
        userDetailedDTO.setAdmin(false);
        userDetailedDTO.setPasswordHash(encoder.encode("password1"));

        userAuthenticateDTO = new UserAuthenticateDTO();
        userAuthenticateDTO.setUserId(1L);
        userAuthenticateDTO.setPassword("password1");
    }

    @Test
    public void register() {
        when(userMapper.userDTOToUser(any())).thenReturn(user);
        when(userMapper.userToUserDetailedDTO(any())).thenReturn(userDetailedDTO);
        userFacade.register(userDTO, "password1");

        Assert.assertEquals(userDTO.getId(), user.getId());
        verify(userService, times(1)).register(any(User.class), any(String.class));
    }

    @Test
    public void findById() {
        when(userMapper.userToUserDetailedDTO(any())).thenReturn(userDetailedDTO);
        Optional<UserDetailedDTO> userDetailedDTO = userFacade.findById(1L);

        Assert.assertTrue(userDetailedDTO.isPresent());
        Assert.assertEquals(userDetailedDTO.get().getId(), user.getId());
        verify(userService, times(1)).findById(any(Long.class));
    }

    @Test
    public void findByEmailAddress() {
        when(userMapper.userToUserDetailedDTO(any())).thenReturn(userDetailedDTO);
        Optional<UserDetailedDTO> userDetailedDTO = userFacade.findByEmailAddress("john@email.com");

        Assert.assertTrue(userDetailedDTO.isPresent());
        Assert.assertEquals(userDetailedDTO.get().getEmailAddress(), user.getEmailAddress());
        verify(userService, times(1)).findByEmailAddress(any(String.class));
    }

    @Test
    public void findByUse() {
        when(userMapper.userToUserDetailedDTO(any())).thenReturn(userDetailedDTO);
        Optional<UserDetailedDTO> userDetailedDTO = userFacade.findByUsername("John");

        Assert.assertTrue(userDetailedDTO.isPresent());
        Assert.assertEquals(userDetailedDTO.get().getUsername(), user.getUsername());
        verify(userService, times(1)).findByUsername(any(String.class));
    }

    @Test
    public void authenticate() {
        when(userService.findById(any())).thenReturn(user);
        when(userService.authenticate(any(User.class), any(String.class))).thenReturn(true);
        Optional<Boolean> answer = userFacade.authenticate(userAuthenticateDTO);

        Assert.assertTrue(answer.isPresent());
        Assert.assertTrue(answer.get());
        verify(userService, times(1)).authenticate(any(User.class), any(String.class));
    }

    @Test
    public void isAdmin() {
        when(userMapper.userDTOToUser(any())).thenReturn(user);
        when(userService.isAdmin(any(User.class))).thenReturn(true);
        Optional<Boolean> answer = userFacade.isAdmin(userDTO);

        Assert.assertTrue(answer.isPresent());
        Assert.assertTrue(answer.get());
        verify(userService, times(1)).isAdmin(any(User.class));
    }

    @Test
    public void disable() {
        when(userMapper.userDTOToUser(any())).thenReturn(user);
        userFacade.disable(userDTO);

        verify(userService, times(1)).disable(any(User.class));
    }

    @Test
    public void update() {
        when(userMapper.userDetailedDTOToUser(any())).thenReturn(user);
        when(userMapper.userToUserDetailedDTO(any())).thenReturn(userDetailedDTO);
        userFacade.update(userDetailedDTO);

        verify(userService, times(1)).update(any(User.class));
    }

    @AfterClass
    public void releaseMocks() throws Exception {
        closable.close();
    }
}
