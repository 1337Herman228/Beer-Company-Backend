package by.bsuir.beerCompany.repo;

import by.bsuir.beerCompany.entity.Purchases;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseRepository extends JpaRepository<Purchases, Long> {
}
