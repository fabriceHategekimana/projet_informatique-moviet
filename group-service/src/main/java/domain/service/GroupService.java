package domain.service;

import java.util.Set;
import domain.model.Group;
import domain.model.User;

public interface GroupService{
    public Set<Group> getAllGroups(); // called by GET request
    public Group getGroup(int id); // called by GET request
    public Group createGroup(Group group); // called by POST request
    public Group addUserToGroup(int id, User user); // called by PUT request
    public Group updateGroup(Group group); // called by PUT request
    public Group deleteGroup(int id); // called by DELETE request
}