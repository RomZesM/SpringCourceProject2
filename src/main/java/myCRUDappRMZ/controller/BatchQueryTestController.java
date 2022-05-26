package myCRUDappRMZ.controller;


import myCRUDappRMZ.dao.PersonDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/tbu")
public class BatchQueryTestController {
	
	private final PersonDAO personDAO;
	
	@Autowired
	public BatchQueryTestController(PersonDAO personDAO){
		this.personDAO = personDAO;
	}
	
	@GetMapping()
	public String index(){
		return "batch/buttons";
	}
	
	@GetMapping("without-batch")
		public String insertWithoutBatch(){
		personDAO.insert1000withoutBatch();
		return "redirect:/people/index/";
		}
		
		@GetMapping("with-batch")
		public String insertBatch(){
		personDAO.insert1000withBatch();
		return "redirect:/people/index/";
		}
	
	
		
}
