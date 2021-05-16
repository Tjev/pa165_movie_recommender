package cz.muni.fi.pa165.facade;

import cz.muni.fi.pa165.dto.user.UserAuthenticateDTO;
import cz.muni.fi.pa165.dto.user.UserDTO;
import cz.muni.fi.pa165.dto.user.UserDetailedDTO;
import cz.muni.fi.pa165.dto.user.UserRegisterDTO;
import cz.muni.fi.pa165.service.UserService;
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
    private UserRegisterDTO userRegisterDTO;

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
        userAuthenticateDTO.setId(1L);
        userAuthenticateDTO.setPassword("password1");

        userRegisterDTO = new UserRegisterDTO();
        userRegisterDTO.setUsername("John");
        userRegisterDTO.setEmailAddress("john@email.com");
        userRegisterDTO.setPassword("password1");
    }

    @Test
    public void register() {
        when(userMapper.userToUserDetailedDTO(any())).thenReturn(userDetailedDTO);
        userFacade.register(userRegisterDTO);

        Assert.assertEquals(userDTO.getId(), user.getId());
        verify(userService, times(1)).register(any(User.class), any(String.class));
        verifyNoMoreInteractions(userService);
    }

    @Test
    public void findById() {
        when(userMapper.userToUserDetailedDTO(any(User.class))).thenReturn(userDetailedDTO);
        when(userService.findById(any(Long.class))).thenReturn(user);
        Optional<UserDetailedDTO> userDetailedDTO = userFacade.findById(1L);

        Assert.assertTrue(userDetailedDTO.isPresent());
        Assert.assertEquals(userDetailedDTO.get().getId(), user.getId());
        verify(userService, times(1)).findById(any(Long.class));
        verifyNoMoreInteractions(userService);
    }

    @Test
    public void findByEmailAddress() {
        when(userMapper.userToUserDetailedDTO(any(User.class))).thenReturn(userDetailedDTO);
        when(userService.findByEmailAddress(any(String.class))).thenReturn(user);
        Optional<UserDetailedDTO> userDetailedDTO = userFacade.findByEmailAddress("john@email.com");

        Assert.assertTrue(userDetailedDTO.isPresent());
        Assert.assertEquals(userDetailedDTO.get().getEmailAddress(), user.getEmailAddress());
        verify(userService, times(1)).findByEmailAddress(any(String.class));
        verifyNoMoreInteractions(userService);
    }

    @Test
    public void findByUsername() {
        when(userMapper.userToUserDetailedDTO(any(User.class))).thenReturn(userDetailedDTO);
        when(userService.findByUsername(any(String.class))).thenReturn(user);
        Optional<UserDetailedDTO> userDetailedDTO = userFacade.findByUsername("John");

        Assert.assertTrue(userDetailedDTO.isPresent());
        Assert.assertEquals(userDetailedDTO.get().getUsername(), user.getUsername());
        verify(userService, times(1)).findByUsername(any(String.class));
        verifyNoMoreInteractions(userService);
    }

    @Test
    public void authenticate() {
        when(userService.findById(any(Long.class))).thenReturn(user);
        when(userService.authenticate(any(User.class), any(String.class))).thenReturn(true);

        Boolean answer = userFacade.authenticate(userAuthenticateDTO);

        Assert.assertTrue(answer);
        verify(userService, times(1)).authenticate(any(User.class), any(String.class));
        verify(userService, times(1)).findById(any(Long.class));
        verifyNoMoreInteractions(userService);
    }

    @Test
    public void isAdmin() {
        when(userMapper.userDTOToUser(any(UserDTO.class))).thenReturn(user);
        when(userService.isAdmin(any(User.class))).thenReturn(true);
        Boolean answer = userFacade.isAdmin(userDTO);

        Assert.assertTrue(answer);
        verify(userService, times(1)).isAdmin(any(User.class));
        verifyNoMoreInteractions(userService);
    }

    @Test
    public void isDisabled() {
        when(userMapper.userDTOToUser(any(UserDTO.class))).thenReturn(user);
        when(userService.isDisabled(any(User.class))).thenReturn(true);
        Boolean answer = userFacade.isDisabled(userDTO);

        Assert.assertTrue(answer);
        verify(userService, times(1)).isDisabled(any(User.class));
        verifyNoMoreInteractions(userService);
    }

    @Test
    public void disable() {
        when(userMapper.userDTOToUser(any(UserDTO.class))).thenReturn(user);
        userFacade.disable(userDTO);

        verify(userService, times(1)).disable(any(User.class));
        verifyNoMoreInteractions(userService);
    }

    @Test
    public void update() {
        when(userMapper.userDetailedDTOToUser(any(UserDetailedDTO.class))).thenReturn(user);
        when(userMapper.userToUserDetailedDTO(any(User.class))).thenReturn(userDetailedDTO);
        when(userService.update(any(User.class))).thenReturn(user);
        userFacade.update(userDetailedDTO);

        verify(userService, times(1)).update(any(User.class));
        verifyNoMoreInteractions(userService);
    }

    @AfterClass
    public void releaseMocks() throws Exception {
        closable.close();
    }
}
