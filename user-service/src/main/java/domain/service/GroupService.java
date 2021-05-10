package domain.service;

import java.util.List;
import domain.model.Group;

public interface GroupService{
    public List<Group> getAllGroups(); // called by GET request
    public Group getGroup(int id); // called by GET request
    public Group createGroup(Group group); // called by POST request
    public Group updateGroup(Group group); // called by PUT request
    public Group deleteGroup(int id); // called by DELETE request
}