package myCRUDappRMZ.dao;

import myCRUDappRMZ.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ParameterizedPreparedStatementSetter;
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
		return jdbcTemplate.query("SELECT * FROM Person", new BeanPropertyRowMapper<>(Person.class));
	}
	
	//method to show 1 person with certain index (return Person.Object)\
	public Person show (int id){
		//jdbcTemplate use PreparedStatement by default -->
		//second arg is an array with an arguments for prepared statement (replace "?" by order)
		//query return list, but we need only one object, or NULL if there is nothing
		return jdbcTemplate.query("SELECT * FROM Person WHERE id=?",  new Object[]{id}, new PersonMapper())
				.stream().findAny().orElse(null);
	}
	//overload SHOW method, receive an email, and check if there are a copy of it
	public Person show(String email){
		return jdbcTemplate.query("SELECT * FROM Person WHERE email=?",
						new BeanPropertyRowMapper<>(Person.class), email).stream().findAny().orElse(null);
	}
	
	//we get an information from controller <- html-form <- user;
	public void save(Person person)
	{
		//update take VARARGS, that uses as a values for prepared statement
		jdbcTemplate.update("INSERT INTO Person(name, age, email) VALUES(?, ?, ?)", person.getName(), person.getAge(),
		person.getEmail());
	}
	
	public void update(int id, Person updatedPerson){
		jdbcTemplate.update("UPDATE Person SET name=?, age=?, email=? WHERE id=?",
				updatedPerson.getName(),
				updatedPerson.getAge(),
				updatedPerson.getEmail(),
				id);
	}
	
	public void delete(int id){
		jdbcTemplate.update("DELETE FROM Person WHERE id=?", id);
		
	}
	
	
	/*************************
	 * Тестируем Batch query *
	 *************************/
	
	public void insert1000withoutBatch() {
		List<Person> personList = create1000persons();
		long before = System.currentTimeMillis();
		for (Person person: personList) {
			jdbcTemplate.update("INSERT INTO Person VALUES(?, ?, ?)",
					person.getName(),
					person.getAge(),
					person.getEmail()
			);
		}
		long after = System.currentTimeMillis();
		System.out.println("NoBatch " + (after - before));
	}
	
	public void insert1000withBatch(){
		List<Person> personList = create1000persons();
		long before = System.currentTimeMillis();
		jdbcTemplate.batchUpdate("INSERT INTO Person(name, age, email) VALUES(?, ?, ?)",
				new BatchPreparedStatementSetter() {
					
					@Override
					public void setValues(PreparedStatement ps, int i) throws SQLException {
						//ps.setInt(1, personList.get(i).getId());
						ps.setString(1, personList.get(i).getName());
						ps.setInt(2, personList.get(i).getAge());
						ps.setString(3, personList.get(i).getEmail());
					}
					@Override
					public int getBatchSize() {
						return personList.size();
					}
					
				});
		long after = System.currentTimeMillis();
		System.out.println("Batch " + (after - before));
	}
	
	
	public List<Person> create1000persons(){
		List<Person> list = new ArrayList<>();
		
		for (int i = 0; i < 1000; i++) {
			list.add(new Person(i, "Name"+i, "email" +i + "@mauk.ru", 30 ));
		}
		
		return list;
	}
}
