package api;

import domain.model.MovieDisplayInfo;
import domain.service.MovieRequester;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/movies")
public class MovieRestService {
    private final MovieRequester movieRequester;

    public MovieRestService() {
        this.movieRequester = new MovieRequester();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDisplayInfo(@PathParam("id") int id) {

        MovieDisplayInfo displayInfo = movieRequester.getDisplayInfo(id);

        if (displayInfo == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(displayInfo).build();
    }

}
