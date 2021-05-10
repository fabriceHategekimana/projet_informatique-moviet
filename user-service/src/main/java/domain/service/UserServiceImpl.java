package domain.service;

import java.util.List;

import domain.model.User;

import javax.enterprise.context.ApplicationScoped; // ApplicationScoped ~singleton
import lombok.NonNull;

// JPA
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.transaction.Transactional;  // needed otherwise TransactionRequiredException will be thrown
// See https://www.baeldung.com/jpa-hibernate-persistence-context for more informations


@ApplicationScoped
public class UserServiceImpl implements UserService{
    /*
    We use null as return when there's an error. The HTTP code associated to them are written in UserRestService.
    If errors we did not catch, we'll have error 500.. we'll have problems if id's can be null

    If no error, return the user or list of users.
    */

    // container-managed entity manager
    @PersistenceContext(unitName = "UserPU") // name is the same as in persistence.xml file
    private EntityManager em;


    public List<User> getAllUsers(){
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<User> criteria = builder.createQuery( User.class );
        criteria.from(User.class);
        return em.createQuery( criteria ).getResultList();
    }

    // find by ID, names are not unique
    public User getUser(int id){
        /* Need to find the user then return it, Id's are unique
        if not in the list return null, the Rest Service will take care of returning some HTTP code (404 not found here)
        https://docs.oracle.com/javaee/7/api/javax/persistence/EntityManager.html#find-java.lang.Class-java.lang.Object-
        */
        return em.find(User.class, id); // null if not found, 404
    }

    // create a user using only the name
    @Transactional
    public User createUser(@NonNull User user){
        /*
        Can always create.. no restriction due to auto increment of unique identifier / primary key
         */
        if ((user.getId() != 0) || (user.getFirstName() == null) || (user.getLastName() == null)){ // if non initialized.
            // Actually if we do not check. SQL will throw an error because NOT NULL for the attributein the table
            // To put Only other attributes than id are (must be) initialized: " + user in the logs
            return null;
        }
        em.persist(user);
        return user;
    }

    @Transactional
    public User updateUser(@NonNull User user){
        // User need to has a non null id.
        User g = em.find(User.class, user.getId());
        if (g == null) {
            return null; // error 404 not found in the user
        }
        em.merge(user);
        return user;
    }

    @Transactional
    public User deleteUser(int id){
        User user = em.find(User.class, id);
        if (user == null) {
            return null; // user does not exist, return null -> will be HTTP status code 404 not found
        }
        em.remove(user);
        return user;
    }
}
