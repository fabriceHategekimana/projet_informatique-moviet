package domain.model;

import lombok.*;

import javax.persistence.*;
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
}