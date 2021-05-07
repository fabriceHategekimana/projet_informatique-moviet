package domain.service;

import com.uwetrottmann.tmdb2.entities.*;
import com.uwetrottmann.tmdb2.services.SearchService;
import org.jetbrains.annotations.Nullable;
import retrofit2.Response;

import java.util.logging.Level;
import java.util.logging.Logger;

public class SearchRequester implements SearchRequesterInterface {
    private static final Logger LOGGER = Logger.getLogger(SearchRequester.class.getName());
    public final String language;
    public final String region;
    public final Boolean includeAdult;
    private final TmdbConfiguration tmdbConfiguration;

    public SearchRequester() {
        tmdbConfiguration = TmdbConfiguration.getInstance();
        language = "fr";
        region = "CH";
        includeAdult = false;
    }

    @Override
    public CompanyResultsPage searchCompany(String query, @Nullable Integer page) {
        CompanyResultsPage companyResultsPage = null;

        SearchService searchService = tmdbConfiguration.getTmdb().searchService();

        try {
            Response<CompanyResultsPage> response = searchService
                    .company(query, page)
                    .execute();
            if (response.isSuccessful()) {
                companyResultsPage = response.body();
            }
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, String.format("Research for company failed. (query: %s, page: %d)", query, page), e);
        }

        return companyResultsPage;
    }

    @Override
    public CollectionResultsPage searchCollection(String query, @Nullable Integer page) {
        CollectionResultsPage collectionResultsPage = null;

        SearchService searchService = tmdbConfiguration.getTmdb().searchService();

        try {
            Response<CollectionResultsPage> response = searchService
                    .collection(query, page, language)
                    .execute();
            if (response.isSuccessful()) {
                collectionResultsPage = response.body();
            }
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, String.format("Research for collection failed. (query: %s, page: %d, language: %s)", query, page, language), e);
        }

        return collectionResultsPage;
    }

    @Override
    public KeywordResultsPage searchKeyword(String query, @Nullable Integer page) {
        KeywordResultsPage keywordResultsPage = null;

        SearchService searchService = tmdbConfiguration.getTmdb().searchService();

        try {
            Response<KeywordResultsPage> response = searchService
                    .keyword(query, page)
                    .execute();
            if (response.isSuccessful()) {
                keywordResultsPage = response.body();
            }
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, String.format("Research for keyword failed. (query: %s, page: %d)", query, page), e);
        }

        return keywordResultsPage;
    }

    @Override
    public MovieResultsPage searchMovie(String query, @Nullable Integer page, @Nullable String language, @Nullable String region, @Nullable Boolean includeAdult, @Nullable Integer year, @Nullable Integer primaryReleaseYear) {
        MovieResultsPage movieResultsPage = null;

        SearchService searchService = tmdbConfiguration.getTmdb().searchService();

        try {
            Response<MovieResultsPage> response = searchService
                    .movie(query, page, language, region, includeAdult, year, primaryReleaseYear)
                    .execute();
            if (response.isSuccessful()) {
                movieResultsPage = response.body();
            }
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, String.format("Research for movie failed. (query: %s, page: %d, language: %s, region: %s, includeAdult:%b, year:%d, primaryReleaseYear:%d)", query, page, language, region, includeAdult, year, primaryReleaseYear), e);
        }

        return movieResultsPage;
    }

    @Override
    public MediaResultsPage searchMulti(String query, @Nullable Integer page, @Nullable Boolean includeAdult) {
        MediaResultsPage mediaResultsPage = null;

        SearchService searchService = tmdbConfiguration.getTmdb().searchService();

        try {
            Response<MediaResultsPage> response = searchService
                    .multi(query, page, language, region, includeAdult)
                    .execute();
            if (response.isSuccessful()) {
                mediaResultsPage = response.body();
            }
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, String.format("Research for multiple model failed. (query: %s, page: %d, language: %s, region: %s, include_adult: %s)", query, page, language, region, includeAdult), e);
        }

        return mediaResultsPage;
    }

    @Override
    public PersonResultsPage searchPerson(String query, @Nullable Integer page, @Nullable Boolean includeAdult) {
        PersonResultsPage personResultsPage = null;

        SearchService searchService = tmdbConfiguration.getTmdb().searchService();

        try {
            Response<PersonResultsPage> response = searchService
                    .person(query, page, language, region, includeAdult)
                    .execute();
            if (response.isSuccessful()) {
                personResultsPage = response.body();
            }
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, String.format("Research for person failed. (query: %s, page: %d, language: %s, region: %s, include_adult: %s)", query, page, language, region, includeAdult), e);
        }

        return personResultsPage;
    }
}
