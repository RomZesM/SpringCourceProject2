package myCRUDappRMZ.controller;

import myCRUDappRMZ.dao.BookDAO;
import myCRUDappRMZ.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
	
	@GetMapping("/new")
	public String newBookForm(Model model){
		//add a new empty object for thymeleaf
		model.addAttribute("book", new Book());
		
		return "/book/new";
	}
	
	@PostMapping
	public String createNewBook(@ModelAttribute("book") Book book){
		bookDAO.add(book);
		return "redirect:books/index";
		
	}
	@GetMapping("/{bookid}")//{} -> mean that it could be any id
	public String show(@PathVariable int bookid, Model model){
		model.addAttribute("book", bookDAO.show(bookid));
		return "/book/id";
	}
	
	@GetMapping("/{bookid}/edit")
	public String edit(@PathVariable int bookid, Model model){
		//put an object to fill empty form for editing
		model.addAttribute("book", bookDAO.show(bookid));
		return "/book/edit";
	}
	@PatchMapping("/{bookid}")
	public String update(@PathVariable int bookid, @ModelAttribute("book") Book book){
		bookDAO.update(bookid, book);
		return "redirect:index";
	}
	
	@DeleteMapping("/{bookid}")
	public String delete(@PathVariable int bookid){
		bookDAO.delete(bookid);
		return "redirect:index";
	}
	
}
