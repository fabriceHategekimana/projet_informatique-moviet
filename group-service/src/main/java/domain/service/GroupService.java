package domain.service;

import java.util.List;
import domain.model.Group;

public interface GroupService{

    public List<Group> getAllGroups();
    public Group getGroup(String id);
    public void addGroup(String id);
}