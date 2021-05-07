package domain.service;

import com.uwetrottmann.tmdb2.entities.*;
import org.jetbrains.annotations.Nullable;

public interface SearchRequesterInterface {
    CompanyResultsPage searchCompany(String query, @Nullable Integer page);

    CollectionResultsPage searchCollection(String query, @Nullable Integer page);

    KeywordResultsPage searchKeyword(String query, @Nullable Integer page);

    MovieResultsPage searchMovie(String query, @Nullable Integer page, @Nullable String language, @Nullable String region, @Nullable Boolean includeAdult, @Nullable Integer year, @Nullable Integer primaryReleaseYear);

    MediaResultsPage searchMulti(String query, @Nullable Integer page, @Nullable Boolean includeAdult);

    PersonResultsPage searchPerson(String query, @Nullable Integer page, @Nullable Boolean includeAdult);
}
