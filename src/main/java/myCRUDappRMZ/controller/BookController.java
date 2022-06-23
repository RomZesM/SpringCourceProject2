package myCRUDappRMZ.controller;

import myCRUDappRMZ.model.Book;
import myCRUDappRMZ.model.Person;
import myCRUDappRMZ.servicies.BookServicies;
import myCRUDappRMZ.servicies.PersonServicies;
import myCRUDappRMZ.utils.BookValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@RequestMapping("/books")
public class BookController {
	
	private final BookValidator bookValidator;
	private final BookServicies bookServicies;
	private final PersonServicies personServicies;
	
	@Autowired
	public BookController(PersonServicies personServicies,
	                      BookServicies bookServicies,
	                      BookValidator bookValidator){
		this.personServicies = personServicies;
		this.bookServicies =  bookServicies;
		this.bookValidator = bookValidator;
	}
	
	@GetMapping("/index")
	public String bookIndex(Model model, HttpServletRequest request){
		int pageInt = 0;
		int bookPPInt = 0;
		String page = request.getParameter("page");
		String bookPerPage = request.getParameter("books_per_page");
		String sortByYear = request.getParameter("sort_by_year");
		if(page != null && bookPerPage != null)//make a pagination if get needed parameters
		{
			pageInt = Integer.parseInt(page);
			bookPPInt = Integer.parseInt(bookPerPage);
			model.addAttribute("books", bookServicies.indexPage(pageInt, bookPPInt));
			return "/book/index";
		}
		if(sortByYear != null && sortByYear.contentEquals("true"))//sorting by year
		{
			System.out.println(sortByYear);
			model.addAttribute("books", bookServicies.indexSortingYear());
			return "/book/index";
		}
		
		
//		int bookPerPage = Integer.parseInt(request.getParameter("books_per_page"));
//		System.out.println(page + " : " + bookPerPage );
		//put booklist from bookDao into the model
		model.addAttribute("books", bookServicies.index());
		return "/book/index";
	}
	
	@GetMapping("/new")
	public String newBookForm(Model model){
		//add a new empty object for thymeleaf
		model.addAttribute("book", new Book());
		
		return "/book/new";
	}
	
	@PostMapping
	public String createNewBook(@ModelAttribute("book") @Valid Book book,
	                            BindingResult bindingResult){
		bookValidator.validate(book, bindingResult);
		if(bindingResult.hasErrors())
			return "book/new";
		bookServicies.save(book);
		return "redirect:books/index";
		
	}
	@GetMapping("/{bookid}")//{} -> mean that it could be any id and we get it with @PathVariable
	public String show(@PathVariable int bookid, Model model, @ModelAttribute("personX") Person person){
		if(bookServicies.show(bookid).getOwner() != null)
			model.addAttribute("owner", personServicies.show(bookServicies.show(bookid).getOwner().getPersonid()));
			model.addAttribute("book", bookServicies.show(bookid));
			model.addAttribute("personList", personServicies.index());//put all person in the model for drop-down menu
	//	model.addAttribute("person", new Person());//put an empty object fot list view in form
		return "/book/id";
	}
	
	@GetMapping("/{bookid}/edit")
	public String edit(@PathVariable int bookid, Model model){
		//put an object to fill empty form for editing
		model.addAttribute("book", bookServicies.show(bookid));
		return "/book/edit";
	}
	@PatchMapping("/{bookid}")
	public String update(@PathVariable int bookid, @ModelAttribute("book") @Valid Book book,
						BindingResult bindingResult){
		//check if we have errors in binding result
		//bookValidator.validate(book, bindingResult);
		if(bindingResult.hasErrors())
			return "/book/edit"; //return page with error message
		bookServicies.update(bookid, book);
		return "redirect:index";
	}
	
	@GetMapping("/search")
	public String search(@RequestParam(name="search", required = false) String searchLine, Model model){
		
		if(searchLine != null)
		{
			System.out.println("Search");
			model.addAttribute("searchResult", bookServicies.searchStartingWith(searchLine));
			return "book/search";
		}
		return "book/search";
	}
	
	
	@DeleteMapping("/{bookid}")
	public String delete(@PathVariable int bookid){
		bookServicies.delete(bookid);
		return "redirect:index";
		
	}
	//borrow book to person
	@PatchMapping("/addToPerson/{bookid}")
	public String addBookToPerson(@ModelAttribute("personX") Person person,
	                              Model model, @PathVariable("bookid") int bookid){
		System.out.println(person);
		System.out.println(bookid);
		bookServicies.setOwner(bookid, person.getPersonid());
		
		//return "redirect:/books/index";
		return "redirect:/books/index";
	}
	
	@PatchMapping("/freeFromPerson/{bookid}")
	public String freeBookFromPerson(@ModelAttribute ("personX") Person person,
	                                   @PathVariable("bookid") int bookid)
	{
		bookServicies.freeBook(bookid);
		return "redirect:/books/index";
	}
	
}
