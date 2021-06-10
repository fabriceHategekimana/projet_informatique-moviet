package domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;
// https://projectlombok.org/features/all

// https://youtu.be/FeZ5BC0PirQ

@ToString
@Getter
@NoArgsConstructor // need this otherwise can have some problems with PUT (create) requests
@Entity
@Table(name = "T_users")// JPA, mapping class - table
public class User {
    @Id
    @NotNull // no automatic generation
    @Column(name = "user_id", length = 255)
    private String id = "0";

    @ManyToMany(mappedBy = "users")
    @Setter
    @JsonIgnore
    private Set<Group> groups = new HashSet<>();  // https://www.appsdeveloperblog.com/infinite-recursion-in-objects-with-bidirectional-relationships/

    // https://thorben-janssen.com/6-hibernate-mappings-you-should-avoid-for-high-performance-applications/
    public User(String id) {
        this.id = id;
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
