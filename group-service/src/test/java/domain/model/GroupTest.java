package domain.model;

// Unit/Component testing using JUnit 5
// https://junit.org/junit5/docs/current/user-guide/#writing-tests
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

// just a test for unit testing
public class GroupTest {
    private final Group group=new Group("1", "hello world");

    @Test // if forget to put this : test not launched
    void testGetId(){
        assertEquals("1", group.getId() );
    }

    @Test
    void testSetId(){
        group.setId("10");
        assertEquals("10", group.getId() );
    }

    @Test
    void testGetName(){
        assertEquals("hello world", group.getName() );
    }

    @Test
    void testSetName(){
        group.setName("hello universe");
        assertEquals("hello universe", group.getName() );
    }

    // fail a test -> Build failure
}