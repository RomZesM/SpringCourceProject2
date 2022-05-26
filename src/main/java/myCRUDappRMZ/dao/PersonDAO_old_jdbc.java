package myCRUDappRMZ.dao;

import myCRUDappRMZ.model.Person;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class PersonDAO_old_jdbc {
	public static int PEOPLE_COUNT;
	//url address, user and password for connecting to db
	public static final String URL = "jdbc:postgresql://localhost:5432/first_db";
	public static final String USERNAME = "postgres";
	public static final String PASSWORD = "12345678";
	
	//create object Connection class,
	private static Connection connection;
	//init our connection in static field
	static {
		try{
			//? with the help of reflection check if driver is in memomry
			Class.forName("org.postgresql.Driver");
		}
		catch (ClassNotFoundException e){e.printStackTrace();}
		try{
			//establish a connection with db
			connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
		}
		catch (SQLException e){e.printStackTrace();}
	}
	
	
	//method to show all list (with the help of model and thymeleaf)
	public List<Person> index(){
		List<Person> personList = new ArrayList<>();
		try{
			//statement object we use for sql query
			Statement statement = connection.createStatement();
			//get result of the query into the special class object resultSet;
			ResultSet resultSet = statement.executeQuery("SELECT * FROM Person");
			while(resultSet.next()){
				Person person = new Person();
				person.setId(resultSet.getInt("id"));
				person.setName(resultSet.getString("name"));
				person.setEmail(resultSet.getString("email"));
				person.setAge(resultSet.getInt("age"));
				//put an object in list and sent it to Spring
				personList.add(person);
			}
		}
		catch(SQLException e){e.printStackTrace();}
		//get all the fields from result set and put it into Person object
		
		return personList;
	}
	
	//method to show 1 person with certain index (return Person.Object)\
	public Person show (int id){
		Person person = null;
		try{
			PreparedStatement preparedStatement = connection.prepareStatement(
			"SELECT * FROM Person WHERE id=?");//here we can have some people with the same id (see "save" method)
													//so we need to get all object with that id
			//we get answer in ResultSet object, there ara all People obj, iterate with "NEXT"
			preparedStatement.setInt(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();
			//here we will return only first object, because in real DB id is uniq
			person = new Person();
			//get the first object
			resultSet.next();
			//fill Person fields
			person.setId(resultSet.getInt("id"));
			person.setAge(resultSet.getInt("age"));
			person.setName(resultSet.getString("name"));
			person.setEmail(resultSet.getString("email"));
			System.out.println(person.getName());
		}
		catch (SQLException e){e.printStackTrace();}
		//we can use cycle in here
		//return personList.stream().filter(p->p.getId() == ind).findAny().orElse(null);
		return  person;
	}
	
	//we get an information from controller <- html-form <- user;
	public void save(Person person)
	{
		try{
			PreparedStatement preparedStatement = connection.prepareStatement(
					"INSERT INTO Person VALUES(1, ?, ?, ?)");
			//replace "?" with data from user (from person object)
			preparedStatement.setString(1, person.getName());
			preparedStatement.setInt(2, person.getAge());
			preparedStatement.setString(3, person.getEmail());
			//update data in database
			preparedStatement.executeUpdate();
			
		}
		catch(SQLException e){e.printStackTrace();}
		/** unsafe using of sql query with data from user
		 * -----------------------------------------------
		 * Statement statement = connection.createStatement();
		 * 			//create SQL update query
		 * 			String SQL = "INSERT INTO Person VALUES("+ 1 + ",'" + person.getName()
		 * 			+ "'," + person.getAge() + ",'" + person.getEmail() + "')";
		 * 			//executeUpdate create new entry in db table
		 * 			statement.executeUpdate(SQL);
		 * -------------------------------------------------
		 * */
		
		
	}
	
	public void update(int id, Person person){
		try{
			PreparedStatement preparedStatement = connection.prepareStatement(
					"UPDATE Person SET name=?, age=?, email=? WHERE id=?");
			preparedStatement.setString(1, person.getName());
			preparedStatement.setInt(2, person.getAge());
			preparedStatement.setString(3, person.getEmail());
			preparedStatement.setInt(4, id);
			
			preparedStatement.executeUpdate();
			
		}
		catch (SQLException e){e.printStackTrace();}
		
	}
	
	public void delete(int id){
		try {
			PreparedStatement preparedStatement =
					connection.prepareStatement("DELETE FROM Person WHERE id=?");
			preparedStatement.setInt(1, id);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
	}
}
