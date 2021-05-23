package domain.service;

import java.util.Set;
import domain.model.Group;
import domain.model.User;

public interface GroupService{
    Set<Group> getAllGroups(); // called by GET request
    Group getGroup(int id); // called by GET request
    Group createGroup(Group group); // called by POST request
    Group addUserToGroup(int id, User user); // called by PUT request
    Group removeUserFromGroup(int group_id, int user_id); // called by DELETE request
    Group updateGroup(Group group); // called by PUT request
    Group deleteGroup(int id); // called by DELETE request
}