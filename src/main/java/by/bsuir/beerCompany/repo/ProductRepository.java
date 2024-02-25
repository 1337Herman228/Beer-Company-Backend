package by.bsuir.beerCompany.repo;

import by.bsuir.beerCompany.entity.Drink;
import by.bsuir.beerCompany.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByPrice(float price);
}
