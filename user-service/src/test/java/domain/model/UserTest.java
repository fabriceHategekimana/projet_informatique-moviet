package domain.model;

// Unit/Component testing using JUnit 5
// https://junit.org/junit5/docs/current/user-guide/#writing-tests
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

// just a test for unit testing
class UserTest {
    private final User user = new User("hello", "world", "42");

    @Test
    void testGetId(){
        assertEquals(0, user.getId() );
    }
    
    @Test
    void testGetFirstName(){
        assertEquals("hello", user.getFirstName());
    }

    @Test
    void testGetLastName(){
        assertEquals("world", user.getLastName());
    } 

    @Test
    void testGetAge(){
        assertEquals("42", user.getAge());
    }

    @Test
    void testSetFirstName(){
        User tmpuser = new User("hello", "world", "42");
        tmpuser.setFirstName("well");
        assertEquals("well", tmpuser.getFirstName() );
    }

    @Test
    void testSetLastName(){
        User tmpuser = new User("well", "world", "42");
        tmpuser.setLastName("Hello there");
        assertEquals("Hello there", tmpuser.getLastName() );
    }

    @Test
    void testSetAge(){
        User tmpuser = new User("well", "hello there", "42");
        tmpuser.setAge("504");
        assertEquals("504", tmpuser.getAge() );
    }

    @Test
    void testGetIdNoArgsConstructor(){
        assertEquals(0, new User().getId() );
    }

    @Test
    void testGetFirstNameNoArgsConstructor(){
        assertEquals(null, new User().getFirstName() );
    }

    @Test
    void testGetLastNameNoArgsConstructor(){
        assertEquals(null, new User().getLastName() );
    }

    @Test
    void testGetAgeNoArgsConstructor(){
        assertEquals(null, new User().getAge() );
    }

    @Test
    void testToString(){
        assertEquals("User(id=0, firstName=hello, lastName=world, age=42)", user.toString() );
    }
    // fail a test -> Build failure
}
