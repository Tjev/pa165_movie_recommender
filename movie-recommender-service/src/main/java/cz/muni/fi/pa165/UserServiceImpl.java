package cz.muni.fi.pa165;

import cz.muni.fi.pa165.dao.UserDao;
import cz.muni.fi.pa165.entity.User;
import cz.muni.fi.pa165.exceptions.ServiceLayerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of the {@link UserService} interface. Handles business logic functions related
 * to the {@link User} entity class.
 *
 * @author Tomas Jevocin
 */
@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final PasswordEncoder encoder = new Argon2PasswordEncoder();

    @Autowired
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public void registerUser(User user, String password) {
        if (user == null) {
            throw new IllegalArgumentException("Movie parameter is null.");
        }

        if (password == null) {
            throw new IllegalArgumentException("Password parameter is null.");
        }

        if (password.isEmpty()) {
            throw new IllegalArgumentException("Password cannot be empty.");
        }

        String passwordHash = encoder.encode(password);
        user.setPasswordHash(passwordHash);

        try {
            userDao.create(user);
        } catch (Exception e) {
            throw new ServiceLayerException("Error occurred while creating User.", e);
        }
    }

    @Override
    public List<User> getAllUsers() {
        try {
            return userDao.findAll();
        } catch (Exception e) {
            throw new ServiceLayerException("Error occurred while retrieving all users.");
        }
    }

    @Override
    public User findUserById(Long id) {
        return getPersistedUserById(id);
    }

    @Override
    public User findUserByEmailAddress(String emailAddress) {
        if (emailAddress == null) {
            throw new IllegalArgumentException("Email address parameter is null.");
        }

        if (emailAddress.isEmpty()) {
            throw new IllegalArgumentException("Email address cannot be empty.");
        }
        try {
            return userDao.findByEmailAddress(emailAddress);
        } catch (Exception e) {
            throw new ServiceLayerException("Error occurred while retrieving user by email address.", e);
        }
    }

    @Override
    public User findUserByUsername(String username) {
        if (username == null) {
            throw new IllegalArgumentException("Username parameter is null.");
        }

        if (username.isEmpty()) {
            throw new IllegalArgumentException("Username address cannot be empty.");
        }
        try {
            return userDao.findByUsername(username);
        } catch (Exception e) {
            throw new ServiceLayerException("Error occurred while retrieving user by username.", e);
        }
    }

    @Override
    public boolean authenticate(User user, String password) {
        User persistedUser = getPersistedUserById(user.getId());
        return encoder.matches(password, persistedUser.getPasswordHash());
    }

    @Override
    public boolean isAdmin(User user) {
        User persistedUser = getPersistedUserById(user.getId());
        return persistedUser.isAdmin();
    }

    private User getPersistedUserById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Id parameter is null.");
        }
        try {
            return userDao.findById(id);
        } catch (Exception e) {
            throw new ServiceLayerException("Error occurred while retrieving persisted user by id.", e);
        }
    }
}
