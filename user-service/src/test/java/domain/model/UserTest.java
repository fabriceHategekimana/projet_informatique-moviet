package domain.model;

// Unit/Component testing using JUnit 5
// https://junit.org/junit5/docs/current/user-guide/#writing-tests
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

// just a test for unit testing
class UserTest {
    private final User user = new User("Hello", "world");

    @Test
    void testGetId(){
        assertEquals("Hello", user.getId() );
    }
    
    @Test
    void testGetUsername(){
        assertEquals("world", user.getUsername());
    }


    @Test
    void testSetUsername(){
        User tmpuser = new User("hello", "world");
        tmpuser.setUsername("well");
        assertEquals("well", tmpuser.getUsername() );
    }

    @Test
    void testToString(){
				User tmpuser = new User("hello", "world");
        assertEquals("User(id=hello, username=world)", tmpuser.toString() );
    }
    // fail a test -> Build failure
}
