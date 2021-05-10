package domain.service;

import java.util.List;
import domain.model.User;

public interface UserService{
    public List<User> getAllUsers(); // called by GET request
    public User getUser(int id); // called by GET request
    public User createUser(User user); // called by POST request
    public User updateUser(User user); // called by PUT request
    public User deleteUser(int id); // called by DELETE request
}