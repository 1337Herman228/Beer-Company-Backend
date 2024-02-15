package by.bsuir.beerCompany.services;

import by.bsuir.beerCompany.entity.Users;
import by.bsuir.beerCompany.repo.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AddUserService {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public void addUser(Users user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }
}
