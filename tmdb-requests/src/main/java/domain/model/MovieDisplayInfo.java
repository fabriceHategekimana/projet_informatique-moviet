package domain.model;

import java.time.Year;
import java.util.List;

public class MovieDisplayInfo {
    public final int id;
    public final String title;
    public final int release_year;
    public final Double score;
    public final String poster_url;
    public final String backdrop_url;
    public final List<String> genres;

    public MovieDisplayInfo(int id, String title, int release_year, Double score, String poster_url, String backdrop_url, List<String> genres) {
        this.id = id;
        this.title = title;
        this.release_year = release_year;
        this.score = score;
        this.poster_url = poster_url;
        this.backdrop_url = backdrop_url;
        this.genres = genres;
    }
}
