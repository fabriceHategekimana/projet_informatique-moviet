package domain.service;

import java.util.List;
// arraylist
import java.util.ArrayList;

import domain.model.Group;

import javax.enterprise.context.ApplicationScoped; // ApplicationScoped ~singleton
import lombok.NonNull;

@ApplicationScoped
public class GroupServiceImpl implements GroupService{
    // TODO: DB + be careful about concurrency
    private List<Group> groups=new ArrayList<>(); // temporary, no DB for the moment..
    /*
    We use null as return when there's an error. The HTTP code associated to them are written in GroupRestService.
    If errors we did not catch, we'll have error 500.. we'll have problems if id's can be null

    If no error, return the group or list of groups.
    */

    // init list..
    public GroupServiceImpl() {
        // https://www.journaldev.com/33297/java-list-add-addall-methods
        groups.add(new Group("1", "erwan"));
        groups.add(new Group("2", "mohsen"));
        groups.add(new Group("3", "ethan"));
        groups.add(new Group("42","raphael"));

    }

    public List<Group> getAllGroups(){
        return groups;
    }

    public Group getGroup(@NonNull String id){
        /* Need to find the group then return it, the code will be replaced later when we'll have DB
           Moreoever, we suppose id's are unique */

        // https://www.tutorialspoint.com/How-to-iterate-over-a-Java-list
        // https://crunchify.com/how-to-iterate-through-java-list-4-way-to-iterate-through-loop/
        for (Group group : groups) {
            if (group.getId().equals(id)) { // comparison of strings
                return group;
            }
        }

        // if not in the list return null, the Rest Service will take care of returning some HTTP code (404 not found here)
        return null;
    }

    public Group createGroup(@NonNull Group group){
        // check if group in list of groups
        for (Group g : groups) {
            if (g.getId().equals(group.getId())) { // comparison of strings
                return null; // the Rest Service will take care of returning some HTTP code, here CONFLICT 409
            }
        }
        groups.add(group);
        return group;
    }

    public Group updateGroup(@NonNull Group group){ // not working yet
        // probably do not want to update the id, only update the name

        // find the group first, then update
        for (Group g : groups) {
            if (g.getId().equals(group.getId())) { // comparison of strings
                return group; // not modifying yet
            }
        }
        return null; // error 404 not found in the group
    }

    public Group deleteGroup(@NonNull String id){
        for (int index=0; index < groups.size(); index++) {
            if (groups.get(index).getId().equals(id)) { // comparison of strings
                Group returnedGroup=groups.get(index);
                groups.remove(index);
                return returnedGroup;
            }
        }
        // group does not exist, return null -> will be HTTP status code 404 not found
        return null;
    }
}