package api;

// Integration Testing

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;

class UserRestServiceIT {
    /* TODO: IT when they fail we have to kill manually processes ?
    sudo netstat -plten | grep java and killing the process with 0.0.0.0:28080
    https://stackoverflow.com/questions/12737293/how-do-i-resolve-the-java-net-bindexception-address-already-in-use-jvm-bind
     */
    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "http://localhost:8080/users";  // port of the container !! in the pom, if we change offset, it changes here. Offset 0 currently
        RestAssured.port = 8080;
        // https://github.com/rest-assured/rest-assured/wiki/Usage#default-values
    }
//
//
//    /* --------------------------------------------------------
//    IT for getAllUsers
//     */
//    @Test
//    void testGetAllUsers_ok(){ // GET
//        // https://github.com/rest-assured/rest-assured/wiki/Usage#deserialization-with-generics
//        get("/").
//        then().
//            statusCode(405); // I don't want getAllusers to be used here
//    }
//
//    /* --------------------------------------------------------
//    IT for getUser, a single user. Id 0 will always result in 404 not found.
//     */
//    @Test
//    void testGetUser_ok(){ // GET
//        // https://rest-assured.io
//        // we won't modify/delete the user with id 1 in the rest of the tests otherwise we might have errors
//        get("/{id}", 1).
//        then().
//            statusCode(200). // OK
//            body("id", equalTo("1"),
//                    "username", containsStringIgnoringCase("Stephane"));
//    }
//
//    @Test
//    void testGetUser_not_found(){ // GET
//        when().
//            get("/{id}",Integer.MAX_VALUE).
//        then().
//            statusCode(404); // NOT FOUND
//    }
//
//
//    /* --------------------------------------------------------
//    IT for createUser
//     */
////    @Test
////    void testCreateUser_created(){ // POST
////        String myJson="{\"id\": 10,\"username\": \"test\"}";
////        given().
////            contentType(ContentType.JSON).
////            body(myJson).
////        when().
////            post("/").
////        then().
////            statusCode(201).
////            body("id", equalTo("10"),
////                    "username", equalTo("test")).
////            header("Location", notNullValue());
////    }
////
////    @Test
////    void testCreateUser_bad_request_attributes(){ // POST, null params.. Id 0 is okay
////        String myJson="{\"id\": 10}";
////        given().
////            contentType(ContentType.JSON).
////            body(myJson).
////        when().
////            post("/").
////        then().
////            statusCode(400);
////    }
////
////    @Test
////    void testCreateUser_bad_request_id(){ // POST
////        String myJson="{\"id\": 0,\"firstName\": \"new\"}";
////        given().
////            contentType(ContentType.JSON).
////            body(myJson).
////        when().
////            post("/").
////        then().
////            statusCode(400);
////    }
////
////    @Test
////    void testCreateUser_bad_request_id_attributes(){ // POST
////        String myJson="{\"id\": 0}";
////        given().
////            contentType(ContentType.JSON).
////            body(myJson).
////        when().
////            post("/").
////        then().
////            statusCode(400);
////    }
//    // -------------------------------------------------------
//    /*
//    IT for updateUser
//     */
//    @Test
//    void testUpdateUser_ok(){
//        String myJson="{\"id\": 2,\"username\": \"Ethan\"}"; // no other test will delete this user otherwise we can have errors
//        given().
//            contentType(ContentType.JSON).
//            body(myJson).
//        when().
//            put("/").
//        then().
//            statusCode(200).
//            body("id", notNullValue(),
//                    "username", equalTo("Ethan"));
//    }
//
//    @Test
//    void testUpdateUser_not_found(){
//        // no other test will delete this user otherwise we can have errors
//        String myJson="{\"id\": ".concat(String.valueOf(Integer.MAX_VALUE)).concat(" ,\"username\": \"Ethan\"}");
//        given().
//            contentType(ContentType.JSON).
//            body(myJson).
//        when().
//            put("/").
//        then().
//            statusCode(404);
//    }
//
//    @Test
//    void testUpdateUser_bad_request_name(){
//        String myJson="{\"id\": 4}";
//        given().
//            contentType(ContentType.JSON).
//            body(myJson).
//        when().
//            put("/").
//        then().
//            statusCode(400);
//    }
//
//    @Test
//    void testUpdateUser_bad_request_id(){
//        String myJson="{\"username\": \"new_name\"}";
//        given().
//            contentType(ContentType.JSON).
//            body(myJson).
//        when().
//            put("/").
//        then().
//            statusCode(400);
//    }
//
//    @Test
//    void testUpdateUser_bad_request_id_name(){
//        String myJson="{\"id\": 0}";
//        given().
//            contentType(ContentType.JSON).
//            body(myJson).
//        when().
//            put("/").
//        then().
//            statusCode(400);
//    }
//    // --------------------------------------------------------
//    /*
//    IT for deleteUser
//     */
//
//    @Test
//    void testDeleteUser_ok(){ // DELETE, always the user number 3
//        given().
//            contentType(ContentType.JSON).
//            body("3"). // id
//        when().
//            delete("/").
//        then().
//            statusCode(200). // OK
//            body("id", equalTo("3"),
//                    "username", notNullValue());
//    }
//
//    @Test
//    void testDeleteUser_not_found(){ // DELETE
//        given().
//            contentType(ContentType.JSON).
//            body(String.valueOf(Integer.MAX_VALUE)). // id
//        when().
//            delete("/").
//        then().
//            statusCode(404); // NOT FOUND
//    }
//
}
