package api;

import domain.model.MovieDisplayInfo;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.enterprise.context.ApplicationScoped; // ApplicationScoped ~singleton
import javax.ws.rs.core.Response;
import java.util.Arrays;

@ApplicationScoped // singleton
@Path("/movies")
public class MovieRestServiceMock {
    public MovieRestServiceMock() {
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDisplayInfo(@PathParam("id") int id) {

        MovieDisplayInfo displayInfo = new MovieDisplayInfo(299534,
                "Avengers : Endgame",
                2019,
                8.3,
                "https://image.tmdb.org/t/p/original/k9hd0dhvRNIQaXuiyniGSa0Mllr.jpg",
                "https://image.tmdb.org/t/p/original/7RyHsO4yDXtBv1zUU3mTpHeQ0d5.jpg",
                Arrays.asList("Aventure", "Science-Fiction", "Action"));

        return Response.ok(displayInfo).build();
    }

}
