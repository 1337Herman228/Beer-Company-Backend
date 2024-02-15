package by.bsuir.beerCompany.repo;

import by.bsuir.beerCompany.entity.Person;
import by.bsuir.beerCompany.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {
}
