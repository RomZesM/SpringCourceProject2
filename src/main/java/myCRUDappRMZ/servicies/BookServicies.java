package myCRUDappRMZ.servicies;

import myCRUDappRMZ.model.Book;
import myCRUDappRMZ.repositories.BookInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BookServicies {
	
	BookInterface bookInterface; //instead of dao
	
	@Autowired
	public BookServicies(BookInterface bookInterface){
		this.bookInterface = bookInterface;
	}
	//#1 Book Index
	public List<Book> index(){
		return bookInterface.findAll();
	}
	
	public Book show(int id){
		Optional optional = bookInterface.findById(id);
		Book book = (Book) optional.orElse(null);
		return book;
	}
	@Transactional
	public void save(Book book){
		bookInterface.save(book);
	}
	
	@Transactional
	public void update(int id, Book updatedBook){
		updatedBook.setBookid(id); //thymeleaf return id separate of updated book
		bookInterface.save(updatedBook);
	}
	
	@Transactional
	public void delete(int id){
		bookInterface.deleteById(id);
	}
}
