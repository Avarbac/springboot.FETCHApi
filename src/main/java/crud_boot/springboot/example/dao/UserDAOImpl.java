package crud_boot.springboot.example.dao;

import crud_boot.springboot.example.models.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class UserDAOImpl implements UserDAO {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public User getUserById(int id) {
        return manager.find(User.class, id);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> getAllUsers() {
        return manager.createQuery("select distinct u from User u join fetch u.roles",
                        User.class)
                .getResultList();
    }

    @Override
    public void saveUser(User user) {
        manager.persist(user);
    }

    @Override
    public void deleteUser(User user) {
        manager.remove(user);
    }

    @Override
    public void updateUser(int id, User user) {
        User updatedUser = getUserById(id);
        updatedUser.setFirstName(user.getFirstName());
        updatedUser.setLastName(user.getLastName());
        updatedUser.setAge(user.getAge());
        user.setRoles(updatedUser.getRoles());
        manager.merge(updatedUser);
    }

    @Override
    public User getUserByName(String name) {
        return manager.createQuery("select u from User as u where u.firstName = :paramName", User.class)
                .setParameter("paramName", name)
                .getSingleResult();
    }
}