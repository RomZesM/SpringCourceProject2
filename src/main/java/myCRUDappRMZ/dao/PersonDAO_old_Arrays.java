/*
package myCRUDappRMZ.dao;

import myCRUDappRMZ.model.Person;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
public class PersonDAO {
	public static int PEOPLE_COUNT;
	public static List<Person> personList;
	
	//initial block for list
	{
		personList = new ArrayList<>();
		personList.add(new Person(++PEOPLE_COUNT, "BOB", "bob@gogle.com", 18));
		personList.add(new Person(++PEOPLE_COUNT, "Sina", "sina@google.com", 19));
		personList.add(new Person(++PEOPLE_COUNT, "Gina", "gina@google.com", 20));
		personList.add(new Person(++PEOPLE_COUNT, "Cruz", "cruz@gmail.com", 21));
		personList.add(new Person(++PEOPLE_COUNT, "Jessica", "jessica@gmail.com", 22));
	}
	
	//method to show all list (with the help of model and thymeleaf)
	public List<Person> index(){
		return personList;
	}
	
	//method to show 1 person with certain index (return Person.Object)\
	public Person show (int ind){
		//we can use cycle in here
		return personList.stream().filter(p->p.getId() == ind).findAny().orElse(null);
	}
	
	//we get an information from controller <- html-form <- user;
	public void save(Person person)
	{
		person.setId(++PEOPLE_COUNT);
		personList.add(person);
	}
	
	public void update(int id, Person person){
		Person personToBeUpdate = show(id);
		personToBeUpdate.setName(person.getName());
		personToBeUpdate.setEmail(person.getEmail());
		personToBeUpdate.setAge(person.getAge());
	}
	
	public void delete(int id){
		personList.removeIf(p -> p.getId() == id);
		//counting ---?
	}
}

*/
