package myCRUDappRMZ.utils;

import myCRUDappRMZ.dao.BookDAO;
import myCRUDappRMZ.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class BookValidator implements Validator {
	BookDAO bookDAO;
	
	@Autowired
	public BookValidator(BookDAO bookDAO){
		this.bookDAO=bookDAO;
	}
	@Override
	public boolean supports(Class<?> classOne) {
		
		return Book.class.equals(classOne);
	}
	
	@Override
	public void validate(Object target, Errors errors) {
		Book book = (Book) target;
		if(bookDAO.show(book.getTitle()) != null)
			errors.rejectValue("title","", "This book is already in the library");
	}
}
