package domain.service;

// https://www.journaldev.com/33297/java-list-add-addall-methods
import java.util.List;
// arraylist
import java.util.ArrayList;

import domain.model.Group;

public class GroupServiceImpl implements GroupService{

    public List<Group> getAllGroups(){
        // temporary, no DB for the moment..
        List<Group> groups=new ArrayList<>();
        groups.add(new Group(1)); // id : 1
        groups.add(new Group(2));
        groups.add(new Group(3));

        groups.add(new Group(42));
        return groups;
    }

}