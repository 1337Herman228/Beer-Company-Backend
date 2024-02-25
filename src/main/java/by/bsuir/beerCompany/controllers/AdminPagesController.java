package by.bsuir.beerCompany.controllers;


import by.bsuir.beerCompany.repo.PersonRepository;
import by.bsuir.beerCompany.repo.RolesRepository;
import by.bsuir.beerCompany.services.JSONConvertingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/admin")
public class AdminPagesController {

    @Autowired
    JSONConvertingService jsonConvertingService;

    @Autowired
    RolesRepository rolesRepository;

    @GetMapping("/roles")
//    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String roles() {
        return jsonConvertingService.convertObjectToJson(rolesRepository.findAll());
    }
}
