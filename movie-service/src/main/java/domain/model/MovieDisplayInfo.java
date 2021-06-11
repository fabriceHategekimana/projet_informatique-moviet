package domain.model;

import com.uwetrottmann.tmdb2.entities.Movie;
import domain.service.TmdbConfiguration;

import java.time.ZoneId;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class MovieDisplayInfo {
    public final int id;
    public final String title;
    public final int release_year;
    public final String tagline;
    public final String overview;
    public final Double score;
    public final String poster_url;
    public final String backdrop_url;
    public final List<String> genres;

    public MovieDisplayInfo(
            int id,
            String title,
            int release_year,
            String tagline,
            String overview,
            Double score,
            String poster_url,
            String backdrop_url,
            List<String> genres
    ) {
        this.id = id;
        this.title = title;
        this.release_year = release_year;
        this.tagline = tagline;
        this.overview = overview;
        this.score = score;
        this.poster_url = poster_url;
        this.backdrop_url = backdrop_url;
        this.genres = genres;
    }

    private static String getBaseUrl() {
        return TmdbConfiguration.getInstance().getBaseUrl();
    }

    public MovieDisplayInfo(Movie movie, String poster_size, String backdrop_size){
        this(
                Objects.requireNonNull(movie.id),
                movie.title,
                Objects.requireNonNull(movie.release_date).toInstant().atZone(ZoneId.systemDefault()).toLocalDate().getYear(),
                movie.tagline,
                movie.overview,
                movie.vote_average,
                getBaseUrl() + poster_size + movie.poster_path,
                getBaseUrl() + backdrop_size + movie.backdrop_path,
                Objects.requireNonNull(movie.genres).stream().map(genre -> genre.name).collect(Collectors.toList())
        );
    }

    public MovieDisplayInfo(Movie movie){
        this(movie, "original", "original");
    }
}
