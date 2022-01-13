package crud_boot.springboot.example.controllers;

import crud_boot.springboot.example.models.User;
import crud_boot.springboot.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AdminRESTController {

    private final UserService userService;

    @Autowired
    public AdminRESTController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public List<User> read () {
        return userService.getAllUsers();
    }

    @GetMapping("/users/{id}")
    public User getUserById(@PathVariable("id") int id) {
        return userService.getUserById(id);
    }

    @PostMapping("/users")
    public void create(@RequestBody User user) {
        userService.saveUser(user);
    }

    @PatchMapping("/users/{id}")
    public void update(@PathVariable("id") int id, @RequestBody User user) {
        userService.updateUser(id, user);
    }

    @DeleteMapping("/users/{id}")
    public void delete (@PathVariable("id") int id) {
        User user = userService.getUserById(id);
        userService.deleteUser(user);
    }

}
