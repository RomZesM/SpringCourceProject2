package myCRUDappRMZ.repositories;

import myCRUDappRMZ.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookInterface extends JpaRepository<Book, Integer> {
}
