package domain.model;

// Unit/Component testing using JUnit 5
// https://junit.org/junit5/docs/current/user-guide/#writing-tests
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

// just a test for unit testing
public class GroupTest {
    private final Group group=new Group("hello world");

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