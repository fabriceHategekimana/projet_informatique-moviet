package domain.model;

// Unit/Component testing using JUnit 5
// https://junit.org/junit5/docs/current/user-guide/#writing-tests
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

// just a test for unit testing
class UserTest {
    private final User user = new User("hello world");

    @Test
    void testGetId(){
        assertEquals(0, user.getId() );
    }
    
    @Test
    void testGetName(){
        assertEquals("hello world", user.getName() );
    }

    @Test
    void testSetName(){
        User tmpuser = new User("hello world");
        tmpuser.setName("hello universe");
        assertEquals("hello universe", tmpuser.getName() );
    }

    @Test
    void testGetIdNoArgsConstructor(){
        assertEquals(0, new User().getId() );
    }

    @Test
    void testGetNameNoArgsConstructor(){
        assertEquals(null, new User().getName() );
    }

    @Test
    void testToString(){
        assertEquals("User(id=0, name=hello world)", user.toString() );
    }
    // fail a test -> Build failure
}