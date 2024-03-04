package by.bsuir.beerCompany.repo;

import by.bsuir.beerCompany.entity.Purchases;
import by.bsuir.beerCompany.entity.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RolesRepository extends JpaRepository<Roles, Long> {
    Optional<Roles> findByPosition(String position);
}
