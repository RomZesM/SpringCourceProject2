package myCRUDappRMZ.dao;

import myCRUDappRMZ.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BookDAO {
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public BookDAO(JdbcTemplate jdbcTemplate){
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public List<Book> index(){
		
		return jdbcTemplate.query("SELECT * FROM Book", new BookMapper());
	}
}
