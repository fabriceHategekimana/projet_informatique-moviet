package domain.service;

import domain.model.MoviePreferences;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

import java.util.List;

public interface GroupServiceRequesterInterface {
    // To make api requests to Group-Service API (using retrofit 2)
    //      * (infos user) => n_sat_w_genre, n_sat_b_genre, n_sat_w_keyword, n_sat_b_keyword, n_sat_date

    @GET("groups/{group_id}/movie_preferences")
    Call<List<MoviePreferences>> getMoviePreferences(@Path("group_id") String group_id);
}
