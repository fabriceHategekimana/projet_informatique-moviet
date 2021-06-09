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
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
// https://projectlombok.org/features/all


@ToString
@Getter
@NoArgsConstructor // need this otherwise can have some problems with PUT (create) requests
@TypeDef( // https://vladmihalcea.com/the-best-way-to-map-an-enum-type-with-jpa-and-hibernate/
        name = "status_db_enum",
        typeClass = StatusDatabaseEnumType.class
)
@Entity @Table( name="T_groups_users")// JPA, mapping class - table
public class GroupUser implements Serializable {
    // serializable : serialize into JSO, XML or a representation different than from memory. Used to send in network

    @Id @GeneratedValue( strategy=GenerationType.IDENTITY ) // Generated Value, automatically generated following how the db was configured
    private int id;
    // No setters for group_id and user_id, we do not use this class to change it directly
    @NotNull
    private int group_id;
    @NotNull @Column(length = 255)
    private String user_id;

    @Setter @Enumerated(EnumType.STRING) @Type( type = "status_db_enum" )
    private Status user_status = Status.CHOOSING;

    /*
    We use this:
    https://vladmihalcea.com/the-best-way-to-map-an-enum-type-with-jpa-and-hibernate/

    We use do not use the converter.. see links below and StatusConverter
    https://www.baeldung.com/jpa-mapping-single-entity-to-multiple-tables
    https://www.baeldung.com/jpa-persisting-enums-in-jpa
     */

    // In another tables !
    /* https://www.callicoder.com/hibernate-spring-boot-jpa-element-collection-demo/
    https://javabydeveloper.com/mapping-collection-of-embeddablecomposite-types-jpa-with-hibernate/
    https://javabydeveloper.com/hibernate-entity-types-vs-value-types/

    @JoinColumns(
    {
            @JoinColumn(name = "group_id", referencedColumnName = "group_id"),
            @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    })
     */
    @Setter
    @ElementCollection(fetch= FetchType.EAGER) // ugliest solution
    @CollectionTable(name = "T_groups_users_keywords", joinColumns={@JoinColumn(name = "group_id", referencedColumnName = "group_id"), @JoinColumn(name = "user_id", referencedColumnName = "user_id")}) // https://docs.oracle.com/javaee/6/api/javax/persistence/CollectionTable.html#joinColumns()
    @Column(name = "keyword_id")
    private Set<Integer> keywords_id = new HashSet<>();
    // primary key is weirdly the group_id and user_id, not id ?! (probably because of intermediate table, the actual primary key in the db is id..)

    @Setter
    @ElementCollection(fetch= FetchType.EAGER) // ugliest solution
    @CollectionTable(name = "T_groups_users_genres", joinColumns={@JoinColumn(name = "group_id", referencedColumnName = "group_id"), @JoinColumn(name = "user_id", referencedColumnName = "user_id")})
    @Column(name = "genre_id")
    private Set<Integer> genres_id = new HashSet<>();

    // In T_groups_users table
    @Setter @Embedded
    private YearRange year_range = new YearRange();


}