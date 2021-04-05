package api;

// Injection
import javax.inject.Inject; // Need to understand what injection is

import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;

// MediaType
import javax.ws.rs.core.MediaType;

// List
import java.util.List;

//  import classes of domain
import domain.model.Group;

// service
import domain.service.GroupService;

// will be removed later
import domain.service.GroupServiceImpl;

// maybe add ApplicationScoped later
@Path("/hello1")
public class GroupRestService {
    private GroupService groupService;
    public GroupRestService() {
        this.groupService=new GroupServiceImpl();
    }

    // keep this hello world for the moment
    @GET
    @Produces("text/plain")
    public Response doGet() {
        return Response.ok("Hello from group-service!").build();
    }

    /*
    @Inject
    private GroupService groupService;
    */

    @GET
    @Path("/groups")// http://localhost:10080/hello1/groups
    @Produces(MediaType.APPLICATION_JSON)
    public List<Group> getAllGroups() {
        return groupService.getAllGroups();
        //return new GroupServiceImpl().getAllGroups();
    }
}
