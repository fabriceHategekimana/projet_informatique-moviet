package domain.model;

import java.util.Set;
import java.util.HashSet;

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

// https://youtu.be/FeZ5BC0PirQ

@ToString
@Getter
@NoArgsConstructor // need this otherwise can have some problems with PUT (create) requests
@Entity @Table( name="T_groups")// JPA, mapping class - table
@TypeDef( // https://vladmihalcea.com/the-best-way-to-map-an-enum-type-with-jpa-and-hibernate/
        name = "status_db_enum",
        typeClass = StatusDatabaseEnumType.class
)
public class Group {
    @Id @GeneratedValue( strategy=GenerationType.IDENTITY ) // Generated Value, automatically generated following how the db was configured
    @Column(name="group_id")
    private int id;
    @Setter @NotNull
    private String name;

    @Setter
    private int admin_id = 0;  // id of admin of the group, 0 for none

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "T_groups_users",
            joinColumns={@JoinColumn(name="group_id", referencedColumnName="group_id")},
            inverseJoinColumns={@JoinColumn(name="user_id", referencedColumnName="user_id")})
    @Setter
    private Set<User> users = new HashSet<>();  // https://www.appsdeveloperblog.com/infinite-recursion-in-objects-with-bidirectional-relationships/
    // https://thorben-janssen.com/6-hibernate-mappings-you-should-avoid-for-high-performance-applications/

    @Setter @Enumerated(EnumType.STRING) @Type( type = "status_db_enum" )
    private Status group_status = Status.CHOOSING;

    public Group(String name){
        this.name = name;
    }

    public void addUser(User user) {
        this.users.add(user);
        user.getGroups().add(this);
    }

    public void removeUser(User user) {
        this.users.remove(user);
        user.getGroups().remove(this);
    }
}