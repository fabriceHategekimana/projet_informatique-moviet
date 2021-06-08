package domain.model;

// Unit/Component testing using JUnit 5
// https://junit.org/junit5/docs/current/user-guide/#writing-tests

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

// just a test for unit testing
class UserTest {
    private final User user = new User("1");

    @Test
    void testGetId(){
        assertEquals("1", user.getId() );
    }

    @Test
    void testGetGroups(){
        assertEquals(0, new User().getGroups().size() );
    }

    @Test
    void testSetGroups(){
        User tmpuser = new User("1");
        Group grp1 = new Group("hello");
        Group grp2 = new Group("world");
        Set<Group> groups = new HashSet<>();
        groups.add(grp1);
        groups.add(grp2);

        tmpuser.setGroups(groups);

        assertEquals(groups, tmpuser.getGroups() );
    }

    @Test
    void testAddGroups(){
        User tmpuser = new User("1");
        Group grp1 = new Group("hello");
        Group grp2 = new Group("world");
        Set<Group> groups = new HashSet<>();
        groups.add(grp1);
        groups.add(grp2);

        tmpuser.addGroup(grp1);
        tmpuser.addGroup(grp2);

        assertEquals(groups, tmpuser.getGroups() );
    }

    @Test
    void testRemoveGroups(){
        User tmpuser = new User("1");
        Group grp1 = new Group("hello");
        Group grp2 = new Group("world");
        Set<Group> groups = new HashSet<>();
        groups.add(grp1);
        groups.add(grp2);

        tmpuser.addGroup(grp1);
        tmpuser.addGroup(grp2);

        assertEquals(groups, tmpuser.getGroups() );

        tmpuser.removeGroup(grp1);
        tmpuser.removeGroup(grp2);

        assertEquals(0, tmpuser.getGroups().size() );
    }

    @Test
    void testGetIdNoArgsConstructor(){
        assertEquals("0", new User().getId() );
    }

    @Test
    void testGetUsersNoArgsConstructor(){
        assertEquals(0, new User().getGroups().size() );
    }

    @Test
    void testToString(){
        assertEquals("User(id=1, groups=[])", user.toString() );
    }
}