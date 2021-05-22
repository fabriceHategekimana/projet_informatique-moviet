package api;

// Injection
import javax.inject.Inject; // dependency injection
import javax.ws.rs.*;

import javax.ws.rs.core.*;

// MediaType
import javax.enterprise.context.ApplicationScoped; // ApplicationScoped ~singleton

//  import classes of domain
import domain.model.Group;
import domain.model.User;

// service
import domain.service.GroupService;

import java.util.Set;
import java.util.HashSet;

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
@Path("/groups")
@Api(value = "group")
@Produces({"application/json", "application/xml"})
public class GroupRestService {
    // Endpoint
    @Inject
    private GroupService groupService; // no more instantiation in the constructor

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "GET a list of all groups")
    public Response getAllGroups() {
        log.info("Trying to get all groups");
        return Response.ok(groupService.getAllGroups()).build(); // we can even add headers using .header() before .build()
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "GET a particular group")
    public Response getGroup(@PathParam("id") String str_id) {
        try {
            log.info("Trying to get a group using: id=" + str_id);
            int id = Integer.parseInt(str_id);
            Group group=groupService.getGroup(id);
            if (group == null) { // group not found
                return Response.status(Response.Status.NOT_FOUND).build();
            }
            // group exists
            return Response.ok(group).build();
        }
        catch(NumberFormatException e){ // invalid id
            return Response.status(Response.Status.BAD_REQUEST).entity("BAD_REQUEST : Invalid id, it should be numerical: id = " + str_id).build();
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
    @ApiOperation(value = "Create, add a group to the existing groups")
    public Response createGroup(Group group, final @Context UriInfo uriInfo){ // TODO: make it work with users (no auto increment id for users ?) and with the intermediate join table
        /*
        Create a group and returns HTTP status code and the location of the newly created object. It's possible to create multiple
        groups with same name. The unique identifier is its id that auto increments. We cannot input a group having an id !!

        Example with curl:
        - curl --verbose -H "Content-Type: application/json" -X POST http://localhost:10080/groups -d '{"name":"test"}'

         Then you use GET to see the created object
        */
        log.info("Trying to create using Group: " + group);
        // only want non init id and non null name, otherwise bad request
        if ((group.getId() != 0) || (group.getName() == null)){
            return Response.status(Response.Status.BAD_REQUEST).entity("BAD_REQUEST : only other attributes than id are (must be) initialized: " + group).build();
        }

        if (group.getUsers() == null){
            group.setUsers(new HashSet<User>()); // empty set
        }

        Group returnedGroup=groupService.createGroup(group); // can never have conflict if id are auto-incremented.
        if (returnedGroup == null){
            log.severe("Tried to create Group: id=" + group.getId() + " name=" +group.getId()+ " admin_id="+group.getAdmin_id() + " with some users but either id was non zero or group had null name or some of the users had user id was 1");
            return Response.status(Response.Status.BAD_REQUEST).entity("BAD_REQUEST : Tried to create Group: id=" + group.getId() + " name=" +group.getId()+ " admin_id="+group.getAdmin_id() + " with some users but either id was non zero or group had null name or some of the users had user id was 1").build();
        }
        // returnedGroup can be null in general but we tested the input before so it's not null.. otherwise bad request..
        UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder(); // https://www.logicbig.com/tutorials/java-ee-tutorial/jax-rs/uri-info.html
        uriBuilder.path(Integer.toString(returnedGroup.getId()));
        return Response.created(uriBuilder.build()).entity(returnedGroup).build(); // 201
    }

    @POST
    @Path("/{group_id}/users/")  // TODO: maybe add another HTTP method for get {group_id}/users/{user_id}
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Adding a new user to an existing group")
    public Response addUserToGroup(@PathParam("group_id") String str_id, User user){ // TODO: check if user already in group
        /*
        Add an user to an existing group. Need to know the id of the group to update. Return modified object.
         */

        try {
            log.info("Trying to add user " + user + "in a Group having id=" + str_id);
            int id = Integer.parseInt(str_id);

            // only want initialized id and non null name, otherwise bad request
            if (user.getId() == 0){
                return Response.status(Response.Status.BAD_REQUEST).entity("BAD_REQUEST : all attributes need to be instantiated: " + user).build();
            }

            if (user.getGroups() == null){
                user.setGroups(new HashSet<>()); // empty set
            }

            Group returnedGroup=groupService.addUserToGroup(id, user);
            // will add user if the Group if exists, otherwise return null
            if (returnedGroup == null){
                // group does not exist already
                return Response.status(Response.Status.NOT_FOUND).build(); // 404
            }
            return Response.ok(returnedGroup).build(); // 200
        }
        catch(NumberFormatException e){ // invalid id
            return Response.status(Response.Status.BAD_REQUEST).entity("BAD_REQUEST : Invalid group id, it should be numerical: id = " + str_id).build();
        }

    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Update existing group")
    public Response updateGroup(Group group){
        /*
        Update existing group. Need to know the id to update. Return modified object.

        Examples:
        - curl --verbose -H "Content-Type: application/json" -X PUT http://localhost:10080/groups -d '{"id":3,"name":"fabrice"}'
        - curl --verbose -H "Content-Type: application/json" -X PUT http://localhost:10080/groups -d '{"id":3,"name":"fabrice", "users": [{"name": "hello"}]}'
         */
        log.info("Trying to update using Group: " + group);
        // only want initialized id and non null name, otherwise bad request
        if ((group.getId() == 0) || (group.getName() == null)){
            return Response.status(Response.Status.BAD_REQUEST).entity("BAD_REQUEST : all attributes need to be instantiated: " + group).build();
        }

        if (group.getUsers() == null){
            group.setUsers(new HashSet<User>()); // empty set
        }

        Group returnedGroup=groupService.updateGroup(group); // get all groups and check if group inside list of groups
        // will update the Group if exists, otherwise return null
        if (returnedGroup == null){
            // group does not exist already
            return Response.status(Response.Status.NOT_FOUND).build(); // 404
        }
        return Response.ok(returnedGroup).build(); // 200
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Delete existing group")
    public Response deleteGroup(String str_id){
        /*
        Delete existing group and return the deleted group.

        Example:
        - curl --verbose -H "Content-Type: application/json" -X DELETE http://localhost:10080/groups -d 4

        This does not work : '{"id":youridhere}'
         */
        try {
            log.info("Trying to delete a group using: id=" + str_id);
            int id = Integer.parseInt(str_id);
            Group returnedGroup=groupService.deleteGroup(id); // get all groups and check if group inside groups
            // will delete the group if exists, otherwise return null
            if (returnedGroup == null){
                // group does not exist already
                return Response.status(Response.Status.NOT_FOUND).build(); // 404
            }
            return Response.ok(returnedGroup).build(); // 200
        }
        catch(NumberFormatException e){ // invalid id
            return Response.status(Response.Status.BAD_REQUEST).entity("BAD_REQUEST : Invalid group id, it should be numerical: id = " + str_id).build();
        }
    }
}
