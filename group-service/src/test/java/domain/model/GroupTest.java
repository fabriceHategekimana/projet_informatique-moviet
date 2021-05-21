package domain.model;

// Unit/Component testing using JUnit 5
// https://junit.org/junit5/docs/current/user-guide/#writing-tests
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

// just a test for unit testing
class GroupTest {
    private final Group group = new Group("hello world");

    @Test
    void testGetId(){
        assertEquals(0, group.getId() );
    }
    
    @Test
    void testGetName(){
        assertEquals("hello world", group.getName() );
    }

    @Test
    void testSetName(){
        Group tmpgroup = new Group("hello world");
        tmpgroup.setName("hello universe");
        assertEquals("hello universe", tmpgroup.getName() );
    }

    @Test
    void testGetIdNoArgsConstructor(){
        assertEquals(0, new Group().getId() );
    }

    @Test
    void testGetNameNoArgsConstructor(){
        assertEquals(null, new Group().getName() );
    }

    @Test
    void testToString(){
        assertEquals("Group(id=0, name=hello world, admin_id=0, users=[])", group.toString() );
    }
    // fail a test -> Build failure
}