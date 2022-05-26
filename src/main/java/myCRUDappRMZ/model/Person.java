package myCRUDappRMZ.model;

import javax.validation.constraints.*;

public class Person {
	private int personid;
	@NotEmpty(message = "Name couldn't be empty.")
	@Size(min = 1, max = 30, message = "Name size between 1-30 symbols.")
	private String name;
	@Min(value = 1930, message = "No books for old man!")
	@Max(value = 2004, message = "You need to be older than 18")
	private int dateOfBirth;
	
	
	public Person(){
	}
	public Person(int personId, String name, int dateOfBirth) {
		this.personid = personId;
		this.name = name;
		this.dateOfBirth = dateOfBirth;
	}
	
	public int getPersonid() {
		return personid;
	}
	
	public void setId(int id) {
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
	
	@Override
	public String toString() {
		return getName() + " " + getDateOfBirth() + " " + getPersonid();
	}
}
