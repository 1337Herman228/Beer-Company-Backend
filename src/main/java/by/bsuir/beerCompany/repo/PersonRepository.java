package by.bsuir.beerCompany.repo;

import by.bsuir.beerCompany.entity.Drink;
import by.bsuir.beerCompany.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PersonRepository extends JpaRepository<Person, Long> {
    Optional<Person> findByName(String name);
    Optional<Person> findBySurname(String surname);
    Optional<Person> findByPhone(String phone);
    Optional<Person> findByEmail(String email);
}
