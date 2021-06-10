package domain.service;

import domain.model.MoviePreferences;
import domain.model.MovieSuggestionInfo;
import retrofit2.Response;

import javax.ws.rs.InternalServerErrorException;
import java.io.IOException;
import java.util.List;

public class MovietRequesterComputer {
    private final MovietRequester moviet;

    public MovietRequesterComputer() {
        this.moviet = new MovietRequester();
    }

    public List<Integer> discoverId(
            Integer page,
            String sortBy,
            String release_year_gte,
            String release_year_lte,
            Integer[] genres,
            Integer[] keywords,
            Integer[] banned_genres,
            Integer[] banned_keywords,
            String genres_operator,
            String keywords_operator
    ) throws IOException, InternalServerErrorException {
        List<Integer> ids;

        MovieServiceRequesterInterface moviesService = moviet.movieRequester();
        retrofit2.Response<List<Integer>> response = moviesService
                .discoverId(
                        page,
                        sortBy,
                        release_year_gte,
                        release_year_lte,
                        genres,
                        keywords,
                        banned_genres,
                        banned_keywords,
                        genres_operator,
                        keywords_operator)
                .execute();

        if (response.isSuccessful()) {
            ids = response.body();
        } else {
            throw new InternalServerErrorException(response.message());
        }

        return ids;
    }

    public MovieSuggestionInfo getSuggestionInfo(int movie_id) throws IOException {
        MovieSuggestionInfo suggestionInfo;

        MovieServiceRequesterInterface moviesService = moviet.movieRequester();
        Response<MovieSuggestionInfo> response = moviesService
                .getSuggestionInfo(movie_id)
                .execute();

        if (response.isSuccessful()) {
            suggestionInfo = response.body();
        } else {
            throw new InternalServerErrorException(response.message());
        }

        return suggestionInfo;
    }

    public List<MoviePreferences> getMoviePreferences(int group_id) throws IOException {
        List<MoviePreferences> preferences;

        GroupServiceRequesterInterface groupService = moviet.groupRequester();
        Response<List<MoviePreferences>> response = groupService
                .getMoviePreferences(String.valueOf(group_id))
                .execute();

        if (response.isSuccessful()) {
            preferences = response.body();
        } else {
            throw new InternalServerErrorException(response.message());
        }

        return preferences;
    }
}
