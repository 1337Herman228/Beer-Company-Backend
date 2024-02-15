package by.bsuir.beerCompany.repo;

import by.bsuir.beerCompany.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
