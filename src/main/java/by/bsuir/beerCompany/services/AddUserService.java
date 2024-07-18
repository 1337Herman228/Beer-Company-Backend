package by.bsuir.beerCompany.services;

import by.bsuir.beerCompany.dao.*;
import by.bsuir.beerCompany.entity.*;
import by.bsuir.beerCompany.mappers.*;
import by.bsuir.beerCompany.repo.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    public boolean addAccount(String requestBody) throws JsonProcessingException{
        ObjectMapper objectMapper = new ObjectMapper();
        AccountDao accObj = objectMapper.readValue(requestBody, AccountDao.class);
        try{
            userRepository.findByLogin(accObj.getLogin()).orElseThrow();
        }catch (Exception e){
            Person person = PersonMapper.INSTANCE.toEntity(accObj);

            Users user = UserMapper.INSTANCE.toEntity(accObj);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
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
            Category category = CategoryMapper.INSTANCE.toEntity(newCategoryObj);
            categoryRepository.save(category);
            return true;
        }
        return false;
    }

    public boolean addDrink(String requestBody) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        DrinkDao newDrinkObj = objectMapper.readValue(requestBody, DrinkDao.class);
        try{
            drinkRepository.findByDrinkName(newDrinkObj.getDrinkName()).orElseThrow();
        }catch (Exception e){
            Drink drink = DrinkMapper.INSTANCE.toEntity(newDrinkObj);
            drinkRepository.save(drink);
            return true;
        }
        return false;
    }

    public boolean addProduct(String requestBody) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        ProductDao newProductObj = objectMapper.readValue(requestBody, ProductDao.class);
        try{
            productRepository.findByDrink(drinkRepository.findById(newProductObj.getDrinkId()).orElseThrow()).orElseThrow();
        }catch (Exception e){

            Product product = ProductMapper.INSTANCE.toEntity(newProductObj);
            product.setDrink(drinkRepository.findById(newProductObj.getDrinkId()).orElseThrow());
            product.setCategory(categoryRepository.findById(newProductObj.getCategoryId()).orElseThrow());

            productRepository.save(product);
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

    public void deleteDrink(Long drinkID){
        Drink drink = drinkRepository.findById(drinkID).orElseThrow();
        Optional<Product> product = productRepository.findByDrink(drink);
        product.ifPresent(value -> productRepository.delete(value));
        drinkRepository.delete(drink);
    }

    public void deleteProduct(Long productID){
        Product product = productRepository.findById(productID).orElseThrow();
        productRepository.delete(product);
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

    public void editDrink(String requestBody, Long drinkID) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        DrinkDao drinkObj = objectMapper.readValue(requestBody, DrinkDao.class);
        Drink drink = drinkRepository.findById(drinkID).orElseThrow();
        drink.setDrinkName(drinkObj.getDrinkName());
        drink.setDescription(drinkObj.getDescription());
        drink.setShortDescription(drinkObj.getShortDescription());
        drink.setCompound(drinkObj.getCompound());
        drink.setImage(drinkObj.getImage());
        drinkRepository.save(drink);
    }

    public boolean editProduct(String requestBody, Long productID) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        ProductDao productObj = objectMapper.readValue(requestBody, ProductDao.class);

        List<Product> products = productRepository.findAll();
        for (Product product : products) {
            if (product.getDrink().getDrinkId() == productObj.getDrinkId() && product.getProductId() != productID) {
                return false;
            }
        }
        Product product = productRepository.findById(productID).orElseThrow();
        product.setDrink(drinkRepository.findById(productObj.getDrinkId()).orElseThrow());
        product.setCategory(categoryRepository.findById(productObj.getCategoryId()).orElseThrow());
        product.setPossibleVolume(productObj.getVolume());
        product.setPrice(productObj.getPrice());
        product.setQuantityLeft(productObj.getQuantity());
        productRepository.save(product);
        return true;
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
