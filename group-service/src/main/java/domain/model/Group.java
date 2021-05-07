package domain.model;

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
// https://youtu.be/FeZ5BC0PirQ

@ToString
@Getter
@NoArgsConstructor // need this otherwise can have some problems with PUT (create) requests
@Entity @Table( name="T_groups")// JPA, mapping class - table
public class Group {
    @Id @GeneratedValue( strategy=GenerationType.IDENTITY ) // Generated Value, automatically generated following how the db was configured
    private int id;
    @Setter @NotNull
    private String name;

    public Group(String name){
        this.name = name;
    }
}