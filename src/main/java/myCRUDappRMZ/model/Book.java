package myCRUDappRMZ.model;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

public class Book {
	private int bookid;
	
	private int personOwnerId;
	@NotBlank(message = "Every book has a title.")
	private String title;
	@NotBlank(message = "You need to enter the author of the book")
	private String author;
	@Min(value=1900, message="Need to be between 1900 and 2022")
	private int year;
	
	public Book(){
	
	}
	
	
	
	public Book(String title, String author, int year, int bookid){
		this.author = author;
		this.title = title;
		this.year = year;
		this.bookid = bookid;
	}
	
	public String getTitle() {
		return this.title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getAuthor() {
		return author;
	}
	
	public void setAuthor(String author) {
		this.author = author;
	}
	
	public int getYear() {
		return year;
	}
	
	public void setYear(int year) {
		this.year = year;
	}
	
	public void setBookid(int bookid){
		this.bookid = bookid;
	}
	
	public int getBookid(){
		return bookid;
	}
	
	public int getPersonOwnerId() {
		return personOwnerId;
	}
	
	public void setPersonOwnerId(int personOwnerId) {
		this.personOwnerId = personOwnerId;
	}
	
	@Override
	public String toString() {
		return "Book{" +
				"bookid=" + bookid +
				", title='" + title + '\'' +
				", author='" + author + '\'' +
				", year=" + year +
				"peronOwner" + personOwnerId +
				'}';
	}
}
