package api;

import com.uwetrottmann.tmdb2.entities.BaseMovie;
import com.uwetrottmann.tmdb2.entities.Genre;
import com.uwetrottmann.tmdb2.entities.MovieResultsPage;
import com.uwetrottmann.tmdb2.entities.TmdbDate;
import com.uwetrottmann.tmdb2.enumerations.SortBy;
import domain.model.MovieDisplayInfo;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


@ApplicationScoped // singleton
@Path("/Mock_discover")
public class DiscoverRestServiceMock {

    @GET
    @Path("/page")
    @Produces(MediaType.APPLICATION_JSON)
    public Response discoverPage(
            @QueryParam("page") Integer page,
            @QueryParam("sortBy") SortBy sortBy,
            @QueryParam("release_date_gte") TmdbDate release_date_gte,
            @QueryParam("release_date_lte") TmdbDate release_date_lte,
            @QueryParam("genres") Integer[] genres,
            @QueryParam("keywords") Integer[] keywords,
            @QueryParam("banned_genres") Integer[] banned_genres,
            @QueryParam("banned_keywords") Integer[] banned_keywords,
            @QueryParam("genres_operator") String genres_operator,
            @QueryParam("keywords_operator") String keywords_operator) {

        MovieResultsPage movieResultsPage = buildMovieResultsPage(
                Arrays.asList(
                        buildBaseMovie(27205, "/s3TBrRGB1iav7gFOCNx3H31MoES.jpg", Arrays.asList(28, 878, 12), "Inception", "Dom Cobb est un voleur expérimenté, le meilleur dans l'art dangereux de l'extraction, voler les secrets les plus intimes enfouis au plus profond du subconscient durant une phase de rêve, lorsque l'esprit est le plus vulnérable. Les capacités de Cobb ont fait des envieux dans le monde tourmenté de l'espionnage industriel alors qu'il devient fugitif en perdant tout ce qu'il a un jour aimé. Une chance de se racheter lui est alors offerte. Une ultime mission grâce à laquelle il pourrait retrouver sa vie passée mais uniquement s'il parvient à accomplir l'impossible inception.", "/aej3LRUga5rhgkmRP6XMFw3ejbl.jpg", new Date(1279152000000L), "Inception", 8.3, 28982),
                        buildBaseMovie(68718, "/lVcI3MOtumvEbOdS1Og7QoV6Lfc.jpg", Arrays.asList(18, 37), "Django Unchained", "Dans le sud des États‐Unis, deux ans avant la guerre de Sécession, le Dr King Schultz, un chasseur de primes allemand, fait l’acquisition de Django, un esclave qui peut l’aider à traquer les frères Brittle, les meurtriers qu’il recherche. Schultz promet à Django de lui rendre sa liberté lorsqu’il aura capturé les Brittle – morts ou vifs. Alors que les deux hommes pistent les dangereux criminels, Django n’oublie pas que son seul but est de retrouver Broomhilda, sa femme, dont il fut séparé à cause du commerce des esclaves… Lorsque Django et Schultz arrivent dans l’immense plantation du puissant Calvin Candie, ils éveillent les soupçons de Stephen, un esclave qui sert Candie et a toute sa confiance. Le moindre de leurs mouvements est désormais épié par une dangereuse organisation de plus en plus proche… Si Django et Schultz veulent espérer s’enfuir avec Broomhilda, ils vont devoir choisir entre l’indépendance et la solidarité, entre le sacrifice et la survie…", "/hodO0759IB5LbzUiiLKB50gT2UO.jpg", new Date(1356393600000L), "Django Unchained", 8.1, 20551),
                        buildBaseMovie(671, "/hziiv14OpD73u9gAak4XDDfBKa2.jpg", Arrays.asList(12, 14), "Harry Potter and the Philosopher's Stone", "Orphelin, Harry Potter a été recueilli en bas âge par sa tante Pétunia et son oncle Vernon, deux abominables créatures qui, depuis dix ans, prennent un malin plaisir à l’humilier, le houspiller et le malmener. Contraint de se nourrir de restes et de dormir dans un placard infesté d’araignées, le malheureux est en butte à l’hostilité de son cousin Dudley, obèse imbécile qui ne manque pas une occasion de le rouer de coups. L’année de ses 11 ans, Harry ne s’attend pas à recevoir de cadeaux, pourtant cette année là, une lettre mystérieuse va lui parvenir qui va changer son existence…", "/fbxQ44VRdM2PVzHSNajUseUteem.jpg", new Date(1005868800000L), "Harry Potter à l'école des sorciers", 7.9, 20199),
                        buildBaseMovie(672, "/1stUIsjawROZxjiCMtqqXqgfZWC.jpg", Arrays.asList(12, 14), "Harry Potter and the Chamber of Secrets", "Alors que l’oncle Vernon, la tante Pétunia et son cousin Dudley reçoivent d’importants invités à dîner, Harry Potter est contraint de passer la soirée dans sa chambre. Dobby, un elfe, fait alors son apparition. Il lui annonce que de terribles dangers menacent l’école de Poudlard et qu’il ne doit pas y retourner en septembre. Harry refuse de le croire. Mais sitôt la rentrée des classes effectuée, ce dernier entend une voix malveillante. Celle‐ci lui dit que la redoutable et légendaire Chambre des Secrets est à nouveau ouverte, permettant ainsi à l’héritier de Serpentard de semer le chaos à Poudlard. Les victimes, retrouvées pétrifiées par une force mystérieuse, se succèdent dans les couloirs de l’école, sans que les professeurs – pas même le populaire Gilderoy Lockhart – ne parviennent à endiguer la menace. Aidé de Ron et Hermione, Harry doit agir au plus vite pour sauver Poudlard.", "/8KpHRokGpiaqEGpjYe0rpywtvUx.jpg", new Date(1037145600000L), "Harry Potter et la Chambre des secrets", 7.7, 16389)
                )
        );

        return Response.ok(movieResultsPage).build();
    }

    @GET
    @Path("/id")
    @Produces(MediaType.APPLICATION_JSON)
    public Response discoverId(
            @QueryParam("page") Integer page,
            @QueryParam("sortBy") SortBy sortBy,
            @QueryParam("release_date_gte") TmdbDate release_date_gte,
            @QueryParam("release_date_lte") TmdbDate release_date_lte,
            @QueryParam("genres") Integer[] genres,
            @QueryParam("keywords") Integer[] keywords,
            @QueryParam("banned_genres") Integer[] banned_genres,
            @QueryParam("banned_keywords") Integer[] banned_keywords,
            @QueryParam("genres_operator") String genres_operator,
            @QueryParam("keywords_operator") String keywords_operator) {

        List<Integer> ids = Arrays.asList(27205, 68718, 671, 672);

        return Response.ok(ids).build();
    }

    @GET
    @Path("/display")
    @Produces(MediaType.APPLICATION_JSON)
    public Response discoverDisplayInfo(
            @QueryParam("page") Integer page,
            @QueryParam("sortBy") SortBy sortBy,
            @QueryParam("release_date_gte") TmdbDate release_date_gte,
            @QueryParam("release_date_lte") TmdbDate release_date_lte,
            @QueryParam("genres") Integer[] genres,
            @QueryParam("keywords") Integer[] keywords,
            @QueryParam("banned_genres") Integer[] banned_genres,
            @QueryParam("banned_keywords") Integer[] banned_keywords,
            @QueryParam("genres_operator") String genres_operator,
            @QueryParam("keywords_operator") String keywords_operator) {

        List<MovieDisplayInfo> movieDisplayInfoList = Arrays.asList(
                new MovieDisplayInfo(27205, "Inception", 2010, "Your mind is the scene of the crime.", "Cobb, a skilled thief who commits corporate espionage by infiltrating the subconscious of his targets is offered a chance to regain his old life as payment for a task considered to be impossible: \"inception\", the implantation of another person's idea into a target's subconscious.", 8.3, "https://image.tmdb.org/t/p/original/9gk7adHYeDvHkCSEqAvQNLV5Uge.jpg", "https://image.tmdb.org/t/p/original/s3TBrRGB1iav7gFOCNx3H31MoES.jpg", Arrays.asList("Action", "Science Fiction", "Adventure")),
                new MovieDisplayInfo(68718, "Django Unchained", 2012, "Ils ont pris sa liberté. Il va tout leur prendre.", "Dans le sud des États‐Unis, deux ans avant la guerre de Sécession, le Dr King Schultz, un chasseur de primes allemand, fait l’acquisition de Django, un esclave qui peut l’aider à traquer les frères Brittle, les meurtriers qu’il recherche. Schultz promet à Django de lui rendre sa liberté lorsqu’il aura capturé les Brittle – morts ou vifs. Alors que les deux hommes pistent les dangereux criminels, Django n’oublie pas que son seul but est de retrouver Broomhilda, sa femme, dont il fut séparé à cause du commerce des esclaves… Lorsque Django et Schultz arrivent dans l’immense plantation du puissant Calvin Candie, ils éveillent les soupçons de Stephen, un esclave qui sert Candie et a toute sa confiance. Le moindre de leurs mouvements est désormais épié par une dangereuse organisation de plus en plus proche… Si Django et Schultz veulent espérer s’enfuir avec Broomhilda, ils vont devoir choisir entre l’indépendance et la solidarité, entre le sacrifice et la survie…", 8.1, "https://image.tmdb.org/t/p/original/hodO0759IB5LbzUiiLKB50gT2UO.jpg", "https://image.tmdb.org/t/p/original/lVcI3MOtumvEbOdS1Og7QoV6Lfc.jpg", Arrays.asList("Drame", "Western")),
                new MovieDisplayInfo(671, "Harry Potter à l'école des sorciers", 2001, "Un voyage au‐delà de votre imagination", "Orphelin, Harry Potter a été recueilli en bas âge par sa tante Pétunia et son oncle Vernon, deux abominables créatures qui, depuis dix ans, prennent un malin plaisir à l’humilier, le houspiller et le malmener. Contraint de se nourrir de restes et de dormir dans un placard infesté d’araignées, le malheureux est en butte à l’hostilité de son cousin Dudley, obèse imbécile qui ne manque pas une occasion de le rouer de coups. L’année de ses 11 ans, Harry ne s’attend pas à recevoir de cadeaux, pourtant cette année là, une lettre mystérieuse va lui parvenir qui va changer son existence…", 7.9, "https://image.tmdb.org/t/p/original/fbxQ44VRdM2PVzHSNajUseUteem.jpg", "https://image.tmdb.org/t/p/original/hziiv14OpD73u9gAak4XDDfBKa2.jpg", Arrays.asList("Aventure", "Fantastique")),
                new MovieDisplayInfo(672, "Harry Potter et la Chambre des secrets", 2002, "Quelque chose de maléfique est de retour à Poudlard !", "Alors que l’oncle Vernon, la tante Pétunia et son cousin Dudley reçoivent d’importants invités à dîner, Harry Potter est contraint de passer la soirée dans sa chambre. Dobby, un elfe, fait alors son apparition. Il lui annonce que de terribles dangers menacent l’école de Poudlard et qu’il ne doit pas y retourner en septembre. Harry refuse de le croire. Mais sitôt la rentrée des classes effectuée, ce dernier entend une voix malveillante. Celle‐ci lui dit que la redoutable et légendaire Chambre des Secrets est à nouveau ouverte, permettant ainsi à l’héritier de Serpentard de semer le chaos à Poudlard. Les victimes, retrouvées pétrifiées par une force mystérieuse, se succèdent dans les couloirs de l’école, sans que les professeurs – pas même le populaire Gilderoy Lockhart – ne parviennent à endiguer la menace. Aidé de Ron et Hermione, Harry doit agir au plus vite pour sauver Poudlard.", 7.7, "https://image.tmdb.org/t/p/original/8KpHRokGpiaqEGpjYe0rpywtvUx.jpg", "https://image.tmdb.org/t/p/original/1stUIsjawROZxjiCMtqqXqgfZWC.jpg", Arrays.asList("Aventure", "Fantastique"))
        );

        return Response.ok(movieDisplayInfoList).build();
    }

    @GET
    @Path("/config/genres")
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

    @GET
    @Path("/config/sortKeys")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSortKeys() {

        List<SortBy> genres = Arrays.asList(
                SortBy.POPULARITY_ASC,
                SortBy.POPULARITY_DESC,
                SortBy.RELEASE_DATE_ASC,
                SortBy.RELEASE_DATE_DESC,
                SortBy.REVENUE_ASC,
                SortBy.REVENUE_DESC,
                SortBy.PRIMARY_RELEASE_DATE_ASC,
                SortBy.PRIMARY_RELEASE_DATE_DESC,
                SortBy.ORIGINAL_TITLE_ASC,
                SortBy.ORIGINAL_TITLE_DESC,
                SortBy.VOTE_AVERAGE_ASC,
                SortBy.VOTE_AVERAGE_DESC,
                SortBy.VOTE_COUNT_ASC,
                SortBy.VOTE_COUNT_DESC
        );

        return Response.ok(genres).build();
    }

    private Genre buildGenre(String name, Integer id) {
        Genre genre = new Genre();
        genre.name = name;
        genre.id = id;
        return genre;
    }

    private BaseMovie buildBaseMovie(
            Integer id,
            String backdrop_path,
            List<Integer> genre_ids,
            String original_title,
            String overview,
            String poster_path,
            Date release_date,
            String title,
            Double vote_average,
            Integer vote_count
    ) {
        BaseMovie baseMovie = new BaseMovie();
        baseMovie.id = id;
        baseMovie.adult = false;
        baseMovie.backdrop_path = backdrop_path;
        baseMovie.genres = null;
        baseMovie.genre_ids = genre_ids;
        baseMovie.original_title = original_title;
        baseMovie.original_language = "en";
        baseMovie.overview = overview;
        baseMovie.popularity = null;
        baseMovie.poster_path = poster_path;
        baseMovie.release_date = release_date;
        baseMovie.title = title;
        baseMovie.vote_average = vote_average;
        baseMovie.vote_count = vote_count;
        baseMovie.media_type = "movie";

        return baseMovie;
    }

    private MovieResultsPage buildMovieResultsPage(List<BaseMovie> results) {
        MovieResultsPage movieResultsPage = new MovieResultsPage();
        movieResultsPage.id = null;
        movieResultsPage.page = 1;
        movieResultsPage.total_pages = 1;
        movieResultsPage.total_results = 4;
        movieResultsPage.results = results;

        return movieResultsPage;
    }

}
