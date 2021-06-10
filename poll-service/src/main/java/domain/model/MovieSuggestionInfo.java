package domain.model;

import java.util.List;

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
}
