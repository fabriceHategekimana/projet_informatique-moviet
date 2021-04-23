package domain.model;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotNull;
// https://projectlombok.org/features/all

// just a test for lombok and unit testing
@Data
@NoArgsConstructor // need this otherwise can have some problems with PUT (create) requests
@AllArgsConstructor // without this : empty constructor generated
public class Group {
    @NotNull
    private String id;
    // need to be a String for http://localhost:10080/groups/{id} to work, otherwise we would have a conversion
    // from string to integer and getGroup wouldn't work as intuitively expected
    @NotNull
    private String name;
}