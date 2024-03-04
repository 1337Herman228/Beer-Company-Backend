package by.bsuir.beerCompany.repo;

import by.bsuir.beerCompany.entity.Product;
import by.bsuir.beerCompany.entity.PurchaseItem;
import by.bsuir.beerCompany.entity.Purchases;
import by.bsuir.beerCompany.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PurchaseItemRepository extends JpaRepository<PurchaseItem, Long> {

    List<PurchaseItem> findByPurchase(Purchases purchases);
    List<PurchaseItem> findByProduct(Product product);
}
