package by.bsuir.beerCompany.controllers;

import by.bsuir.beerCompany.dao.NewUserDao;
import by.bsuir.beerCompany.entity.Users;
import by.bsuir.beerCompany.services.AddUserService;
import by.bsuir.beerCompany.services.JSONConvertingService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1/apps")
public class AppController {

    @Autowired
    JSONConvertingService jsonConvertingService;

    @Autowired
    AddUserService addNewUser;

//    public void yourMethod(HttpServletRequest request) {
//        HttpSession session = request.getSession();
//        String sessionId = session.getId();
//        // Используйте sessionId по вашему усмотрению
//    }


    @GetMapping("/welcome")
    public String welcome(
//            @CookieValue("JSESSIONID") String jsessionId
    ) {
//        System.out.println(jsessionId);

        Users us1 = new Users();
        us1.setLogin("admin");
        us1.setPassword("admin");

        Users us2 = new Users();
        us2.setLogin("user");
        us2.setPassword("user");

        List<Users> users = List.of(us1, us2);

        return jsonConvertingService.convertObjectToJson(users);
    }

    @GetMapping("/all-app")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public String allApp() {
        Users us1 = new Users();
        us1.setLogin("admin");
        us1.setPassword("admin");

        Users us2 = new Users();
        us2.setLogin("user");
        us2.setPassword("user");

        List<Users> users = List.of(us1, us2);

        return jsonConvertingService.convertObjectToJson(users);
//        return "all-app";
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

    @GetMapping("/login")
    String login() {
        return "login";
    }

}
