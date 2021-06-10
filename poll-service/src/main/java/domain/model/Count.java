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
@Entity @Table( name="T_count")
@IdClass(KeyGroupMovie.class)
public class Count {
    @Id @Setter @NotNull
    private int group_id;
    @Id @Setter @NotNull
    private int movie_id;

    @Setter @NotNull
    private int nb_yes;
    @Setter @NotNull
    private int nb_no;
    @Setter @NotNull
    private int nb_maybe;

    public Count(
            @NotNull int group_id, @NotNull int movie_id,
            @NotNull int nb_yes, @NotNull int nb_no, @NotNull int nb_maybe) {
        this.group_id = group_id;
        this.movie_id = movie_id;
        this.nb_yes = nb_yes;
        this.nb_no = nb_no;
        this.nb_maybe = nb_maybe;
    }

    public Count(
            @NotNull int group_id, @NotNull int movie_id) {
        this(group_id, movie_id, 0, 0, 0);
    }

    public float computeScore() {
        return (float) (nb_yes - nb_no + .5 * nb_maybe);
    }

    public void checkValidity() throws IllegalArgumentException {
        if (group_id <= 0) {
            throw new IllegalArgumentException(String.format("group_id should be a strictly positive integer, given %d", group_id));
        }
        if (movie_id <= 0) {
            throw new IllegalArgumentException(String.format("movie_id should be a strictly positive integer, given %d", movie_id));
        }
        if (nb_yes < 0) {
            throw new IllegalArgumentException(String.format("nb_yes should be a positive integer, given %d", nb_yes));
        }
        if (nb_no < 0) {
            throw new IllegalArgumentException(String.format("nb_no should be a positive integer, given %d", nb_no));
        }
        if (nb_maybe < 0) {
            throw new IllegalArgumentException(String.format("nb_maybe should be a positive integer, given %d", nb_maybe));
        }
    }
}