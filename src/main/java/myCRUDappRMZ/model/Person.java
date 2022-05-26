package myCRUDappRMZ.model;




import javax.validation.constraints.*;

public class Person {
	private int id;
	@NotEmpty(message = "Name couldn't be empty.")
	@Size(min = 1, max = 30, message = "Name size between 1-30 symbols.")
	private String name;
	@NotEmpty(message = "Email could not be empty.")
	@Email(message = "Email is incoreect.")
	private String email;
	@Min(value = 1, message = "Age must be grater than 0")
	@Max(value = 120, message = "Really?")
	private int age;
	
	
	public Person(){
	}
	public Person(int id, String name, String email, int age) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.age = age;
	}
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public int getAge() {
		return age;
	}
	
	public void setAge(int age) {
		this.age = age;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
}
