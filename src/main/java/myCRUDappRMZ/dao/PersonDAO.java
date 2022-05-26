package myCRUDappRMZ.dao;

import myCRUDappRMZ.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.*;

import java.util.ArrayList;
import java.util.List;

@Component
public class PersonDAO {
	public static int PEOPLE_COUNT;
	//
	private JdbcTemplate jdbcTemplate;
	//inject jdbcTemplate object with spring
	@Autowired
	public PersonDAO(JdbcTemplate jdbcTemplate)
	{
		this.jdbcTemplate = jdbcTemplate;
	}
	
	//method to show all list (with the help of model and thymeleaf)
	public List<Person> index(){
		//arguments for the query: SQL query, and Mapper (PersonMapper)
		//or we can use BeanPropertyRowMapper if names of DBTable columns = names of fields
		List<Person> lll = jdbcTemplate.query("SELECT * FROM Person", new PersonMapper());
		
		return lll;
	}
	
	//method to show 1 person with certain index (return Person.Object)\
	public Person show (int id){
		//jdbcTemplate use PreparedStatement by default -->
		//second arg is an array with an arguments for prepared statement (replace "?" by order)
		//query return list, but we need only one object, or NULL if there is nothing
		return jdbcTemplate.query("SELECT * FROM Person WHERE personid=?",  new Object[]{id}, new PersonMapper())
				.stream().findAny().orElse(null);
	}
	//overload SHOW method, receive an email, and check if there are a copy of it
//	public Person show(String email){
//		return jdbcTemplate.query("SELECT * FROM Person WHERE email=?",
//						new BeanPropertyRowMapper<>(Person.class), email).stream().findAny().orElse(null);
//	}
	
	//we get an information from controller <- html-form <- user;
	public void save(Person person)
	{
		//update take VARARGS, that uses as a values for prepared statement
		jdbcTemplate.update("INSERT INTO Person(name, dateofbirth) VALUES(?, ?)", person.getName(), person.getDateOfBirth());
	}
	
	public void update(int id, Person updatedPerson){
		System.out.println(updatedPerson + " " + id);
		jdbcTemplate.update("UPDATE Person SET name=?, dateofbirth=? WHERE personid=?",
				updatedPerson.getName(),
				updatedPerson.getDateOfBirth(),
				id);
	}
	
	public void delete(int id){
		jdbcTemplate.update("DELETE FROM Person WHERE personid=?", id);
		
	}
	
}
