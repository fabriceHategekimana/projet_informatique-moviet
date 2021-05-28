package domain.model;

import java.util.Set;
import java.util.HashSet;

// These three are from @Data but we add @Setter one by one and the constructor.
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.ToString;
import lombok.Getter;

import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
// https://projectlombok.org/features/all

// https://youtu.be/FeZ5BC0PirQ

@ToString
@Getter
@NoArgsConstructor // need this otherwise can have some problems with PUT (create) requests
@Entity @Table( name="T_users")// JPA, mapping class - table
public class User {
    @Id @NotNull // no automatic generation
    @Column(name="user_id")
    private int id;

    @ManyToMany(mappedBy = "users")
    @Setter @JsonIgnore
    private Set<Group> groups = new HashSet<>();  // https://www.appsdeveloperblog.com/infinite-recursion-in-objects-with-bidirectional-relationships/
    // https://thorben-janssen.com/6-hibernate-mappings-you-should-avoid-for-high-performance-applications/

    @Setter @Se
    private Status status = Status.CHOOSING;
    /*
    We use the converter ! see links below and StatusConverter
    https://www.baeldung.com/jpa-mapping-single-entity-to-multiple-tables
    https://www.baeldung.com/jpa-persisting-enums-in-jpa
     */

    public User(int id){
        this.id=id;
    }

    public void addGroup(Group group) {
        this.groups.add(group);
        group.getUsers().add(this);
    }

    public void removeGroup(Group group) {
        this.groups.remove(group);
        group.getUsers().remove(this);
    }
}