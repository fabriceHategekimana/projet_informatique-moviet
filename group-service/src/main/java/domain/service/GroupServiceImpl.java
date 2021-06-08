package domain.service;

import java.util.*;

import domain.model.*;

import javax.enterprise.context.ApplicationScoped; // ApplicationScoped ~singleton
import lombok.NonNull;
import lombok.extern.java.Log;

// JPA
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.transaction.Transactional;  // needed otherwise TransactionRequiredException will be thrown
// See https://www.baeldung.com/jpa-hibernate-persistence-context for more informations
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;
import javax.persistence.NoResultException;


@Log // lombok log
@ApplicationScoped
public class GroupServiceImpl implements GroupService{
    /*
    We use null as return when there's an error. The HTTP code associated to them are written in GroupRestService.
    If errors we did not catch, we'll have error 500.. we'll have problems if id's can be null

    If no error, return the group or Set of groups.
    */

    // container-managed entity manager
    @PersistenceContext(unitName = "GroupPU") // name is the same as in persistence.xml file
    private EntityManager em;

    /*
    https://stackoverflow.com/questions/10740021/transactionalpropagation-propagation-required
    https://stackoverflow.com/questions/11746499/how-to-solve-the-failed-to-lazily-initialize-a-collection-of-role-hibernate-ex
     */
    @Transactional
    public Set<Group> getAllGroups() {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Group> criteria = builder.createQuery(Group.class);
        // One solution was to use FetchType EAGER but we prefer not to use it
        Root<Group> root = criteria.from(Group.class);
        root.fetch("users", JoinType.LEFT);
        criteria.select(root);

        // https://www.netsurfingzone.com/hibernate/failed-to-lazily-initialize-a-collection-of-role-could-not-initialize-proxy-no-session/
        // https://www.logicbig.com/tutorials/java-ee-tutorial/jpa/criteria-api-fetch-joins.html
        return new HashSet<>(em.createQuery(criteria).getResultList());
    }

    @Transactional
    public Status getGroupStatus(int group_id){
        Group group = getGroup(group_id);  // group becomes managed as well as existing users in the group
        if ((group == null) || (group.getUsers() == null)){
            return null; // not found group.. or no user meaning that we cannot get the status of an user..
        }
        // we know that the group exists and that there are users up to this point
        return group.getGroup_status();
    }

    // find by ID, names are not unique
    @Transactional
    public Group getGroup(int group_id){
        /* Need to find the group then return it, Id's are unique
        if not in the Set return null, the Rest Service will take care of returning some HTTP code (404 not found here)
        https://docs.oracle.com/javaee/7/api/javax/persistence/EntityManager.html#find-java.lang.Class-java.lang.Object-
        */
        if (group_id == 0){
            return null;
        }
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Group> criteria = builder.createQuery( Group.class );

        Root<Group> root = criteria.from(Group.class);
        root.fetch("users", JoinType.LEFT);
        criteria.select(root);
        criteria.where(builder.equal(root.get("id"), group_id));
        // https://www.initgrep.com/posts/java/jpa/select-values-in-criteria-queries
        Group group;
        try {
            group = em.createQuery(criteria).getSingleResult();  // group becomes managed
        }
        catch (NoResultException nre){
            group = null; // null if not found, 404
        }
        return group; // null if not found, 404
    }

    private User getUser(String user_id){
        // user id can be 0
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<User> criteria = builder.createQuery( User.class );

        Root<User> root = criteria.from(User.class);
        root.fetch("groups", JoinType.LEFT);
        criteria.select(root);
        criteria.where(builder.equal(root.get("id"), user_id));
        // https://www.initgrep.com/posts/java/jpa/select-values-in-criteria-queries
        User user;
        try {
            user = em.createQuery(criteria).getSingleResult();  // user becomes managed
        }
        catch (NoResultException nre){
            user = null; // null if not found, 404
        }
        return user; // null if not found, 404
    }

    private GroupUser getGroupUser(int group_id, String user_id){
        // user id can be 0
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<GroupUser> criteria = builder.createQuery( GroupUser.class );

        Root<GroupUser> root = criteria.from(GroupUser.class);
        criteria.select(root);
        criteria.where(builder.and(builder.equal(root.get("group_id"), group_id), builder.equal(root.get("user_id"), user_id)));
        // https://www.initgrep.com/posts/java/jpa/select-values-in-criteria-queries
        GroupUser groupUser;
        try {
            groupUser = em.createQuery(criteria).getSingleResult();  // user becomes managed
        }
        catch (NoResultException nre){
            groupUser = null; // null if not found, 404
        }
        return groupUser; // null if not found, 404
    }

    // create a group using only the name
    @Transactional
    public Group createGroup(@NonNull Group group){
        /*
        Can always create but no id should be entered and need at least a name..
        auto increment of unique identifier / primary key
         */
        if ((group.getId() != 0)|| (group.getName() == null)){ // if non initialized.
            // Actually if we do not check. SQL will throw an error because NOT NULL for the attribute in the table
            // To put Only other attributes than id are (must be) initialized: " + group in the logs
            return null;
        }
        if (group.getUsers() == null){
            group.setUsers(new HashSet<>()); // empty set, admin id 0 by default too
        }
        // persist group without users then add users afterwards
        Group tmpgroup = new Group(group.getName());
        tmpgroup.setAdmin_id(group.getAdmin_id());
        em.persist(tmpgroup);  // tmpgroup becomes managed

        for (User user: group.getUsers()){
            // flushes implicitely each time, not really good in performance because doing tons of SQL queries
            Group g = addUserToGroup(tmpgroup.getId(), user);
            if (g == null){
                return null;
            }
        }
        em.merge(tmpgroup);
        return tmpgroup;
    }

    @Transactional
    public Group addUserToGroup(int group_id, @NonNull User user){
        Group group = getGroup(group_id);  // group becomes managed
        if (group == null){
            return null; // not found group..
        }
        if (group.getUsers() == null){
            group.setUsers(new HashSet<>()); // empty set
        }
        for (User usr: group.getUsers()){
            if (usr.getId().equals(user.getId())){
                // user id already in group
                em.merge(user);
                em.merge(group);
                return group;
            }
        }
        // user was not in the group
        if (getUser(user.getId()) == null){
            // user did not exist, create user first
            em.persist(user);
        }
        else{ // user already exists, merge user
            em.merge(user);
        }
        group.addUser(user);  // add user to the group
        // Group need to exist.
        em.merge(group);

        // Also update status
        GroupUser gU = getGroupUser(group_id, user.getId());
        gU.setUser_status(Status.CHOOSING);
        em.merge(gU);
        return group;
    }

    @Transactional
    public Group removeUserFromGroup(int group_id, String user_id){
        // Find group
        Group group = getGroup(group_id); // group becomes managed
        if (group == null){
            return null; // not found group..
        }
        if (group.getUsers() == null){
            group.setUsers(new HashSet<>()); // empty set
            return null; // cannot remove user if no users in the group
        }
        // Find user
        User user = getUser(user_id); // user becomes managed
        if (user == null){
            return null; // not found user (if found user, need to verify if this user in the group)..
        }
        if (user.getGroups() == null){
            user.setGroups(new HashSet<>()); // empty set
            return null; // user not in a group.. so cannot remove user from the group
        }
        GroupUser gU;
        // Have to check if user in the group, is yes, can remove it, otherwise return null
        for (User usr: group.getUsers()){
            if (usr.getId().equals(user.getId())){
                // user id in the group ! so we can remove him
                // remove keywords and genres
                gU = getGroupUser(group_id, user.getId());
                gU.setKeywords_id(null);
                gU.setGenres_id(null);

                group.removeUser(user);  // user can still exist, just removed from the group
                em.merge(group);
                return group;
            }
        }
        return null; // user not in group
    }

    @Transactional
    public Group updateGroup(@NonNull Group group){
        /*
        Update directly the whole group.. can even remove users !
         */
        Group g = getGroup(group.getId());  // g becomes managed as well as existing users in the group
        if (g == null){
            return null; // not found group..
        }
        if (group.getUsers() == null){
            group.setUsers(new HashSet<>()); // empty set
        }
        Set<String> user_ids_already_in_group = new HashSet<>();

        for (User user: group.getUsers()){
            if (user_ids_already_in_group.contains(user.getId())){  // if id already in group, we merge
                em.merge(user);
                em.merge(group);
                continue;
            }
            if (getUser(user.getId()) == null){
                // user did not exist, create user first
                em.persist(user); // user was not managed, it becomes managed
            }
            else{ // user already exists, merge user
                em.merge(user);
            }
            user.addGroup(group);
            user_ids_already_in_group.add(user.getId());
            em.merge(group);
            // Also update status
            GroupUser gU = getGroupUser(group.getId(), user.getId());
            gU.setUser_status(Status.CHOOSING);
            em.merge(gU);
        }
        // Group need to exist.
        em.merge(group);
        return group;
    }

    @Transactional // Integer of user id
    public Map<String,Status> getAllUserStatus(int group_id){
        Group group = getGroup(group_id);  // group becomes managed as well as existing users in the group
        if (group == null){
            return null; // not found group..
        }
        // we know that the group exists and that there can be no users
        Map<String,Status> out = new HashMap<>();
        for (User user : group.getUsers()) {
            GroupUser gU = getGroupUser(group_id, user.getId());
            out.put(user.getId(), gU.getUser_status());
        }
        return out; // can be empty
    }


    @Transactional
    public Status getUserStatus(int group_id, String user_id){
        Group group = getGroup(group_id);  // group becomes managed as well as existing users in the group
        if ((group == null) || (group.getUsers() == null)){
            return null; // not found group.. or no user meaning that we cannot get the status of an user..
        }
        // we know that the group exists and that there are users up to this point
        GroupUser groupUser = getGroupUser(group_id, user_id); // groupUser becomes managed
        if (groupUser == null){
            return null; // particular user not found
        }
        return groupUser.getUser_status();
    }

    @Transactional
    public Status updateUserStatus(int group_id, String user_id, String status){
        /*
        Update status of an user who is in a group, should also handle the case !

        When one user status is changed to CHOOSING, everyone else changes to CHOOSING used for reset..
         */
        Group group = getGroup(group_id);  // group becomes managed as well as existing users in the group
        if ((group == null) || (group.getUsers() == null)){
            return null; // not found group.. or no user meaning that we cannot update the status of an user..
        }
        // we know that the group exists and that there are users up to this point
        GroupUser groupUser = getGroupUser(group_id, user_id); // groupUser becomes managed so we can modify status directly !
        if (groupUser == null){  // particular user not found..
            return null;
        }
        String cannot_change_msg = "Cannot change status of user_id=" + user_id + " and group_id=" + group_id + " to status=" + status;

        /* verify if request tries to bypass some status.. the most important are:
            - need everyone choosing or ready before changing status to ready
            - need everyone ready or voting before changing status to voting
         */
        boolean all_ready=true;
        boolean all_done=true;
        for (User user : group.getUsers()){
            if (!user_id.equals(user.getId())){
                GroupUser gU = getGroupUser(group_id, user.getId());
                if (status.equalsIgnoreCase("READY")) {
                    all_done=false;
                    // check if everyone else in CHOOSING or READY, otherwise cannot update status !
                    if (!(gU.getUser_status().equals(Status.CHOOSING)) && !(gU.getUser_status().equals(Status.READY))) {
                        log.severe(cannot_change_msg + " because other users have status VOTING OR DONE");
                        throw new IllegalArgumentException(cannot_change_msg + " because other users have status VOTING OR DONE");
                    }
                    if (!gU.getUser_status().equals(Status.READY)){
                        all_ready=false;
                    }
                }
                else if (status.equalsIgnoreCase("VOTING")){
                    all_ready=false;
                    all_done=false;
                    // check if everyone else in READY or VOTING, otherwise cannot update status !
                    if (!(gU.getUser_status().equals(Status.READY)) && !(gU.getUser_status().equals(Status.VOTING))){
                        log.severe(cannot_change_msg + " because other users have status DONE or still CHOOSING");
                        throw new IllegalArgumentException(cannot_change_msg + " because other users have status DONE or still CHOOSING");
                    }
                }
                else if (status.equalsIgnoreCase("DONE")){
                    all_ready=false;
                    // check if everyone else in VOTING OR DONE, otherwise cannot update status !
                    if (!(gU.getUser_status().equals(Status.VOTING)) && !(gU.getUser_status().equals(Status.DONE))){
                        log.severe(cannot_change_msg + " because other users have status still CHOOSING or READY");
                        throw new IllegalArgumentException(cannot_change_msg + " because other users have status still CHOOSING or READY");
                    }
                    if (!gU.getUser_status().equals(Status.DONE)){
                        all_done=false;
                    }
                }
                else if (status.equalsIgnoreCase("CHOOSING")){
                    // TODO, to remove this part and add reset method hm, and use all_ready and all_done false here again
                    Group groupTmp = getGroup(group_id);  // group becomes managed as well as existing users in the group

                    // set all to status CHOOSING if all users in the group were ready
                    for (User userTmp : groupTmp.getUsers()) {
                        GroupUser gU2 = getGroupUser(group_id, userTmp.getId());
                        gU2.setUser_status(Status.CHOOSING);
                        em.merge(gU2);
                    }

                    // set group status to CHOOSING directly..
                    groupTmp = getGroup(group_id);  // group becomes managed as well as existing users in the group
                    groupTmp.setGroup_status(Status.CHOOSING);
                    em.merge(groupTmp);
                    return Status.CHOOSING;
                }
            }
        }
        if (all_ready){
            // set all to status VOTING if all users in the group were ready
            for (User user : group.getUsers()) {
                GroupUser gU = getGroupUser(group_id, user.getId());
                gU.setUser_status(Status.VOTING);
                em.merge(gU);
            }
            // set group status to VOTING, the group status can never be READY actually.
            group = getGroup(group_id);  // group becomes managed as well as existing users in the group
            group.setGroup_status(Status.VOTING);
            em.merge(group);
            status = "voting";
        }
        else if (all_done){
            groupUser.setUser_status(Status.valueOf(status.toUpperCase())); // https://www.tutorialspoint.com/how-to-convert-a-string-to-an-enum-in-java
            em.merge(groupUser);
            // set group status to DONE
            group = getGroup(group_id);  // group becomes managed as well as existing users in the group
            group.setGroup_status(Status.DONE);
            em.merge(group);
        }
        else{
            groupUser.setUser_status(Status.valueOf(status.toUpperCase())); // https://www.tutorialspoint.com/how-to-convert-a-string-to-an-enum-in-java
            em.merge(groupUser);
        }
        return Status.valueOf(status.toUpperCase());
    }

    @Transactional
    public Status skipAllUserStatus(int group_id){
        /*
        Change all status of users in the group group_id to VOTING if user status were CHOOSING or READY or to DONE if user status were VOTING or DONE.

        Also changes the group status !
         */
        Group group = getGroup(group_id);  // group becomes managed as well as existing users in the group
        if (group == null){
            return null; // not found group..
        }
        // we suppose all user status are modified by updateUserStatus
        boolean skipToDone = false; // will skip all to VOTING
        for (User user : group.getUsers()) {
            GroupUser gU = getGroupUser(group_id, user.getId());
            if ((gU.getUser_status() == Status.VOTING) || (gU.getUser_status() == Status.DONE)){
                skipToDone = true;
            }
        }
        Status newStatus;
        if (!skipToDone){
            newStatus = Status.VOTING;
        }
        else{
            newStatus = Status.DONE;
        }

        // we know that the group exists and that there are users up to this point
        for (User user : group.getUsers()) {
            GroupUser gU = getGroupUser(group_id, user.getId());
            gU.setUser_status(newStatus);
            em.merge(gU);
        }

        group = getGroup(group_id);  // group becomes managed as well as existing users in the group
        group.setGroup_status(newStatus);
        em.merge(group);
        return newStatus;
    }

    @Transactional
    public boolean updateMoviePreferences(int group_id, String user_id, MoviePreferences movie_preferences){
        Group group = getGroup(group_id);  // group becomes managed as well as existing users in the group
        if ((group == null) || (group.getUsers() == null)){
            return false; // not found group.. or no user meaning that we cannot get the status of an user..
        }
        // we know that the group exists and that there are users up to this point
        GroupUser groupUser = getGroupUser(group_id, user_id); // groupUser becomes managed
        if (groupUser == null){
            return false; // particular user not found
        }
        log.info("Trying to update movies preferences to " + movie_preferences);
        groupUser.setYear_range(new YearRange(movie_preferences.getYear_from(), movie_preferences.getYear_to()));
        groupUser.setKeywords_id(movie_preferences.getKeywords_id());
        groupUser.setGenres_id(movie_preferences.getGenres_id());
        em.merge(groupUser);
        return true;
    }

    @Transactional
    public MoviePreferences getMoviePreferences(int group_id, String user_id){
        Group group = getGroup(group_id);  // group becomes managed as well as existing users in the group
        if ((group == null) || (group.getUsers() == null)){
            return null; // not found group.. or no user meaning that we cannot get the status of an user..
        }
        // we know that the group exists and that there are users up to this point
        GroupUser groupUser = getGroupUser(group_id, user_id); // groupUser becomes managed
        if (groupUser == null){
            return null; // particular user not found
        }
        Integer year_from = null;
        Integer year_to = null;
        if (groupUser.getYear_range() != null){
            year_from = groupUser.getYear_range().getYear_from();
            year_to = groupUser.getYear_range().getYear_to();
        }
        return new MoviePreferences(groupUser.getKeywords_id(), groupUser.getGenres_id(), year_from, year_to);
    }

    @Transactional
    public Group deleteGroup(int group_id){
        Group group = getGroup(group_id);  // group becomes managed as well as users in the group
        if (group == null){
            return null;
        }
        // Group need to exist.
        GroupUser gU;
        Iterator<User> it = group.getUsers().iterator();
        while (it.hasNext()){
            User user = it.next();
            log.info("user id : " + user.getId() + " is being removed from users");
            // remove keywords and genres
            gU = getGroupUser(group_id, user.getId());
            gU.setKeywords_id(null);
            gU.setGenres_id(null);

            it.remove();
            user.getGroups().remove(group);
            log.info("user id : " + user.getId() + " removed from users");
        }
        em.remove(group);
        return group;
    }
}