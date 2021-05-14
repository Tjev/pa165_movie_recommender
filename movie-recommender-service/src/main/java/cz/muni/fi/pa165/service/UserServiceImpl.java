package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.dao.UserDao;
import cz.muni.fi.pa165.entity.User;
import cz.muni.fi.pa165.exception.ServiceLayerException;
import javax.inject.Inject;
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

    @Inject
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public User register(User user, String password) {
        if (user == null) {
            throw new IllegalArgumentException("Movie parameter is null.");
        }

        validateStringParameter(password, "Password");

        String passwordHash = encoder.encode(password);
        user.setPasswordHash(passwordHash);

        try {
            return userDao.create(user);
        } catch (Exception e) {
            throw new ServiceLayerException("Error occurred while creating User.", e);
        }
    }

    @Override
    public List<User> findAll() {
        try {
            return userDao.findAll();
        } catch (Exception e) {
            throw new ServiceLayerException("Error occurred while retrieving all users.");
        }
    }

    @Override
    public User findById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Id parameter is null.");
        }
        try {
            return userDao.findById(id);
        } catch (Exception e) {
            throw new ServiceLayerException("Error occurred while retrieving persisted user by id.", e);
        }
    }

    @Override
    public User findByEmailAddress(String emailAddress) {
        validateStringParameter(emailAddress, "Email address");

        try {
            return userDao.findByEmailAddress(emailAddress);
        } catch (Exception e) {
            throw new ServiceLayerException("Error occurred while retrieving user by email address.", e);
        }
    }

    @Override
    public User findByUsername(String username) {
        validateStringParameter(username, "Username");

        try {
            return userDao.findByUsername(username);
        } catch (Exception e) {
            throw new ServiceLayerException("Error occurred while retrieving user by username.", e);
        }
    }

    @Override
    public Boolean authenticate(User user, String password) {
        if (user == null) {
            throw new IllegalArgumentException("User parameter is null.");
        }

        validateStringParameter(password, "Password");

        User persistedUser = findById(user.getId());
        return encoder.matches(password, persistedUser.getPasswordHash());
    }

    @Override
    public Boolean isAdmin(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User parameter is null.");
        }

        User persistedUser = findById(user.getId());
        return persistedUser.isAdmin();
    }

    @Override
    public User disable(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User parameter is null.");
        }

        User persistedUser = findById(user.getId());
        if (persistedUser == null) {
            throw new IllegalArgumentException("User is not in the database.");
        }

        persistedUser.setDisabled(true);
        try {
            return userDao.update(persistedUser);
        } catch (Exception e) {
            throw new ServiceLayerException("Error occurred while updating user.", e);
        }
    }

    @Override
    public Boolean isDisabled(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User parameter is null.");
        }

        User persistedUser = findById(user.getId());
        return persistedUser.isDisabled();
    }

    @Override
    public User update(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User parameter is null.");
        }

        User persistedUser = findById(user.getId());
        if (persistedUser == null) {
            throw new IllegalArgumentException("User is not in the database.");
        }

        try {
            return userDao.update(user);
        } catch (Exception e) {
            throw new ServiceLayerException("Error occurred while updating user", e);
        }
    }

    private void validateStringParameter(String string, String paramName) {
        if (string == null) {
            throw new IllegalArgumentException((String.format("%s parameter is null.", paramName)));
        }

        if (string.isEmpty()) {
            throw new IllegalArgumentException(String.format("%s cannot be empty.", paramName));
        }
    }
}
