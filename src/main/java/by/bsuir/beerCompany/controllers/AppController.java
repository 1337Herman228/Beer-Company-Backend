package by.bsuir.beerCompany.controllers;

import by.bsuir.beerCompany.dao.NewUserDao;
import by.bsuir.beerCompany.entity.Users;
import by.bsuir.beerCompany.services.AddUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/apps")
public class AppController {

    @Autowired
    AddUserService addNewUser;

    @GetMapping("/welcome")
    public String welcome() {
        return "Welcome";
    }

    @GetMapping("/all-app")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public String allApp() {
        return "all-app";
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public int app(@PathVariable int id) {
        return id;
    }

    @PostMapping("/new-user")
    public String addUser(@RequestBody NewUserDao newUser) {
        addNewUser.addUser(newUser);
        return "User added";
    }

}
