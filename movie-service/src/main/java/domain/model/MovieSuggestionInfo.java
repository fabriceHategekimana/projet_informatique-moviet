package domain.model;

import com.uwetrottmann.tmdb2.entities.Movie;
import domain.service.TmdbConfiguration;

import javax.validation.constraints.NotNull;
import java.time.ZoneId;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class MovieSuggestionInfo {
    public final int id;
    public final double popularity;
    public final int release_year;
    public final List<Integer> genre_ids;
    public final List<Integer> keyword_ids;

    public MovieSuggestionInfo(
            int id,
            double popularity,
            List<Integer> genre_ids,
            List<Integer> keyword_ids,
            int release_year
    ) {
        this.id = id;
        this.popularity = popularity;
        this.genre_ids = genre_ids;
        this.keyword_ids = keyword_ids;
        this.release_year = release_year;
    }

    public MovieSuggestionInfo(@NotNull Movie movie, String poster_size, String backdrop_size) {
        this(
                Objects.requireNonNull(movie.id),
                Objects.requireNonNull(movie.popularity),
                Objects.requireNonNull(movie.genres).stream().map(genre -> genre.id).collect(Collectors.toList()),
                Objects.requireNonNull(Objects.requireNonNull(movie.keywords).keywords).stream().map(keyword -> keyword.id).collect(Collectors.toList()),
                Objects.requireNonNull(movie.release_date).toInstant().atZone(ZoneId.systemDefault()).toLocalDate().getYear()
        );
    }

    public MovieSuggestionInfo(Movie movie) {
        this(movie, "original", "original");
    }

    private static String getBaseUrl() {
        return TmdbConfiguration.getInstance().getBaseUrl();
    }

}
