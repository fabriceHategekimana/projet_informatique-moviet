package api;

// Integration Testing
import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import io.restassured.http.ContentType;

/*
https://github.com/rest-assured/rest-assured/wiki/Usage#static-imports

The names of the tests start with a letter for the alphabetic runOrder of maven failsafe plugin

INTEGRATION TESTS SHOULD BE RUNNED WHILE SERVER IS RUNNING
 */
import io.restassured.RestAssured;

// Unit/Component testing using JUnit 5
// https://junit.org/junit5/docs/current/user-guide/#writing-tests
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.BeforeAll;

public class GroupRestServiceIT {

    @BeforeAll
    public static void setup() {
        //RestAssured.baseURI = "http://localhost:10080";
        RestAssured.port = 10080;
        RestAssured.basePath = "/groups";
        // https://github.com/rest-assured/rest-assured/wiki/Usage#default-values
    }


    /* --------------------------------------------------------
    IT for getAllGroups
     */
    @Test
    void testGetAllGroups(){ // GET
        // https://github.com/rest-assured/rest-assured/wiki/Usage#deserialization-with-generics
    }

    /* --------------------------------------------------------
    IT for getGroup, a single group
     */
    @Test
    void A_testGetGroup_ok(){ // GET
        // https://rest-assured.io
        get("/{id}", 1).
        then().
            statusCode(200). // OK
            body("id", equalTo("1"),
                    "name", equalTo("erwan"));
    }

    @Test
    void B_testGetGroup_not_found(){ // GET
        when().
            get("/{id}",100).
        then().
            statusCode(404); // NOT FOUND
    }

    @Test
    void C_testGetGroup_bad_request(){ // GET
        when().
            get("/{id}","$").
        then().
            statusCode(400); // BAD REQUEST
    }


    /* --------------------------------------------------------
    IT for createGroup
     */
    @Test
    void D_testCreateGroup_conflict(){ // POST
        String myJson="{\"id\": \"1\",\"name\":\"don\" }";
        given().
            contentType(ContentType.JSON).
            body(myJson). // conflict about existing id .param("id", "1").param("name", "don")
        when().
            post("/").
        then().
            statusCode(409); // CONFLICT
    }

    @Test
    void E_testCreateGroup_created(){ // POST
        String myJson="{\"id\": \"100\",\"name\":\"ethan\" }";
        given().
            contentType(ContentType.JSON).
            body(myJson).
        when().
            post("/").
        then().
            statusCode(201).
            body("id", equalTo(100),
                    "name", equalTo("ethan"));
        // have to remove the group created afterward... we do it in the testDeleteGroup()
    }

    // TODO: Maybe add BAD_REQUEST test for createGroup when id is null

    // TODO: IT for updateGroup
    /* --------------------------------------------------------
    IT for updateGroup
     */
    @Test
    void F_testUpdateGroup(){
        // nothing yet
    }


    /* --------------------------------------------------------
    IT for deleteGroup
     */
    @Test
    void G_testDeleteGroup_ok(){ // DELETE
        //String myJson="{\"id\": \"100\",\"name\":\"ethan\" }";
        given().
            param("100"). // id
        when().
            delete("/").
        then().
            statusCode(200). // OK
            body("id", equalTo(100), // also returns body
                    "name", equalTo("ethan"));
    }

    @Test
    void H_testDeleteGroup_not_found(){ // DELETE
        given().
            param("id", "100").
        when().
            delete("/").
        then().
            statusCode(404); // NOT FOUND
    }

    @Test
    void I_testDeleteGroup_bad_request(){ // DELETE
        given().
            param("id", "$").
        when().
            delete("/").
        then().
            statusCode(400); // BAD REQUEST
    }
}
