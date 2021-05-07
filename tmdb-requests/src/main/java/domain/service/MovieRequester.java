package domain.service;

import com.uwetrottmann.tmdb2.DiscoverMovieBuilder;
import com.uwetrottmann.tmdb2.entities.Genre;
import com.uwetrottmann.tmdb2.entities.GenreResults;
import com.uwetrottmann.tmdb2.entities.Movie;
import com.uwetrottmann.tmdb2.entities.MovieResultsPage;
import com.uwetrottmann.tmdb2.services.DiscoverService;
import com.uwetrottmann.tmdb2.services.GenresService;
import com.uwetrottmann.tmdb2.services.MoviesService;
import domain.model.DiscoverRequest;
import domain.model.MovieDisplayInfo;
import org.jetbrains.annotations.Nullable;
import retrofit2.Call;
import retrofit2.Response;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MovieRequester implements MovieRequesterInterface {
    private static final Logger LOGGER = Logger.getLogger(MovieRequester.class.getName());

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

                if (movie != null) {
                    displayInfo = new MovieDisplayInfo(movie, poster_size, backdrop_size);
                }
            }
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, String.format("Couldn't retrieve movie information. (id: %s)", id), e);
        }

        return displayInfo;
    }

    @Override
    public List<Genre> getGenres() {
        List<Genre> genres = null;

        GenresService genreService = tmdbConfiguration.getTmdb().genreService();

        try {
            Response<GenreResults> response = genreService
                    .movie(language)
                    .execute();
            if (response.isSuccessful()) {
                GenreResults genreResults = response.body();

                if (genreResults != null) {
                    genres = genreResults.genres;
                }
            }
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, String.format("Couldn't retrieve possible genres. (language: %s)", language), e);
        }

        return genres;

    }

    @Override
    public MovieResultsPage discover(DiscoverRequest request, @Nullable Integer page) {
        MovieResultsPage movieResultsPage = null;

        DiscoverService discoverService = tmdbConfiguration.getTmdb().discoverService();
        DiscoverMovieBuilder discoverMovieBuilder = new DiscoverMovieBuilder(discoverService);

        try {
            Call<MovieResultsPage> call = discoverMovieBuilder
                    .sort_by(request.sortBy)
                    .page(page)
                    .release_date_gte(request.release_date_gte)
                    .release_date_lte(request.release_date_lte)
                    .with_genres(request.genres_formula)
                    .with_keywords(request.keywords_formula)
                    .without_genres(request.banned_genres_formula)
                    .without_keywords(request.banned_keywords_formula)
                    .build();
            Response<MovieResultsPage> response = call.execute();
            if (response.isSuccessful()) {
                movieResultsPage = response.body();
            }
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Discover Request didn't succeed.", e);
        }

        return movieResultsPage;
    }
}
