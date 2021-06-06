package domain.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

// no JPA in this class, not persisted directly !
@ToString
@Getter @Setter
@NoArgsConstructor // need this otherwise can have some problems with PUT (create) requests
public class MoviePreferences {

    private Set<Integer> keywords_id = new HashSet<>();
    private Set<Integer> genres_id = new HashSet<>();
    private Integer year_from = null;
    private Integer year_to = null;

}
