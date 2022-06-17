package myCRUDappRMZ.model;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.List;

@Entity
@Table(name = "Person")
public class Person {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "personid")
	private int personid;
	
	@Column(name = "name")
	@NotEmpty(message = "Name couldn't be empty.")
	@Size(min = 1, max = 50, message = "Name size between 1-50 symbols.")
	private String name;
	
	@Column(name = "dateofbirth")
	@Min(value = 1930, message = "No books for old man!")
	@Max(value = 2004, message = "You need to be older than 18")
	private int dateOfBirth;
	
	@OneToMany(mappedBy = "owner")
	private List<Book> boosList;
	
	public Person(){
	}
	public Person(int personid, String name, int dateOfBirth) {
		this.personid = personid;
		this.name = name;
		this.dateOfBirth = dateOfBirth;
	}
	
	public int getPersonid() {
		return personid;
	}
	
	public void setPersonid(int id) {
		this.personid = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int getDateOfBirth() {
		return dateOfBirth;
	}
	
	public void setDateOfBirth(int dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	
	public List<Book> getBoosList() {
		return boosList;
	}
	
	public void setBoosList(List<Book> boosList) {
		this.boosList = boosList;
	}
	
	@Override
	public String toString() {
		return "Person: " + getName() + " " + getDateOfBirth() + " " + getPersonid();
	}
}
