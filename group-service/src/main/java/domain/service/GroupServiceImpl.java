package domain.service;

import java.util.List;

import domain.model.Group;

import javax.enterprise.context.ApplicationScoped; // ApplicationScoped ~singleton
import lombok.NonNull;

// JPA
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import javax.persistence.EntityTransaction;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.transaction.Transactional;  // needed otherwise TransactionRequiredException will be thrown
// See https://www.baeldung.com/jpa-hibernate-persistence-context for more informations


@ApplicationScoped
public class GroupServiceImpl implements GroupService{
    /*
    We use null as return when there's an error. The HTTP code associated to them are written in GroupRestService.
    If errors we did not catch, we'll have error 500.. we'll have problems if id's can be null

    If no error, return the group or list of groups.
    */

    // container-managed entity manager
    @PersistenceContext(unitName = "GroupPU") // name is the same as in persistence.xml file
    private EntityManager em;


    public List<Group> getAllGroups(){
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Group> criteria = builder.createQuery( Group.class );
        criteria.from(Group.class);
        return em.createQuery( criteria ).getResultList();
    }

    // find by ID, names are not unique
    public Group getGroup(@NonNull int id){
        /* Need to find the group then return it, Id's are unique
        if not in the list return null, the Rest Service will take care of returning some HTTP code (404 not found here)
        https://docs.oracle.com/javaee/7/api/javax/persistence/EntityManager.html#find-java.lang.Class-java.lang.Object-
        */
        return em.find(Group.class, id); // null if not found, 404
    }

    // create a group using only the name
    @Transactional
    public Group createGroup(@NonNull Group group){
        /*
        Can always create.. no restriction due to auto increment of unique identifier / primary key
         */
        if ((group.getId() != 0)|| (group.getName() == null)){ // if non initialized
            // to put Only other attributes than id are (must be) initialized: " + group in the logs
            return null;
        }
        em.persist(group);
        return group;
    }

    @Transactional
    public Group updateGroup(@NonNull Group group){
        // Group need to has a non null id.
        Group g = em.find(Group.class, group.getId());
        if (g == null) {
            return null; // error 404 not found in the group
        }
        em.merge(group);
        return group;
    }

    @Transactional
    public Group deleteGroup(@NonNull int id){
        Group group = em.find(Group.class, id);
        if (group == null) {
            return null; // group does not exist, return null -> will be HTTP status code 404 not found
        }
        em.remove(group);
        return group;
    }
}