package myCRUDappRMZ.dao;

import myCRUDappRMZ.model.Person;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

//translate data from DB request (query) into the Person object to map it (отобразить)
public class PersonMapper implements RowMapper<Person> {
	@Override
	//resultSet contain data, that statement.executeQuery returns
	public Person mapRow(ResultSet resultSet, int rowNum) throws SQLException {
		Person person = new Person();
		person.setPersonid(resultSet.getInt("personid"));
		person.setName(resultSet.getString("name"));
		person.setDateOfBirth(resultSet.getInt("dateofbirth"));
		
		return person;
	}
}
