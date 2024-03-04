package by.bsuir.beerCompany.repo;

import by.bsuir.beerCompany.entity.Purchases;
import by.bsuir.beerCompany.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface PurchaseRepository extends JpaRepository<Purchases, Long> {
    Optional<Purchases> findByDate(Date date);
    Optional<Purchases> findByDestination(String destination);
    List<Purchases> findByUser(Users user);
//    Optional<Purchases> findByUser(Users user);
}
