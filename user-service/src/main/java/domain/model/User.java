package domain.model;

// These three are from @Data but we add @Setter one by one and the constructor.

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
// https://projectlombok.org/features/all

// https://youtu.be/FeZ5BC0PirQ

@ToString

@NoArgsConstructor
@Getter
@Entity
@Table(name = "T_users")// JPA, mapping class - table
public class User {


    @Id
    @Column(length = 255)
    private String id = "0"; // no auto increment

    @Getter
    @Setter
    @NotNull
    private String username;


    public User(String id, String username) {
        this.id = id;
        this.username = username;
    }
}
