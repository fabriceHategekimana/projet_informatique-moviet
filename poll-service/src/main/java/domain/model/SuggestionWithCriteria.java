package domain.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@ToString
@Data
@NoArgsConstructor
@Entity @Table( name="T_unprocessed")
@IdClass(KeyGroupMovie.class)
public class SuggestionWithCriteria {
    @Id @Setter @NotNull
    private int group_id;
    @Id @Setter @NotNull
    private int movie_id;

    @Setter @NotNull
    private float popularity;
    @Setter @NotNull
    private int n_sat_w_genre;
    @Setter
    @NotNull
    private int n_sat_b_genre;
    @Setter
    @NotNull
    private int n_match_w_keyword;
    @Setter
    @NotNull
    private int n_match_b_keyword;
    @Setter
    @NotNull
    private int n_sat_date;

    public SuggestionWithCriteria(
            @NotNull int group_id, @NotNull int movie_id,
            @NotNull float popularity,
            @NotNull int n_sat_w_genre, @NotNull int n_sat_b_genre,
            @NotNull int n_match_w_keyword, @NotNull int n_match_b_keyword,
            @NotNull int n_sat_date) {

        this.group_id = group_id;
        this.movie_id = movie_id;
        this.popularity = popularity;
        this.n_sat_w_genre = n_sat_w_genre;
        this.n_sat_b_genre = n_sat_b_genre;
        this.n_match_w_keyword = n_match_w_keyword;
        this.n_match_b_keyword = n_match_b_keyword;
        this.n_sat_date = n_sat_date;
    }

    public static SuggestionWithCriteria fromRawSuggestion(RawSuggestion suggestion) {
        int group_id = suggestion.getGroup_id();
        int movie_id = suggestion.getMovie_id();
        float popularity = 42;  // from Movie-Service
        int n_sat_w_genre = 42;  // genres from Movie-Service w_genres from Group-Service
        int n_sat_b_genre = 42;  // genres from Movie-Service b_genres from Group-Service
        int n_match_w_keyword = 42;  // keyword from Movie-Service w_keywords from Group-Service
        int n_match_b_keyword = 42;  // keyword from Movie-Service b_keywords from Group-Service
        int n_sat_date = 42;  // dates from Movie-Service w_dates from Group-Service

        // TODO use other services
        return new SuggestionWithCriteria(
                group_id, movie_id,
                popularity,
                n_sat_w_genre, n_sat_b_genre,
                n_match_w_keyword, n_match_b_keyword,
                n_sat_date);
    }

    public void checkValidity() throws IllegalArgumentException {
        if (group_id <= 0) {
            throw new IllegalArgumentException(String.format("group_id should be a strictly positive integer, given '%d'", group_id));
        }
        if (movie_id <= 0) {
            throw new IllegalArgumentException(String.format("movie_id should be a strictly positive integer, given '%d'", movie_id));
        }
        if (n_sat_w_genre < 0) {
            throw new IllegalArgumentException(String.format("n_sat_w_genre should be a positive integer, given '%d'", n_sat_w_genre));
        }
        if (n_sat_b_genre < 0) {
            throw new IllegalArgumentException(String.format("n_sat_b_genre should be a positive integer, given '%d'", n_sat_b_genre));
        }
        if (n_match_w_keyword < 0) {
            throw new IllegalArgumentException(String.format("n_sat_w_keyword should be a positive integer, given '%d'", n_match_w_keyword));
        }
        if (n_match_b_keyword < 0) {
            throw new IllegalArgumentException(String.format("n_sat_b_keyword should be a positive integer, given '%d'", n_match_b_keyword));
        }
        if (n_sat_date < 0) {
            throw new IllegalArgumentException(String.format("n_sat_date should be a positive integer, given '%d'", n_sat_date));
        }
    }
}