package myCRUDappRMZ.controller;

import myCRUDappRMZ.dao.PersonDAO;
import myCRUDappRMZ.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;

//create imitation of choosing admin among people in the list to work with drop-down list
@Controller
@RequestMapping("/admin")
public class AdminController {
	private final PersonDAO personDAO;
	
	@Autowired
	public AdminController (PersonDAO personDAO){
		this.personDAO = personDAO;
	}
	
	//we need 2 methods one for mapping list, second -> make person an "admin"
	@GetMapping //this method call with GET-REQUEST
	public String adminPage(Model model, @ModelAttribute ("person") Person person){
		//add a List<People> in model from database
		model.addAttribute("people", personDAO.index());
		
		return "/adminPage";
	}
	
	@PatchMapping("/add")
	public String makeAdmin(@ModelAttribute("person") Person person){
		System.out.println(person.getPersonid());
		return "redirect:/people/index";
	}
}

