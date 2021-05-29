package domain.model;

// These three are from @Data but we add @Setter one by one and the constructor.
import lombok.ToString;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
// https://projectlombok.org/features/all


@ToString
@Getter
@NoArgsConstructor // need this otherwise can have some problems with PUT (create) requests
@TypeDef( // https://vladmihalcea.com/the-best-way-to-map-an-enum-type-with-jpa-and-hibernate/
        name = "status_db_enum",
        typeClass = StatusDatabaseEnumType.class
)
@Entity @Table( name="T_groups_users")// JPA, mapping class - table
public class GroupUser {
    @Id @GeneratedValue( strategy=GenerationType.IDENTITY ) // Generated Value, automatically generated following how the db was configured
    private int id;
    // No setters for group_id and user_id, we do not use this class to change it directly
    @NotNull
    private int group_id;
    @NotNull
    private int user_id;

    @Setter @Enumerated(EnumType.STRING) @Type( type = "status_db_enum" )
    private Status user_status = Status.CHOOSING;
    /*
    We use this:
    https://vladmihalcea.com/the-best-way-to-map-an-enum-type-with-jpa-and-hibernate/

    We use do not use the converter.. see links below and StatusConverter
    https://www.baeldung.com/jpa-mapping-single-entity-to-multiple-tables
    https://www.baeldung.com/jpa-persisting-enums-in-jpa
     */

}