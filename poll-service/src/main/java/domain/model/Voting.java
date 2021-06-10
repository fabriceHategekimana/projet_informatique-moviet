package domain.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@ToString
@Data
@NoArgsConstructor
@Entity
@Table(name = "T_voting")
public class Voting {
    @Id
    @Setter
    @NotNull
    private int group_id;

    public Voting(
            @NotNull int group_id) {
        this.group_id = group_id;
    }

    public void checkValidity() throws IllegalArgumentException {
        if (group_id <= 0) {
            throw new IllegalArgumentException(String.format("group_id should be a strictly positive integer, given %d", group_id));
        }
    }
}
