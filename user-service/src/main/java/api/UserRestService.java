package api;

// Injection
import javax.inject.Inject; // dependency injection
import javax.ws.rs.*;

import javax.ws.rs.core.*;

// MediaType
import javax.enterprise.context.ApplicationScoped; // ApplicationScoped ~singleton

//  import classes of domain
import domain.model.User;

// service
import domain.service.UserService;

/*
https://thorntail.io/posts/wildfly-swarm-s-got-swagger/

In simple terms, Swagger is a JSON representation of a RESTful API, typically made available over HTTP at /swagger.json.
This JSON document contains information about your APIs, including names, paths, endpoints, parameters, descriptions,
keywords, expected responses, and more.
Per the Open API Specification, the goal of Swagger is to "define a standard, language-agnostic interface to REST APIs
which allows both humans and computers to discover and understand the capabilities of the service without access to
source code, documentation, or through network traffic inspection".

 */
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.java.Log;

// https://www.restapitutorial.com/lessons/httpmethods.html

@Log // lombok log
@ApplicationScoped // singleton
@Path("/users")
@Api(value = "user")
@Produces({"application/json", "application/xml"})
public class UserRestService {
    // Endpoint
    @Inject
    private UserService userService; // no more instantiation in the constructor

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "GET a particular user")
    public Response getUser(@PathParam("id") String str_id) {
        try {
            log.info("Trying to get a user using: id=" + str_id);
            User user=userService.getUser(str_id);
            if (user == null) { // user not found
                return Response.status(Response.Status.NOT_FOUND).build();
            }
            // user exists
            return Response.ok(user).build();
        }
        catch(NumberFormatException e){ // invalid id
            return Response.status(Response.Status.BAD_REQUEST).entity("BAD_REQUEST : Invalid id, it should be a string: id = " + str_id).build();
        }
    }
    /*
    https://stackoverflow.com/questions/4687271/jax-rs-how-to-return-json-and-http-status-code-together
    https://docs.oracle.com/javaee/7/api/javax/ws/rs/core/Response.Status.html
    https://docs.oracle.com/javaee/7/api/javax/ws/rs/core/Response.html
    */

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Create, add a user to the existing users")
    public Response createUser(User user, final @Context UriInfo uriInfo){
        /*
        Create a user and returns HTTP status code and the location of the newly created object. It's possible to create multiple
        users with same name. We cannot input an user WITHOUT ID

        Example with curl:
        - curl --verbose -H "Content-Type: application/json" -X POST http://localhost:10082/users -d '{"name":"test"}'

         Then you use GET to see the created object
        */
        log.info("Trying to create using User: " + user);
        // only want non init id and non null first/last names, otherwise bad request
        if ((user.getId().equals("0")) || (user.getId() == null)  || (user.getUsername() == null)){
            return Response.status(Response.Status.BAD_REQUEST).entity("BAD_REQUEST : all attributes must be initialized correctly: " + user).build();
        }

        User returnedUser=userService.createUser(user); // TODO : manage when conflict of ID !!
        // returnedUser can be null in general but we tested the input before so it's not null.. otherwise bad request..
        UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder(); // https://www.logicbig.com/tutorials/java-ee-tutorial/jax-rs/uri-info.html
        uriBuilder.path(returnedUser.getId());
        return Response.created(uriBuilder.build()).entity(returnedUser).build(); // 201
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Update existing user")
    public Response updateUser(User user){
        /*
        Update existing user. Need to know the id to update. Return modified object.

        Example:
        - curl --verbose -H "Content-Type: application/json" -X PUT http://localhost:10080/users -d '{"id":3,"name":"fabrice"}'
         */
        log.info("Trying to update using User: " + user);
        // only want initialized id and non null names, otherwise bad request
        if ((user.getId().equals("0")) || (user.getId() == null)  || (user.getUsername() == null)){
            return Response.status(Response.Status.BAD_REQUEST).entity("BAD_REQUEST : all attributes need to be correctly instantiated: " + user).build();
        }
        User returnedUser=userService.updateUser(user); // get all users and check if user inside list of users
        // will update the User if exists, otherwise return null
        if (returnedUser == null){
            // user does not exist already
            return Response.status(Response.Status.NOT_FOUND).build(); // 404
        }
        return Response.ok(returnedUser).build(); // 200
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Delete existing user")
    public Response deleteUser(String str_id){
        /*
        Delete existing user and return the deleted user.

        Example:
        - curl --verbose -H "Content-Type: application/json" -X DELETE http://localhost:10082/users -d 4

        This does not work : '{"id":youridhere}'
         */
        try {
            log.info("Trying to delete a user using: id=" + str_id);
            User returnedUser=userService.deleteUser(str_id); // get all users and check if user inside users
            // will delete the user if exists, otherwise return null
            if (returnedUser == null){
                // user does not exist already
                return Response.status(Response.Status.NOT_FOUND).build(); // 404
            }
            return Response.ok(returnedUser).build(); // 200
        }
        catch(NumberFormatException e){ // invalid id
            return Response.status(Response.Status.BAD_REQUEST).entity("BAD_REQUEST : Invalid id, it should be numerical: id = " + str_id).build();
        }
    }

    @GET
    @Path("/whoami")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "get the user associated with an authenticated client")
    public Response whoami(final @HeaderParam("X-User") String user_id) {
		    User user=userService.getUser(user_id);
				if(user == null){
						return Response.status(Response.Status.NOT_FOUND).build();
				}
        return Response.ok(user).build();
    }

}









