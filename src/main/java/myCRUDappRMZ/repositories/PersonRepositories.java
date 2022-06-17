package myCRUDappRMZ.repositories;


import myCRUDappRMZ.model.Book;
import myCRUDappRMZ.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonRepositories extends JpaRepository<Person, Integer> {

public List<Book> findAllByPersonid(int Personid);

}
