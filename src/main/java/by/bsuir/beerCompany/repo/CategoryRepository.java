package by.bsuir.beerCompany.repo;

import by.bsuir.beerCompany.entity.Category;
import by.bsuir.beerCompany.entity.Drink;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByCategoryName(String categoryName);
}
