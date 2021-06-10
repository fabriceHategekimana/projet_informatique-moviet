package domain.model;

import domain.service.MovietRequesterComputer;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@ToString
@Data
@NoArgsConstructor
@Entity
@Table(name = "T_unprocessed")
@IdClass(KeyGroupMovie.class)
public class SuggestionWithCriteria {
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
    private float popularity;
    @Setter
    @NotNull
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

        MovieSuggestionInfo suggestionInfo = null;

        try {
            suggestionInfo = new MovietRequesterComputer().getSuggestionInfo(movie_id);
        } catch (IOException ignore) {
            // Malpractice
        }


        assert suggestionInfo != null;
        float popularity = (float) suggestionInfo.popularity;
        int n_sat_w_genre = countSatisfaction(new HashSet<>(suggestionInfo.genre_ids), null);
        int n_sat_b_genre = countSatisfaction(new HashSet<>(suggestionInfo.genre_ids), null);
        int n_match_w_keyword = countMatch(new HashSet<>(suggestionInfo.keyword_ids), null);
        int n_match_b_keyword = countMatch(new HashSet<>(suggestionInfo.keyword_ids), null);
        int n_sat_date = countSatDate(suggestionInfo.release_year, null);

        // TODO use other services
        return new SuggestionWithCriteria(
                group_id, movie_id,
                popularity,
                n_sat_w_genre, n_sat_b_genre,
                n_match_w_keyword, n_match_b_keyword,
                n_sat_date);
    }

    public static int countSatisfaction(Set<Integer> ids, List<Set<Integer>> listUserPrefs) {
        // https://www.leveluplunch.com/java/examples/count-boolean-true-values-in-arraylist/#java8
        // https://stackoverflow.com/questions/8708542/something-like-contains-any-for-java-set
        return (int) listUserPrefs.stream().filter(userPref -> userPref.stream().anyMatch(ids::contains)).count();
    }

    public static int countMatch(Set<Integer> ids, List<Set<Integer>> listUserPrefs) {
        int count = 0;
        for (Set<Integer> userPref : listUserPrefs) {
            Set<Integer> intersection = new HashSet<>(ids); // use the copy constructor
            intersection.retainAll(userPref);
            count += intersection.size();
        }
        return count;
    }

    public static int countSatDate(int year, List<Objects> listYears) {
        // TODO replace by YearBounds
        Integer y_gte = 0;
        Integer y_lte = 3000;
        return (int) listYears.stream().filter(years -> (y_gte == null || y_gte <= year) && (y_lte == null || year <= y_lte)).count();
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