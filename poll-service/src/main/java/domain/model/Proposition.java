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
@Entity
@Table(name = "T_proposition")
@IdClass(KeyGroupMovie.class)
public class Proposition {
    @Id
    @Setter
    @NotNull
    private int group_id;
    @Id
    @Setter
    @NotNull
    private int movie_id;

    @Setter
    @NotNull
    private float score;

    public Proposition(
            @NotNull int group_id, @NotNull int movie_id,
            @NotNull float score) {
        this.group_id = group_id;
        this.movie_id = movie_id;
        this.score = score;
    }

    public static float computeScore(
            float popularity,
            int n_sat_w_genre,
            int n_sat_b_genre,
            int n_match_w_keyword,
            int n_match_b_keyword,
            int n_sat_date,
            float mean_popularity,
            int n_voters,
            int max_match_w_keyword,
            int max_match_b_keyword) {

        int cPopularity = scoreConst.cPopularity;
        int cWGenre = scoreConst.cWGenre;
        int cBGenre = scoreConst.cBGenre;
        int cWKeyword = scoreConst.cWKeyword;
        int cBKeyword = scoreConst.cBKeyword;
        int cDate = scoreConst.cDate;

        float score = 0;
        if (popularity != 0) {
            score += cPopularity * popularity / mean_popularity;
        }
        if (n_sat_w_genre != 0) {
            score += (float) cWGenre * n_sat_w_genre / n_voters;
        }
        if (n_sat_b_genre != 0) {
            score -= (float) cBGenre * n_sat_b_genre / n_voters;
        }
        if (n_match_w_keyword != 0) {
            score += (float) cWKeyword * n_match_w_keyword / (n_voters * max_match_w_keyword);
        }
        if (n_match_b_keyword != 0) {
            score -= (float) cBKeyword * n_match_b_keyword / (n_voters * max_match_b_keyword);
        }
        if (n_sat_date != 0) {
            score += (float) cDate * n_sat_date / n_voters;
        }

        return score;
    }

    public static Proposition fromSuggestionWithCriteria(SuggestionWithCriteria suggestion) {
        int group_id = suggestion.getGroup_id();
        int movie_id = suggestion.getMovie_id();
        float score = computeScore(
                suggestion.getPopularity(),
                suggestion.getN_sat_w_genre(),
                suggestion.getN_sat_b_genre(),
                suggestion.getN_match_w_keyword(),
                suggestion.getN_match_b_keyword(),
                suggestion.getN_sat_date(),
                100,
                6,
                6,
                6);
        // TODO get n_voters from Group-Service
        // mean_popularity should be a more clever metrics eventually evolving though time, needs a memory of previous popularity...
        // max_match also but can be approximated by setting to n_voters

        return new Proposition(group_id, movie_id, score);
    }

    public void checkValidity() throws IllegalArgumentException {
        if (group_id <= 0) {
            throw new IllegalArgumentException(String.format("group_id should be a strictly positive integer, given %d", group_id));
        }
        if (movie_id <= 0) {
            throw new IllegalArgumentException(String.format("movie_id should be a strictly positive integer, given %d", movie_id));
        }
    }

    private static class scoreConst {
        public static int cPopularity = 1;
        public static int cWGenre = 1;
        public static int cBGenre = 1;
        public static int cWKeyword = 1;
        public static int cBKeyword = 1;
        public static int cDate = 1;
    }
}