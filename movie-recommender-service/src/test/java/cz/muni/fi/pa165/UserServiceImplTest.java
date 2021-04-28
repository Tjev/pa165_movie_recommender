package cz.muni.fi.pa165;

import cz.muni.fi.pa165.dao.UserDao;
import cz.muni.fi.pa165.entity.User;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;


public class UserServiceImplTest {

    @Mock
    private UserDao userDao;

    private UserService userService;

    private AutoCloseable closable;

    private static final String PASSWORD1 = "password1";
    private static final String PASSWORD2 = "password2";
    private User user1;
    private User user2;
    private List<User> users;

    @BeforeMethod
    public void setup() {
        closable = MockitoAnnotations.openMocks(this);
        userService = new UserServiceImpl(userDao);

        PasswordEncoder encoder = new Argon2PasswordEncoder();

        user1 = new User("John", "john@email.com");
        user1.setId(1L);
        user1.setAdmin(true);
        user1.setPasswordHash(encoder.encode(PASSWORD1));

        user2 = new User("George", "george@email.com");
        user2.setId(2L);
        user2.setPasswordHash(encoder.encode(PASSWORD2));

        users = new ArrayList<>();
        users.add(user1);
        users.add(user2);
    }

    @Test
    public void register() {
        User user = new User();

        userService.registerUser(user, PASSWORD1);

        verify(userDao, times(1)).create(user);
        verifyNoMoreInteractions(userDao);
    }

    @Test
    public void findAll() {
        when(userDao.findAll()).thenReturn(users);

        List<User> result = userService.getAllUsers();

        verify(userDao, times(1)).findAll();
        verifyNoMoreInteractions(userDao);

        Assert.assertEquals(result, users);
    }

    @Test
    public void findById() {
        when(userDao.findById(user1.getId())).thenReturn(user1);

        User result = userService.findUserById(user1.getId());

        verify(userDao, times(1)).findById(user1.getId());
        verifyNoMoreInteractions(userDao);

        Assert.assertEquals(result, user1);
    }

    @Test
    public void findByEmailAdress() {
        when(userDao.findByEmailAddress(user1.getEmailAddress())).thenReturn(user1);

        User result = userService.findUserByEmailAddress(user1.getEmailAddress());

        verify(userDao, times(1)).findByEmailAddress(user1.getEmailAddress());
        verifyNoMoreInteractions(userDao);

        Assert.assertEquals(result, user1);
    }

    @Test
    public void findByUsername() {
        when(userDao.findByUsername(user1.getUsername())).thenReturn(user1);

        User result = userService.findUserByUsername(user1.getUsername());

        verify(userDao, times(1)).findByUsername(user1.getUsername());
        verifyNoMoreInteractions(userDao);

        Assert.assertEquals(result, user1);
    }

    @Test
    public void authenticate() {
        when(userDao.findById(user1.getId())).thenReturn(user1);
        when(userDao.findById(user2.getId())).thenReturn(user2);

        Assert.assertTrue(userService.authenticate(user1, PASSWORD1));
        Assert.assertTrue(userService.authenticate(user2, PASSWORD2));
    }

    @Test
    public void isAdmin() {
        when(userDao.findById(user1.getId())).thenReturn(user1);
        when(userDao.findById(user2.getId())).thenReturn(user2);

        Assert.assertTrue(userService.isAdmin(user1));
        Assert.assertFalse(userService.isAdmin(user2));
    }

    @Test
    public void registerNullUser() {
        Assert.assertThrows(IllegalArgumentException.class, () -> {
            userService.registerUser(null, PASSWORD1);
        });
    }

    @Test
    public void registerNullPassword() {
        User user = new User("John", "john@email.com");
        Assert.assertThrows(IllegalArgumentException.class, () -> {
            userService.registerUser(user, null);
        });
    }

    @Test
    public void findByNullId() {
        Assert.assertThrows(IllegalArgumentException.class, () -> {
            userService.findUserById(null);
        });
    }

    @Test
    public void findByNullEmailAddress() {
        Assert.assertThrows(IllegalArgumentException.class, () -> {
            userService.findUserByEmailAddress(null);
        });
    }

    @Test
    public void findByNullUsername() {
        Assert.assertThrows(IllegalArgumentException.class, () -> {
            userService.findUserByUsername(null);
        });
    }

    @Test
    public void authenticateNullUser() {
        Assert.assertThrows(IllegalArgumentException.class, () -> {
            userService.authenticate(null, PASSWORD1);
        });
    }

    @Test
    public void authenticateNullPassword() {
        Assert.assertThrows(IllegalArgumentException.class, () -> {
            userService.authenticate(user1, null);
        });
    }

    @Test
    public void isAdminNullUser() {
        Assert.assertThrows(IllegalArgumentException.class, () -> {
            userService.isAdmin(null);
        });
    }

    @AfterClass
    public void releaseMocks() throws Exception {
        closable.close();
    }
}
