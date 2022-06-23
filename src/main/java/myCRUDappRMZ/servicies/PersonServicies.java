package myCRUDappRMZ.servicies;

import myCRUDappRMZ.model.Book;
import myCRUDappRMZ.model.Person;
import myCRUDappRMZ.repositories.BookRepositories;
import myCRUDappRMZ.repositories.PersonRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PersonServicies {
	
	private final PersonRepositories personRepositories;
	private final BookRepositories bookRepositories;
	
	@Autowired
	public PersonServicies(PersonRepositories personRepositories,
	                       BookRepositories bookRepositories){
		this.personRepositories = personRepositories;
		this.bookRepositories = bookRepositories;
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
	
	
	public List<Book> personBooks(int personid) {
		
		List<Book> allBooks = bookRepositories.findAllByPersonid(personid);
		//check if book was expired
		for (Book book: allBooks) {
			
			long expired = (new Date().getTime() - book.getBorrowtime().getTime()) / 1000 / 60 / 60 / 24;
			if(expired > 10)
				book.setExpired(true);
			else
				book.setExpired(false);
		}
		return allBooks;
	}
}
