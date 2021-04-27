package cz.muni.fi.pa165;

import cz.muni.fi.pa165.dao.UserDao;
import cz.muni.fi.pa165.entity.User;
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
        String passwordHash = encoder.encode(password);
        user.setPasswordHash(passwordHash);
        userDao.create(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userDao.findAll();
    }

    @Override
    public User findUserById(Long id) {
        return userDao.findById(id);
    }

    @Override
    public User findUserByEmailAddress(String emailAddress) {
        return userDao.findByEmailAddress(emailAddress);
    }

    @Override
    public User findUserByUsername(String username) {
        return userDao.findByUsername(username);
    }

    @Override
    public boolean authenticate(User user, String password) {
        User persistedUser = userDao.findById(user.getId());
        return encoder.matches(password, persistedUser.getPasswordHash());
    }

    @Override
    public boolean isAdmin(User user) {
        User persistedUser = userDao.findById(user.getId());
        return persistedUser.isAdmin();
    }
}
