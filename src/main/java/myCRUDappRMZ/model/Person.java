package myCRUDappRMZ.model;

import javax.validation.constraints.*;

public class Person {
	private int personid;
	@NotEmpty(message = "Name couldn't be empty.")
	@Size(min = 1, max = 50, message = "Name size between 1-50 symbols.")
	private String name;
	@Min(value = 1930, message = "No books for old man!")
	@Max(value = 2004, message = "You need to be older than 18")
	private int dateOfBirth;
	
	
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
	
	@Override
	public String toString() {
		return "Person: " + getName() + " " + getDateOfBirth() + " " + getPersonid();
	}
}
