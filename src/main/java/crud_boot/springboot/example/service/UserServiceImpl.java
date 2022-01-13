package crud_boot.springboot.example.service;

import crud_boot.springboot.example.dao.UserDAO;
import crud_boot.springboot.example.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class UserServiceImpl implements UserService {

    private final UserDAO userDAO;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserDAO userDAO, PasswordEncoder passwordEncoder) {
        this.userDAO = userDAO;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User getUserById(int id) {
        return userDAO.getUserById(id);
    }
    @Override
    public List<User> getAllUsers() {
        return userDAO.getAllUsers();
    }
    @Override
    @Transactional
    public void saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDAO.saveUser(user);
    }
    @Override
    @Transactional
    public void deleteUser(User user) {
        userDAO.deleteUser(user);
    }
    @Override
    @Transactional
    public void updateUser(int id, User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDAO.updateUser(id, user);
    }

    @Override
    public User getUserByName(String name) {
        return userDAO.getUserByName(name);
    }

}
