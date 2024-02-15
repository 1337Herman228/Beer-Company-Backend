package by.bsuir.beerCompany.repo;

import by.bsuir.beerCompany.entity.Roles;
import by.bsuir.beerCompany.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolesRepository extends JpaRepository<Roles, Long> {
}
