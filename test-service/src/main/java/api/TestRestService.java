package api;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.enterprise.context.ApplicationScoped;
import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.java.Log;

@Log 
@ApplicationScoped 
@Path("/test")
@Api(value = "test")
@Produces({"application/json", "application/xml"})
public class TestRestService {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "return the request header")
    public Response getHeader(@Context HttpHeaders headers){
        return Response.ok(headers.getRequestHeaders()).build();    //building the server response
    }
}
