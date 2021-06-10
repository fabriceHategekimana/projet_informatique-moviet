package domain.model;

// Unit/Component testing using JUnit 5
// https://junit.org/junit5/docs/current/user-guide/#writing-tests

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

// just a test for unit testing
class GroupTest {
    private final Group group = new Group("hello world");

    @Test
    void testGetId() {
        assertEquals(0, group.getId());
    }
    
    @Test
    void testGetName(){
        assertEquals("hello world", group.getName() );
    }

    @Test
    void testGetAdmin_id(){
        assertEquals("0", group.getAdmin_id());
    }

    @Test
    void testGetUsers(){
        assertEquals(0, new Group().getUsers().size() );
    }

    @Test
    void testGetGroup_status(){
        assertEquals(Status.CHOOSING, new Group().getGroup_status());
    }

    @Test
    void testSetName(){
        Group tmpgroup = new Group("hello world");
        tmpgroup.setName("hello universe");
        assertEquals("hello universe", tmpgroup.getName() );
    }

    @Test
    void testSetAdmin_id() {
        Group tmpgroup = new Group("hello world");
        tmpgroup.setAdmin_id("10");
        assertEquals("10", tmpgroup.getAdmin_id());
    }

    @Test
    void testSetUsers() {
        Group tmpgroup = new Group("hello world");
        User usr1 = new User("1");
        User usr2 = new User("2");
        Set<User> users = new HashSet<>();
        users.add(usr1);
        users.add(usr2);

        tmpgroup.setUsers(users);

        assertEquals(users, tmpgroup.getUsers());
    }

    @Test
    void testAddUsers() {
        Group tmpgroup = new Group("hello world");
        User usr1 = new User("1");
        User usr2 = new User("2");
        Set<User> users = new HashSet<>();
        users.add(usr1);
        users.add(usr2);

        tmpgroup.addUser(usr1);
        tmpgroup.addUser(usr2);

        assertEquals(users, tmpgroup.getUsers());
    }

    @Test
    void testRemoveUsers() {
        Group tmpgroup = new Group("hello world");
        User usr1 = new User("1");
        User usr2 = new User("2");
        Set<User> users = new HashSet<>();
        users.add(usr1);
        users.add(usr2);

        tmpgroup.addUser(usr1);
        tmpgroup.addUser(usr2);

        assertEquals(users, tmpgroup.getUsers());

        tmpgroup.removeUser(usr1);
        tmpgroup.removeUser(usr2);

        assertEquals(0, tmpgroup.getUsers().size() );
    }

    @Test
    void testSetGroup_status() {
        Group grp = new Group();
        grp.setGroup_status(Status.READY);
        assertEquals(Status.READY, grp.getGroup_status());
    }

    @Test
    void testGetIdNoArgsConstructor(){
        assertEquals(0, new Group().getId() );
    }

    @Test
    void testGetNameNoArgsConstructor(){
        assertNull(new Group().getName() );
    }

    @Test
    void testGetAdmin_idNoArgsConstructor(){
        assertEquals("0", new Group().getAdmin_id());
    }

    @Test
    void testGetUsersNoArgsConstructor(){
        assertEquals(0, new Group().getUsers().size() );
    }

    @Test
    void testToString(){
        assertEquals("Group(id=0, name=hello world, admin_id=0, users=[], group_status=CHOOSING)", group.toString() );
    }
}