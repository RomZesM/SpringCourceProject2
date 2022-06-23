package myCRUDappRMZ.utils;

import myCRUDappRMZ.model.Person;
import myCRUDappRMZ.servicies.PersonServicies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class PersonValidator implements Validator {
	private final PersonServicies personServicies;
	
	@Autowired
	public PersonValidator(PersonServicies personServicies){
		this.personServicies = personServicies;
	}
	
	@Override
	//need to specify what class we want to validate
	public boolean supports(Class<?> aClass) {
		//check if clazz = person and return boolean
		return Person.class.equals(aClass);
	}
	
	@Override
	//what we want to validate
	public void validate(Object target, Errors errors) {
	Person person = (Person) target;
	//check DB if there is person with that email
	
//	if(personDAO.show(person.getName()) != null)
//		errors.rejectValue("email", "", "This email is not uniq");
	}
}
