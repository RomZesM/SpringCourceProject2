package myCRUDappRMZ.utils;

import myCRUDappRMZ.model.Book;
import myCRUDappRMZ.servicies.BookServicies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class BookValidator implements Validator {
	BookServicies bookServicies;
	
	@Autowired
	public BookValidator(BookServicies bookServicies){
		this.bookServicies = bookServicies;
	}
	@Override
	public boolean supports(Class<?> classOne) {
		
		return Book.class.equals(classOne);
	}
	
	@Override
	public void validate(Object target, Errors errors) {
		Book book = (Book) target;
		if(bookServicies.findByTitle(book.getTitle()) != null)
			errors.rejectValue("title","", "This book is already in the library");
	}
}
