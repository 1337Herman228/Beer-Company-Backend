package by.bsuir.beerCompany.controllers;


import by.bsuir.beerCompany.dao.AccountDao;
import by.bsuir.beerCompany.dao.CategoryDao;
import by.bsuir.beerCompany.repo.CategoryRepository;
import by.bsuir.beerCompany.repo.PersonRepository;
import by.bsuir.beerCompany.repo.RolesRepository;
import by.bsuir.beerCompany.repo.UserRepository;
import by.bsuir.beerCompany.services.AddUserService;
import by.bsuir.beerCompany.services.JSONConvertingService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/admin")
public class AdminPagesController {

    @Autowired
    JSONConvertingService jsonConvertingService;

    @Autowired
    RolesRepository rolesRepository;
    @Autowired
    PersonRepository personRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    AddUserService userService;
    @Autowired
    CategoryRepository categoryRepository;

    @GetMapping("/roles")
//    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String roles() {
        return jsonConvertingService.convertObjectToJson(rolesRepository.findAll());
    }

    @GetMapping("/accounts")
//    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String accounts() {
        return jsonConvertingService.convertObjectToJson(userService.getAccounts());
    }

    @GetMapping("/categories")
//    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String categories() {
        return jsonConvertingService.convertObjectToJson(categoryRepository.findAll());
    }

    @GetMapping("/role/{roleID}")
//    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String getRoleByID( @PathVariable Long roleID) {
        return jsonConvertingService.convertObjectToJson(rolesRepository.findById(roleID).orElseThrow());
    }

    @GetMapping("/account/{userID}")
//    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String getAccountByID( @PathVariable Long userID) {
        AccountDao account =  userService.getAccount(userID);
        return jsonConvertingService.convertObjectToJson(account);
    }

    @GetMapping("/category/{categoryID}")
//    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String getCategoryByID( @PathVariable Long categoryID) {
        return jsonConvertingService.convertObjectToJson(categoryRepository.findById(categoryID).orElseThrow());
    }


    //    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/del-role/{roleID}")
//    @PreAuthorize("hasAuthority('ROLE_ADMIN')")   
    public String deleteRole( @PathVariable Long roleID) {
        userService.deleteRole(roleID);
        return jsonConvertingService.convertObjectToJson(rolesRepository.findAll());
    }

    @PostMapping("/del-account/{userID}")
//    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String deleteAccount( @PathVariable Long userID) {
        userService.deleteAccount(userID);
        return jsonConvertingService.convertObjectToJson(userService.getAccounts());
    }

    @PostMapping("/del-category/{categoryID}")
//    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String deleteCategory( @PathVariable Long categoryID) {
        userService.deleteCategory(categoryID);
        return jsonConvertingService.convertObjectToJson(categoryRepository.findAll());
    }

    @PostMapping("/add-new-role")
    public ResponseEntity<String> addNewRole( @RequestBody String requestBody) throws JsonProcessingException {
        userService.addRole(requestBody);
        return ResponseEntity.ok("Request body received");
    }

    @PostMapping("/add-new-account")
    public String addNewAccount( @RequestBody String requestBody) throws JsonProcessingException {
        boolean result = userService.addAccount(requestBody);
        return result+"";
    }

    @PostMapping("/add-new-category")
    public String addNewCategory( @RequestBody String requestBody) throws JsonProcessingException {
        boolean result = userService.addCategory(requestBody);
        return result+"";
    }

    @PostMapping("/edit-role/{roleID}")
    public ResponseEntity<String> editRole(@PathVariable Long roleID,@RequestBody String requestBody) throws JsonProcessingException {
        userService.editRole(requestBody, roleID);
        return ResponseEntity.ok("Request body received");
    }

    @PostMapping("/edit-account/{userID}")
    public ResponseEntity<String> editAccount(@PathVariable Long userID,@RequestBody String requestBody) throws JsonProcessingException {
        userService.editAccount(requestBody, userID);
        return ResponseEntity.ok("Request body received");
    }

    @PostMapping("/edit-category/{categoryID}")
    public ResponseEntity<String> editCategory(@PathVariable Long categoryID,@RequestBody String requestBody) throws JsonProcessingException {
        userService.editCategory(requestBody, categoryID);
        return ResponseEntity.ok("Request body received");
    }
}
