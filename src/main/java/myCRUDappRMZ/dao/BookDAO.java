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
	
	public void add(Book book){
		
		jdbcTemplate.update("INSERT INTO Book (title, author, year) VALUES (?, ?, ?)",
				book.getTitle(),
				book.getAuthor(),
				book.getYear());
	}
	
	public Book show(int bookid){
		return jdbcTemplate.query("SELECT * FROM Book WHERE bookid=?", new BookMapper(), bookid).
				stream().findAny().orElse(null);
		
	}
	
	public void update(int id, Book book){
		jdbcTemplate.update("UPDATE Book SET title=?, author=?, year=? WHERE bookid=?",
				book.getTitle(),
				book.getAuthor(),
				book.getYear(),
				id);
	}
	
	public void delete(int bookid){
		jdbcTemplate.update("DELETE FROM Book WHERE bookid=?", bookid);
	}
	
	public void setOwner(int bookid, int personOwnerId){
		jdbcTemplate.update("UPDATE Book SET personid=? WHERE bookid=?", personOwnerId, bookid);
	}
}
