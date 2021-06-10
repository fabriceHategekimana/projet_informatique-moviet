package domain.service;

import java.util.List;
import domain.model.User;

public interface UserService{
		List<User> getAllUsers(); // not called by the API, used for testing purposes (it has it's own tests)
    User getUser(String id); // called by GET request
    User createUser(User user); // called by POST request
    User updateUser(User user); // called by PUT request
    User deleteUser(String id); // called by DELETE request
}
