package cz.muni.fi.pa165;

import cz.muni.fi.pa165.dao.UserDao;
import cz.muni.fi.pa165.entity.Movie;
import cz.muni.fi.pa165.entity.User;
import cz.muni.fi.pa165.exceptions.ServiceLayerException;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.persistence.PersistenceException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

/**
 * Tests for UserServiceImpl class.
 *
 * @author Radoslav Chudovsky, Jiri Papousek
 */
public class UserServiceTest {

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
        user2.setDisabled(true);
        user2.setPasswordHash(encoder.encode(PASSWORD2));

        users = new ArrayList<>();
        users.add(user1);
        users.add(user2);
    }

    @Test
    public void register() {
        User user = new User();

        userService.register(user, PASSWORD1);

        verify(userDao, times(1)).create(user);
        verifyNoMoreInteractions(userDao);
    }

    @Test
    public void findAll() {
        when(userDao.findAll()).thenReturn(users);

        List<User> result = userService.findAll();

        verify(userDao, times(1)).findAll();
        verifyNoMoreInteractions(userDao);

        Assert.assertEquals(result, users);
    }

    @Test
    public void findById() {
        when(userDao.findById(user1.getId())).thenReturn(user1);

        User result = userService.findById(user1.getId());

        verify(userDao, times(1)).findById(user1.getId());
        verifyNoMoreInteractions(userDao);

        Assert.assertEquals(result, user1);
    }

    @Test
    public void findByEmailAdress() {
        when(userDao.findByEmailAddress(user1.getEmailAddress())).thenReturn(user1);

        User result = userService.findByEmailAddress(user1.getEmailAddress());

        verify(userDao, times(1)).findByEmailAddress(user1.getEmailAddress());
        verifyNoMoreInteractions(userDao);

        Assert.assertEquals(result, user1);
    }

    @Test
    public void findByUsername() {
        when(userDao.findByUsername(user1.getUsername())).thenReturn(user1);

        User result = userService.findByUsername(user1.getUsername());

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
    public void disable() {
        when(userDao.findById(user1.getId())).thenReturn(user1);

        userService.disable(user1);

        verify(userDao, times(1)).findById(anyLong());
        verify(userDao, times(1)).update(any());
        verifyNoMoreInteractions(userDao);
    }

    @Test
    public void update() {
        when(userDao.findById(user1.getId())).thenReturn(user1);

        user1.setEmailAddress("john_new@mail.com");
        userService.update(user1);

        verify(userDao, times(1)).findById(anyLong());
        verify(userDao, times(1)).update(any());
        verifyNoMoreInteractions(userDao);
    }

    @Test
    public void isDisabled() {
        when(userDao.findById(user1.getId())).thenReturn(user1);
        when(userDao.findById(user2.getId())).thenReturn(user2);

        Assert.assertFalse(userService.isDisabled(user1));
        Assert.assertTrue(userService.isDisabled(user2));
    }

    @Test
    public void registerNullUser() {
        Assert.assertThrows(IllegalArgumentException.class, () -> {
            userService.register(null, PASSWORD1);
        });
    }

    @Test
    public void registerNullPassword() {
        User user = new User("John", "john@email.com");
        Assert.assertThrows(IllegalArgumentException.class, () -> {
            userService.register(user, null);
        });
    }

    @Test
    public void findByNullId() {
        Assert.assertThrows(IllegalArgumentException.class, () -> {
            userService.findById(null);
        });
    }

    @Test
    public void findByNullEmailAddress() {
        Assert.assertThrows(IllegalArgumentException.class, () -> {
            userService.findByEmailAddress(null);
        });
    }

    @Test
    public void findByNullUsername() {
        Assert.assertThrows(IllegalArgumentException.class, () -> {
            userService.findByUsername(null);
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

    @Test
    public void registerEmptyPassword() {
        User user = new User("John", "john@email.com");
        Assert.assertThrows(IllegalArgumentException.class, () -> {
            userService.register(user, "");
        });
    }

    @Test
    public void findByEmptyEmailAddress() {
        Assert.assertThrows(IllegalArgumentException.class, () -> {
            userService.findByEmailAddress("");
        });
    }

    @Test
    public void findByEmptyUsername() {
        Assert.assertThrows(IllegalArgumentException.class, () -> {
            userService.findByUsername("");
        });
    }

    @Test
    public void authenticateEmptyPassword() {
        Assert.assertThrows(IllegalArgumentException.class, () -> {
            userService.authenticate(user1, "");
        });
    }

    @Test
    public void registerPersistenceLayerFailed() {
        User user = new User("John", "john@email.com");

        doThrow(PersistenceException.class)
                .when(userDao)
                .create(user);

        Assert.assertThrows(ServiceLayerException.class, () -> {
            userService.register(user, PASSWORD1);
        });
    }

    @Test
    public void findAllPersistenceLayerFailed() {
        doThrow(PersistenceException.class)
                .when(userDao)
                .findAll();

        Assert.assertThrows(ServiceLayerException.class, () -> {
            userService.findAll();
        });
    }

    @Test
    public void findByIdPersistenceLayerFailed() {
        doThrow(PersistenceException.class)
                .when(userDao)
                .findById(user1.getId());

        Assert.assertThrows(ServiceLayerException.class, () -> {
            userService.findById(user1.getId());
        });
    }

    @Test
    public void findByUsernamePersistenceLayerFailed() {
        doThrow(PersistenceException.class)
                .when(userDao)
                .findByUsername(user1.getUsername());

        Assert.assertThrows(ServiceLayerException.class, () -> {
            userService.findByUsername(user1.getUsername());
        });
    }

    @Test
    public void findByEmailAddressPersistenceLayerFailed() {
        doThrow(PersistenceException.class)
                .when(userDao)
                .findByEmailAddress(user1.getEmailAddress());

        Assert.assertThrows(ServiceLayerException.class, () -> {
            userService.findByEmailAddress(user1.getEmailAddress());
        });
    }

    @Test
    public void authenticatePersistenceLayerFailed() {
        doThrow(PersistenceException.class)
                .when(userDao)
                .findById(user1.getId());

        Assert.assertThrows(ServiceLayerException.class, () -> {
            userService.authenticate(user1, PASSWORD1);
        });
    }

    @Test
    public void isAdminPersistenceLayerFailed() {
        doThrow(PersistenceException.class)
                .when(userDao)
                .findById(user1.getId());

        Assert.assertThrows(ServiceLayerException.class, () -> {
            userService.isAdmin(user1);
        });
    }

    @Test
    public void isDisabledNullUser() {
        Assert.assertThrows(IllegalArgumentException.class, () -> {
            userService.isDisabled(null);
        });
    }

    @Test
    public void disableNullUser() {
        Assert.assertThrows(IllegalArgumentException.class, () -> {
            userService.disable(null);
        });
    }

    @Test
    public void updateNullUser() {
        Assert.assertThrows(IllegalArgumentException.class, () -> {
            userService.update(null);
        });
    }

    @Test
    public void isDisabledPersistenceLayerFailed() {
        doThrow(PersistenceException.class)
                .when(userDao)
                .findById(user1.getId());

        Assert.assertThrows(ServiceLayerException.class, () -> {
            userService.isDisabled(user1);
        });
    }

    @Test
    public void disablePersistenceLayerFailed() {
        doThrow(PersistenceException.class)
                .when(userDao)
                .update(user1);

        Assert.assertThrows(IllegalArgumentException.class, () -> {
            userService.disable(user1);
        });
    }

    @Test
    public void updatePersistenceLayerFailed() {
        doThrow(PersistenceException.class)
                .when(userDao)
                .update(user1);

        Assert.assertThrows(IllegalArgumentException.class, () -> {
            userService.update(user1);
        });
    }

    @Test
    public void isAdminUserNotInDatabase() {
        User user = new User("John", "john@mail.com");

        Assert.assertThrows(IllegalArgumentException.class, () -> {
            userService.isAdmin(user);
        });
    }

    @Test
    public void isDisabledUserNotInDatabase() {
        User user = new User("John", "john@mail.com");

        Assert.assertThrows(IllegalArgumentException.class, () -> {
            userService.isDisabled(user);
        });
    }

    @Test
    public void disableUserNotInDatabase() {
        User user = new User("John", "john@mail.com");

        Assert.assertThrows(IllegalArgumentException.class, () -> {
            userService.disable(user);
        });
    }

    @Test
    public void updateUserNotInDatabase() {
        User user = new User("John", "john@mail.com");

        Assert.assertThrows(IllegalArgumentException.class, () -> {
            userService.update(user);
        });
    }

    @AfterClass
    public void releaseMocks() throws Exception {
        closable.close();
    }
}
