package myCRUDappRMZ.controller;

import myCRUDappRMZ.dao.BookDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/books")
public class BookController {
	private BookDAO bookDAO;
	
	@Autowired
	public BookController(BookDAO bookDAO){
		this.bookDAO = bookDAO;
	}
	
	@GetMapping("/index")
	public String bookIndex(Model model){
		//put booklist from bookDao into the model
		model.addAttribute("books", bookDAO.index());
		return "/book/index";
	}
	
	
}
