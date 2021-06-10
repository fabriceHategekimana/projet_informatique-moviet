package domain.service;

import domain.model.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers(); // not called by the API, used for testing purposes (it has it's own tests)

    User getUser(String id); // called by GET request

    User createUser(User user); // called by POST request

    User updateUser(User user); // called by PUT request

    User deleteUser(String id); // called by DELETE request
}
