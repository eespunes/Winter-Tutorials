package cat.tecnocampus.omega.persistanceController;

import cat.tecnocampus.omega.domain.User;
import cat.tecnocampus.omega.persistance.UserDAO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("UserController")
public class UserController {

    private final UserDAO userDAO;

    public UserController(UserDAO userDAO){
        this.userDAO = userDAO;
    }
    @Transactional
    public int addUser(User user) {
        return userDAO.insertUser(user);
    }

    public List<User> findAll() {
        return userDAO.findAll();
    }

    public User getUser(String username) {
        return userDAO.findByUsername(username);
    }

    public int deleteUser(User user) {
        return userDAO.deleteUser(user);
    }
}
