package domain.model;

import com.uwetrottmann.tmdb2.entities.DiscoverFilter;
import com.uwetrottmann.tmdb2.entities.TmdbDate;
import com.uwetrottmann.tmdb2.enumerations.SortBy;

public class DiscoverRequest {
    public SortBy sortBy;
    public TmdbDate release_date_gte;
    public TmdbDate release_date_lte;
    public DiscoverFilter genres_formula;
    public DiscoverFilter keywords_formula;
    public DiscoverFilter banned_genres_formula;
    public DiscoverFilter banned_keywords_formula;

    public DiscoverRequest(SortBy sortBy, TmdbDate release_date_gte, TmdbDate release_date_lte, DiscoverFilter genres_formula, DiscoverFilter keywords_formula, DiscoverFilter banned_genres_formula, DiscoverFilter banned_keywords_formula) {
        this.sortBy = sortBy;
        this.release_date_gte = release_date_gte;
        this.release_date_lte = release_date_lte;
        this.genres_formula = genres_formula;
        this.keywords_formula = keywords_formula;
        this.banned_genres_formula = banned_genres_formula;
        this.banned_keywords_formula = banned_keywords_formula;
    }
}
