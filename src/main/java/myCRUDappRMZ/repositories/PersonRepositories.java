package myCRUDappRMZ.repositories;


import myCRUDappRMZ.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PersonRepositories extends JpaRepository<Person, Integer> {



}
