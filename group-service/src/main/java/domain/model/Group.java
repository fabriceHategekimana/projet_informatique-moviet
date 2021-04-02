package domain.model;

import lombok.Data;
import lombok.AllArgsConstructor;
// https://projectlombok.org/features/all

// just a test for lombok and unit testing
@Data
@AllArgsConstructor // without this : empty constructor generated
public class Group {
    private Integer id;
}