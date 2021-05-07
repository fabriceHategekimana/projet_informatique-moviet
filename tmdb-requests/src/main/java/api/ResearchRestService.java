package api;

import com.uwetrottmann.tmdb2.entities.*;
import domain.service.SearchRequester;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@ApplicationScoped // singleton
@Path("/search")
public class ResearchRestService {
    private final SearchRequester searchRequester;

    public ResearchRestService() {
        this.searchRequester = new SearchRequester();
    }

    @GET
    @Path("/company/{query}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response researchCompany(@PathParam("query") String query) {

        CompanyResultsPage companyResultsPage = searchRequester.searchCompany(query, null);

        if (companyResultsPage == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(companyResultsPage).build();
    }

    @GET
    @Path("/company/{query}/{page}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response researchCompanyPage(@PathParam("query") String query, @PathParam("page") Integer page) {

        CompanyResultsPage companyResultsPage = searchRequester.searchCompany(query, page);

        if (companyResultsPage == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(companyResultsPage).build();
    }

    @GET
    @Path("/collection/{query}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response researchCollection(@PathParam("query") String query) {

        CollectionResultsPage collectionResultsPage = searchRequester.searchCollection(query, null);

        if (collectionResultsPage == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(collectionResultsPage).build();
    }

    @GET
    @Path("/collection/{query}/{page}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response researchCollectionPage(@PathParam("query") String query, @PathParam("page") Integer page) {

        CollectionResultsPage collectionResultsPage = searchRequester.searchCollection(query, page);

        if (collectionResultsPage == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(collectionResultsPage).build();
    }

    @GET
    @Path("/keyword/{query}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response researchKeyword(@PathParam("query") String query) {

        KeywordResultsPage keywordResultsPage = searchRequester.searchKeyword(query, null);

        if (keywordResultsPage == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(keywordResultsPage).build();
    }

    @GET
    @Path("/keyword/{query}/{page}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response researchKeywordPage(@PathParam("query") String query, @PathParam("page") Integer page) {

        KeywordResultsPage keywordResultsPage = searchRequester.searchKeyword(query, page);

        if (keywordResultsPage == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(keywordResultsPage).build();
    }

    @GET
    @Path("/multi/{query}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response researchMedia(@PathParam("query") String query) {

        MediaResultsPage mediaResultsPage = searchRequester.searchMulti(query, null, false);

        if (mediaResultsPage == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(mediaResultsPage).build();
    }

    @GET
    @Path("/multi/{query}/{page}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response researchMediaPage(@PathParam("query") String query, @PathParam("page") Integer page) {

        MediaResultsPage mediaResultsPage = searchRequester.searchMulti(query, page, false);

        if (mediaResultsPage == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(mediaResultsPage).build();
    }

    @GET
    @Path("/movie/{query}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response researchMovie(@PathParam("query") String query, @QueryParam("year") Integer year, @QueryParam("primaryReleaseYear") Integer primaryReleaseYear) {

        MovieResultsPage movieResultsPage = searchRequester.searchMovie(query, null, searchRequester.language, searchRequester.region, searchRequester.includeAdult, year, primaryReleaseYear);

        if (movieResultsPage == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(movieResultsPage).build();
    }

    @GET
    @Path("/movie/{query}/{page}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response researchMoviePage(@PathParam("query") String query, @QueryParam("year") Integer year, @QueryParam("primaryReleaseYear") Integer primaryReleaseYear) {

        MovieResultsPage movieResultsPage = searchRequester.searchMovie(query, null, searchRequester.language, searchRequester.region, searchRequester.includeAdult, year, primaryReleaseYear);

        if (movieResultsPage == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(movieResultsPage).build();
    }

    @GET
    @Path("/person/{query}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response researchPerson(@PathParam("query") String query) {

        PersonResultsPage personResultsPage = searchRequester.searchPerson(query, null, false);

        if (personResultsPage == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(personResultsPage).build();
    }

    @GET
    @Path("/person/{query}/{page}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response researchPersonPage(@PathParam("query") String query, @PathParam("page") Integer page) {

        PersonResultsPage personResultsPage = searchRequester.searchPerson(query, page, false);

        if (personResultsPage == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(personResultsPage).build();
    }
}
