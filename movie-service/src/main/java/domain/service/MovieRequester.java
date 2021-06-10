package domain.service;

import com.uwetrottmann.tmdb2.DiscoverMovieBuilder;
import com.uwetrottmann.tmdb2.entities.*;
import com.uwetrottmann.tmdb2.enumerations.AppendToResponseItem;
import com.uwetrottmann.tmdb2.enumerations.SortBy;
import com.uwetrottmann.tmdb2.services.DiscoverService;
import com.uwetrottmann.tmdb2.services.GenresService;
import com.uwetrottmann.tmdb2.services.MoviesService;
import domain.model.DiscoverRequest;
import domain.model.MovieDisplayInfo;
import domain.model.MovieSuggestionInfo;
import org.jetbrains.annotations.Nullable;
import retrofit2.Call;
import retrofit2.Response;

import java.util.Arrays;
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
    public MovieSuggestionInfo getSuggestionInfo(int id) {
        MovieSuggestionInfo suggestionInfo = null;

        MoviesService moviesService = tmdbConfiguration.getTmdb().moviesService();

        try {
            AppendToResponseItem[] items = {
                    AppendToResponseItem.KEYWORDS
            };
            AppendToResponse appendToResponse = new AppendToResponse(items);
            Response<Movie> response = moviesService
                    .summary(id, language, appendToResponse)
                    .execute();
            if (response.isSuccessful()) {
                Movie movie = response.body();

                if (movie != null) {
                    suggestionInfo = new MovieSuggestionInfo(movie, poster_size, backdrop_size);
                }
            }
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, String.format("Couldn't retrieve movie information. (id: %s)", id), e);
        }

        return suggestionInfo;
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
    public List<SortBy> getSortKeys() {
        return Arrays.asList(
                SortBy.POPULARITY_ASC,
                SortBy.POPULARITY_DESC,
                SortBy.RELEASE_DATE_ASC,
                SortBy.RELEASE_DATE_DESC,
                SortBy.REVENUE_ASC,
                SortBy.REVENUE_DESC,
                SortBy.PRIMARY_RELEASE_DATE_ASC,
                SortBy.PRIMARY_RELEASE_DATE_DESC,
                SortBy.ORIGINAL_TITLE_ASC,
                SortBy.ORIGINAL_TITLE_DESC,
                SortBy.VOTE_AVERAGE_ASC,
                SortBy.VOTE_AVERAGE_DESC,
                SortBy.VOTE_COUNT_ASC,
                SortBy.VOTE_COUNT_DESC
        );
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
