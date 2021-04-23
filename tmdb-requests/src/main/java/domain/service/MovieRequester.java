package domain.service;

import com.uwetrottmann.tmdb2.entities.Movie;
import com.uwetrottmann.tmdb2.services.MoviesService;
import domain.model.MovieDisplayInfo;
import retrofit2.Response;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MovieRequester implements MovieRequesterInterface {
    public final String language;
    private final TmdbConfiguration tmdbConfiguration;
    private final String poster_size;
    private final String backdrop_size;

    public MovieRequester() {
        tmdbConfiguration = TmdbConfiguration.getInstance();
        language = "fr";
        poster_size = "original";
        backdrop_size = "original";
    }

    @Override
    public MovieDisplayInfo getDisplayInfo(int id) {
        MovieDisplayInfo displayInfo = null;

        MoviesService moviesService = tmdbConfiguration.getTmdb().moviesService();

        try {
            Response<Movie> response = moviesService
                    .summary(id, language)
                    .execute();
            if (response.isSuccessful()) {
                Movie movie = response.body();

                if (movie != null && movie.release_date != null){
                    String title = movie.title;
                    LocalDate localDate = movie.release_date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    int release_year = localDate.getYear();
                    Double score = movie.vote_average;
                    String poster_url = tmdbConfiguration.getBaseUrl() + this.poster_size + movie.poster_path;
                    String backdrop_url = tmdbConfiguration.getBaseUrl() + this.backdrop_size + movie.backdrop_path;

                    List<String> genres = null;
                    if (movie.genres != null){
                        genres = movie.genres.stream().map(genre -> genre.name).collect(Collectors.toList());
                    }

                    displayInfo = new MovieDisplayInfo(id, title, release_year, score, poster_url, backdrop_url, genres);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return displayInfo;
    }
}
