package myCRUDappRMZ.servicies;

import myCRUDappRMZ.model.Book;
import myCRUDappRMZ.model.Person;
import myCRUDappRMZ.repositories.BookRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BookServicies {
	
	BookRepositories bookRepositories; //instead of dao
	PersonServicies personServicies;
	
	@Autowired
	public BookServicies(BookRepositories bookRepositories,
	                     PersonServicies personServicies){
		this.bookRepositories = bookRepositories;
		this.personServicies = personServicies;
	}
	//#1 Book Index
	public List<Book> index(){
		
		return bookRepositories.findAll();
	}
	
	public List<Book> indexPage(int pageInt, int bookPPInt){
		
		return bookRepositories.findAll(PageRequest.of(pageInt, bookPPInt)).getContent();
	}
	
	public List<Book> indexSortingYear(){
		System.out.println("Inside index Sorting");
		List<Book> sortList = bookRepositories.findAllByOrderByYearDesc();
		return sortList;
	}
	
	public Book show(int id){
		Optional optional = bookRepositories.findById(id);
		Book book = (Book) optional.orElse(null);
		return book;
	}
	@Transactional
	public void save(Book book){
		bookRepositories.save(book);
	}
	
	@Transactional
	public void update(int id, Book updatedBook){
		updatedBook.setBookid(id); //thymeleaf return id separate of updated book
	//	bookRepositories.deleteById(id);
		bookRepositories.save(updatedBook);
	}
	
	@Transactional
	public void delete(int id){
		bookRepositories.deleteById(id);
	}
	
	@Transactional
	public void setOwner(int bookid, int personOwnerId){
		Book book = show(bookid);
		Person pOwner = personServicies.show(personOwnerId);
		book.setOwner(pOwner);
		bookRepositories.save(book);
	}
	@Transactional
	public void freeBook(int bookid){
		Book book = show(bookid);
		book.setOwner(null);
		bookRepositories.save(book);
	}
	
	public Book findByTitle(String title){
		return bookRepositories.findByTitle(title);
		
	}
	
	public List<Book> searchStartingWith(String startingWith){
		System.out.println("In BookServicies -> search");
		List<Book> bookList = bookRepositories.findByTitleStartingWith(startingWith);
		return(bookList);
	}
	
}
