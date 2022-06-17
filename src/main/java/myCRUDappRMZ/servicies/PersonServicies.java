package myCRUDappRMZ.servicies;

import myCRUDappRMZ.dao.BookMapper;
import myCRUDappRMZ.model.Book;
import myCRUDappRMZ.model.Person;
import myCRUDappRMZ.repositories.PersonRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PersonServicies {
	
	PersonRepositories personRepositories;
	
	@Autowired
	public PersonServicies(PersonRepositories personRepositories){
		this.personRepositories = personRepositories;
	}
	
	public List<Person> index(){
		return personRepositories.findAll();
	}
	
	public Person show(int id){
		Optional optional = personRepositories.findById(id);
		Person person = (Person) optional.orElse(null);
		return person;
	}
	
	@Transactional
	public void save(Person person){
		personRepositories.save(person);
	}
	
	@Transactional
	public void update(int id, Person updatedPerson){
		updatedPerson.setPersonid(id);
		personRepositories.save(updatedPerson);
	}
	
	@Transactional
	public void delete(int id){
		personRepositories.deleteById(id);
	}
	
	
	public List<Book> personBooks(int id) {
		
		List<Book> allBooks = personRepositories.findAllByPersonid(id);
		
		return allBooks;
	}
}
