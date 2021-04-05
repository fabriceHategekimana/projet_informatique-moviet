package domain.model;

import lombok.Data;
import lombok.AllArgsConstructor;
// https://projectlombok.org/features/all

// just a test for lombok and unit testing
@Data
@AllArgsConstructor // without this : empty constructor generated
public class Group {
    private String id;
    // need to be a String for http://localhost:10080/groups/{id} to work, otherwise we would have a conversion
    // from string to integer and getGroup wouldn't work as intuitively expected
}