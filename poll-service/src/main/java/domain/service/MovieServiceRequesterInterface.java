package domain.service;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

import java.util.List;


public interface MovieServiceRequesterInterface {
    // To make api requests to Movie-Service API (using retrofit2)
    //      * discoverMovie
    //      * info necessary to compute pertinence score: (special class to implement in Movie-Service) only popularity for now

    @GET("movie-service/discover/id")
    Call<List<Integer>> discoverId(
            @Query("page") Integer page,
            @Query("sortBy") String sortBy,
            @Query("release_year_gte") String release_date_gte,
            @Query("release_year_lte") String release_date_lte,
            @Query("genres") Integer[] genres,
            @Query("keywords") Integer[] keywords,
            @Query("banned_genres") Integer[] banned_genres,
            @Query("banned_keywords") Integer[] banned_keywords,
            @Query("genres_operator") String genres_operator,
            @Query("keywords_operator") String keywords_operator
    );
}
