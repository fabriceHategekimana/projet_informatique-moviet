package domain.model;

import lombok.ToString;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@ToString
@Getter @Setter
@NoArgsConstructor // need this otherwise can have some problems with PUT (create) requests
@Embeddable // this class won't have its own table, it will be embedded in GroupUser table !
public class YearRange {

    private Integer year_from = null;
    private Integer year_to = null;

}
