package domain.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@ToString
@Data
@NoArgsConstructor
@Entity @Table( name="T_proposition")
@IdClass(KeyGroupMovie.class)
public class Proposition {
    @Id @Setter @NotNull
    private int group_id;
    @Id @Setter @NotNull
    private int movie_id;

    @Setter @NotNull
    private float score;

    public Proposition(
            @NotNull int group_id, @NotNull int movie_id,
            @NotNull float score) {
        this.group_id = group_id;
        this.movie_id = movie_id;
        this.score = score;
    }

    public void checkValidity() throws IllegalArgumentException{
        if (group_id <= 0){
            throw new IllegalArgumentException(String.format("group_id should be a strictly positive integer, given %d", group_id));
        }
        if (movie_id <= 0){
            throw new IllegalArgumentException(String.format("movie_id should be a strictly positive integer, given %d", movie_id));
        }
    }
}