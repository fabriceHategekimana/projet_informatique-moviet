package domain.model;

import java.util.HashSet;
import java.util.Set;

import lombok.ToString;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@ToString
@Getter @Setter
@NoArgsConstructor // need this otherwise can have some problems with PUT (create) requests
@Embeddable // this class won't have its own table, it will be embedded in GroupUser table !
public class MoviePreferences {

    private Set<Integer> keywords_id = new HashSet<>(); // can have null inside
    private Set<Integer> genres_id = new HashSet<>(); // can have null inside
    private Integer year_from = null;
    private Integer year_to = null;

}
