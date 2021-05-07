package api;

import com.uwetrottmann.tmdb2.entities.Genre;
import domain.model.MovieDisplayInfo;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Arrays;
import java.util.List;

@ApplicationScoped // singleton
@Path("/Mock_movies")
public class MovieRestServiceMock {
    public MovieRestServiceMock() {
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDisplayInfo(@PathParam("id") int id) {

        MovieDisplayInfo displayInfo = new MovieDisplayInfo(
                299534,
                "Avengers : Endgame",
                2019,
                8.3,
                "https://image.tmdb.org/t/p/original/k9hd0dhvRNIQaXuiyniGSa0Mllr.jpg",
                "https://image.tmdb.org/t/p/original/7RyHsO4yDXtBv1zUU3mTpHeQ0d5.jpg",
                Arrays.asList("Aventure", "Science-Fiction", "Action"));

        return Response.ok(displayInfo).build();
    }

    @GET
    @Path("/genres")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getGenres() {
        List<Genre> genres = Arrays.asList(
                buildGenre("Action", 28),
                buildGenre("Aventure", 12),
                buildGenre("Animation", 16),
                buildGenre("Comédie", 35),
                buildGenre("Crime", 80),
                buildGenre("Documentaire", 99),
                buildGenre("Drame", 18),
                buildGenre("Familial", 10751),
                buildGenre("Fantastique", 14),
                buildGenre("Histoire", 36),
                buildGenre("Horreur", 27),
                buildGenre("Musique", 10402),
                buildGenre("Mystère", 9648),
                buildGenre("Romance", 10749),
                buildGenre("Science-Fiction", 878),
                buildGenre("Téléfilm", 10770),
                buildGenre("Thriller", 53),
                buildGenre("Guerre", 10752),
                buildGenre("Western", 37));

        return Response.ok(genres).build();
    }

    private Genre buildGenre(String name, Integer id) {
        Genre genre = new Genre();
        genre.name = name;
        genre.id = id;
        return genre;
    }
}
