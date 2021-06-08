package domain.model;

// These three are from @Data but we add @Setter one by one and the constructor.
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
    @Id
    @Column(length=255)
    private String id="0"; // no auto increment
    @Setter @NotNull
    private String firstName;
	@Setter @NotNull
	private String lastName;

	@Setter @NotNull
	private String age;

//	private ArrayList<String> prefs;

    public User(String id, String firstName, String lastName, String age){
        this.id = id;
        this.firstName = firstName;
		this.lastName = lastName;
		this.age = age;

		
//		this.prefs = new ArrayList<String>();
    }
}
