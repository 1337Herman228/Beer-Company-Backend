package by.bsuir.beerCompany.repo;

import by.bsuir.beerCompany.entity.Cart.Cart;
import by.bsuir.beerCompany.entity.PurchaseItem;
import by.bsuir.beerCompany.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    List<Cart> findByUser(Users user);
    Optional<Cart> findByPurchaseItem(PurchaseItem purchaseItem);
}