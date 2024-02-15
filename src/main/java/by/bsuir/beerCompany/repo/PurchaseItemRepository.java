package by.bsuir.beerCompany.repo;

import by.bsuir.beerCompany.entity.PurchaseItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseItemRepository extends JpaRepository<PurchaseItem, Long> {
}
