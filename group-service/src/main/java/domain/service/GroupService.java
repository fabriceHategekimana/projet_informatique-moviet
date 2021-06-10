package domain.service;

import java.util.Map;
import java.util.Set;
import domain.model.Group;
import domain.model.MoviePreferences;
import domain.model.User;
import domain.model.Status;

public interface GroupService{
    Set<Group> getAllGroups(); // called by GET request
    Group getGroup(int id); // called by GET request
    Group createGroup(Group group); // called by POST request
    Group addUserToGroup(int id, User user); // called by POST request
    Group removeUserFromGroup(int group_id, String user_id); // called by DELETE request
    Group updateGroup(Group group); // called by PUT request
    Map<String,Status> getAllUserStatus(int group_id); // called by GET request
    Status getUserStatus(int group_id, String user_id); // called by GET request
    Status updateUserStatus(int group_id, String user_id, String status); // called by PUT request
    Status skipAllUserStatus(int group_id); // called by PUT request
    Status getGroupStatus(int group_id); // called by GET request
    boolean updateMoviePreferences(int group_id, String user_id, MoviePreferences movie_preferences); // called by PUT request
    MoviePreferences getMoviePreferences(int group_id, String user_id); // called by GET request
    Group deleteGroup(int id); // called by DELETE request
}