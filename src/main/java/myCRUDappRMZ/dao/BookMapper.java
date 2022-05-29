package myCRUDappRMZ.dao;

import myCRUDappRMZ.model.Book;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BookMapper implements RowMapper<Book> {
	@Override
	public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
		Book book = new Book();
		book.setBookid(rs.getInt("bookid"));
		book.setPersonOwnerId(rs.getInt("personid"));
		book.setTitle(rs.getString("title"));
		book.setAuthor(rs.getString("author"));
		book.setYear(rs.getInt("year"));
		
		return book;
	}
}
