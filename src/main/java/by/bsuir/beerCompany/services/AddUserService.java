package by.bsuir.beerCompany.services;

import by.bsuir.beerCompany.dao.AccountDao;
import by.bsuir.beerCompany.dao.CategoryDao;
import by.bsuir.beerCompany.dao.NewRoleDao;
import by.bsuir.beerCompany.dao.NewUserDao;
import by.bsuir.beerCompany.entity.*;
import by.bsuir.beerCompany.repo.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class AddUserService {

    private UserRepository userRepository;
    private RolesRepository rolesRepository;
    private PersonRepository personRepository;
    private PurchaseRepository purchaseRepository;
    private PurchaseItemRepository purchaseItemRepository;
    private CategoryRepository categoryRepository;
    private ProductRepository productRepository;
    private DrinkRepository drinkRepository;

    private PasswordEncoder passwordEncoder;

// How to add new user with Postman

//    localhost:8080/api/v1/apps/new-user
//    {
//            "login": "user",
//            "password": "user",
//            "name": "name",
//            "surname": "surname",
//            "phone": "phone",
//            "email": "email"
//    }


    public void addUser(NewUserDao newUser){

        Roles role = new Roles();
        role.setPosition(newUser.getPosition());
        rolesRepository.save(role);

        Person person = new Person();
        person.setName(newUser.getName());
        person.setSurname(newUser.getSurname());
        person.setPhone(newUser.getPhone());
        person.setEmail(newUser.getEmail());
        personRepository.save(person);

        Users user = new Users();
        user.setLogin(newUser.getLogin());
        user.setPassword(passwordEncoder.encode(newUser.getPassword()));
        user.setRoles(role);
        user.setPerson(person);
        userRepository.save(user);
    }

    public boolean addAccount(String requestBody) throws JsonProcessingException{
        ObjectMapper objectMapper = new ObjectMapper();
        AccountDao accObj = objectMapper.readValue(requestBody, AccountDao.class);
        try{
            userRepository.findByLogin(accObj.getLogin()).orElseThrow();
        }catch (Exception e){
            Person person = new Person();
            person.setName(accObj.getName());
            person.setSurname(accObj.getSurname());
            person.setPhone(accObj.getPhone());
            person.setEmail(accObj.getEmail());
            Users user = new Users();
            user.setLogin(accObj.getLogin());
            user.setPassword(passwordEncoder.encode(accObj.getPassword()));
            user.setPerson(person);
            user.setRoles(rolesRepository.findByPosition(accObj.getPosition()).orElseThrow());
            personRepository.save(person);
            userRepository.save(user);
            return true;
        }
        return false;
    }

    public boolean addCategory(String requestBody) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        CategoryDao newCategoryObj = objectMapper.readValue(requestBody, CategoryDao.class);
        try{
            categoryRepository.findByCategoryName(newCategoryObj.getCategoryName()).orElseThrow();
        }catch (Exception e){
            Category category = new Category();
            category.setCategoryName(newCategoryObj.getCategoryName());
            category.setCategoryDescription(newCategoryObj.getCategoryDescription());
            categoryRepository.save(category);
            return true;
        }
        return false;
    }

    public AccountDao getAccount(Long userID){
        Users user = userRepository.findById(userID).orElseThrow();
        return fullAccountDaoObj(user);
    }

    public AccountDao fullAccountDaoObj(Users user){
        AccountDao el = new AccountDao();
        el.setPosition(rolesRepository.findById(user.getRoles().getRoleId()).orElseThrow().getPosition());
        el.setLogin(user.getLogin());
        el.setUserId(user.getUserId());
        el.setName(personRepository.findById(user.getPerson().getPersonId()).orElseThrow().getName());
        el.setSurname(personRepository.findById(user.getPerson().getPersonId()).orElseThrow().getSurname());
        el.setPhone(personRepository.findById(user.getPerson().getPersonId()).orElseThrow().getPhone());
        el.setEmail(personRepository.findById(user.getPerson().getPersonId()).orElseThrow().getEmail());
        return el;
    }

    public void deleteRole(Long roleID){
        List<Users> users = userRepository.findAll();
        for (Users user : users) {
            if (user.getRoles().getRoleId() == roleID) {

                try{
                    List<Purchases> purchase = purchaseRepository.findByUser(user);
                    List<PurchaseItem> purchaseItems = purchaseItemRepository.findByPurchase(purchase.get(0));
                    purchaseItemRepository.deleteAllInBatch(purchaseItems);
                    purchaseRepository.delete(purchase.get(0));
                }
                catch (Exception e){
                    //todo:relax
                }
                userRepository.delete(user);
                Person person = personRepository.findById(user.getPerson().getPersonId()).orElseThrow();
                personRepository.delete(person);
            }
        }
        rolesRepository.delete(rolesRepository.findById(roleID).orElseThrow());
    }

    public void deleteAccount(Long userID){
        Users user = userRepository.findById(userID).orElseThrow();
        Person person = personRepository.findById(user.getPerson().getPersonId()).orElseThrow();
        List<Purchases> purchases = purchaseRepository.findByUser(user);
        purchaseItemRepository.deleteAllById(purchases.stream().map(Purchases::getPurchaseId).toList());
        purchaseRepository.deleteAll(purchases);
        userRepository.delete(user);
        personRepository.delete(person);
    }

    public void deleteCategory(Long categoryID){
        List<Product> products = productRepository.findAll();
        for (Product product : products) {
            if (product.getCategory().getCategoryId() == categoryID) {
                try{
                    List<PurchaseItem> purchaseItems = purchaseItemRepository.findByProduct(product);
                    List<Purchases> purchases = new ArrayList<>();
                    for (PurchaseItem purchaseItem : purchaseItems) {
                        purchases.add(purchaseItem.getPurchase());
                        purchaseItemRepository.delete(purchaseItem);
                    }
                    purchaseRepository.deleteAll(purchases);

                    productRepository.delete(product);
                    Drink drink = drinkRepository.findById(product.getDrink().getDrinkId()).orElseThrow();
                    drinkRepository.delete(drink);
                }
                catch (Exception e){
                    //todo:relax
                }

            }
        }
        categoryRepository.delete(categoryRepository.findById(categoryID).orElseThrow());
    }

    public void addRole(String requestBody) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        NewRoleDao roleObj = objectMapper.readValue(requestBody, NewRoleDao.class);
        Roles role = new Roles();
        role.setPosition(roleObj.getPosition());
        rolesRepository.save(role);
    }

    public void editRole(String requestBody, Long roleID) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        NewRoleDao roleObj = objectMapper.readValue(requestBody, NewRoleDao.class);
        Roles role = rolesRepository.findById(roleID).orElseThrow();
        role.setPosition(roleObj.getPosition());
        rolesRepository.save(role);
    }

    public void editCategory(String requestBody, Long categoryID) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        CategoryDao categoryObj = objectMapper.readValue(requestBody, CategoryDao.class);
        Category category = categoryRepository.findById(categoryID).orElseThrow();
        category.setCategoryName(categoryObj.getCategoryName());
        category.setCategoryDescription(categoryObj.getCategoryDescription());
        categoryRepository.save(category);
    }

    public void editAccount(String requestBody, Long userID) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        AccountDao accObj = objectMapper.readValue(requestBody, AccountDao.class);
        Users user = userRepository.findById(userID).orElseThrow();
        user
            .setRoles(rolesRepository.findByPosition(accObj.getPosition()).orElseThrow())
            .setLogin(accObj.getLogin())
            .setPassword(passwordEncoder.encode(accObj.getPassword()));
        Person person = personRepository.findById(user.getPerson().getPersonId()).orElseThrow();
        person
            .setName(accObj.getName())
            .setSurname(accObj.getSurname())
            .setPhone(accObj.getPhone())
            .setEmail(accObj.getEmail());
        userRepository.save(user);
        personRepository.save(person);
    }

    public List<AccountDao> getAccounts(){
        List<Users> allUsers= userRepository.findAll();
        List<AccountDao> allAccounts  = new ArrayList<>();

        for(Users user: allUsers){
            allAccounts.add(fullAccountDaoObj(user));
        }

        return allAccounts;
    }
}
