package domain.service;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

import javax.ws.rs.core.Response;

public interface PollServiceRequesterInterface {
    @POST("poll-service/poll/choice/{group_id}")
    Call<Response> sendMoviePreferences(
            @Path("group_id") int group_id,
            @Query("release_year_gte") String release_date_gte,
            @Query("release_year_lte") String release_date_lte,
            @Query("genres") Integer[] genres,
            @Query("keywords") Integer[] keywords
    );
}
