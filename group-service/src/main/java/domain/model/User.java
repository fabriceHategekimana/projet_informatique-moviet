package domain.model;

import java.util.Set;
import java.util.HashSet;

// These three are from @Data but we add @Setter one by one and the constructor.
import lombok.ToString;
import lombok.Getter;

import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
// https://projectlombok.org/features/all

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;

import javax.persistence.ManyToMany;
import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.FetchType;
// https://youtu.be/FeZ5BC0PirQ

import domain.model.Group;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@ToString
@Getter
@NoArgsConstructor // need this otherwise can have some problems with PUT (create) requests
@Entity @Table( name="T_users")// JPA, mapping class - table
public class User {
    @Id @GeneratedValue( strategy=GenerationType.IDENTITY ) // Generated Value, automatically generated following how the db was configured
    @Column(name="user_id")
    private int id;
    @Setter @NotNull
    private String name;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "T_groups_users",
            joinColumns={@JoinColumn(name="user_id", referencedColumnName="user_id")},
            inverseJoinColumns={@JoinColumn(name="group_id", referencedColumnName="group_id")})
    @Setter @JsonBackReference
    private Set<Group> groups = new HashSet<Group>();  // https://www.appsdeveloperblog.com/infinite-recursion-in-objects-with-bidirectional-relationships/
    // https://thorben-janssen.com/6-hibernate-mappings-you-should-avoid-for-high-performance-applications/
    public User(String name){
        this.name = name;
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