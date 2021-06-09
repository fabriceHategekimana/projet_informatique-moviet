package domain.service;

import java.util.List;
import domain.model.User;

public interface UserService{
    List<User> getAllUsers(); // called by GET request
    User getUser(String id); // called by GET request
    User createUser(User user); // called by POST request
    User updateUser(User user); // called by PUT request
    User deleteUser(String id); // called by DELETE request
}