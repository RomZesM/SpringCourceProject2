package myCRUDappRMZ.repositories;

import myCRUDappRMZ.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepositories extends JpaRepository<Book, Integer> {
	
	public Book findByTitle(String title);
	
	List<Book> findAllByPersonid(int personid);
	
	List<Book> findAllByOrderByYearDesc();
	
	List<Book> findByTitleStartingWith(String startingWith);
}
