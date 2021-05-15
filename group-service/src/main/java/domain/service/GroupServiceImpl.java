package domain.service;

import java.util.Set;
import java.util.HashSet;
import java.util.Iterator;

import domain.model.Group;
import domain.model.User;

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
import java.lang.Exception;

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
    public Set<Group> getAllGroups(){
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Group> criteria = builder.createQuery( Group.class );
        // One solution was to use FetchType EAGER but we prefer not to use it
        Root<Group> root = criteria.from(Group.class);
        root.fetch("users", JoinType.LEFT);
        criteria.select(root);

        // Set<Group> groups = em.createQuery( criteria ).getResultList();
        // https://www.netsurfingzone.com/hibernate/failed-to-lazily-initialize-a-collection-of-role-could-not-initialize-proxy-no-session/
        // https://www.logicbig.com/tutorials/java-ee-tutorial/jpa/criteria-api-fetch-joins.html
        return new HashSet<>(em.createQuery( criteria ).getResultList());
    }

    // find by ID, names are not unique
    @Transactional
    public Group getGroup(int id){
        /* Need to find the group then return it, Id's are unique
        if not in the Set return null, the Rest Service will take care of returning some HTTP code (404 not found here)
        https://docs.oracle.com/javaee/7/api/javax/persistence/EntityManager.html#find-java.lang.Class-java.lang.Object-
        */
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Group> criteria = builder.createQuery( Group.class );

        Root<Group> root = criteria.from(Group.class);
        root.fetch("users", JoinType.LEFT);
        criteria.select(root);
        criteria.where(builder.equal(root.get("id"), id));
        // https://www.initgrep.com/posts/java/jpa/select-values-in-criteria-queries
        Group group = null;
        try {
            group = em.createQuery(criteria).getSingleResult();
        }
        catch (NoResultException nre){
            group = null; // null if not found, 404
        }

        return group; // null if not found, 404
    }

    // create a group using only the name
    @Transactional
    public Group createGroup(@NonNull Group group){
        /*
        Can always create.. no restriction due to auto increment of unique identifier / primary key
         */
        if ((group.getId() != 0)|| (group.getName() == null)){ // if non initialized.
            // Actually if we do not check. SQL will throw an error because NOT NULL for the attributein the table
            // To put Only other attributes than id are (must be) initialized: " + group in the logs
            return null;
        }
        if (group.getUsers() == null){
            group.setUsers(new HashSet<User>()); // empty set
        }
        em.persist(group);
        return group;
    }

    @Transactional
    public Group addUserToGroup(int id, @NonNull User user){
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Group> criteria = builder.createQuery( Group.class );

        Root<Group> root = criteria.from(Group.class);
        root.fetch("users", JoinType.LEFT);
        criteria.select(root);
        criteria.where(builder.equal(root.get("id"), id));

        Group group = null;
        try {
            group = em.createQuery(criteria).getSingleResult();
        }
        catch (NoResultException nre){
            return null; // error 404 not found in the group
        }
        if (group.getUsers() == null){
            group.setUsers(new HashSet<User>()); // empty set
        }
        group.addUser(user);
        // Group need to exist.
        em.merge(group);
        return group;
    }

    @Transactional
    public Group updateGroup(@NonNull Group group){
        /*
        Update directly the whole group..
         */
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Group> criteria = builder.createQuery( Group.class );

        Root<Group> root = criteria.from(Group.class);
        root.fetch("users", JoinType.LEFT);
        criteria.select(root);
        criteria.where(builder.equal(root.get("id"), group.getId()));

        Group g = null;
        try {
            g = em.createQuery(criteria).getSingleResult();
        }
        catch (NoResultException nre){
            return null; // error 404 not found in the group
        }
        if (group.getUsers() == null){
            group.setUsers(new HashSet<User>()); // empty set
        }
        // Group need to exist.
        em.merge(group);
        return group;
    }

    @Transactional
    public Group deleteGroup(int id){
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Group> criteria = builder.createQuery( Group.class );

        Root<Group> root = criteria.from(Group.class);
        root.fetch("users", JoinType.LEFT);
        criteria.select(root);
        criteria.where(builder.equal(root.get("id"), id));

        Group group = null;
        try {
            group = em.createQuery(criteria).getSingleResult();
        }
        catch (NoResultException nre){
            return null; // group does not exist, return null -> will be HTTP status code 404 not found
        }
        // Group need to exist.
        Iterator<User> it = group.getUsers().iterator();
        while (it.hasNext()){
            User user = it.next();
            log.info("user id : " + user.getId() + " , username: " + user.getName() + " is being removed from users");
            it.remove();
            user.getGroups().remove(group);
            log.info("user id : " + user.getId() + " , username: " + user.getName() + " removed from users");
        }
        log.info("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
        em.remove(group);
        return group;
    }
}