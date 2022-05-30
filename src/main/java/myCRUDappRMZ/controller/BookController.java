package myCRUDappRMZ.controller;

import myCRUDappRMZ.dao.BookDAO;
import myCRUDappRMZ.dao.PersonDAO;
import myCRUDappRMZ.model.Book;
import myCRUDappRMZ.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.sound.midi.Soundbank;
import javax.validation.Valid;

@Controller
@RequestMapping("/books")
public class BookController {
	private BookDAO bookDAO;
	private PersonDAO personDAO;
	
	@Autowired
	public BookController(BookDAO bookDAO, PersonDAO personDAO){
		this.bookDAO = bookDAO;
		this.personDAO = personDAO;
		
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
	public String createNewBook(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult){
		if(bindingResult.hasErrors())
			return "book/new";
		bookDAO.add(book);
		return "redirect:books/index";
		
	}
	@GetMapping("/{bookid}")//{} -> mean that it could be any id and we get it with @PathVariable
	public String show(@PathVariable int bookid, Model model, @ModelAttribute("personX") Person person){
		if(bookDAO.show(bookid).getPersonOwnerId() > 0)
			model.addAttribute("owner", personDAO.show(bookDAO.show(bookid).getPersonOwnerId()));
		model.addAttribute("book", bookDAO.show(bookid));
		model.addAttribute("personList", personDAO.index());//put all person in the model for drop-down menu
	//	model.addAttribute("person", new Person());//put an empty object fot list view in form
		return "/book/id";
	}
	
	@GetMapping("/{bookid}/edit")
	public String edit(@PathVariable int bookid, Model model){
		//put an object to fill empty form for editing
		model.addAttribute("book", bookDAO.show(bookid));
		return "/book/edit";
	}
	@PatchMapping("/{bookid}")
	public String update(@PathVariable int bookid, @ModelAttribute("book") @Valid Book book,
						BindingResult bindingResult){
		//check if we have errors in binding result
		if(bindingResult.hasErrors())
			return "/book/edit"; //return page with error message
		bookDAO.update(bookid, book);
		return "redirect:index";
	}
	
	@DeleteMapping("/{bookid}")
	public String delete(@PathVariable int bookid){
		bookDAO.delete(bookid);
		return "redirect:index";
		
	}
	//borrow book to person
	@PatchMapping("/addToPerson/{bookid}")
	public String addBookToPerson(@ModelAttribute("personX") Person person,
	                              Model model, @PathVariable("bookid") int bookid){
		System.out.println(person);
		System.out.println(bookid);
		bookDAO.setOwner(bookid, person.getPersonid());
		
		//return "redirect:/books/index";
		return "redirect:/books/index";
	}
	
	@PatchMapping("/freeFromPerson/{bookid}")
	public String freeBookFromPerson(@ModelAttribute ("personX") Person person,
	                                   @PathVariable("bookid") int bookid)
	{
		bookDAO.freeBook(bookid);
		return "redirect:/books/index";
	}
	
}
