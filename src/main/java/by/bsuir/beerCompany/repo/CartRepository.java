package by.bsuir.beerCompany.repo;

import by.bsuir.beerCompany.entity.Cart.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
}