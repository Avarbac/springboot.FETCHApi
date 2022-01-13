package crud_boot.springboot.example.service;

import crud_boot.springboot.example.models.User;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface UserService {

    User getUserById(int id);

    List<User> getAllUsers();

    void saveUser(User user);

    void deleteUser(User user);

    void updateUser(int id, User user);

    User getUserByName(String name);
}
