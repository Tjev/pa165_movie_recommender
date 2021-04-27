package cz.muni.fi.pa165;

import cz.muni.fi.pa165.dao.UserDao;
import cz.muni.fi.pa165.entity.User;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


public class UserServiceTest {

    @Mock
    private UserDao userDao;

    private UserService userService;

    private AutoCloseable closable;

    @BeforeClass
    public void setup() {
        closable = MockitoAnnotations.openMocks(this);
        userService = new UserServiceImpl(userDao);
    }

    @Test
    public void registerUserTest() {
        User user = new User();
        String password = "123";

        userService.registerUser(user, password);

        verify(userDao, times(1)).create(user);
    }

    @AfterClass
    public void releaseMocks() throws Exception {
        closable.close();
    }
}
