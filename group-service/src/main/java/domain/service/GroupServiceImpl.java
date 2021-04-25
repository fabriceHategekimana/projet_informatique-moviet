package domain.service;

import java.util.List;
// arraylist
import java.util.ArrayList;

import domain.model.Group;

import javax.enterprise.context.ApplicationScoped; // ApplicationScoped ~singleton
import lombok.NonNull;

// JPA
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import javax.persistence.EntityManagerFactory;

@ApplicationScoped
public class GroupServiceImpl implements GroupService{
    // TODO: DB + be careful about concurrency
    // private List<Group> groups=new ArrayList<>(); // temporary, no DB for the moment..

    //@PersistenceContext(unitName = "GroupPU") // name is the same as in persistence.xml file
    private EntityManager em;
    private final EntityManagerFactory emFactory;
    // TODO: when do I close : if ( emFactory != null ) emFactory.close(); ?

    /*
    We use null as return when there's an error. The HTTP code associated to them are written in GroupRestService.
    If errors we did not catch, we'll have error 500.. we'll have problems if id's can be null

    If no error, return the group or list of groups.
    */
    public GroupServiceImpl(){
        emFactory = Persistence.createEntityManagerFactory("GroupPU"); // name is the same as in persistence.xml file
    }


    public List<Group> getAllGroups(){
        try {
            em = emFactory.createEntityManager();
            CriteriaBuilder builder = em.getCriteriaBuilder();
            CriteriaQuery<Group> criteria = builder.createQuery( Group.class );
            criteria.from(Group.class);
            return em.createQuery( criteria ).getResultList();
        }
        finally {
            if ( em != null ) em.close();
            //if ( emFactory != null ) emFactory.close();
        }
    }

    // find by ID, names are not unique
    public Group getGroup(@NonNull int id){
        /* Need to find the group then return it, Id's are unique
        if not in the list return null, the Rest Service will take care of returning some HTTP code (404 not found here)
        https://docs.oracle.com/javaee/7/api/javax/persistence/EntityManager.html#find-java.lang.Class-java.lang.Object-
        */
        try {
            em = emFactory.createEntityManager();
            System.out.println(id);
            return em.find(Group.class, id); // null if not found, 404
        }
        finally {
            if ( em != null ) em.close();
        }
    }

    // create a group using only the name
    public Group createGroup(@NonNull Group group){
        /*
        Can always create.. no restriction
        TODO: return correctly group json ? maybe change the auto increment ?
        TODO: Can always create.. no restriction. The Rest Service will take care of returning some HTTP code, here CONFLICT 409 ?
         */
        try{
            em = emFactory.createEntityManager();
            EntityTransaction trans = em.getTransaction();
            trans.begin();

            em.persist(group);
            trans.commit();
            return group;
        }
        finally{
            if ( em != null ) em.close();
        }
    }

    public Group updateGroup(@NonNull Group group){
        // Group need to has a non null id.
        try{
            em = emFactory.createEntityManager();

            Group g = em.find(Group.class, group.getId());
            if (g == null) {
                return null; // error 404 not found in the group
            }

            EntityTransaction trans = em.getTransaction();
            trans.begin();

            em.merge(group);

            trans.commit();
            return group;
        }
        finally{
            if ( em != null ) em.close();
        }
    }

    public Group deleteGroup(@NonNull int id){
        try{
            em = emFactory.createEntityManager();

            Group group = em.find(Group.class, id);
            if (group == null) {
                return null; // group does not exist, return null -> will be HTTP status code 404 not found
            }

            EntityTransaction trans = em.getTransaction();
            trans.begin();

            em.remove(group);

            trans.commit();
            return group;
        }
        finally{
            if ( em != null ) em.close();
        }
    }
}