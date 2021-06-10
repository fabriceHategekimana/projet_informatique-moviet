package api;

import com.uwetrottmann.tmdb2.entities.DiscoverFilter;
import com.uwetrottmann.tmdb2.entities.Genre;
import com.uwetrottmann.tmdb2.entities.MovieResultsPage;
import com.uwetrottmann.tmdb2.entities.TmdbDate;
import com.uwetrottmann.tmdb2.enumerations.SortBy;
import domain.helper.MovieConverter;
import domain.model.DiscoverRequest;
import domain.model.MovieDisplayInfo;
import domain.service.MovieRequester;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;


@ApplicationScoped // singleton
@Path("/discover")
public class DiscoverRestService {
    private final MovieRequester movieRequester;

    public DiscoverRestService() {
        this.movieRequester = new MovieRequester();
    }

    @GET
    @Path("/page")
    @Produces(MediaType.APPLICATION_JSON)
    public Response discoverPage(
            @QueryParam("page") Integer page,
            @QueryParam("sortBy") SortBy sortBy,
            @QueryParam("release_year_gte") String release_year_gte,
            @QueryParam("release_year_lte") String release_year_lte,
            @QueryParam("genres") Integer[] genres,
            @QueryParam("keywords") Integer[] keywords,
            @QueryParam("banned_genres") Integer[] banned_genres,
            @QueryParam("banned_keywords") Integer[] banned_keywords,
            @QueryParam("genres_operator") String genres_operator,
            @QueryParam("keywords_operator") String keywords_operator) {

        MovieResultsPage movieResultsPage = movieRequester.discover(new Query(
                sortBy,
                release_year_gte,
                release_year_lte,
                genres,
                keywords,
                banned_genres,
                banned_keywords,
                genres_operator,
                keywords_operator).toDiscoverRequest(), page);

        if (movieResultsPage == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(movieResultsPage).build();
    }

    @GET
    @Path("/id")
    @Produces(MediaType.APPLICATION_JSON)
    public Response discoverId(
            @QueryParam("page") Integer page,
            @QueryParam("sortBy") SortBy sortBy,
            @QueryParam("release_year_gte") String release_year_gte,
            @QueryParam("release_year_lte") String release_year_lte,
            @QueryParam("genres") Integer[] genres,
            @QueryParam("keywords") Integer[] keywords,
            @QueryParam("banned_genres") Integer[] banned_genres,
            @QueryParam("banned_keywords") Integer[] banned_keywords,
            @QueryParam("genres_operator") String genres_operator,
            @QueryParam("keywords_operator") String keywords_operator) {

        List<Integer> ids = MovieConverter.pageToIds(movieRequester.discover(new Query(
                sortBy,
                release_year_gte,
                release_year_lte,
                genres,
                keywords,
                banned_genres,
                banned_keywords,
                genres_operator,
                keywords_operator).toDiscoverRequest(), page));

        if (ids == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(ids).build();
    }

    @GET
    @Path("/display")
    @Produces(MediaType.APPLICATION_JSON)
    public Response discoverDisplayInfo(
            @QueryParam("page") Integer page,
            @QueryParam("sortBy") SortBy sortBy,
            @QueryParam("release_year_gte") String release_year_gte,
            @QueryParam("release_year_lte") String release_year_lte,
            @QueryParam("genres") Integer[] genres,
            @QueryParam("keywords") Integer[] keywords,
            @QueryParam("banned_genres") Integer[] banned_genres,
            @QueryParam("banned_keywords") Integer[] banned_keywords,
            @QueryParam("genres_operator") String genres_operator,
            @QueryParam("keywords_operator") String keywords_operator) {

        List<MovieDisplayInfo> movieDisplayInfoList = MovieConverter.pageToDisplayInfo(movieRequester.discover(new Query(
                sortBy,
                release_year_gte,
                release_year_lte,
                genres,
                keywords,
                banned_genres,
                banned_keywords,
                genres_operator,
                keywords_operator).toDiscoverRequest(), page));

        if (movieDisplayInfoList == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(movieDisplayInfoList).build();
    }


    @GET
    @Path("/config/genres")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getGenres() {

        List<Genre> genres = movieRequester.getGenres();

        if (genres == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(genres).build();
    }

    @GET
    @Path("/config/sortKeys")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSortKeys() {

        List<SortBy> genres = movieRequester.getSortKeys();

        if (genres == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(genres).build();
    }

    public static class Query {
        public SortBy sortBy;
        public String release_year_gte;
        public String release_year_lte;
        public Integer[] genres;
        public Integer[] keywords;
        public Integer[] banned_genres;
        public Integer[] banned_keywords;
        public String genres_operator;
        public String keywords_operator;

        public Query(SortBy sortBy,
                     String release_year_gte,
                     String release_year_lte,
                     Integer[] genres,
                     Integer[] keywords,
                     Integer[] banned_genres,
                     Integer[] banned_keywords,
                     String genres_operator,
                     String keywords_operator) {
            this.sortBy = sortBy;
            this.release_year_gte = release_year_gte;
            this.release_year_lte = release_year_lte;
            this.genres = genres;
            this.keywords = keywords;
            this.banned_genres = banned_genres;
            this.banned_keywords = banned_keywords;
            this.genres_operator = genres_operator;
            this.keywords_operator = keywords_operator;
        }

        public static TmdbDate stringToTmdbDate(String year, String month, String day) {
            return new TmdbDate(year + "-" + month + "-" + day);
        }

        private static TmdbDate yearLteToDate(String yearLte) {
            if (yearLte == null) {
                return null;
            }
            return stringToTmdbDate(yearLte, "01", "01");
        }

        private static TmdbDate yearGteToDate(String yearGte) {
            if (yearGte == null) {
                return null;
            }
            return stringToTmdbDate(yearGte, "12", "31");
        }

        public static DiscoverFilter.Separator stringToSeparator(String s) {
            if (s == null) {
                return DiscoverFilter.Separator.AND;
            }
            switch (s) {
                case "AND":
                    return DiscoverFilter.Separator.AND;
                case "OR":
                    return DiscoverFilter.Separator.OR;
                default:
                    throw new IllegalArgumentException();
            }
        }

        public DiscoverRequest toDiscoverRequest() {
            return new DiscoverRequest(
                    sortBy,
                    yearGteToDate(release_year_gte),
                    yearLteToDate(release_year_lte),
                    new DiscoverFilter(stringToSeparator(genres_operator), genres),
                    new DiscoverFilter(stringToSeparator(keywords_operator), keywords),
                    new DiscoverFilter(banned_genres),
                    new DiscoverFilter(banned_keywords));
        }
    }
}
