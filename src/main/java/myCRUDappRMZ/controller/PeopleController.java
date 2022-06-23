package myCRUDappRMZ.controller;


import myCRUDappRMZ.model.Book;
import myCRUDappRMZ.model.Person;
import myCRUDappRMZ.servicies.PersonServicies;
import myCRUDappRMZ.utils.PersonValidator;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
//all url would start with people
@RequestMapping("/people")
public class PeopleController {
	
	
	private final PersonServicies personServicies;
	private final PersonValidator personValidator;
	@Autowired
	public PeopleController(PersonServicies personServicies, PersonValidator personValidator) {
		this.personServicies = personServicies;
		this.personValidator = personValidator;
	}
	
	
	@GetMapping("/index")
	public String index(Model model){
		//DAO return all people from BD and sent it to the view (with ThymeLEAF)
		model.addAttribute("people", personServicies.index());
		
		return "/people/p_index";
	}
	@GetMapping("/{id}")
	public String show(@PathVariable("id") int id, Model model){
		//DAO return one person with ID(it was sent in HTTP request) and send it to the webView
		//in 'model object'
		System.out.println("Here");
		List<Book> bookL = personServicies.personBooks(id);
		System.out.println(bookL.size());
		model.addAttribute("personBooks", personServicies.personBooks(id)); //TODO
		model.addAttribute("person", personServicies.show(id));
		return "/people/id";
	}
	//method for creating new person
	@GetMapping("/new")
	//thymeleaf need an exemplar of class, to create a html form for creating such new object
	public String newPerson(Model model)
	{
		//put a new object of class person into the model, so thymeleaf can get it
		model.addAttribute("person", new Person());
		//return a form, to fullfill an Person attribute
		return ("/people/new");
	}
	
	//Post request for controller, we get a new object from html form and then add it into DB
	//
	//@valid check fields, that user create, and put error in BindingResult object, u need place it just after @Valid object
	@PostMapping
	public String create(@ModelAttribute("person") @Valid Person person,
	                     BindingResult bindingResult){
		//check, if we get error in fields, we redirect to the NEW view, ModelAttribute put an error
		//object in 'person', and we can correct them
		//then we get a DB query with a PersonController and check if there are a copy of email
		personValidator.validate(person, bindingResult);
		if(bindingResult.hasErrors())
		{
			return("people/new");
		}
		
		//ModelAttribute automatically fill new Peron fields with info from POST request
		
		personServicies.save(person);
		//after adding information to database we can make redirect to something
		//or open a page with information for user, about successfully creation
		return("redirect:/people/index");
	}
	//PATCH (edit existing object in base)
	@GetMapping("/{id}/edit")
	public String edit(Model model, @PathVariable ("id") int id){
		//put in model object we want to edit, because we need to see what we should edit
		model.addAttribute("person", personServicies.show(id));
		return ("people/edit");
	}
	
	//method, that get changed object from html\thymeleaf form and sent it in DAO to update db
	@PatchMapping("/{id}")
	//fill an empty person with data from form
	public String update(@ModelAttribute("person")  @Valid Person person, BindingResult bindingResult,
	                     @PathVariable ("id") int id){
		personValidator.validate(person, bindingResult);
		if (bindingResult.hasErrors())
			return("people/edit");
		
		personServicies.update(id, person);
		return("redirect:/people/index");
	}
	//delete method, receive in request an id of person, we need to delete
	@DeleteMapping("/{id}")
	public String delete(@PathVariable ("id") int id){
		personServicies.delete(id);
		return("redirect:/people/index");
	}
	
}
