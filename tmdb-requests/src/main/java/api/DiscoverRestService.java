package api;

import com.uwetrottmann.tmdb2.entities.DiscoverFilter;
import com.uwetrottmann.tmdb2.entities.MovieResultsPage;
import com.uwetrottmann.tmdb2.entities.TmdbDate;
import com.uwetrottmann.tmdb2.enumerations.SortBy;
import domain.helper.DiscoverFilterBuilder;
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
            @QueryParam("release_date_gte") TmdbDate release_date_gte,
            @QueryParam("release_date_lte") TmdbDate release_date_lte,
            @QueryParam("genres") Integer[] genres,
            @QueryParam("keywords") Integer[] keywords,
            @QueryParam("banned_genres") Integer[] banned_genres,
            @QueryParam("banned_keywords") Integer[] banned_keywords,
            @QueryParam("genres_operator") String genres_operator,
            @QueryParam("keywords_operator") String keywords_operator) {

        MovieResultsPage movieResultsPage = movieRequester.discover(new Query(
                sortBy,
                release_date_gte,
                release_date_lte,
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
            @QueryParam("release_date_gte") TmdbDate release_date_gte,
            @QueryParam("release_date_lte") TmdbDate release_date_lte,
            @QueryParam("genres") Integer[] genres,
            @QueryParam("keywords") Integer[] keywords,
            @QueryParam("banned_genres") Integer[] banned_genres,
            @QueryParam("banned_keywords") Integer[] banned_keywords,
            @QueryParam("genres_operator") String genres_operator,
            @QueryParam("keywords_operator") String keywords_operator) {

        List<Integer> ids = MovieConverter.pageToIds(movieRequester.discover(new Query(
                sortBy,
                release_date_gte,
                release_date_lte,
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
            @QueryParam("release_date_gte") TmdbDate release_date_gte,
            @QueryParam("release_date_lte") TmdbDate release_date_lte,
            @QueryParam("genres") Integer[] genres,
            @QueryParam("keywords") Integer[] keywords,
            @QueryParam("banned_genres") Integer[] banned_genres,
            @QueryParam("banned_keywords") Integer[] banned_keywords,
            @QueryParam("genres_operator") String genres_operator,
            @QueryParam("keywords_operator") String keywords_operator) {

        List<MovieDisplayInfo> movieDisplayInfoList = MovieConverter.pageToDisplayInfo(movieRequester.discover(new Query(
                sortBy,
                release_date_gte,
                release_date_lte,
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

    public static class Query {
        public SortBy sortBy;
        public TmdbDate release_date_gte;
        public TmdbDate release_date_lte;
        public Integer[] genres;
        public Integer[] keywords;
        public Integer[] banned_genres;
        public Integer[] banned_keywords;
        public String genres_operator;
        public String keywords_operator;

        public Query(SortBy sortBy,
                     TmdbDate release_date_gte,
                     TmdbDate release_date_lte,
                     Integer[] genres,
                     Integer[] keywords,
                     Integer[] banned_genres,
                     Integer[] banned_keywords,
                     String genres_operator,
                     String keywords_operator) {
            this.sortBy = sortBy;
            this.release_date_gte = release_date_gte;
            this.release_date_lte = release_date_lte;
            this.genres = genres;
            this.keywords = keywords;
            this.banned_genres = banned_genres;
            this.banned_keywords = banned_keywords;
            this.genres_operator = genres_operator;
            this.keywords_operator = keywords_operator;
        }

        public DiscoverRequest toDiscoverRequest() {
            return new DiscoverRequest(
                    sortBy,
                    release_date_gte,
                    release_date_lte,
                    DiscoverFilterBuilder.build(stringToSeparator(genres_operator), genres),
                    DiscoverFilterBuilder.build(stringToSeparator(keywords_operator), keywords),
                    new DiscoverFilter(banned_genres),
                    new DiscoverFilter(banned_keywords));
        }

        public DiscoverFilter.Separator stringToSeparator(String s) {
            if (s == null) {
                throw new IllegalArgumentException();
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
    }
}
