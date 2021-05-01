package api;

// Injection
import javax.inject.Inject; // dependency injection
import javax.ws.rs.*;

import javax.ws.rs.core.Response;
// MediaType
import javax.ws.rs.core.MediaType;
import javax.enterprise.context.ApplicationScoped; // ApplicationScoped ~singleton

//  import classes of domain
import domain.model.Group;

// service
import domain.service.GroupService;


// https://www.restapitutorial.com/lessons/httpmethods.html

@ApplicationScoped // singleton
@Path("/groups")
public class GroupRestService {
    // Endpoint

    private final String current_link="http://localhost:10080/groups/"; // for link informations

    @Inject
    private GroupService groupService; // no more instantiation in the constructor


    // http://localhost:10080/groups
    // GET a list of all groups
    @GET
    @Produces(MediaType.APPLICATION_JSON) // TODO: reduce the number of groups that we return
    public Response getAllGroups() {
        return Response.ok(groupService.getAllGroups()).build(); // we can even add headers using .header() before .build()
    }

    // http://localhost:10080/groups/{id}
    // GET a particular group
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getGroup(@PathParam("id") String str_id) {
        try {
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

    Just to try something : Response.ok(groupService.getGroup(id)).header("hello",42).build();
    */

    // Create, add a group to the existing groups
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response createGroup(Group group){
        /*
        Create a group and returns HTTP status code and the location of the newly created object. It's possible to create multiple
        groups with same name. The unique identifier is its id that auto increments. We cannot input a group having an id !!

        Example with curl:
        - curl --verbose -H "Content-Type: application/json" -X POST http://localhost:10080/groups -d '{"name":"test"}'

         Then you use GET to see the created object
        */
        // only want non init id and non null name, otherwise bad request
        if ((group.getId() != 0) || (group.getName() == null)){
            return Response.status(Response.Status.BAD_REQUEST).entity("BAD_REQUEST : only other attributes than id are (must be) initialized: " + group).build();
        }
        Group returnedGroup=groupService.createGroup(group); // can never have conflict if id are auto-incremented.

        return Response.status(Response.Status.CREATED).header("Location", current_link.concat(String.valueOf(returnedGroup.getId()))).build(); // 201
    }

    // Update existing group
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateGroup(Group group){
        /*
        Update existing group. Need to know the id to update. Return modified object.

        Example:
        - curl --verbose -H "Content-Type: application/json" -X PUT http://localhost:10080/groups -d '{"id":3,"name":"fabrice"}'
         */
        // only want initialized id and non null name, otherwise bad request
        if ((group.getId() == 0) || (group.getName() == null)){
            return Response.status(Response.Status.BAD_REQUEST).entity("BAD_REQUEST : all attributes need to be instantiated: " + group).build();
        }
        Group returnedGroup=groupService.updateGroup(group); // get all groups and check if group inside list of groups
        // will update the Group if exists, otherwise return null
        if (returnedGroup == null){
            // group does not exist already
            return Response.status(Response.Status.NOT_FOUND).build(); // 404
        }

        return  Response.ok(returnedGroup).header("Location", current_link.concat(String.valueOf(group.getId()))).build(); // 200
    }

    // Delete existing group
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteGroup(String str_id){
        /*
        Delete existing group and return the deleted group.

        Example:
        - curl --verbose -H "Content-Type: application/json" -X DELETE http://localhost:10080/groups -d 4

        This does not work : '{"id":youridhere}'
         */
        try {
            int id = Integer.parseInt(str_id);
            Group returnedGroup=groupService.deleteGroup(id); // get all groups and check if group inside groups
            // will delete the group if exists, otherwise return null
            if (returnedGroup == null){
                // group does not exist already
                return Response.status(Response.Status.NOT_FOUND).build(); // 404
            }
            return  Response.ok(returnedGroup).build(); // 200
        }
        catch(NumberFormatException e){ // invalid id
            return Response.status(Response.Status.BAD_REQUEST).entity("BAD_REQUEST : Invalid id, it should be numerical: id = " + str_id).build();
        }
    }
}
