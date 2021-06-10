package api;

import domain.model.Count;
import domain.model.RawSuggestion;
import domain.model.Voting;
import domain.service.MovietRequesterComputer;
import domain.service.SuggestionManager;
import domain.service.VoteManager;
import domain.service.VoteProcessor;
import io.swagger.annotations.ApiOperation;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

@ApplicationScoped // singleton
@Path("/poll")
public class PollRestService {
    private static final Logger LOGGER = Logger.getLogger(PollRestService.class.getName());

    private final SuggestionManager suggestionManager;
    private final VoteManager voteManager;
    private final MovietRequesterComputer movietComputer;
    private final ExecutorService pool;

    public PollRestService() {
        this.suggestionManager = new SuggestionManager();
        this.voteManager = new VoteManager();
        this.movietComputer = new MovietRequesterComputer();
        this.pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    }

    @POST
    @Path("/choice/{group_id}")
    @ApiOperation(value = "From choices add RawSuggestion's to t_unprocessed")
    public Response sendChoices(@PathParam("group_id") int group_id,
                                @QueryParam("page") Integer page,
                                @QueryParam("sortBy") String sortBy,
                                @QueryParam("release_year_gte") String release_year_gte,
                                @QueryParam("release_year_lte") String release_year_lte,
                                @QueryParam("genres") Integer[] genres,
                                @QueryParam("keywords") Integer[] keywords,
                                @QueryParam("banned_genres") Integer[] banned_genres,
                                @QueryParam("banned_keywords") Integer[] banned_keywords,
                                @QueryParam("genres_operator") String genres_operator,
                                @QueryParam("keywords_operator") String keywords_operator) {

        List<Integer> ids;
        LOGGER.log(Level.WARNING, "HELLO");

        try {
            ids = movietComputer.discoverId(
                    page,
                    sortBy,
                    release_year_gte,
                    release_year_lte,
                    genres,
                    keywords,
                    banned_genres,
                    banned_keywords,
                    genres_operator,
                    keywords_operator
            );
        } catch (InternalServerErrorException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
                    "Request to TMDb failed:" + e.getMessage()).build();
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Discover Request didn't succeed.", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
                    "An exception has occurred while requesting TMDb:" + e.toString()).build();
        }

        if (ids == null) {
            LOGGER.log(Level.INFO, "Discover request to TMDb has returned null");
        } else {
            LOGGER.log(Level.INFO, "BBBBBBBBBBBBBBBBBBBB ids not null");
            for (Integer movie_id : ids) {
                try {
                    suggestionManager.addRawSuggestion(new RawSuggestion(group_id, movie_id));
                } catch (EntityExistsException ignore) {
                } catch (Exception e) {
                    LOGGER.log(Level.WARNING, "Encountered an exception while adding RawSuggestion's", e);
                    return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
                }
            }
        }

        return Response.status(Response.Status.OK).build();
    }

    @POST
    @Path("/status/{group_id}")
    @ApiOperation(value = "Add group_id in t_voting")
    public Response beginVote(@PathParam("group_id") int group_id) {

        try {
            voteManager.beginVoting(new Voting(group_id));
        } catch (EntityExistsException e) {
            return Response.status(Response.Status.CONFLICT.getStatusCode(), String.format("Group '%d' has already been found in table", group_id)).build();
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Encountered an exception in beginVote", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }

        Runnable r = new VoteProcessor(group_id);
        pool.execute(r);

        return Response.status(Response.Status.OK).build();
    }

    @DELETE
    @Path("/status/{group_id}")
    @ApiOperation(value = "Remove group_id from all tables")
    public Response endVote(@PathParam("group_id") int group_id) {
        try {
            voteManager.endVoting(group_id);
        } catch (EntityNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND.getStatusCode(), String.format("Group with key %d has not been found in table", group_id)).build();
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Encountered an exception in endVote", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }

        return Response.status(Response.Status.OK).build();
    }

    @POST
    @Path("/vote/{group_id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Vote YES/NO/MAYBE on a designated movie and increment count in t_count")
    public Response sendVote(@PathParam("group_id") int group_id, int movie_id, VoteEnum vote) {
        try {
            switch (vote) {
                case YES:
                    voteManager.incrementYes(group_id, movie_id);
                    break;
                case NO:
                    voteManager.incrementNo(group_id, movie_id);
                    break;
                case MAYBE:
                    voteManager.incrementMaybe(group_id, movie_id);
                    break;
                default:
                    return Response.status(Response.Status.BAD_REQUEST.getStatusCode(),
                            String.format("invalid vote value: '%s'", vote)).build();
            }
        } catch (EntityNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND.getStatusCode(),
                    String.format("Count with key (%d, %d) has not been found in table", group_id, movie_id)).build();
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Encountered an exception in sendVote", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }

        return Response.status(Response.Status.OK).build();
    }

    @GET
    @Path("/result/{group_id}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Get list of most voted movies and associated count")
    public Response getResults(@PathParam("group_id") int group_id) {
        List<Count> counts;
        try {
            counts = voteManager.getResults(group_id);
            if (counts == null) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Encountered an exception in getResults", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }

        return Response.ok(counts).build();
    }

    @DELETE
    @Path("/result/{group_id}")
    @ApiOperation(value = "Clear t_proposition of group_id so new propositions can be filled in")
    public Response newRound(@PathParam("group_id") int group_id) {
        try {
            LOGGER.log(Level.WARNING, "HELLO I am in result");
            if (voteManager.deleteAllPolls(group_id) == 0) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Encountered an exception in newRound", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }

        return Response.status(Response.Status.OK).build();
    }

    public enum VoteEnum {
        YES("YES"),
        NO("NO"),
        MAYBE("MAYBE");

        private final String value;

        VoteEnum(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return value;
        }
    }

}
