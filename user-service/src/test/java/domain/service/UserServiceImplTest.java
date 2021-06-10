package domain.service;

import domain.service.UserServiceImpl;
import domain.model.User;

// Unit/Component testing using JUnit 5
// https://junit.org/junit5/docs/current/user-guide/#writing-tests
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import eu.drus.jpa.unit.api.JpaUnit;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(JpaUnit.class) // see documentation of dadrus jpa unit, junit 5
@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    /*
    Recall that tests are not always run with the same order as in the code..

    Create random users and :
    - check if can get all by counting how many
    - check if can get a particular user by getting all users and comparing a random user with a user obtained
    through getUser(id) (id obtained from the random chosen user)

    */
    // dependency injections
    @Spy
    @PersistenceContext(unitName = "UserPUTest") // name is the same as in persistence.xml file. Test database !
    EntityManager em;
    @InjectMocks
    private UserServiceImpl userServiceImpl;

    @Test
    void testGetAllUsers() {
        // Count the number of users after adding new users and compare it to previous # of users + number of users added
        int size = initDataStore();
        assertEquals(size, userServiceImpl.getAllUsers().size());
    }

    @Test
    void testGetUser() {
        initDataStore();  // create new users
        List<User> users = userServiceImpl.getAllUsers(); // get list of users through the business service
        int random_choice = (int) (Math.random() * users.size());
        String id = users.get(random_choice).getId(); // get the id through Java object ! (list of users)

        User usr= userServiceImpl.getUser(id); // get the specific user through the business service
        assertEquals(users.get(random_choice).getId(), usr.getId()); // check the ids
        assertEquals(users.get(random_choice).getUsername(), usr.getUsername());
    }

    @Test
    void testGetNonExistantUser() {
        assertNull(userServiceImpl.getUser(String.valueOf(Integer.MAX_VALUE))); // check if null (when we get a non existant user)
    }

    @Test
    void testCreateUser() { // TODO: test User input entered in createUser in Impl
        User user = getRandomUser();
        User returned_user = userServiceImpl.createUser(user);
        assertNotEquals("0", returned_user.getId()); // check if id not 0 (meaning that the id was incremented and initialized)
    }

    @Test
    void testCreateNullUser(){
        User usr = null;
        assertThrows(NullPointerException.class, ()-> userServiceImpl.createUser(usr)); // due to @NotNull annotation in Impl
    }


    @Test
    void testUpdateUserUsername() { // TODO: test User input entered in updateUser in Impl
        // create a user and modify its name
        userServiceImpl.createUser(getRandomUser());
        List<User> users = userServiceImpl.getAllUsers(); // get list of users through the business service
        User user = users.get(users.size() - 1);  // get last user

        assertNotNull(user);
        String id = user.getId();
        user.setUsername("XXX");
        userServiceImpl.updateUser(user);
        user = userServiceImpl.getUser(id);
        assertEquals("XXX", user.getUsername());
    }


    @Test
    void testUpdateNonExistantUser() {
        initDataStore();  // create new users
        List<User> users = userServiceImpl.getAllUsers(); // get list of users through the business service
        // delete the last one
        User old_user = users.get(users.size() - 1);  // get last user
        assertNotNull(old_user);
        String id = old_user.getId();
        userServiceImpl.deleteUser(id);
        User user = userServiceImpl.getUser(id);
        assertNull(user); // check that the user disappeared

        // old_user was deleted, try to update it
        assertNull(userServiceImpl.updateUser(old_user)); // due to @NotNull annotation in Impl
    }

    @Test
    void testUpdateNullUser() {
        User usr = null;
        assertThrows(NullPointerException.class, ()-> userServiceImpl.updateUser(usr)); // due to @NotNull annotation in Impl
    }

    @Test
    void testDeleteUser() {
        // create a user and then delete one of the users
        userServiceImpl.createUser(getRandomUser());
        List<User> users = userServiceImpl.getAllUsers(); // get list of users through the business service
        User user = users.get(users.size() - 1);  // get last user

        assertNotNull(user);
        String id = user.getId();
        userServiceImpl.deleteUser(id);
        user = userServiceImpl.getUser(id);
        assertNull(user); // check that the user disappeared
    }

    @Test
    void testDeleteNonExistantUser() {
        assertNull(userServiceImpl.deleteUser(String.valueOf(Integer.MAX_VALUE)));  // check that we "cannot delete" non existant user
    }


    private List<User> getUsers() {
        List<User> users = new ArrayList<>();
        long numberOfNewUsr = Math.round((Math.random() * 10)) + 5;
        for (int i = 0; i < numberOfNewUsr; i++) {
            users.add(getRandomUser());
        }
        return users;
    }

    private int initDataStore() {
        int size = userServiceImpl.getAllUsers().size();
        List<User> newUsers = getUsers();
        for (User u : newUsers) {
            em.persist(u);
        }
        return size + newUsers.size();
    }

    private User getRandomUser() {
	    return new User(UUID.randomUUID().toString(), UUID.randomUUID().toString());
    }
}
