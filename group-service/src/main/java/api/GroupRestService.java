package api;

// Injection
// import javax.inject.Inject; // Need to understand what injection is
import javax.ws.rs.*;

import javax.ws.rs.core.Response;
// MediaType
import javax.ws.rs.core.MediaType;
import javax.enterprise.context.ApplicationScoped; // ApplicationScoped ~singleton

// List
import java.util.List;
//  import classes of domain
import domain.model.Group;

// service
import domain.service.GroupService;

// will be removed later
import domain.service.GroupServiceImpl;


// https://www.restapitutorial.com/lessons/httpmethods.html
@ApplicationScoped
@Path("/groups")
public class GroupRestService {
    // Endpoint

    /*
    @Inject
    private GroupService groupService;
    */

    private GroupService groupService;

    public GroupRestService() {
        this.groupService=new GroupServiceImpl();
    }


    // http://localhost:10080/groups
    // GET a list of all groups in JSON
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Group> getAllGroups() {
        return groupService.getAllGroups();
    }

    // http://localhost:10080/groups/{id}
    // GET a particular group in JSON
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getGroup(@PathParam("id") String id) {
        // need String instead of Integer otherwise we would have a conversion in the parameters !

        // check parameters
        if (id == null || !id.chars().allMatch(Character::isLetterOrDigit)) {
            //https://www.techiedelight.com/check-string-contains-alphanumeric-characters-java/#:~:text=The%20idea%20is%20to%20use,matches%20the%20given%20regular%20expression.
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        Group group=groupService.getGroup(id);
        if (group == null) { // group not found
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        // group exists
        return Response.ok(group).build();

    }
    /*
    https://stackoverflow.com/questions/4687271/jax-rs-how-to-return-json-and-http-status-code-together
    https://docs.oracle.com/javaee/7/api/javax/ws/rs/core/Response.Status.html
    https://docs.oracle.com/javaee/7/api/javax/ws/rs/core/Response.html

    Just to try something : Response.ok(groupService.getGroup(id)).header("hello",42).build();
    */

    // TODO: convention createGroup ?
    // Create, add a group to the existing groups
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response createGroup(Group group){
        /*
        Examples with curl:
        - curl --verbose -H "Content-Type: application/json" -X POST http://localhost:10080/groups -d '{"id":"24","name":"fabrice"}'
        - curl --verbose -H "Content-Type: application/json" -X POST http://localhost:10080/groups -d '{"id":"13"}'

         Then use GET
         */
        // TODO: Need to check the JSON further
        if (group.getId() == null) {
            throw new IllegalArgumentException("Group id is null ! ");
            // curl --verbose -H "Content-Type: application/json" -X DELETE http://localhost:10080/groups -d '{"name":"11"}'
            // gives BAD REQUEST
        }

        Group returnedGroup=groupService.createGroup(group); // get all groups and check if group inside list of groups
        // will add the Group if does not exist, otherwise return null
        if (returnedGroup == null){
            // group exists already
            return Response.status(Response.Status.CONFLICT).build(); // 409
        }
        // TODO: remove hardcoded link
        return Response.status(Response.Status.CREATED).header("Location", "localhost/groups/{id}").build(); // 201
    }

    // Update existing group
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateGroup(Group group){
        /*
        curl --verbose -H "Content-Type: application/json" -X PUT http://localhost:10080/groups -d '{"id":"24","name":"fabrice"}'
         */
        // TODO: Need to check the JSON, and add 204 (No Content)

        Group returnedGroup=groupService.updateGroup(group); // get all groups and check if group inside list of groups
        // will update the Group if exists, otherwise return null
        if (returnedGroup == null){
            // group does not exist already
            return Response.status(Response.Status.NOT_FOUND).build(); // 404
        }
        // TODO: remove hardcoded link
        return  Response.ok(returnedGroup).header("Location", "localhost/groups/{id}").build(); // 200
    }

    // Delete existing group
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteGroup(String id){
        /*
        curl --verbose -H "Content-Type: application/json" -X DELETE http://localhost:10080/groups -d "youridhere"
        Example:
        - curl --verbose -H "Content-Type: application/json" -X DELETE http://localhost:10080/groups -d "24"

        This does not work : '{"id":youridhere}'
         */
        // check parameters
        if (id == null || !id.chars().allMatch(Character::isLetterOrDigit)) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        Group returnedGroup=groupService.deleteGroup(id); // get all groups and check if group inside list of groups
        // will delete the group if exists, otherwise return null
        if (returnedGroup == null){
            // group does not exist already
            return Response.status(Response.Status.NOT_FOUND).build(); // 404
        }

        return  Response.ok(returnedGroup).build(); // 200
    }
}
