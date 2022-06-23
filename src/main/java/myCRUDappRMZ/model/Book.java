package myCRUDappRMZ.model;



import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Entity
@Table(name = "Book")
public class Book {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "bookid")
	private int bookid;
	
	@ManyToOne
	@JoinColumn(name = "personid", referencedColumnName = "personid")
	private Person owner;
	@Column(name = "title")
	@NotBlank(message = "Every book has a title.")
	private String title;
	@Column( name = "author")
	@NotBlank(message = "You need to enter the author of the book")
	private String author;
	@Column(name = "year")
	@Min(value=1900, message="Need to be between 1900 and 2022")
	private int year;
	
	@Column(name = "personid", insertable = false, updatable = false)
	private Integer personid;

	@Column(name = "borrowtime")
	@Temporal(TemporalType.TIMESTAMP)
	private Date borrowtime;
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
	
	public Person getOwner() {
		return owner;
	}
	
	public void setOwner(Person owner) {
		this.owner = owner;
	}
	
	public Integer getPersonid() {
		return personid;
	}
	
	public void setPersonid(int personid) {
		this.personid = personid;
	}
	
	public Date getBorrowtime() {
		return borrowtime;
	}
	
	public void setBorrowtime(Date borrowtime) {
		this.borrowtime = borrowtime;
	}
	
	@Override
	public String toString() {
		return "Book{" +
				"bookid=" + bookid +
				", title='" + title + '\'' +
				", author='" + author + '\'' +
				", year=" + year +
				"peronOwner" + owner +
				'}';
	}
}
