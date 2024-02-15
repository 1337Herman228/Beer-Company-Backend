package by.bsuir.beerCompany.services;

import by.bsuir.beerCompany.dao.NewUserDao;
import by.bsuir.beerCompany.entity.Person;
import by.bsuir.beerCompany.entity.Roles;
import by.bsuir.beerCompany.entity.Users;
import by.bsuir.beerCompany.repo.PersonRepository;
import by.bsuir.beerCompany.repo.RolesRepository;
import by.bsuir.beerCompany.repo.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AddUserService {

    private UserRepository userRepository;
    private RolesRepository rolesRepository;
    private PersonRepository personRepository;

    private PasswordEncoder passwordEncoder;

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
}
