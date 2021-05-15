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

import domain.model.User;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@ToString
@Getter
@NoArgsConstructor // need this otherwise can have some problems with PUT (create) requests
@Entity @Table( name="T_groups")// JPA, mapping class - table
public class Group {
    @Id @GeneratedValue( strategy=GenerationType.IDENTITY ) // Generated Value, automatically generated following how the db was configured
    @Column(name="group_id")
    private int id;
    @Setter @NotNull
    private String name;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "T_groups_users",
            joinColumns={@JoinColumn(name="group_id", referencedColumnName="group_id")},
            inverseJoinColumns={@JoinColumn(name="user_id", referencedColumnName="user_id")})
    @Setter @JsonManagedReference
    private Set<User> users = new HashSet<User>();  // https://www.appsdeveloperblog.com/infinite-recursion-in-objects-with-bidirectional-relationships/
    // https://thorben-janssen.com/6-hibernate-mappings-you-should-avoid-for-high-performance-applications/
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