package api;

import com.uwetrottmann.tmdb2.entities.Genre;
import domain.model.MovieDisplayInfo;
import domain.model.MovieSuggestionInfo;

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
                "Rien ne peut vous préparer pour la fin.",
                "Après leur défaite face au Titan Thanos qui dans le film précédent s'est approprié toutes les pierres du Gant de l'infini , les Avengers et les Gardiens de la Galaxie ayant survécu à son claquement de doigts qui a pulvérisé « la moitié de toute forme de vie dans l'Univers », Captain America, Thor, Bruce Banner, Natasha Romanoff, War Machine, Tony Stark, Nébula et Rocket, vont essayer de trouver une solution pour ramener leurs coéquipiers disparus et vaincre Thanos en se faisant aider par Ronin alias Clint Barton, Captain Marvel et Ant-Man.",
                8.3,
                "https://image.tmdb.org/t/p/original/k9hd0dhvRNIQaXuiyniGSa0Mllr.jpg",
                "https://image.tmdb.org/t/p/original/7RyHsO4yDXtBv1zUU3mTpHeQ0d5.jpg",
                Arrays.asList("Aventure", "Science-Fiction", "Action"));

        return Response.ok(displayInfo).build();
    }

    @GET
    @Path("/suggestion-info/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSuggestionInfo(@PathParam("id") int id) {

        MovieSuggestionInfo suggestionInfo = new MovieSuggestionInfo(
                299534,
                248.234,
                Arrays.asList(12, 878, 28),
                Arrays.asList(3801, 4379, 5455, 9663, 9717, 14909, 155030, 173776, 180547, 186760, 218015, 240303, 241738),
                2019);
        return Response.ok(suggestionInfo).build();
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
