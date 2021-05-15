package api;

// Integration Testing
import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import io.restassured.http.ContentType;

import domain.model.User;
import domain.model.Group;

/*
https://github.com/rest-assured/rest-assured/wiki/Usage#static-imports

The names of the tests start with a letter for the alphabetic runOrder of maven failsafe plugin

INTEGRATION TESTS SHOULD BE RUNNED WHILE SERVER IS RUNNING. (Either run docker containers or .war etc.)

We use H2 database for the IT, the test database based on META-INF/groups_test.sql
 */
import io.restassured.RestAssured;

// Unit/Component testing using JUnit 5
// https://junit.org/junit5/docs/current/user-guide/#writing-tests
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.BeforeAll;

class GroupRestServiceIT {
    /* TODO: IT when they fail we have to kill manually processes ?
    sudo netstat -plten | grep java and killing the process with 0.0.0.0:28080
    https://stackoverflow.com/questions/12737293/how-do-i-resolve-the-java-net-bindexception-address-already-in-use-jvm-bind
     */
    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "http://localhost:8080/groups";  // port of the container !! in the pom, if we change offset, it changes here. Offset 0 currently
        RestAssured.port = 8080;
        // https://github.com/rest-assured/rest-assured/wiki/Usage#default-values
    }


    /* --------------------------------------------------------
    IT for getAllGroups
     */
    @Test
    void testGetAllGroups_ok(){ // GET
        // https://github.com/rest-assured/rest-assured/wiki/Usage#deserialization-with-generics
        get("/").
        then().
            statusCode(200). // OK
            body(containsStringIgnoringCase("erwan"));
    }

    /* --------------------------------------------------------
    IT for getGroup, a single group. Id 0 will always result in 404 not found.
     */
    @Test
    void testGetGroup_ok(){ // GET
        // https://rest-assured.io
        // we won't modify/delete the group with id 1 in the rest of the tests otherwise we might have errors

        get("/{id}", 1).
        then().
            statusCode(200). // OK
            body("id", equalTo(1),
                    "name", equalTo("erwan"),
                    "users", containsStringIgnoringCase("user-erwan"));
    }

    @Test
    void testGetGroup_not_found(){ // GET
        when().
            get("/{id}",Integer.MAX_VALUE).
        then().
            statusCode(404); // NOT FOUND
    }

    @Test
    void testGetGroup_bad_request(){ // GET
        when().
            get("/{id}","$").
        then().
            statusCode(400); // BAD REQUEST
    }


    /* --------------------------------------------------------
    IT for createGroup
     */
    @Test
    void testCreateGroup_created(){ // POST
        String myJson="{\"name\":\"new_group\"}";
        given().
            contentType(ContentType.JSON).
            body(myJson).
        when().
            post("/").
        then().
            statusCode(201).
            body("id", notNullValue(),
                    "name", equalTo("new_group")).
            header("Location", notNullValue());
    }

    @Test
    void testCreateGroup_created_id_0(){ // POST
        String myJson="{\"id\": 0, \"name\":\"new_group\"}";  // id 0 is like not even adding id in the JSON
        given().
            contentType(ContentType.JSON).
            body(myJson).
        when().
            post("/").
        then().
            statusCode(201).
            body("id", notNullValue(),
                    "name", equalTo("new_group"));
    }

    @Test
    void testCreateGroup_bad_request_name(){ // POST, null name.. Id 0 is okay
        String myJson="{\"id\": 0}";
        given().
            contentType(ContentType.JSON).
            body(myJson).
        when().
            post("/").
        then().
            statusCode(400);
    }

    @Test
    void testCreateGroup_bad_request_id(){ // POST
        String myJson="{\"id\": 100,\"name\":\"ethan\" }";
        given().
            contentType(ContentType.JSON).
            body(myJson).
        when().
            post("/").
        then().
            statusCode(400);
    }

    @Test
    void testCreateGroup_bad_request_id_name(){ // POST
        String myJson="{\"id\": 4}";
        given().
            contentType(ContentType.JSON).
            body(myJson).
        when().
            post("/").
        then().
            statusCode(400);
    }

    // -------------------------------------------------------
    /*
    IT for updateGroup
     */
    @Test
    void testUpdateGroup_ok(){
        String myJson="{\"id\": 2,\"name\":\"ethan\" }"; // no other test will delete this group otherwise we can have errors
        given().
            contentType(ContentType.JSON).
            body(myJson).
        when().
            put("/").
        then().
            statusCode(200).
            body("id", equalTo(2),
                    "name", equalTo("ethan"));
    }

    @Test
    void testUpdateGroup_not_found(){
        // no other test will delete this group otherwise we can have errors
        String myJson="{\"id\": ".concat(String.valueOf(Integer.MAX_VALUE)).concat(" ,\"name\":\"ethan\" }");
        given().
            contentType(ContentType.JSON).
            body(myJson).
        when().
            put("/").
        then().
            statusCode(404);
    }

    @Test
    void testUpdateGroup_bad_request_name(){
        String myJson="{\"id\": 4}";
        given().
            contentType(ContentType.JSON).
            body(myJson).
        when().
            put("/").
        then().
            statusCode(400);
    }

    @Test
    void testUpdateGroup_bad_request_id(){
        String myJson="{\"name\": \"new_name\"}";
        given().
            contentType(ContentType.JSON).
            body(myJson).
        when().
            put("/").
        then().
            statusCode(400);
    }

    @Test
    void testUpdateGroup_bad_request_id_name(){
        String myJson="{\"id\": 0}";
        given().
            contentType(ContentType.JSON).
            body(myJson).
        when().
            put("/").
        then().
            statusCode(400);
    }

    // --------------------------------------------------------
    /*
    IT for deleteGroup
     */

    @Test
    void testDeleteGroup_ok(){ // DELETE, always the group number 3
        given().
            contentType(ContentType.JSON).
            body("3"). // id
        when().
            delete("/").
        then().
            statusCode(200). // OK
            body("id", equalTo(3), // also returns body
            "name", notNullValue());
    }

    @Test
    void testDeleteGroup_not_found(){ // DELETE
        given().
            contentType(ContentType.JSON).
            body(String.valueOf(Integer.MAX_VALUE)). // id
        when().
            delete("/").
        then().
            statusCode(404); // NOT FOUND
    }

    @Test
    void testDeleteGroup_bad_request(){ // DELETE
        given().
            contentType(ContentType.JSON).
            body(""). // id
        when().
            delete("/").
        then().
            statusCode(400); // BAD REQUEST
    }

}
