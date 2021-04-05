package domain.service;

import java.util.List;
// arraylist
import java.util.ArrayList;
// iterator
import java.util.Iterator;

import domain.model.Group;

public class GroupServiceImpl implements GroupService{
    private List<Group> groups=new ArrayList<>(); // temporary, no DB for the moment..

    // init list..
    public GroupServiceImpl() {
        // https://www.journaldev.com/33297/java-list-add-addall-methods
        groups.add(new Group("1")); // id : 1
        groups.add(new Group("2"));
        groups.add(new Group("3"));

        groups.add(new Group("42"));
    }

    public List<Group> getAllGroups(){
        return groups;
    }

    public Group getGroup(String id){
        // need to find the group then return it, the code will be replaced later when we'll have DB
        // we suppose id's are unique.

        // https://www.tutorialspoint.com/How-to-iterate-over-a-Java-list
        // https://crunchify.com/how-to-iterate-through-java-list-4-way-to-iterate-through-loop/
        for (int i = 0; i < groups.size(); i++) {
            if (groups.get(i).getId().equals(id)){ // comparison of strings
                return groups.get(i);
            }
        }

        /*
        Iterator<Group> iterator = groups.iterator();
        while(iterator.hasNext()) {
            if (iterator.next().getId() == id){
                return iterator.next();
            }
        }
        */
        // should do something when not in the list..
        return null;
    }
}